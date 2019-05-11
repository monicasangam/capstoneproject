package us.analytiq.knime.qvx.reader;

import java.net.MalformedURLException;
import java.net.URL;

import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeLogger;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.util.tokenizer.TokenizerSettings;

import us.analytiq.knime.qvx.jaxb.QvxTableHeader;

public class QvxFileReaderNodeSettings extends TokenizerSettings {

    private static final NodeLogger LOGGER = NodeLogger.getLogger(QvxFileReaderNodeSettings.class);

    private QvxReader qvxReader = null;
    private QvxTableHeader qvxTableHeader =  null;
    
    private URL dataFileLocation = null;

    private String rowHeaderPrefix;
    private boolean uniquifyRowIDs;

    
    public static final String DEF_ROWPREFIX = "Row";
    public static final String CFGKEY_DATAURL = "DataURL";
    


    public QvxFileReaderNodeSettings() throws MalformedURLException {
    	
    }

    public QvxFileReaderNodeSettings(final QvxFileReaderNodeSettings clonee) {
        super(clonee);
        dataFileLocation = clonee.dataFileLocation;
        rowHeaderPrefix = clonee.rowHeaderPrefix;
        uniquifyRowIDs = clonee.uniquifyRowIDs;
    }

    public QvxFileReaderNodeSettings(final NodeSettingsRO cfg)
            throws InvalidSettingsException, MalformedURLException {

        super(cfg);
        if (cfg != null) {
            try {
                dataFileLocation = new URL(cfg.getString(CFGKEY_DATAURL));
            } catch (MalformedURLException mfue) {
            	String errorMessage = "Cannot create URL of data file" + " from '"
                        + cfg.getString(CFGKEY_DATAURL)
                        + "' in filereader config";
                LOGGER.error(errorMessage);
                throw new IllegalArgumentException(errorMessage, mfue);
            } catch (InvalidSettingsException ice) {
            	String errorMessage = "Illegal config object for "
                        + "file reader settings! Key '" + CFGKEY_DATAURL
                        + "' missing!";
            	LOGGER.error(errorMessage);
                throw new InvalidSettingsException(errorMessage, ice);
            }
        }
    }

    @Override
    public void saveToConfiguration(final NodeSettingsWO cfg) {
        if (cfg == null) {
        	String errorMessage = "Can't save 'file reader settings' to null config!";
        	LOGGER.error(errorMessage);
            throw new NullPointerException(errorMessage);
        }

        if (dataFileLocation != null) {
            cfg.addString(CFGKEY_DATAURL, dataFileLocation.toString());
        }
        
        super.saveToConfiguration(cfg);
    }


    public void setDataFileLocationAndUpdateTableName(
            final URL dataFileLocation) {
    	
    	this.dataFileLocation = dataFileLocation;
    }


    public URL getDataFileLocation() {
        return dataFileLocation;
    }

	public void setQvxReader(QvxReader qvxReader) {
		this.qvxReader = qvxReader;		
	}
	
	public QvxReader getQvxReader() {
		return this.qvxReader;		
	}

	public void setQvxTableHeader(QvxTableHeader tableHeader) {
		this.qvxTableHeader = tableHeader;
	}

	public QvxTableHeader getQvxTableHeader() {
		return this.qvxTableHeader;
	}
}
