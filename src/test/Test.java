package test;

import java.awt.EventQueue;
import java.io.IOException;

public class Test {
	static Hoge execute;

	public static void main(String[] args) throws IOException, InterruptedException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					execute = new Hoge();
					execute.run();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		execute.stop();

	}

}
class Hoge {
	boolean flg = true;

	public void run() {
		while(flg){
			System.out.println("test");
		}
	}

	public void stop(){
		flg = false;
	}
}