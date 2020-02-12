package cn.org.orion.bxcxjc.Move;

import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {
		
		MyPanel panel = new MyPanel();
		Thread panelThread = new Thread(panel);
		JFrame frame = new JFrame();
		frame.add(panel);
		frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setTitle("模拟行星运动");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panelThread.start();
	}

}
