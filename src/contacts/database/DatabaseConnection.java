package contacts.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class DatabaseConnection {

	public Connection connect() {
		Connection conn = null;
		try {
			
			if(conn == null) {
				Class.forName("org.sqlite.JDBC");
				String path =System.getProperty("user.dir")+File.separator+"contact.db";
				conn = DriverManager.getConnection("jdbc:sqlite:" + path);
			}

		} catch (ClassNotFoundException e) {JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (SQLException e) {JOptionPane.showMessageDialog(null, "SQLException!");} 
		
		return conn;
	}

	public List<Person> readAllContacts() {
		List<Person> list = new ArrayList<Person>();
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		try {
			connect();
			ps = connect().prepareStatement("SELECT * FROM CONTACTS");
			resultSet = ps.executeQuery();

			while (resultSet.next()) {
				list.add(new Person(resultSet.getInt("ID"), resultSet.getString("NAME"),
						resultSet.getString("LASTNAME"), resultSet.getString("PHONE"), resultSet.getString("EMAIL")));
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} finally {
			try {resultSet.close();} catch (Exception e) {}
			try {ps.close();} catch (Exception e) {}
		}
		return list;
	}

	public boolean updateContact(int Id, Person person) {
		boolean updated = false;
		PreparedStatement ps = null;
		try {

			connect();
			ps = connect().prepareStatement("UPDATE CONTACTS SET NAME=?, LASTNAME=?, PHONE=?, EMAIL=? WHERE ID=" + Id);
			ps.setString(1, person.getName());
			ps.setString(2, person.getLastName());
			ps.setString(3, person.getPhone());
			ps.setString(4, person.getEmail());
			int st = ps.executeUpdate();

			if (st > 0) {
				updated = true;
			}
		} catch (SQLException e) {
			updated = false;
			JOptionPane.showMessageDialog(null, e.getMessage());
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
		return updated;
	}

	public Person showToUpdate(int Id) {
		Person p = new Person();
		PreparedStatement ps = null;
		try {

			connect();
			ps = connect().prepareStatement("SELECT FROM CONTACTS WHERE ID=" + Id);
			ResultSet rs = ps.getResultSet();
			while (rs.next()) {
				p.setName(rs.getString("NAME"));
				p.setLastName(rs.getString("LASTNAME"));
				p.setPhone(rs.getString("PHONE"));
				p.setEmail(rs.getString("EMAIL"));
			}

			ps.executeUpdate();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
		return p;
	}

	public boolean removeContact(int Id) {
		boolean removed = false;
		connect();
		try {
			PreparedStatement ps = connect().prepareStatement("DELETE FROM CONTACTS WHERE ID=" + Id);
			int rs = ps.executeUpdate();

			if (rs > 0) {
				removed = true;
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			removed = false;
		}
		return removed;
	}

	public boolean insertContact(Person person) {
		boolean inserted = false;
		try {
				connect();
				PreparedStatement ps = connect()
						.prepareStatement("INSERT INTO CONTACTS (NAME, LASTNAME, PHONE, EMAIL) VALUES(?,?,?,?)");
				ps.setString(1, person.getName());
				ps.setString(2, person.getLastName());
				ps.setString(3, person.getPhone());
				ps.setString(4, person.getEmail());
	
				int st = ps.executeUpdate();
	
				if (st > 0) {
					inserted = true;
				}
			
		} catch (SQLException e) {
			inserted = false;
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		readAllContacts();
		return inserted;
	}
	
	public boolean insertContactAslist(List<String> list) {

		boolean inserted = false;
		try {
				connect();
				PreparedStatement ps = connect()
						.prepareStatement("INSERT INTO CONTACTS (ID, NAME, LASTNAME, PHONE, EMAIL) VALUES(?,?,?,?,?)");
				int count = 1;
				for (String object : list) {
					ps.setString(count++, object);

				}

				int st = ps.executeUpdate();
	
				if (st > 0) {
					inserted = true;
				}
			
		} catch (SQLException e) {
			inserted = false;
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		readAllContacts();
		return inserted;
	}
}
