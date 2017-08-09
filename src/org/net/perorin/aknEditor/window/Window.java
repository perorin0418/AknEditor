package org.net.perorin.aknEditor.window;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.xml.bind.JAXB;

import org.net.perorin.aknEditor.data.Config;
import org.net.perorin.aknEditor.etc.CloseButtonTabbedPane;
import org.net.perorin.aknEditor.etc.ConsoleTextField;
import org.net.perorin.aknEditor.etc.FileTreeCellRenderer;
import org.net.perorin.aknEditor.etc.FolderWillExpandListener;
import org.net.perorin.aknEditor.model.Model;
import org.net.perorin.toolkit.FileUtils;

import jsyntaxpane.DefaultSyntaxKit;

public class Window {

	private JFrame frame;
	private Font sysFont;
	private Font edtFont;
	private Font conFont;
	private CloseButtonTabbedPane tabbedPane;
	private JSplitPane splitPane_HORIZONAL;
	private JSplitPane splitPane_VERTICAL;
	private ConsoleTextField consoleField;
	private JTextArea consoleTextArea;
	private JScrollPane consoleTextAreaScroll;
	private JTree tree;
	private Config config;
	private Process p;
	private String command;
	private HashMap<JScrollPane, Boolean> isEditMap;
	private HashMap<JScrollPane, JEditorPane> editorMap;

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
	private final String iconTabClosePath = "./META-INF/close-icon.png";

	private final String fontEditorPath = "./META-INF/RictyDiminished-Regular.ttf";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
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
	public Window() {
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
		try {
			edtFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontEditorPath));
		} catch (FontFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		edtFont = edtFont.deriveFont(15.0f);

		// コンソールフォントの設定
		try {
			conFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontEditorPath));
		} catch (FontFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		conFont = edtFont.deriveFont(15.0f);

		// 設定ファイル読み込み
		config = JAXB.unmarshal(new File("./META-INF/config.xml"), Config.class);

		// 編集済フラグを作る
		isEditMap = new HashMap<>();

		// スクロールペーンとエディターペーンを関連付ける
		editorMap = new HashMap<>();

		// フレーム
		frame = new JFrame();
		frame.setBounds(100, 100, 1600, 900);
		frame.setIconImage(new ImageIcon(iconMainPath).getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(TITLE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				beforeClosing();
			}
		});
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		initMenuBar();
		initTool();
		initSplitPane();
		initEditor();
		initDirectory();
		initConsole();

		initDataSet();
	}

	private void initSplitPane() {
		splitPane_HORIZONAL = new JSplitPane();
		splitPane_HORIZONAL.setDividerLocation(1100);
		frame.getContentPane().add(splitPane_HORIZONAL, BorderLayout.CENTER);
		splitPane_VERTICAL = new JSplitPane();
		splitPane_VERTICAL.setOrientation(JSplitPane.VERTICAL_SPLIT);
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
		menuItemNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newFile();
			}
		});
		menuFile.add(menuItemNew);

		JMenuItem menuItemOpenFile = new JMenuItem("ファイルを開く");
		menuItemOpenFile.setFont(sysFont);
		menuItemOpenFile.setIcon(new ImageIcon(iconOpenPath));
		menuItemOpenFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				openFile();
			}
		});
		menuFile.add(menuItemOpenFile);

		menuFile.add(Box.createRigidArea(new Dimension(5, 5)));

		JMenuItem menuItemClose = new JMenuItem("閉じる");
		menuItemClose.setFont(sysFont);
		menuItemClose.setIcon(new ImageIcon(iconClosePath));
		menuItemClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
			}
		});
		menuFile.add(menuItemClose);

		JMenuItem menuItemAllClose = new JMenuItem("すべて閉じる");
		menuItemAllClose.setFont(sysFont);
		menuItemAllClose.setIcon(new ImageIcon(iconCloseAllPath));
		menuItemAllClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.removeAll();
			}
		});
		menuFile.add(menuItemAllClose);

		menuFile.add(Box.createRigidArea(new Dimension(5, 5)));

		JMenuItem menuItemSave = new JMenuItem("保存");
		menuItemSave.setFont(sysFont);
		menuItemSave.setIcon(new ImageIcon(iconSavePath));
		menuItemSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveAction((JScrollPane) tabbedPane.getSelectedComponent(), tabbedPane.getSelectedIndex());
			}
		});
		menuFile.add(menuItemSave);

		JMenuItem menuItemSaveAs = new JMenuItem("名前を付けて保存");
		menuItemSaveAs.setFont(sysFont);
		menuItemSaveAs.setIcon(new ImageIcon(iconSaveAsPath));
		menuItemSaveAs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveAsAction((JScrollPane) tabbedPane.getSelectedComponent(), tabbedPane.getSelectedIndex());
			}
		});
		menuFile.add(menuItemSaveAs);

		JMenuItem menuItemAllSave = new JMenuItem("すべて保存");
		menuItemAllSave.setFont(sysFont);
		menuItemAllSave.setIcon(new ImageIcon(iconSaveAllPath));
		menuFile.add(menuItemAllSave);

		menuFile.add(Box.createRigidArea(new Dimension(5, 5)));

		JMenuItem menuItemExit = new JMenuItem("終了");
		menuItemExit.setFont(sysFont);
		menuItemExit.setIcon(new ImageIcon(iconExitPath));
		menuItemExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				beforeClosing();
				System.exit(0);
			}
		});
		menuFile.add(menuItemExit);

		JMenu menuEdit = new JMenu("編集");
		menuEdit.setFont(sysFont);
		menuBar.add(menuEdit);

		JMenuItem menuItemUndo = new JMenuItem("元に戻す");
		menuItemUndo.setFont(sysFont);
		menuItemUndo.setIcon(new ImageIcon(iconUndoPath));
		menuItemUndo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.getSelectedComponent().transferFocus();
				try {
					Robot r = new Robot();
					r.keyPress(KeyEvent.VK_CONTROL);
					r.keyPress(KeyEvent.VK_Y);
					r.keyRelease(KeyEvent.VK_Y);
					r.keyRelease(KeyEvent.VK_CONTROL);
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
			}
		});
		menuEdit.add(menuItemUndo);

		JMenuItem menuItemRedo = new JMenuItem("やり直し");
		menuItemRedo.setFont(sysFont);
		menuItemRedo.setIcon(new ImageIcon(iconRedoPath));
		menuItemRedo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.getSelectedComponent().transferFocus();
				try {
					Robot r = new Robot();
					r.keyPress(KeyEvent.VK_CONTROL);
					r.keyPress(KeyEvent.VK_Z);
					r.keyRelease(KeyEvent.VK_Z);
					r.keyRelease(KeyEvent.VK_CONTROL);
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
			}
		});
		menuEdit.add(menuItemRedo);

		menuEdit.add(Box.createRigidArea(new Dimension(5, 5)));

		JMenuItem menuItemCut = new JMenuItem("切り取り");
		menuItemCut.setFont(sysFont);
		menuItemCut.setIcon(new ImageIcon(iconCutPath));
		menuItemCut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Robot r = new Robot();
					r.keyPress(KeyEvent.VK_CONTROL);
					r.keyPress(KeyEvent.VK_X);
					r.keyRelease(KeyEvent.VK_X);
					r.keyRelease(KeyEvent.VK_CONTROL);
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
			}
		});
		menuEdit.add(menuItemCut);

		JMenuItem menuItemCopy = new JMenuItem("コピー");
		menuItemCopy.setFont(sysFont);
		menuItemCopy.setIcon(new ImageIcon(iconCopyPath));
		menuItemCopy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.getSelectedComponent().transferFocus();
				try {
					Robot r = new Robot();
					r.keyPress(KeyEvent.VK_CONTROL);
					r.keyPress(KeyEvent.VK_C);
					r.keyRelease(KeyEvent.VK_C);
					r.keyRelease(KeyEvent.VK_CONTROL);
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
			}
		});
		menuEdit.add(menuItemCopy);

		JMenuItem menuItemPaste = new JMenuItem("貼り付け");
		menuItemPaste.setFont(sysFont);
		menuItemPaste.setIcon(new ImageIcon(iconPastePath));
		menuItemPaste.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.getSelectedComponent().transferFocus();
				try {
					Robot r = new Robot();
					r.keyPress(KeyEvent.VK_CONTROL);
					r.keyPress(KeyEvent.VK_V);
					r.keyRelease(KeyEvent.VK_V);
					r.keyRelease(KeyEvent.VK_CONTROL);
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
			}
		});
		menuEdit.add(menuItemPaste);

		menuEdit.add(Box.createRigidArea(new Dimension(5, 5)));

		JMenuItem menuItemAllSelect = new JMenuItem("すべて選択");
		menuItemAllSelect.setFont(sysFont);
		menuItemAllSelect.setIcon(new ImageIcon(iconSelectAllPath));
		menuItemAllSelect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.getSelectedComponent().transferFocus();
				try {
					Robot r = new Robot();
					r.keyPress(KeyEvent.VK_CONTROL);
					r.keyPress(KeyEvent.VK_A);
					r.keyRelease(KeyEvent.VK_A);
					r.keyRelease(KeyEvent.VK_CONTROL);
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
			}
		});
		menuEdit.add(menuItemAllSelect);

		JMenuItem menuItemFind = new JMenuItem("検索/置換");
		menuItemFind.setFont(sysFont);
		menuItemFind.setIcon(new ImageIcon(iconFindPath));
		menuItemFind.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.getSelectedComponent().transferFocus();
				try {
					Robot r = new Robot();
					r.keyPress(KeyEvent.VK_CONTROL);
					r.keyPress(KeyEvent.VK_H);
					r.keyRelease(KeyEvent.VK_H);
					r.keyRelease(KeyEvent.VK_CONTROL);
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
			}
		});
		menuEdit.add(menuItemFind);

		//		JMenu menuHelp = new JMenu("ヘルプ");
		//		menuHelp.setFont(sysFont);
		//		menuBar.add(menuHelp);
		//
		//		JMenuItem menuItemNotice = new JMenuItem("このソフトウェアについて");
		//		menuItemNotice.setFont(sysFont);
		//		menuItemNotice.setIcon(new ImageIcon(iconNoticePath));
		//		menuHelp.add(menuItemNotice);
	}

	private void initDirectory() {
		JPanel directoryPanel = new JPanel();
		splitPane_VERTICAL.setLeftComponent(directoryPanel);
		directoryPanel.setLayout(new BorderLayout(0, 0));

		FileSystemView fileSystemView = FileSystemView.getFileSystemView();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(workSpacePath);
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

		tree = new JTree(treeModel);
		tree.setFont(sysFont);
		tree.addTreeWillExpandListener(new FolderWillExpandListener(fileSystemView));
		tree.setCellRenderer(new FileTreeCellRenderer(tree.getCellRenderer(), fileSystemView));
		tree.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
					JTree tree = (JTree) e.getSource();
					TreePath path = tree.getPathForLocation(e.getX(), e.getY());
					if (path != null) {
						openEditor(new File(path.getLastPathComponent().toString()));
					}
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(tree);
		directoryPanel.add(scrollPane, BorderLayout.CENTER);

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		scrollPane.setColumnHeaderView(toolBar);

		toolBar.add(Box.createRigidArea(new Dimension(5, 5)));

		JButton btnNewFile = new JButton(new ImageIcon(iconNewPath));
		btnNewFile.setText("新規ファイル");
		btnNewFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newFile();
			}
		});
		toolBar.add(btnNewFile);

		JButton btnOpen = new JButton(new ImageIcon(iconOpenPath));
		btnOpen.setText("開く");
		btnOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				openFile();
			}
		});
		toolBar.add(btnOpen);

		JButton btnDelete = new JButton(new ImageIcon(iconExitPath));
		btnDelete.setText("削除");
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tree.getSelectionPath() != null) {
					String file = String.valueOf(tree.getSelectionPath().getLastPathComponent());
					new File(file).delete();
					int height = splitPane_VERTICAL.getDividerLocation();
					initDirectory();
					splitPane_VERTICAL.setDividerLocation(height);
				}
			}
		});
		toolBar.add(btnDelete);
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
		btnNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newFile();
			}
		});
		toolBar.add(btnNew);

		JButton btnOpen = new JButton(new ImageIcon(iconOpenPath));
		btnOpen.setToolTipText("ファイルを開く");
		btnOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				openFile();
			}
		});
		toolBar.add(btnOpen);

		toolBar.add(Box.createRigidArea(new Dimension(10, 5)));

		JButton btnClose = new JButton(new ImageIcon(iconClosePath));
		btnClose.setToolTipText("閉じる");
		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
			}
		});
		toolBar.add(btnClose);

		JButton btnCloseAll = new JButton(new ImageIcon(iconCloseAllPath));
		btnCloseAll.setToolTipText("すべて閉じる");
		btnCloseAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.removeAll();
			}
		});
		toolBar.add(btnCloseAll);

		toolBar.add(Box.createRigidArea(new Dimension(10, 5)));

		JButton btnSave = new JButton(new ImageIcon(iconSavePath));
		btnSave.setToolTipText("保存");
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveAction((JScrollPane) tabbedPane.getSelectedComponent(), tabbedPane.getSelectedIndex());
			}
		});
		toolBar.add(btnSave);

		JButton btnSaveAs = new JButton(new ImageIcon(iconSaveAsPath));
		btnSaveAs.setToolTipText("名前を付けて保存");
		btnSaveAs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveAsAction((JScrollPane) tabbedPane.getSelectedComponent(), tabbedPane.getSelectedIndex());
			}
		});
		toolBar.add(btnSaveAs);

		JButton btnSaveAll = new JButton(new ImageIcon(iconSaveAllPath));
		btnSaveAll.setToolTipText("すべて保存");
		toolBar.add(btnSaveAll);

		toolBar.add(Box.createRigidArea(new Dimension(10, 5)));

		JButton btnUndo = new JButton(new ImageIcon(iconUndoPath));
		btnUndo.setToolTipText("元に戻す");
		btnUndo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.getSelectedComponent().transferFocus();
				try {
					Robot r = new Robot();
					r.keyPress(KeyEvent.VK_CONTROL);
					r.keyPress(KeyEvent.VK_Y);
					r.keyRelease(KeyEvent.VK_Y);
					r.keyRelease(KeyEvent.VK_CONTROL);
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
			}
		});
		toolBar.add(btnUndo);

		JButton btnRedo = new JButton(new ImageIcon(iconRedoPath));
		btnRedo.setToolTipText("やり直す");
		btnRedo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.getSelectedComponent().transferFocus();
				try {
					Robot r = new Robot();
					r.keyPress(KeyEvent.VK_CONTROL);
					r.keyPress(KeyEvent.VK_Z);
					r.keyRelease(KeyEvent.VK_Z);
					r.keyRelease(KeyEvent.VK_CONTROL);
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
			}
		});
		toolBar.add(btnRedo);

		toolBar.add(Box.createRigidArea(new Dimension(10, 5)));

		JButton btnCut = new JButton(new ImageIcon(iconCutPath));
		btnCut.setToolTipText("切り取り");
		btnCut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.getSelectedComponent().transferFocus();
				try {
					Robot r = new Robot();
					r.keyPress(KeyEvent.VK_CONTROL);
					r.keyPress(KeyEvent.VK_X);
					r.keyRelease(KeyEvent.VK_X);
					r.keyRelease(KeyEvent.VK_CONTROL);
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
			}
		});
		toolBar.add(btnCut);

		JButton btnCopy = new JButton(new ImageIcon(iconCopyPath));
		btnCopy.setToolTipText("コピー");
		btnCopy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.getSelectedComponent().transferFocus();
				try {
					Robot r = new Robot();
					r.keyPress(KeyEvent.VK_CONTROL);
					r.keyPress(KeyEvent.VK_C);
					r.keyRelease(KeyEvent.VK_C);
					r.keyRelease(KeyEvent.VK_CONTROL);
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
			}
		});
		toolBar.add(btnCopy);

		JButton btnPaste = new JButton(new ImageIcon(iconPastePath));
		btnPaste.setToolTipText("貼り付け");
		btnPaste.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.getSelectedComponent().transferFocus();
				try {
					Robot r = new Robot();
					r.keyPress(KeyEvent.VK_CONTROL);
					r.keyPress(KeyEvent.VK_V);
					r.keyRelease(KeyEvent.VK_V);
					r.keyRelease(KeyEvent.VK_CONTROL);
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
			}
		});
		toolBar.add(btnPaste);

		toolBar.add(Box.createRigidArea(new Dimension(10, 5)));

		JButton btnSelectAll = new JButton(new ImageIcon(iconSelectAllPath));
		btnSelectAll.setToolTipText("すべて選択");
		btnSelectAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.getSelectedComponent().transferFocus();
				try {
					Robot r = new Robot();
					r.keyPress(KeyEvent.VK_CONTROL);
					r.keyPress(KeyEvent.VK_A);
					r.keyRelease(KeyEvent.VK_A);
					r.keyRelease(KeyEvent.VK_CONTROL);
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
			}
		});
		toolBar.add(btnSelectAll);

		JButton btnFind = new JButton(new ImageIcon(iconFindPath));
		btnFind.setToolTipText("検索/置き換え");
		btnFind.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.getSelectedComponent().transferFocus();
				try {
					Robot r = new Robot();
					r.keyPress(KeyEvent.VK_CONTROL);
					r.keyPress(KeyEvent.VK_H);
					r.keyRelease(KeyEvent.VK_H);
					r.keyRelease(KeyEvent.VK_CONTROL);
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
			}
		});
		toolBar.add(btnFind);

		toolBar.add(Box.createRigidArea(new Dimension(10, 5)));

	}

	private void initEditor() {
		JPanel editorPanel = new JPanel();
		editorPanel.setLayout(new BorderLayout(0, 0));
		splitPane_HORIZONAL.setLeftComponent(editorPanel);

		tabbedPane = new CloseButtonTabbedPane(JTabbedPane.TOP, new ImageIcon(iconTabClosePath)) {
			@Override
			public boolean beforeRemoveTabAt(int index) {
				return removeTab(index);
			}
		};
		editorPanel.add(tabbedPane, BorderLayout.CENTER);
	}

	private void initConsole() {
		JPanel consolePanel = new JPanel();
		consolePanel.setLayout(new BorderLayout(0, 0));
		splitPane_VERTICAL.setRightComponent(consolePanel);

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		consolePanel.add(toolBar, BorderLayout.SOUTH);

		consoleField = new ConsoleTextField() {

			@Override
			public void actionPerform(ActionEvent e) {
				super.actionPerform(e);
				executeCommand(e.getActionCommand());
				consoleField.setSelectedIndex(-1);
			}

			@Override
			public void actionStop() {
				super.actionStop();
			}
		};
		toolBar.add(consoleField);

		JButton btnStart = new JButton(new ImageIcon(iconStartPath));
		btnStart.setToolTipText("実行");
		btnStart.setFocusable(false);
		btnStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				consoleField.addList((String) consoleField.getEditor().getItem());
				consoleField.listReset();
				consoleField.getEditor().setItem("");
			}
		});
		toolBar.add(btnStart);

		JButton btnStop = new JButton(new ImageIcon(iconStopPath));
		btnStop.setToolTipText("停止");
		btnStop.setFocusable(false);
		btnStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				executeStop();
			}
		});
		toolBar.add(btnStop);

		consoleTextArea = new JTextArea();
		consoleTextArea.setEditable(false);
		consoleTextArea.setFont(conFont);
		consoleTextArea.setBounds(consolePanel.getBounds());

		consoleTextAreaScroll = new JScrollPane(consoleTextArea);
		consoleTextAreaScroll.setBounds(consolePanel.getBounds());
		consolePanel.add(consoleTextAreaScroll);

		executeCommand("");
	}

	private void initDataSet() {
		// 前回開いていたファイルを開く
		for (String file : new ArrayList<>(config.getOpenedFiles())) {
			openEditor(new File(file));
		}

	}

	private <F> void openEditor(F file) {

		// 引数がFile型かどうか確認
		if (!(file instanceof File)) {
			return;
		}

		// 開こうしてるファイルが既に開かれてないか確認
		Component coms[] = tabbedPane.getComponents();
		for (Component com : coms) {
			if (com instanceof JScrollPane) {
				if (com.getName().equals(((File) file).getName())) {
					tabbedPane.setSelectedComponent(com);
					return;
				}
			}
		}

		JEditorPane editor = new JEditorPane();
		JScrollPane scroll = new JScrollPane(editor);
		editorMap.put(scroll, editor);
		editor.setContentType("text/java");
		editor.setFont(edtFont);
		scroll.setName(((File) file).getName());
		if (!Model.setFile2Editor(editor, (File) file)) {
			return;
		}
		editor.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (!isEditMap.get((JScrollPane) tabbedPane.getSelectedComponent())) {
					tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), "*" + tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));
					isEditMap.put((JScrollPane) tabbedPane.getSelectedComponent(), true);
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				if (!isEditMap.get((JScrollPane) tabbedPane.getSelectedComponent())) {
					tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), "*" + tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));
					isEditMap.put((JScrollPane) tabbedPane.getSelectedComponent(), true);
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				if (!isEditMap.get((JScrollPane) tabbedPane.getSelectedComponent())) {
					tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), "*" + tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));
					isEditMap.put((JScrollPane) tabbedPane.getSelectedComponent(), true);
				}
			}
		});
		editor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if (e.isControlDown()) {
					if (83 == e.getKeyCode()) {
						saveAction((JScrollPane) tabbedPane.getSelectedComponent(), tabbedPane.getSelectedIndex());
					}
				}
			}
		});
		editor.setCaretPosition(0);
		tabbedPane.addTab(((File) file).getName(), scroll);
		tabbedPane.setSelectedComponent(scroll);
		isEditMap.put(scroll, false);
		ArrayList<String> list = config.getOpenedFiles();
		if (!list.contains(((File) file).toString())) {
			config.getOpenedFiles().add(((File) file).toString());
		}
	}

	private void saveAction(JScrollPane scroll, int index) {
		if ('*' == tabbedPane.getTitleAt(index).charAt(0)) {
			Model.fileSave(editorMap.get(scroll), tabbedPane.getTitleAt(index));
			tabbedPane.setTitleAt(index, tabbedPane.getTitleAt(index).substring(1, tabbedPane.getTitleAt(index).length()));
			isEditMap.put(scroll, false);
		}
	}

	private void saveAsAction(JScrollPane scroll, int index) {
		JFileChooser filechooser = new JFileChooser();
		FileNameExtensionFilter ff = new FileNameExtensionFilter("Javaファイル(*.java)", "java");
		filechooser.setFileFilter(ff);
		int selected = filechooser.showSaveDialog(frame);
		if (selected == JFileChooser.APPROVE_OPTION) {
			File file = filechooser.getSelectedFile();
			Model.fileSaveAs(editorMap.get(scroll), new File(file.toString() + ".java"));
		}
	}

	private void openFile() {
		JFileChooser filechooser = new JFileChooser();
		FileNameExtensionFilter ff = new FileNameExtensionFilter("Javaファイル(*.java)", "java");
		filechooser.setFileFilter(ff);
		int selected = filechooser.showOpenDialog(frame);
		if (selected == JFileChooser.APPROVE_OPTION) {
			File file = filechooser.getSelectedFile();
			try {
				FileUtils.copyFile(file, new File(workSpacePath + "/" + file.getName()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void newFile() {
		String value = JOptionPane.showInputDialog(frame, "ファイル名を決定してください。\n頭文字は大文字にしてください。");

		if (value != null) {

			if (!Character.isUpperCase(value.charAt(0))) {
				JOptionPane.showMessageDialog(frame, "ファイル名の頭文字は大文字にしてください。");
				return;
			}
			File file = new File(workSpacePath + "/" + value + ".java");
			try {
				if (file.createNewFile()) {
					Model.setMainMethod(file);
					openEditor(file);
					int height = splitPane_VERTICAL.getDividerLocation();
					initDirectory();
					splitPane_VERTICAL.setDividerLocation(height);
				} else {
					JOptionPane.showMessageDialog(frame, "ファイルの作成に失敗しました。");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void executeCommand(String command) {
		this.command = command;
		Execute execute = new Execute();
		Thread thread = new Thread(execute);
		thread.start();
	}

	private void executeStop() {
		p.destroy();
	}

	private void beforeClosing() {
		try {
			for (int index = 0; index < tabbedPane.getComponentCount() - 1; index++) {
				JScrollPane remove = (JScrollPane) tabbedPane.getComponentAt(index);
				if (isEditMap.get(remove)) {
					int ret = JOptionPane.showConfirmDialog(frame.getContentPane(),
							tabbedPane.getTitleAt(index) + "は変更されています。保存しますか？", "確認", JOptionPane.YES_NO_CANCEL_OPTION);
					switch (ret) {
					case 0:
						// 保存処理
						saveAction(remove, index);
						break;

					}
				}
			}

			JAXB.marshal(config, new FileOutputStream("./META-INF/config.xml"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private class Execute implements Runnable {
		public void run() {
			try {
				ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cd");
				pb.directory(workSpacePath);
				pb.redirectErrorStream(true);
				p = pb.start();
				p.waitFor();

				InputStreamReader is = new InputStreamReader(p.getInputStream(), "MS932");
				BufferedReader br = new BufferedReader(is);
				String line;
				while ((line = br.readLine()) != null) {
					consoleTextArea.setText(consoleTextArea.getText() + line.substring(line.length() - 23, line.length()) + " > " + command + "\n");
					JScrollBar vBar = consoleTextAreaScroll.getVerticalScrollBar();
					int vBarMax = vBar.getMaximum();
					vBar.setValue(vBarMax);
					JScrollBar hBar = consoleTextAreaScroll.getHorizontalScrollBar();
					int hBarMin = hBar.getMinimum();
					hBar.setValue(hBarMin);
				}

				if (!"".equals(command)) {
					if ("java".equals(command.split(" ")[0]) || "javac".equals(command.split(" ")[0])) {
						command = "..\\java\\bin\\" + command;
					}
					command = "cmd /c " + command;

					pb.command(command.split(" "));
					p = pb.start();
					p.waitFor();

					is = new InputStreamReader(p.getInputStream(), "MS932");
					br = new BufferedReader(is);
					int count = 0;
					while ((line = br.readLine()) != null) {
						if (count > config.getLogSize()) {
							break;
						}
						consoleTextArea.setText(consoleTextArea.getText() + line + "\n");
						JScrollBar vBar = consoleTextAreaScroll.getVerticalScrollBar();
						int vBarMax = vBar.getMaximum();
						vBar.setValue(vBarMax);
						JScrollBar hBar = consoleTextAreaScroll.getHorizontalScrollBar();
						int hBarMin = hBar.getMinimum();
						hBar.setValue(hBarMin);
						count++;
					}
					if (count > config.getLogSize()) {
						consoleTextArea.setText(consoleTextArea.getText() + "＊＊＊＊＊出力ログの量が多すぎます。＊＊＊＊＊" + "\n");
					}

					pb.command("cmd", "/c", "cd");
					p = pb.start();
					p.waitFor();

					is = new InputStreamReader(p.getInputStream(), "MS932");
					br = new BufferedReader(is);
					while ((line = br.readLine()) != null) {
						consoleTextArea.setText(consoleTextArea.getText() + "\n" + line.substring(line.length() - 23, line.length()) + " > \n");
						JScrollBar vBar = consoleTextAreaScroll.getVerticalScrollBar();
						int vBarMax = vBar.getMaximum();
						vBar.setValue(vBarMax);
						JScrollBar hBar = consoleTextAreaScroll.getHorizontalScrollBar();
						int hBarMin = hBar.getMinimum();
						hBar.setValue(hBarMin);
					}
				}
				int height = splitPane_VERTICAL.getDividerLocation();
				initDirectory();
				splitPane_VERTICAL.setDividerLocation(height);
				consoleTextArea.setCaretPosition(consoleTextArea.getDocument().getLength());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean removeTab(int index) {
		JScrollPane remove = (JScrollPane) tabbedPane.getComponentAt(index);
		if (isEditMap.get(remove)) {
			int ret = JOptionPane.showConfirmDialog(frame.getContentPane(),
					tabbedPane.getTitleAt(index) + "は変更されています。保存しますか？", "確認", JOptionPane.YES_NO_CANCEL_OPTION);
			switch (ret) {
			case 0:
				// 保存処理
				saveAction(remove, index);
				break;

			case 1:
				// 何もしない
				break;

			case 2:
				return false;

			default:
				return false;
			}
		}
		config.getOpenedFiles().remove(index);
		editorMap.remove(remove);
		return true;
	}
}
