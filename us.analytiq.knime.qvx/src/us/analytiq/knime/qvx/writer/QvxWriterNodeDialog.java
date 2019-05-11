package us.analytiq.knime.qvx.writer;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.knime.core.data.DataTableSpec;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeLogger;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.NotConfigurableException;
import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;
import org.knime.core.node.util.FilesHistoryPanel;
import org.knime.core.node.util.FilesHistoryPanel.LocationValidation;
import org.knime.core.node.workflow.FlowVariable;

import us.analytiq.knime.qvx.writer.QvxWriterNodeSettings.OverwritePolicy;

import static us.analytiq.knime.qvx.writer.QvxWriterUtil.removeSuffix;
import static us.analytiq.knime.qvx.writer.QvxWriterUtil.toTitleCase;

/**
 * <code>NodeDialog</code> for the "QvxWriter" Node.
 * 
 *
 * This node dialog derives from {@link DefaultNodeSettingsPane} which allows
 * creation of a simple dialog with standard components. If you need a more 
 * complex dialog please derive directly from 
 * {@link org.knime.core.node.NodeDialogPane}.
 * 
 * @author 
 */
public class QvxWriterNodeDialog extends NodeDialogPane {
	
    public static final NodeLogger LOGGER = NodeLogger.getLogger(QvxWriterNodeModel.class);

	//settingsPanel and all of its sub-components
	private final JPanel settingsPanel;
	
	private final JPanel filesPanel;
	private final FilesHistoryPanel filesHistoryPanel;
	
	private final TableNamePanel tableNamePanel;
	
	private final JPanel overwritePolicyPanel;
	private final JRadioButton overwritePolicyAbortButton;
	private final JRadioButton overwritePolicyOverwriteButton;
	
	private final AdvancedPanel advancedPanel;
	private final FieldAttrPanel fieldAttributesPanel;
		
	private static final double SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private static final double SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
    protected QvxWriterNodeDialog() {
        super();
            
        // Settings panel
        settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        
        filesPanel = new JPanel();
        filesPanel.setBorder(new TitledBorder("Output Location"));
        filesHistoryPanel = new FilesHistoryPanel(
        		createFlowVariableModel("CFGKEY_FILE", FlowVariable.Type.STRING),
        		"History ID", LocationValidation.FileOutput, ".qvx");
        filesHistoryPanel.setDialogTypeSaveWithExtension(".qvx");
        filesPanel.add(filesHistoryPanel);
        
        tableNamePanel = new TableNamePanel();
         
        overwritePolicyPanel = new JPanel();
        overwritePolicyPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        overwritePolicyPanel.setBorder(new TitledBorder("If file exists..."));
        overwritePolicyAbortButton = new JRadioButton();
        overwritePolicyAbortButton.setText(OverwritePolicy.ABORT.toString());
        overwritePolicyOverwriteButton = new JRadioButton();
        overwritePolicyOverwriteButton.setText(OverwritePolicy.OVERWRITE.toString());
        overwritePolicyAbortButton.setSelected(true);
        ButtonGroup group = new ButtonGroup();
        group.add(overwritePolicyAbortButton);
        group.add(overwritePolicyOverwriteButton);
        overwritePolicyPanel.add(overwritePolicyAbortButton);
        overwritePolicyPanel.add(overwritePolicyOverwriteButton);
        
        filesHistoryPanel.addChangeListener(new ChangeListener() {
        	@Override
        	public void stateChanged(final ChangeEvent e) {
        		String tableName = filesHistoryPanel.getSelectedFile();
        		File f = new File(tableName);
        		tableName = f.getName();	
        		tableName = removeSuffix(tableName, ".qvx");
        		tableName = toTitleCase(tableName);
        		tableNamePanel.setDefaultName(tableName);
        	}
        });
        
        settingsPanel.add(filesPanel);
        settingsPanel.add(tableNamePanel);
        settingsPanel.add(overwritePolicyPanel);       
        addTab("Settings", settingsPanel);
        
        advancedPanel = new AdvancedPanel();
        addTab("Advanced", advancedPanel);
        
        fieldAttributesPanel = new FieldAttrPanel();
        JScrollPane scrollPane = new JScrollPane(fieldAttributesPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension((int)(SCREEN_WIDTH/5), (int)(SCREEN_HEIGHT/5)));
        addTab("Field Attributes", scrollPane);                
    }
    
	@Override
	protected void saveSettingsTo(NodeSettingsWO settings) throws InvalidSettingsException {

		QvxWriterNodeSettings mSettings = new QvxWriterNodeSettings();
	
		//fileName
		String fileName = filesHistoryPanel.getSelectedFile();
		mSettings.setFileName(fileName);
		
		//overwritePolicy
		OverwritePolicy overwritePolicy = null;
		if (overwritePolicyAbortButton.isSelected()) {
			overwritePolicy = OverwritePolicy.ABORT;
		}else if (overwritePolicyOverwriteButton.isSelected()) {
			overwritePolicy = OverwritePolicy.OVERWRITE;
		}
		mSettings.setOverwritePolicy(overwritePolicy);
		
		advancedPanel.saveSettingsInto(mSettings);
		fieldAttributesPanel.saveSettingsInto(mSettings);
		tableNamePanel.saveSettingsInto(mSettings);
		
		mSettings.saveSettingsTo(settings);
	}
	
	@Override
	protected void loadSettingsFrom(final NodeSettingsRO settings,
            final DataTableSpec[] specs) throws NotConfigurableException {
		
		try {
			//fileName
			String fileName = settings.getString(QvxWriterNodeSettings.CFGKEY_FILE_NAME);
			String overwritePolicy = settings.getString(
					QvxWriterNodeSettings.CFGKEY_OVERWRITE_POLICY);			

			filesHistoryPanel.setSelectedFile(fileName);
			
			//overwritePolicy
			if (overwritePolicy.equals(OverwritePolicy.ABORT.toString())){
				overwritePolicyAbortButton.setSelected(true);
			}else if (overwritePolicy.equals(OverwritePolicy.OVERWRITE.toString())) {
				overwritePolicyOverwriteButton.setSelected(true);
			}
			
			advancedPanel.loadValuesIntoPanel(settings);
			fieldAttributesPanel.loadValuesIntoPanel(settings, specs[0]);
			tableNamePanel.loadValuesIntoPanel(settings);
		} catch (InvalidSettingsException e) {
			LOGGER.warn("Error loading settings");
		}
	}
}
