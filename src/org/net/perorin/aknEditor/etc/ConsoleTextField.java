package org.net.perorin.aknEditor.etc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTextField;

public class ConsoleTextField extends JTextField {

	ArrayList<String> list = null;

	public ConsoleTextField() {
		super();
		list = new ArrayList<>();
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				list.add(ConsoleTextField.this.getText());
			}
		});
	}

}
