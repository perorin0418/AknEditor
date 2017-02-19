package org.net.perorin.aknEditor.etc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class CloseButtonTabbedPane extends JTabbedPane {

	private final Icon icon;
	private final Dimension buttonSize;

	public CloseButtonTabbedPane(Icon icon) {
		super();
		this.icon = icon;
		buttonSize = new Dimension(icon.getIconWidth(), icon.getIconHeight());
	}

	public CloseButtonTabbedPane(int tabPlacement, Icon icon) {
		super(tabPlacement);
		this.icon = icon;
		buttonSize = new Dimension(icon.getIconWidth(), icon.getIconHeight());
	}

	public void addTab(String title, final JComponent content) {
		JPanel tab = new JPanel(new BorderLayout());
		tab.setOpaque(false);
		JLabel label = new JLabel(title);
		label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 4));
		JButton button = new JButton(icon);
		button.setContentAreaFilled(false);
		button.setPreferredSize(buttonSize);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeTabAt(indexOfComponent(content));
			}
		});
		tab.add(label, BorderLayout.WEST);
		tab.add(button, BorderLayout.EAST);
		tab.setBorder(BorderFactory.createEmptyBorder(2, 1, 1, 1));
		super.addTab(title, content);
		setTabComponentAt(getTabCount() - 1, tab);
	}

	@Override
	public void setTitleAt(int index, String title) {
		super.setTitleAt(index, title);
		JPanel tab = (JPanel) getTabComponentAt(index);
		JLabel label = (JLabel) tab.getComponent(0);
		label.setText(title);
	}

	@Override
	public void removeTabAt(int index) {
		boolean flg = beforeRemoveTabAt(index);
		if (flg) {
			super.removeTabAt(index);
		}
	}

	/**
	 * このメソッドはオーバーライドして使用してください。<br>
	 * 返却値をfalseにするとタブの削除を中止させることができます。
	 * @param component
	 */
	public boolean beforeRemoveTabAt(int index) {
		return true;
	}
}
