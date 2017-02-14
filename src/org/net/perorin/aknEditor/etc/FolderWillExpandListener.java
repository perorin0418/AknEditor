package org.net.perorin.aknEditor.etc;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;

public class FolderWillExpandListener implements TreeWillExpandListener {

	private final FileSystemView fileSystemView;

	public FolderWillExpandListener(FileSystemView fileSystemView) {
		this.fileSystemView = fileSystemView;
	}

	@Override
	public void treeWillExpand(TreeExpansionEvent treeexpansionevent)
			throws ExpandVetoException {

		final JTree tree = (JTree) treeexpansionevent.getSource();
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeexpansionevent.getPath().getLastPathComponent();
		final DefaultTreeModel model = (DefaultTreeModel) tree.getModel();

		//        final TreePath path = treeexpansionevent.getPath();

		final File parent = (File) node.getUserObject();

		if (!parent.isDirectory())
			return;

		for (int i = 0; i < node.getChildCount(); i++) {

			final DefaultMutableTreeNode newnode = (DefaultMutableTreeNode) node.getChildAt(i);
			final File newFile = (File) newnode.getUserObject();

			if (newnode.getChildCount() > 0)
				continue;

			SwingWorker<String, File> worker = new SwingWorker<String, File>() {

				@Override
				public String doInBackground() {

					File[] children = fileSystemView.getFiles(newFile, true);
					ArrayList<File> sortedChildren = sortFiles(children);
					for (File child : sortedChildren) {
						//                    if(child.isDirectory()){
						publish(child);
						//                    }
						//                        try{
						//                            Thread.sleep(10);
						//                        }catch(InterruptedException ex) {
						//                            ex.printStackTrace();
						//                        }
					}
					return "done";
				}

				@Override
				protected void process(List<File> chunks) {

					ArrayList<File> sortedChunks = sortFiles(chunks);
					for (File file : sortedChunks) {
						newnode.add(new DefaultMutableTreeNode(file));
					}
					model.nodeStructureChanged(newnode);

					//                tree.expandPath( path );
				}

			};
			worker.execute();

		}

	}

	@Override
	public void treeWillCollapse(TreeExpansionEvent treeexpansionevent)
			throws ExpandVetoException {
		// TODO 自動生成されたメソッド・スタブ

	}

	private final ArrayList<File> sortFiles(List<File> orgFiles) {

		File[] files = orgFiles.toArray(new File[0]);
		return sortFiles(files);

	}

	private final ArrayList<File> sortFiles(File[] files) {

		ArrayList<File> ret = new ArrayList<File>();
		ArrayList<File> fileList = new ArrayList<File>();

		Arrays.sort(files);

		for (File f : files) {

			if (f.isDirectory())
				ret.add(f);
			else if (f.isFile())
				fileList.add(f);

		}

		ret.addAll(fileList);

		return ret;
	}

}