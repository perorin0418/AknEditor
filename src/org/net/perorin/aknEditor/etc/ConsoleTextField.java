package org.net.perorin.aknEditor.etc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JTextField;

public class ConsoleTextField extends JTextField {

	ArrayList<String> list = null;

	int target = -1;

	public ConsoleTextField() {
		super();
		list = new ArrayList<>();

		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ConsoleTextField.this.actionPerform();
			}
		});
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.isControlDown()) {
					if (67 == e.getKeyCode()) {
						// Ctrl + C
						ConsoleTextField.this.actionStop();
					}
				} else if (38 == e.getKeyCode()) {
					// ↑キー
					if (target > 0) {
						target--;
						ConsoleTextField.this.setText(list.get(target));
					}
				} else if (40 == e.getKeyCode()) {
					// ↓キー
					if (target < list.size() - 1) {
						target++;
						ConsoleTextField.this.setText(list.get(target));
					}
				}
			}
		});
	}

	public void actionPerform() {
		if (!"".equals(getText())) {
			target++;
			list.add(target, getText());
		}
	}

	public void actionStop() {

	}

}
