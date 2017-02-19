package org.net.perorin.aknEditor.data;

import java.util.ArrayList;

public class Config {

	public Config() {
	}

	private ArrayList<String> openedFiles = new ArrayList<>();

	public ArrayList<String> getOpenedFiles() {
		return openedFiles;
	}

	public void setOpenedFiles(ArrayList<String> openedFiles) {
		this.openedFiles = openedFiles;
	}

}
