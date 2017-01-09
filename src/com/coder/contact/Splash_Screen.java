package com.coder.contact;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import com.apple.eawt.Application;

import javax.swing.ImageIcon;

public class Splash_Screen extends javax.swing.JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int i = 0;
    private Timer timer;

    final static int interval = 50;

    public Splash_Screen() {
        initComponents();
    }

    public synchronized void start() {
        Progress();
        timer.start();
    }

    public void Progress() {
    timer = new Timer(interval, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (i == 100) {
                    timer.stop();
                }
                if (i == 30) {
                    jLabel1.setText("Getting Files.");
                }
                if (i == 55) {
                    jLabel1.setText("Progessing..");
                    i += 5;
                }
                if (i == 65) {
                    jLabel1.setText("Reading database...");
                }
                if(i == 90) {
                	jLabel1.setText("Preparing...");
                }
                if (i == 99) {
                    jLabel1.setText("     DONE");
                } else {
                    i++;
                    jProgressBar1.setValue(i);
                }
            }
        });

    }

    
    private void initComponents() {
    	String vers = System.getProperty("os.name").toLowerCase();
		if(vers.indexOf("windows") != -1){
			
			setIconImage(Toolkit.getDefaultToolkit().getImage(Contact_UI.class.getResource("/icons/ContactBook.png")));
		}else{
			Application.getApplication().setDockIconImage(new ImageIcon(getClass().getResource("/icons/ContactBook.png")).getImage());
		}
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        setLocation(new java.awt.Point(400, 200));
        setMinimumSize(new Dimension(400, 305));
        setName("jframe1"); // NOI18N
        setTitle("Welcome to Coder ContactBook");
        setType(java.awt.Window.Type.POPUP);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);

        jProgressBar1.setForeground(new java.awt.Color(0, 102, 204));
        jProgressBar1.setBorderPainted(false);
        
        getContentPane().add(jProgressBar1);
        jProgressBar1.setBounds(8, 218, 367, 10);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 153, 0));
        jLabel1.setText("Loading...");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(160, 190, 130, 20);
        jLabel1.getAccessibleContext().setAccessibleName("");

        jLabel2.setFont(new java.awt.Font("Maiandra GD", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(240, 240, 240));
        jLabel2.setText("  Developed By Coder ACJHP");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(20, 90, 360, 50);
        jLabel2.getAccessibleContext().setAccessibleName(" ");
        jLabel2.getAccessibleContext().setAccessibleDescription("");

        jLabel3.setFont(new java.awt.Font("Maiandra GD", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 102, 102));
        jLabel3.setText("2015-2016");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(164, 130, 80, 20);

        Background.setBackground(new java.awt.Color(255, 255, 255));
        Background.setIcon(new ImageIcon(Splash_Screen.class.getResource("/icons/motion-line.gif"))); // NOI18N
        Background.setDoubleBuffered(true);
        getContentPane().add(Background);
        Background.setBounds(0, 0, 400, 267);
        pack();
    }

    private javax.swing.JLabel Background;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JProgressBar jProgressBar1;

}
