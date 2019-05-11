package us.analytiq.knime.qvx.reader;

import org.knime.core.node.NodeView;

/**
 * <code>NodeView</code> for the "Qvx" Node.
 * Qvx Node
 *
 * @author Monica
 */
public class QvxReaderNodeView extends NodeView<QvxReaderNodeModel> {

    /**
     * Creates a new view.
     * 
     * @param nodeModel The model (class: {@link QvxNodeModel})
     */
    protected QvxReaderNodeView(final QvxReaderNodeModel nodeModel) {
        super(nodeModel);

        // No additional action is necessary
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void modelChanged() {

        QvxReaderNodeModel nodeModel = 
            (QvxReaderNodeModel)getNodeModel();
        assert nodeModel != null; 
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onClose() {
    
        // No additional action is necessary
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onOpen() {

        // No additional action is necessary
    }

}

