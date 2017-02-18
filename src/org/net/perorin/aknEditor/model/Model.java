package org.net.perorin.aknEditor.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JEditorPane;

public class Model {

	public static void setFile2Editor(JEditorPane editor, File file) {
		try {
			String text = "";

			//ファイルを読み込む
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			//読み込んだファイルを１行ずつ画面出力する
			String line;
			while ((line = br.readLine()) != null) {
				text += line + "\n";
			}

			editor.setText(text);

			//終了処理
			br.close();
			fr.close();

		} catch (IOException ex) {
			//例外発生時処理
			ex.printStackTrace();
		}
	}
}
