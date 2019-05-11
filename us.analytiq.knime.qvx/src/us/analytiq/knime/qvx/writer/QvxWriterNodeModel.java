package us.analytiq.knime.qvx.writer;

import java.io.File;
import java.io.IOException;

import org.knime.core.data.DataTableSpec;
import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.CanceledExecutionException;
import org.knime.core.node.ExecutionContext;
import org.knime.core.node.ExecutionMonitor;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeLogger;
import org.knime.core.node.NodeModel;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.util.CheckUtils;

import us.analytiq.knime.qvx.writer.QvxWriterNodeSettings.OverwritePolicy;

import static us.analytiq.knime.qvx.writer.QvxWriterNodeSettings.CFGKEY_FILE_NAME;
import static us.analytiq.knime.qvx.writer.QvxWriterNodeSettings.CFGKEY_OVERWRITE_POLICY;

/**
 * This is the model implementation of QvxWriter.
 * 
 *
 * @author 
 */
public class QvxWriterNodeModel extends NodeModel {
    
    public static final NodeLogger LOGGER = NodeLogger.getLogger(QvxWriterNodeModel.class);

    private QvxWriterNodeSettings mSettings;
    
    /**
     * Constructor for the node model.
     */
    protected QvxWriterNodeModel() {
    
        // 1 incoming port and 0 outgoing ports
        super(1, 0);
        mSettings = new QvxWriterNodeSettings();
    }

    @Override
    protected BufferedDataTable[] execute(final BufferedDataTable[] inData,
            final ExecutionContext exec) throws InvalidSettingsException {
    	
    	if (!mSettings.getFileName().equals("")) {
    		/*A non-empty file name also implies that every other value in settings is non-empty
    		(which implies that the writeQvxFile has all the necessary settings configured)*/
    		writeQvxFile(inData[0]);
    		return new BufferedDataTable[] {};
    	}else {
    		LOGGER.error("No settings available");
    		return new BufferedDataTable[] {};
    	}
    }
    
    protected void writeQvxFile(final BufferedDataTable table) {
    	
    	QvxWriter qvxWriter = new QvxWriter();
    	String outFileName = mSettings.getFileName();
    	qvxWriter.writeQvxFile(table, outFileName, mSettings);
    }

    @Override
    protected void reset() {
    	// No additional action needs to be taken on reset
    }

    @Override
    protected DataTableSpec[] configure(final DataTableSpec[] inSpecs)
            throws InvalidSettingsException {

    	if (mSettings.getFileName().equals("")) {
            throw new InvalidSettingsException("No settings available");
        }
        return new DataTableSpec[]{null};
    }

    @Override
    protected void saveSettingsTo(final NodeSettingsWO settings) {
    	
        mSettings.saveSettingsTo(settings);
    }
    
    @Override
    protected void loadValidatedSettingsFrom(final NodeSettingsRO settings)
            throws InvalidSettingsException {
        
    	mSettings = new QvxWriterNodeSettings(settings);
    }
    
    @Override
    protected void validateSettings(final NodeSettingsRO settings)
            throws InvalidSettingsException {
            
    	validateFileName(settings);
    }
    
    @Override
    protected void loadInternals(final File internDir,
            final ExecutionMonitor exec) throws IOException,
            CanceledExecutionException {
    	
    	/* Everything handed to output ports is loaded automatically.
    	 * No additional action is necessary.
    	 */
    }
    
    @Override
    protected void saveInternals(final File internDir,
            final ExecutionMonitor exec) throws IOException,
            CanceledExecutionException {
    	
    	/* Everything written to output ports is saved automatically.
    	 * No additional action is necessary.
    	 */
    }
    
    protected void validateFileName(NodeSettingsRO settings) throws InvalidSettingsException {
    	
    	String fileName = settings.getString(CFGKEY_FILE_NAME);
    	String overwritePolicy = settings.getString(CFGKEY_OVERWRITE_POLICY);
    	boolean overwriteFile = overwritePolicy.contentEquals(OverwritePolicy.OVERWRITE.toString());
    	
    	File file = new File(fileName);
    	if (file.isDirectory()) {
    		throw new InvalidSettingsException("The provided file name is a directory");
    	}
    	
    	if(file.exists() && overwritePolicy.equals(OverwritePolicy.ABORT.toString())) {
    		throw new InvalidSettingsException("File already exists");
    	}
    	
    	CheckUtils.checkDestinationFile(fileName, overwriteFile);
    	
    	if(!fileName.endsWith(".qvx")) {
    		throw new InvalidSettingsException("Invalid file extension: \".qvx\" expected");
    	}
    }
}
