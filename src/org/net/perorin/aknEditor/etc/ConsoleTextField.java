package org.net.perorin.aknEditor.etc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;

public class ConsoleTextField extends JComboBox<String> {

	ArrayList<String> list = null;

	public ConsoleTextField() {
		super();
		setEditable(true);
		list = new ArrayList<>();
		this.getEditor().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ConsoleTextField.this.actionPerform(e);
			}
		});
	}

	@Override
	public String getSelectedItem() {
		return (String) super.getSelectedItem();
	}

	public void actionPerform(ActionEvent e) {
		addList(e.getActionCommand());
		listReset();
	}

	public void listReset() {
		this.removeAllItems();
		for (int i = list.size() - 1; i >= 0; i--) {
			this.addItem(list.get(i));
		}
	}

	public void addList(String cmd) {
		if (!"".equals(cmd)) {
			if (!list.contains(cmd)) {
				list.add(cmd);
			}
		}
	}

	public void actionStop() {

	}

}
