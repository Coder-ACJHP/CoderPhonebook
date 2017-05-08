package com.coder.contacts.view;

import java.awt.EventQueue;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class ApplicationStart {

	public static void main(String[] args) {
		ApplicationSplash load = new ApplicationSplash();
		load.setVisible(true);
		load.start();
		try {
			Thread.sleep(6500);
			load.dispose();
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
						ApplicationView frame = new ApplicationView();
						frame.setVisible(true);
					} catch (Exception e) {JOptionPane.showMessageDialog(null, e.getMessage());}
				}
			});
		} catch (Exception e) {JOptionPane.showMessageDialog(null, e.getMessage());}
	}
}
