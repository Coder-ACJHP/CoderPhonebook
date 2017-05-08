package com.coder.contacts.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Dialog.ModalExclusionType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.apple.eawt.Application;
import com.coder.contacts.entity.Person;
import com.coder.contacts.enums.RequestedThings;
import com.coder.contacts.service.Service;

public class AddContact extends JFrame implements ActionListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private JPanel contentPane;
		protected RequestedThings req;
		private JButton OK_Btn, CLEAR_Btn;
		private final JPanel panel = new JPanel();
		private String name, lastName, tel, email;
		private JTextField LASTNAME_Field, NAME_Field, PHONENUM_Field, EMAIL_Field;
		private JLabel StatusLabel = new JLabel(""), lblrequired = new JLabel("(required)"), lblContactText,
				lblContactIcon, NAME_Lbl, LASTNAME_Lbl, PHONENUM_Lbl, EMAIL_Lbl, lblCheckEmail;

		/**
		 * Create the frame.
		 */
		private JTable table;
		private DefaultTableModel model;
		
		public void setModel(DefaultTableModel theModel) {
			this.model = theModel;
		}
		
		public void setTable(JTable theTable) {
			this.table = theTable;
		}

		public AddContact() {
			
			String version = System.getProperty("os.name").toLowerCase();
			if (version.indexOf("windows") != -1) {
				setIconImage(
						Toolkit.getDefaultToolkit().getImage(AddContact.class.getResource("/com/coder/contacts/icons/ContactBook.png")));
			} else {
				Application.getApplication()
						.setDockIconImage(new ImageIcon(getClass().getResource("/com/coder/contacts/icons/ContactBook.png")).getImage());
			}
			setFont(new Font("Dialog", Font.BOLD, 12));
			setAlwaysOnTop(true);
			setResizable(false);
			setModalExclusionType(ModalExclusionType.TOOLKIT_EXCLUDE);
			setVisible(true);
			setTitle("NEW CONTACT");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(1110, 205, 336, 650);

			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			panel.setBounds(0, 0, 359, 361);
			contentPane.add(panel);
			panel.setLayout(null);

			lblContactText = new JLabel(" New Contact");
			lblContactText.setAutoscrolls(true);
			lblContactText.setAlignmentX(Component.CENTER_ALIGNMENT);
			lblContactText.setOpaque(true);
			lblContactText.setBackground(new Color(255, 165, 0));
			lblContactText.setFont(new Font("Monospaced", Font.BOLD, 15));
			lblContactText.setBounds(38, 6, 320, 41);
			panel.add(lblContactText);

			lblContactIcon = new JLabel("");
			lblContactIcon.setAutoscrolls(true);
			lblContactIcon.setHorizontalAlignment(SwingConstants.CENTER);
			lblContactIcon.setVerifyInputWhenFocusTarget(false);
			lblContactIcon.setLabelFor(lblContactText);
			lblContactIcon.setOpaque(true);
			lblContactIcon.setBackground(new Color(255, 255, 255));
			lblContactIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
			lblContactIcon.setIcon(new ImageIcon(AddContact.class.getResource("/com/coder/contacts/icons/Add.png")));
			lblContactIcon.setBounds(0, 6, 41, 41);
			panel.add(lblContactIcon);

			NAME_Lbl = new JLabel("NAME :");
			NAME_Lbl.setFont(new Font("Dialog", Font.BOLD, 11));
			NAME_Lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
			NAME_Lbl.setBounds(10, 71, 92, 32);
			panel.add(NAME_Lbl);

			LASTNAME_Lbl = new JLabel("LASTNAME : ");
			LASTNAME_Lbl.setFont(new Font("Dialog", Font.BOLD, 11));
			LASTNAME_Lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
			LASTNAME_Lbl.setBounds(10, 117, 92, 32);
			panel.add(LASTNAME_Lbl);

			PHONENUM_Lbl = new JLabel("PHONE NUMBER : ");
			PHONENUM_Lbl.setFont(new Font("Dialog", Font.BOLD, 11));
			PHONENUM_Lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
			PHONENUM_Lbl.setBounds(10, 164, 92, 32);
			panel.add(PHONENUM_Lbl);

			EMAIL_Lbl = new JLabel("EMAIL :");
			EMAIL_Lbl.setFont(new Font("Dialog", Font.BOLD, 11));
			EMAIL_Lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
			EMAIL_Lbl.setBounds(10, 213, 92, 32);
			panel.add(EMAIL_Lbl);

			LASTNAME_Field = new JTextField();
			LASTNAME_Field.setBounds(102, 116, 169, 33);
			panel.add(LASTNAME_Field);
			LASTNAME_Field.setColumns(10);

			NAME_Field = new JTextField();
			NAME_Field.setBounds(102, 70, 169, 33);
			panel.add(NAME_Field);
			NAME_Field.setColumns(10);

			PHONENUM_Field = new JTextField();
			PHONENUM_Field.setBounds(102, 165, 169, 33);
			panel.add(PHONENUM_Field);
			PHONENUM_Field.setColumns(10);

			EMAIL_Field = new JTextField();
			EMAIL_Field.setBounds(102, 212, 169, 33);
			panel.add(EMAIL_Field);
			EMAIL_Field.setColumns(10);

			StatusLabel.setOpaque(true);
			StatusLabel.setFont(new Font("Monospaced", Font.ITALIC, 16));
			StatusLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			StatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
			StatusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			StatusLabel.setBounds(65, 583, 214, 28);

			lblrequired.setVisible(false);
			lblrequired.setForeground(new Color(255, 0, 0));
			lblrequired.setBounds(275, 72, 58, 33);

			OK_Btn = new JButton("ADD");
			OK_Btn.setIcon(new ImageIcon(AddContact.class.getResource("/com/coder/contacts/icons/OK.png")));
			OK_Btn.setFont(new Font("Dialog", Font.BOLD, 11));
			OK_Btn.setAlignmentX(Component.CENTER_ALIGNMENT);
			OK_Btn.addActionListener(this);

			OK_Btn.setBounds(208, 276, 115, 29);
			panel.add(OK_Btn);

			CLEAR_Btn = new JButton("CLEAR");
			CLEAR_Btn.setIcon(new ImageIcon(AddContact.class.getResource("/com/coder/contacts/icons/Delete.png")));
			CLEAR_Btn.setFont(new Font("Dialog", Font.BOLD, 11));
			CLEAR_Btn.setAlignmentX(Component.CENTER_ALIGNMENT);
			CLEAR_Btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					name = NAME_Field.getText();
					lastName = LASTNAME_Field.getText();
					tel = PHONENUM_Field.getText();
					email = EMAIL_Field.getText();

					lblrequired.setVisible(false);

					if (name.length() > 1 || lastName.length() > 1 || tel.length() > 1) {
						clearFields();
						StatusLabel.setVisible(true);
						StatusLabel.setBackground(Color.GREEN);
						StatusLabel.setText("CLEARED");

					} else {

						StatusLabel.setVisible(true);
						StatusLabel.setBackground(Color.ORANGE);
						StatusLabel.setText("NOTHING TO CLEAR");
					}
				}
			});

			CLEAR_Btn.setBounds(86, 276, 96, 29);
			panel.add(CLEAR_Btn);
			panel.add(lblrequired);

			lblCheckEmail = new JLabel("(check it)");
			lblCheckEmail.setForeground(Color.RED);
			lblCheckEmail.setBounds(275, 214, 58, 32);
			lblCheckEmail.setVisible(false);
			panel.add(lblCheckEmail);
			contentPane.add(StatusLabel);

			JSeparator separator = new JSeparator();
			separator.setBounds(0, 373, 359, 2);
			contentPane.add(separator);
		}

		public void clearFields() {
			NAME_Field.setText(" ");
			LASTNAME_Field.setText(" ");
			PHONENUM_Field.setText(" ");
			EMAIL_Field.setText(" ");
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == OK_Btn) {

				name = NAME_Field.getText();
				lastName = LASTNAME_Field.getText();
				tel = PHONENUM_Field.getText();
				email = EMAIL_Field.getText();

				String pattern = "^(.+)@(.+)$";
				Pattern patt = Pattern.compile(pattern);
				Matcher match = patt.matcher(email);
				if (name.length() > 1 || lastName.length() > 1 || tel.length() > 1) {
					if (match.matches() == true) {
						try {
							req = RequestedThings.FIRED;
							Service conn = new Service();
							conn.insertContact(new Person(name, lastName, tel, email));
							int x = table.getRowCount() - 1;
							String Id = table.getModel().getValueAt(x, 0).toString();
							model.addRow(new String[] { Id, name, lastName, tel, email });

							StatusLabel.setVisible(true);
							StatusLabel.setBackground(Color.GREEN);
							StatusLabel.setText("SUCCESSED");
							clearFields();

						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
							StatusLabel.setVisible(true);
							StatusLabel.setBackground(Color.RED);
							StatusLabel.setText("WRITING ERROR!");
						}

					} else {
						lblCheckEmail.setVisible(true);
					}

				} else {

					lblrequired.setVisible(true);
					StatusLabel.setBackground(Color.RED);
					StatusLabel.setText("PLEASE FILL THE BLANK");
				}
			}
		}
	}

