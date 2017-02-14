package org.net.perorin.aknEditor.etc;

import java.awt.Component;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

public class FileTreeCellRenderer extends DefaultTreeCellRenderer {
	private final TreeCellRenderer renderer;
	private final FileSystemView fileSystemView;

	public FileTreeCellRenderer(TreeCellRenderer renderer, FileSystemView fileSystemView) {
		super();
		this.renderer = renderer;
		this.fileSystemView = fileSystemView;
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		JLabel c = (JLabel) renderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		if (selected) {
			c.setOpaque(false);
			c.setForeground(getTextSelectionColor());
			//c.setBackground(Color.BLUE); //getBackgroundSelectionColor());
		} else {
			c.setOpaque(true);
			c.setForeground(getTextNonSelectionColor());
			c.setBackground(getBackgroundNonSelectionColor());
		}
		if (value instanceof DefaultMutableTreeNode) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			Object o = node.getUserObject();
			if (o instanceof File) {
				File file = (File) o;
				c.setIcon(fileSystemView.getSystemIcon(file));
				c.setText(fileSystemView.getSystemDisplayName(file));
				c.setToolTipText(file.getPath());
			}
		}
		return c;
	}
}
