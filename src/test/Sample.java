package test;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import jsyntaxpane.DefaultSyntaxKit;

@SuppressWarnings("serial")
public class Sample extends JFrame {

    private Sample() {

        DefaultSyntaxKit.initKit();

        setTitle("jsyntaxpane");

        final JEditorPane codeEditor = new JEditorPane();
        JScrollPane scrPane = new JScrollPane(codeEditor);

        // 表示するスタイルを指定
        codeEditor.setContentType("text/java");
        codeEditor.setText("public static void main(String[] args) {\n}");

        // デモ用に大きめのフォントサイズを指定
        Font font = codeEditor.getFont();
        codeEditor.setFont(new Font(font.getName(), font.getStyle(), 16));
        getContentPane().add(scrPane);
    }

    private static void startup() {
        Sample frame = new Sample();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(500, 300));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                startup();
            }
        });
    }
}