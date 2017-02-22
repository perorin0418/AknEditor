package org.net.perorin.aknEditor.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JEditorPane;

public class Model {

	public static final String JAVA_PATH = "./workspace/AknEditor/java/jre1.7.0_80/bin/java.exe ";
	public static final String JAVAW_PATH = "./workspace/AknEditor/java/jre1.7.0_80/bin/javaw.exe ";
	public static final String JAVAC_PATH = "./workspace/AknEditor/java/jre1.7.0_80/bin/javaw.exe ";

	public static boolean setFile2Editor(JEditorPane editor, File file) {
		try {
			String text = "";

			//ファイルを読み込む
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line;
			while ((line = br.readLine()) != null) {
				text += line + "\n";
			}

			editor.setText(text);

			//終了処理
			br.close();
			fr.close();

			return true;

		} catch (IOException ex) {
			//例外発生時処理
			ex.printStackTrace();
		}

		return false;
	}

	public static void setMainMethod(File file) {
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("public class " + file.getName().substring(0, file.getName().length() - 5) + " {\n");
			bw.write("    public static void main(String[] args) {\n");
			bw.write("        \n");
			bw.write("    }\n");
			bw.write("}\n");
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void fileSave(JEditorPane editor, String title) {
		try {
			if ('*' == title.charAt(0)) {
				File file = new File("./workspace/" + title.substring(1, title.length()));
				FileWriter fw = new FileWriter(file);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(editor.getText());
				bw.close();
				fw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void fileSaveAs(JEditorPane editor, File path) {
		try {
			File file = path;
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(editor.getText());
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
