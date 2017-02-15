package org.net.perorin.aknEditor.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.net.perorin.aknEditor.etc.FileTreeCellRenderer;
import org.net.perorin.aknEditor.etc.FolderWillExpandListener;

import jsyntaxpane.DefaultSyntaxKit;

public class view {

	private JFrame frame;
	private Font sysFont;
	private Font edtFont;
	private Font conFont;
	private JSplitPane splitPane_HORIZONAL;
	private JSplitPane splitPane_VERTICAL;

	private final String TITLE = "茜ちゃんがJava教えてくれるってよ";

	private final File workSpacePath = new File("./workspace");

	private final String iconMainPath = "./META-INF/icon_nc92313.png";
	private final String iconNoticePath = "./META-INF/completions.png";
	private final String iconCopyPath = "./META-INF/copy-to-clipboard.png";
	private final String iconCutPath = "./META-INF/cut-to-clipboard.png";
	private final String iconFindPath = "./META-INF/find.png";
	private final String iconPastePath = "./META-INF/paste-from-clipboard.png";
	private final String iconRedoPath = "./META-INF/redo.png";
	private final String iconSelectAllPath = "./META-INF/select-all.png";
	private final String iconUndoPath = "./META-INF/undo.png";
	private final String iconSavePath = "./META-INF/Save.png";
	private final String iconSaveAsPath = "./META-INF/Save-as-icon.png";
	private final String iconSaveAllPath = "./META-INF/Save-all.png";
	private final String iconNewPath = "./META-INF/ModernXP-26-Filetype-New-icon.png";
	private final String iconOpenPath = "./META-INF/open-file-icon.png";
	private final String iconClosePath = "./META-INF/close.png";
	private final String iconCloseAllPath = "./META-INF/close-all.png";
	private final String iconExitPath = "./META-INF/Close-2-icon.png";
	private final String iconStartPath = "./META-INF/Play-1-Pressed-icon.png";
	private final String iconStopPath = "./META-INF/Stop-icon.png";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					view window = new view();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public view() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		// UIをWindows風に
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}

		// JSyntaxPaneの初期化
		DefaultSyntaxKit.initKit();

		// システムフォントの設定
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String fontfamilys[] = ge.getAvailableFontFamilyNames();
		if (Arrays.asList(fontfamilys).contains("Yu Gothic UI")) {
			sysFont = new Font("Yu Gothic UI", Font.PLAIN, 12);
		} else if (Arrays.asList(fontfamilys).contains("Meiryo UI")) {
			sysFont = new Font("Meiryo UI", Font.PLAIN, 12);
		} else {
			sysFont = new Font("MS UI Gothic", Font.PLAIN, 12);
		}

		// エディターフォントの設定
		edtFont = new Font("MS Gothic", Font.PLAIN, 15);

		// コンソールフォントの設定
		conFont = new Font("メイリオ", Font.PLAIN, 15);

		// フレーム
		frame = new JFrame();
		frame.setBounds(100, 100, 1600, 900);
		frame.setIconImage(new ImageIcon(iconMainPath).getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(TITLE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		initMenuBar();
		initTool();
		initSplitPane();
		initEditor();
		initDirectory();
		initConsole();
	}

	private void initSplitPane() {
		splitPane_HORIZONAL = new JSplitPane();
		splitPane_HORIZONAL.setDividerLocation(1100);
		frame.getContentPane().add(splitPane_HORIZONAL, BorderLayout.CENTER);
		splitPane_VERTICAL = new JSplitPane();
		splitPane_VERTICAL.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_VERTICAL.setDividerLocation(400);
		splitPane_HORIZONAL.setRightComponent(splitPane_VERTICAL);
	}

	private void initMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu menuFile = new JMenu("ファイル");
		menuFile.setFont(sysFont);
		menuBar.add(menuFile);

		JMenuItem menuItemNew = new JMenuItem("新規ファイル");
		menuItemNew.setFont(sysFont);
		menuItemNew.setIcon(new ImageIcon(iconNewPath));
		menuFile.add(menuItemNew);

		JMenuItem menuItemOpenFile = new JMenuItem("ファイルを開く");
		menuItemOpenFile.setFont(sysFont);
		menuItemOpenFile.setIcon(new ImageIcon(iconOpenPath));
		menuFile.add(menuItemOpenFile);

		menuFile.add(Box.createRigidArea(new Dimension(5, 5)));

		JMenuItem menuItemClose = new JMenuItem("閉じる");
		menuItemClose.setFont(sysFont);
		menuItemClose.setIcon(new ImageIcon(iconClosePath));
		menuFile.add(menuItemClose);

		JMenuItem menuItemAllClose = new JMenuItem("すべて閉じる");
		menuItemAllClose.setFont(sysFont);
		menuItemAllClose.setIcon(new ImageIcon(iconCloseAllPath));
		menuFile.add(menuItemAllClose);

		menuFile.add(Box.createRigidArea(new Dimension(5, 5)));

		JMenuItem menuItemSave = new JMenuItem("保存");
		menuItemSave.setFont(sysFont);
		menuItemSave.setIcon(new ImageIcon(iconSavePath));
		menuFile.add(menuItemSave);

		JMenuItem menuItemSaveAs = new JMenuItem("名前を付けて保存");
		menuItemSaveAs.setFont(sysFont);
		menuItemSaveAs.setIcon(new ImageIcon(iconSaveAsPath));
		menuFile.add(menuItemSaveAs);

		JMenuItem menuItemAllSave = new JMenuItem("すべて保存");
		menuItemAllSave.setFont(sysFont);
		menuItemAllSave.setIcon(new ImageIcon(iconSaveAllPath));
		menuFile.add(menuItemAllSave);

		menuFile.add(Box.createRigidArea(new Dimension(5, 5)));

		JMenuItem menuItemExit = new JMenuItem("終了");
		menuItemExit.setFont(sysFont);
		menuItemExit.setIcon(new ImageIcon(iconExitPath));
		menuFile.add(menuItemExit);

		JMenu menuEdit = new JMenu("編集");
		menuEdit.setFont(sysFont);
		menuBar.add(menuEdit);

		JMenuItem menuItemUndo = new JMenuItem("元に戻す");
		menuItemUndo.setFont(sysFont);
		menuItemUndo.setIcon(new ImageIcon(iconUndoPath));
		menuEdit.add(menuItemUndo);

		JMenuItem menuItemRedo = new JMenuItem("やり直し");
		menuItemRedo.setFont(sysFont);
		menuItemRedo.setIcon(new ImageIcon(iconRedoPath));
		menuEdit.add(menuItemRedo);

		menuEdit.add(Box.createRigidArea(new Dimension(5, 5)));

		JMenuItem menuItemCut = new JMenuItem("切り取り");
		menuItemCut.setFont(sysFont);
		menuItemCut.setIcon(new ImageIcon(iconCutPath));
		menuEdit.add(menuItemCut);

		JMenuItem menuItemCopy = new JMenuItem("コピー");
		menuItemCopy.setFont(sysFont);
		menuItemCopy.setIcon(new ImageIcon(iconCopyPath));
		menuEdit.add(menuItemCopy);

		JMenuItem menuItemPaste = new JMenuItem("ペースト");
		menuItemPaste.setFont(sysFont);
		menuItemPaste.setIcon(new ImageIcon(iconPastePath));
		menuEdit.add(menuItemPaste);

		menuEdit.add(Box.createRigidArea(new Dimension(5, 5)));

		JMenuItem menuItemAllSelect = new JMenuItem("すべて選択");
		menuItemAllSelect.setFont(sysFont);
		menuItemAllSelect.setIcon(new ImageIcon(iconSelectAllPath));
		menuEdit.add(menuItemAllSelect);

		JMenuItem menuItemFind = new JMenuItem("検索/置換");
		menuItemFind.setFont(sysFont);
		menuItemFind.setIcon(new ImageIcon(iconFindPath));
		menuEdit.add(menuItemFind);

		JMenu menuHelp = new JMenu("ヘルプ");
		menuHelp.setFont(sysFont);
		menuBar.add(menuHelp);

		JMenuItem menuItemNotice = new JMenuItem("このソフトウェアについて");
		menuItemNotice.setFont(sysFont);
		menuItemNotice.setIcon(new ImageIcon(iconNoticePath));
		menuHelp.add(menuItemNotice);
	}

	private void initDirectory() {
		JPanel directoryPanel = new JPanel();
		splitPane_VERTICAL.setLeftComponent(directoryPanel);
		directoryPanel.setLayout(new BorderLayout(0, 0));

		FileSystemView fileSystemView = FileSystemView.getFileSystemView();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		DefaultTreeModel treeModel = new DefaultTreeModel(root);
		for (File fileSystemRoot : fileSystemView.getFiles(workSpacePath, true)) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(fileSystemRoot);
			root.add(node);
			for (File file : fileSystemView.getFiles(fileSystemRoot, true)) {
				if (file.isDirectory()) {
					node.add(new DefaultMutableTreeNode(file));
				}
			}
		}

		JTree tree = new JTree(treeModel);
		tree.setFont(sysFont);
		tree.addTreeWillExpandListener(new FolderWillExpandListener(fileSystemView));
		tree.setCellRenderer(new FileTreeCellRenderer(tree.getCellRenderer(), fileSystemView));
		directoryPanel.add(new JScrollPane(tree), BorderLayout.CENTER);
	}

	private void initTool() {
		JPanel toolPanel = new JPanel();
		frame.getContentPane().add(toolPanel, BorderLayout.NORTH);
		toolPanel.setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolPanel.add(toolBar);

		JButton btnNew = new JButton(new ImageIcon(iconNewPath));
		btnNew.setToolTipText("新規ファイル");
		toolBar.add(btnNew);

		JButton btnOpen = new JButton(new ImageIcon(iconOpenPath));
		btnOpen.setToolTipText("ファイルを開く");
		toolBar.add(btnOpen);

		toolBar.add(Box.createRigidArea(new Dimension(10, 5)));

		JButton btnClose = new JButton(new ImageIcon(iconClosePath));
		btnClose.setToolTipText("閉じる");
		toolBar.add(btnClose);

		JButton btnCloseAll = new JButton(new ImageIcon(iconCloseAllPath));
		btnCloseAll.setToolTipText("すべて閉じる");
		toolBar.add(btnCloseAll);

		toolBar.add(Box.createRigidArea(new Dimension(10, 5)));

		JButton btnSave = new JButton(new ImageIcon(iconSavePath));
		btnSave.setToolTipText("保存");
		toolBar.add(btnSave);

		JButton btnSaveAs = new JButton(new ImageIcon(iconSaveAsPath));
		btnSaveAs.setToolTipText("名前を付けて保存");
		toolBar.add(btnSaveAs);

		JButton btnSaveAll = new JButton(new ImageIcon(iconSaveAllPath));
		btnSaveAll.setToolTipText("すべて保存");
		toolBar.add(btnSaveAll);

		toolBar.add(Box.createRigidArea(new Dimension(10, 5)));

		JButton btnUndo = new JButton(new ImageIcon(iconUndoPath));
		btnUndo.setToolTipText("元に戻す");
		toolBar.add(btnUndo);

		JButton btnRedo = new JButton(new ImageIcon(iconRedoPath));
		btnRedo.setToolTipText("やり直す");
		toolBar.add(btnRedo);

		toolBar.add(Box.createRigidArea(new Dimension(10, 5)));

		JButton btnCut = new JButton(new ImageIcon(iconCutPath));
		btnCut.setToolTipText("切り取り");
		toolBar.add(btnCut);

		JButton btnCopy = new JButton(new ImageIcon(iconCopyPath));
		btnCopy.setToolTipText("コピー");
		toolBar.add(btnCopy);

		JButton btnPaste = new JButton(new ImageIcon(iconPastePath));
		btnPaste.setToolTipText("貼り付け");
		toolBar.add(btnPaste);

		toolBar.add(Box.createRigidArea(new Dimension(10, 5)));

		JButton btnSelectAll = new JButton(new ImageIcon(iconSelectAllPath));
		btnSelectAll.setToolTipText("すべて選択");
		toolBar.add(btnSelectAll);

		JButton btnFind = new JButton(new ImageIcon(iconFindPath));
		btnFind.setToolTipText("検索/置き換え");
		toolBar.add(btnFind);

		toolBar.add(Box.createRigidArea(new Dimension(10, 5)));

		JButton btnStart = new JButton(new ImageIcon(iconStartPath));
		btnStart.setToolTipText("実行");
		toolBar.add(btnStart);

		JButton btnStop = new JButton(new ImageIcon(iconStopPath));
		btnStop.setToolTipText("停止");
		toolBar.add(btnStop);
	}

	private void initEditor() {
		JPanel editorPanel = new JPanel();
		editorPanel.setLayout(new BorderLayout(0, 0));
		splitPane_HORIZONAL.setLeftComponent(editorPanel);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		editorPanel.add(tabbedPane, BorderLayout.CENTER);

		JEditorPane editorTextArea = new JEditorPane();
		editorTextArea.setBounds(editorPanel.getBounds());

		JScrollPane editorTextAreaScroll = new JScrollPane(editorTextArea);
		editorTextAreaScroll.setBounds(editorPanel.getBounds());

		editorTextArea.setContentType("text/java");
		editorTextArea.setFont(edtFont);

		tabbedPane.addTab("New tab", null, editorTextAreaScroll, null);
	}

	private void initConsole() {
		JPanel consolePanel = new JPanel();
		consolePanel.setLayout(new BorderLayout(0, 0));
		splitPane_VERTICAL.setRightComponent(consolePanel);

		JTextArea consoleTextArea = new JTextArea();
		consoleTextArea.setFont(conFont);
		consoleTextArea.setBounds(consolePanel.getBounds());

		JScrollPane consoleTextAreaScroll = new JScrollPane(consoleTextArea);
		consoleTextAreaScroll.setBounds(consolePanel.getBounds());
		consolePanel.add(consoleTextAreaScroll);
	}

}
