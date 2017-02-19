package test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.bind.JAXB;

import org.net.perorin.aknEditor.data.Config;

public class Test {

	public static void main(String[] args) throws FileNotFoundException {

		Config con = new Config();
		con.getOpenedFiles().add("./workspace/001.java");
		con.getOpenedFiles().add("./workspace/002.java");
		con.getOpenedFiles().add("./workspace/003.java");
		JAXB.marshal(con, new FileOutputStream("./META-INF/config.xml"));

	}

}
