package com.coder.contacts.entity;

public class Person {

	private int Id;
	private String Name;
	private String LastName;
	private String phone;
	private String Email;
	
	public Person() {
		super();
	}

	public Person(int id, String name, String lastName, String phone, String email) {
		super();
		Id = id;
		Name = name;
		LastName = lastName;
		this.phone = phone;
		Email = email;
	}

	
	public Person(String name, String lastName, String phone, String email) {
		super();
		Name = name;
		LastName = lastName;
		this.phone = phone;
		Email = email;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}
	
	
}
