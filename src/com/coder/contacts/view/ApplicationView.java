package com.coder.contacts.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.apple.eawt.Application;
import com.coder.contacts.entity.Person;
import com.coder.contacts.enums.RequestedThings;
import com.coder.contacts.service.Service;

public class ApplicationView extends JFrame {

	/**
	 * 
	 */
	private int row = 0;
	private JTable table;
	private JMenu mnAbout;
	private String[] text;
	private static File file;
	private JMenu themesMenu;
	private JTableHeader header;
	private JMenuItem mntmPrint;
	private JMenuItem mnýtmMint;
	private JTextField textField;
	private Vector<String> datas;
	static ApplicationView frame;
	private JScrollPane scrollPane;
	private JPanel contentPane, panel;
	private String textLine = null, theme;
	private JButton btnNew, btnDelete, btnRefresh;
	private static final long serialVersionUID = 1L;
	private JLabel lblAddContact, lblNewLabel, lblReadingTableDatas;
	private String[] colname = new String[] { "ID", "NAME ", "LASTNAME ", "PHONE", "EMAIL  " };
	private DefaultTableModel model = new DefaultTableModel(colname, 0);

	/**
	 * Create the frame.
	 * 
	 * 
	 */

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		ApplicationView.file = file;
	}

	public ApplicationView() {
		String vers = System.getProperty("os.name").toLowerCase();
		if (vers.indexOf("windows") != -1) {
				setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/coder/contacts/icons/ContactBook.png")));
			
		} else {
			Application.getApplication()
					.setDockIconImage(new ImageIcon(getClass().getResource("/com/coder/contacts/icons/ContactBook.png")).getImage());
		}

		final Service conn = new Service();
		conn.connect();
		setTitle("Coder Contacts");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 50, 600, 650);
		setResizable(false);
		setLocationRelativeTo(null);
		for (Person p : conn.readAllContacts()) {
			model.addRow(new Object[] { p.getId(), p.getName(), p.getLastName(), p.getPhone(), p.getEmail() });
		}

		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		mnFile.setFont(new Font("Dialog", Font.BOLD, 12));
		mnFile.setPreferredSize(new Dimension(70, 22));
		mnFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblReadingTableDatas.setText("Menubar -> File");
				super.mouseEntered(e);
			}
		});
		menuBar.add(mnFile);

		JMenuItem mnetmImportFile = new JMenuItem("Import file");
		mnetmImportFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
				(java.awt.event.InputEvent.SHIFT_MASK | (Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()))));
		mnetmImportFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblReadingTableDatas.setText("Importing external database...");
				JOptionPane.showMessageDialog(frame,
						"The type of the imported file should be '.txt'\n" + "file and it should be this way : \n"
								+ "Id--Name--Lastname--Phone--Email",
						"Informaiton", JOptionPane.INFORMATION_MESSAGE);
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt", "text");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(frame);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					file = chooser.getSelectedFile();
					BufferedReader bf = null;
					FileReader fileReader = null;

					try {

						fileReader = new FileReader(getFile());
						bf = new BufferedReader(fileReader);
						while ((textLine = bf.readLine()) != null) {
							text = textLine.split("\\s");
							datas = new Vector<String>(Arrays.asList(text));
							conn.insertContactAslist(Arrays.asList(text));
							model.insertRow(row++, datas);

						}
						bf.close();
						lblReadingTableDatas.setText("External database imported successfully.");
					} catch (IOException e) {
						System.err.println(e.getMessage());
					}
				} else {
					lblReadingTableDatas.setText("Open command cancelled by user.");
				}
			}
		});
		mnetmImportFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblReadingTableDatas.setText("Menubar -> File -> Import file");
				super.mouseEntered(e);
			}
		});
		mnetmImportFile.setIcon(new ImageIcon(ApplicationView.class.getResource("/com/coder/contacts/icons/import-icon (1).png")));
		mnetmImportFile.setOpaque(true);
		mnetmImportFile.setFont(new Font("Dialog", Font.BOLD, 12));
		mnFile.add(mnetmImportFile);

		lblReadingTableDatas = new JLabel("");
		lblReadingTableDatas.setFont(new Font("Dialog", Font.BOLD, 12));
		lblReadingTableDatas.setForeground(new Color(0, 100, 0));

		mntmPrint = new JMenuItem("Print");
		mntmPrint.setFont(new Font("Dialog", Font.BOLD, 12));
		mntmPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				(java.awt.event.InputEvent.SHIFT_MASK | (Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()))));
		mntmPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblReadingTableDatas.setText("Printing...");
				try {
					boolean complete = table.print();
					if (complete == true) {
						lblReadingTableDatas.setText("Printed successfully");
					} else {
						lblReadingTableDatas.setText("Print job failed!");
					}
				} catch (PrinterException e1) {
					e1.printStackTrace();
				}
			}
		});
		mntmPrint.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblReadingTableDatas.setText("Menubar -> File -> Print");
				super.mouseEntered(e);
			}
		});
		lblReadingTableDatas.setText("Reading table datas...");

		mntmPrint.setIcon(new ImageIcon(ApplicationView.class.getResource("/com/coder/contacts/icons/print-icon.png")));
		mnFile.add(mntmPrint);

		themesMenu = new JMenu("Themes");
		themesMenu.setFont(new Font("Dialog", Font.BOLD, 12));
		themesMenu.setIcon(new ImageIcon(ApplicationView.class.getResource("/com/coder/contacts/icons/theme-icon.png")));
		themesMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblReadingTableDatas.setText("Menubar -> File -> Themes");
				super.mouseEntered(e);
			}
		});
		
		mnFile.add(themesMenu);
		
		mnýtmMint = new JMenuItem("Mint");
		mnýtmMint.setFont(new Font("Dialog", Font.BOLD, 12));
		mnýtmMint.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblReadingTableDatas.setText("Menubar -> File -> Themes -> Mint");
			}
		});
		mnýtmMint.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				theme = mnýtmMint.getActionCommand();
				try {
					ChangeTheme(theme);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(frame, "Theme changed to "+ theme);
				}
			}
		});
		themesMenu.add(mnýtmMint);
		
		JMenuItem mnýtmMcwin = new JMenuItem("McWin");
		mnýtmMcwin.setSelectedIcon(new ImageIcon(ApplicationView.class.getResource("/com/coder/contacts/icons/OK.png")));
		mnýtmMcwin.setFont(new Font("Dialog", Font.BOLD, 12));
		mnýtmMcwin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblReadingTableDatas.setText("Menubar -> File -> Themes -> McWin");
			}
		});
		mnýtmMcwin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				theme = mnýtmMcwin.getActionCommand();
				try {
					ChangeTheme(theme);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(frame, "Theme changed to "+ theme);
				}
			}
		});
		themesMenu.add(mnýtmMcwin);
		
		JMenuItem mnýtmYellow = new JMenuItem("Yellow");
		mnýtmYellow.setFont(new Font("Dialog", Font.BOLD, 12));
		mnýtmYellow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblReadingTableDatas.setText("Menubar -> File -> Themes -> Yellow");
			}
		});
		mnýtmYellow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				theme = mnýtmYellow.getActionCommand();
				try {
					ChangeTheme(theme);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(frame, "Theme changed to "+ theme);
				}
			}
		});
		themesMenu.add(mnýtmYellow);
		
		JMenuItem mnetmExit = new JMenuItem("Exit");
		mnetmExit.setFont(new Font("Dialog", Font.BOLD, 12));
		mnetmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblReadingTableDatas.setText("Application closing...");
				System.exit(1);
			}
		});
		mnetmExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblReadingTableDatas.setText("Menubar -> File -> Exit");
				super.mouseEntered(e);
			}
		});
		
		
		mnetmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				(java.awt.event.InputEvent.SHIFT_MASK | (Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()))));
		mnetmExit.setIcon(new ImageIcon(ApplicationView.class.getResource("/com/coder/contacts/icons/Exit.png")));
		mnFile.add(mnetmExit);

		mnAbout = new JMenu("About");
		mnAbout.setFont(new Font("Dialog", Font.BOLD, 12));
		mnAbout.setPreferredSize(new Dimension(80, 22));
		mnAbout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblReadingTableDatas.setText("Menubar -> About");
				super.mouseEntered(e);
			}
		});
		menuBar.add(mnAbout);

		JMenuItem mntmHelp = new JMenuItem("Info");
		mntmHelp.setIcon(new ImageIcon(ApplicationView.class.getResource("/com/coder/contacts/icons/Info-icon.png")));
		mntmHelp.setFont(new Font("Dialog", Font.BOLD, 12));
		mntmHelp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblReadingTableDatas.setText("Menubar -> About -> Help");
				super.mouseEntered(e);
			}
		});
		mntmHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblReadingTableDatas.setText("About ContactBook application");
				JOptionPane.showMessageDialog(null,
						"                            Developed by Onur Isik"
								+ "\nThis application is pro version and free,created with JAVA SE... "
								+ "\nFor any issue or feedback please mail me from 'hexa.octabin@gmail.com' "
								+ "\n------------------------------------------------------------------- "
								+ "\n<Coder Paint>  Copyright (C) <2016> <Coder ACJHP>"
								+ "\nThis program is free software: you can redistribute it and/or modify"
								+ "\nit under the terms of the GNU General Public License as published by"
								+ "\nthe Free Software Foundation, either version 3 of the License, or"
								+ "\n(at your option) any later version."
								+ "\nThis program is distributed in the hope that it will be useful,"
								+ "\nbut WITHOUT ANY WARRANTY; without even the implied warranty of"
								+ "\nMERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the"
								+ "\nGNU General Public License for more details."
								+ "\nYou should have received a copy of the GNU General Public License"
								+ "\nalong with this program.  If not, see <http://www.gnu.org/licenses/>."
								+ "\nAlso add information on how to contact you by electronic and paper mail.",
						"Info", JOptionPane.INFORMATION_MESSAGE);

			}
		});
		mnAbout.add(mntmHelp);

		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(105, 105, 105));
		separator.setMaximumSize(new Dimension(10, 25));
		separator.setAlignmentX(Component.LEFT_ALIGNMENT);
		separator.setAutoscrolls(true);
		separator.setPreferredSize(new Dimension(0, 2));
		separator.setOrientation(SwingConstants.VERTICAL);
		menuBar.add(separator);

		JLabel lblNotification = new JLabel("Status : ");
		lblNotification.setFont(new Font("Dialog", Font.BOLD, 12));
		lblNotification.setForeground(Color.RED);
		lblNotification.setToolTipText("Enter a word to search.");
		menuBar.add(lblNotification);

		menuBar.add(lblReadingTableDatas);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		panel = new JPanel();
		panel.setAutoscrolls(true);
		panel.setFont(new Font("Monospaced", Font.PLAIN, 14));
		panel.setBackground(new Color(255, 165, 0));
		panel.setPreferredSize(new Dimension(10, 40));
		panel.setMinimumSize(new Dimension(10, 30));
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(null);

		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(ApplicationView.class.getResource("/com/coder/contacts/icons/addressbook_search_24.png")));
		lblNewLabel.setBounds(0, 0, 44, 40);
		lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setOpaque(true);
		panel.add(lblNewLabel);

		lblAddContact = new JLabel("Filter : ");
		lblAddContact.setBounds(54, 0, 81, 40);
		lblAddContact.setFont(new Font("Monospaced", Font.BOLD, 15));
		panel.add(lblAddContact);

		textField = new JTextField();
		textField.setBounds(134, 10, 95, 20);
		textField.setColumns(10);
		panel.add(textField);

		textField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {

				String kelime = textField.getText();
				filter(kelime);

			}
		});

		btnNew = new JButton("Add ");
		btnNew.setFont(new Font("Dialog", Font.BOLD, 12));
		btnNew.setIcon(new ImageIcon(ApplicationView.class.getResource("/com/coder/contacts/icons/Add.png")));
		btnNew.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnNew.setMinimumSize(new Dimension(63, 23));
		btnNew.setMaximumSize(new Dimension(63, 23));
		btnNew.setBounds(247, 7, 104, 25);

		panel.add(btnNew);

		btnDelete = new JButton("Delete");
		btnDelete.setToolTipText("If the text charset not \"UFT_8\" it cannot delete!");
		btnDelete.setFont(new Font("Dialog", Font.BOLD, 12));
		btnDelete.setIcon(new ImageIcon(ApplicationView.class.getResource("/com/coder/contacts/icons/Delete.png")));
		btnDelete.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnDelete.setBounds(356, 7, 104, 25);
		panel.add(btnDelete);

		btnRefresh = new JButton("Refresh");
		btnRefresh.setFont(new Font("Dialog", Font.BOLD, 12));
		btnRefresh.setIcon(new ImageIcon(ApplicationView.class.getResource("/com/coder/contacts/icons/refresh.png")));
		btnRefresh.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnRefresh.setBounds(465, 7, 104, 25);
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowCount = model.getRowCount();
				for (int i = rowCount - 1; i >= 0; i--) {

					model.removeRow(i);
				}
				lblReadingTableDatas.setText("Table datas updated successfully.");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				for (Person p : conn.readAllContacts()) {
					model.addRow(new Object[] { p.getId(), p.getName(), p.getLastName(), p.getPhone(), p.getEmail() });
				}

			}
		});
		panel.add(btnRefresh);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(537, 17, 0, 0);
		lblNewLabel_1.setAutoscrolls(true);
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel_1.setBackground(Color.WHITE);
		panel.add(lblNewLabel_1);

		table = new JTable(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		TableColumn col = table.getColumnModel().getColumn(0);
		col.setPreferredWidth(5);
		table.setFont(new Font("Monospaced", Font.PLAIN, 13));
		table.setBackground(new Color(245, 245, 245));
		table.setRowHeight(25);
		table.setMinimumSize(new Dimension(60, 20));
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2) {
					lblReadingTableDatas.setText("Press 'ENTER' key after editing cells to save.");
				} else if (e.getClickCount() == 1) {
					int rowIndex = table.getSelectedRow();
					String x = table.getModel().getValueAt(rowIndex, 0).toString();
					lblReadingTableDatas.setText("Selected ID is : " + x);

				}
				super.mouseClicked(e);
			}
		});

		table.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					int rowNum = table.getSelectedRow();
					int Id = (Integer) table.getValueAt(rowNum, 0);
					String name = (String) table.getValueAt(rowNum, 1);
					String lastname = (String) table.getValueAt(rowNum, 2);
					String phone = (String) table.getValueAt(rowNum, 3);
					String email = (String) table.getValueAt(rowNum, 4);
					boolean updated = conn.updateContact(Id, new Person(name, lastname, phone, email));
					model.fireTableRowsUpdated(rowNum, rowNum);
					if (updated) {
						lblReadingTableDatas.setText("Contact updated successfully.");
					}
				}

			}
		});
		header = table.getTableHeader();
		header.setPreferredSize(new Dimension(20, 30));
		header.setFont(new Font("Arial", Font.TRUETYPE_FONT, 17));

		scrollPane = new JScrollPane(table);

		contentPane.add(scrollPane, BorderLayout.CENTER);

		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowIndex = table.getSelectedRow();
				if(rowIndex > -1) {
					int x = (Integer) table.getModel().getValueAt(rowIndex, 0);
					if (conn.removeContact(x) == true) {
	
						model.removeRow(rowIndex);
						lblReadingTableDatas.setText("Contact deleted successfully.");
						model.fireTableDataChanged();
					} else {
						lblReadingTableDatas.setText("Cannot remove at this time!");
					}
				}else {
					lblReadingTableDatas.setText("At first you need select a row with single click!");
					return;
				}
			}
		});

		btnNew.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				lblReadingTableDatas.setText("Adding new users...");
				AddContact newContact = new AddContact();
				newContact.setTable(table);
				newContact.setModel(model);
				newContact.setVisible(true);
				newContact.setAlwaysOnTop(true);
				newContact.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
				if (newContact.req == RequestedThings.FIRED)
					lblReadingTableDatas.setText("Contact added successfully.");
				model.fireTableDataChanged();

			}
		});

	}

	private void filter(String query) {
		TableRowSorter<TableModel> tr = new TableRowSorter<TableModel>(model);
		table.setRowSorter(tr);
		tr.setRowFilter(RowFilter.regexFilter(query));
	}
	
	private void ChangeTheme(String theme) {
		try {
			
			switch (theme) {
			case "Mint":
				UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
				SwingUtilities.updateComponentTreeUI(frame);
				break;

			case "McWin":
				UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
				SwingUtilities.updateComponentTreeUI(frame);
				break;
				
			case "Yellow":	
				UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
				SwingUtilities.updateComponentTreeUI(frame);
				break;
			}
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}

}
