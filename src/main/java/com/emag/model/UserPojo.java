package com.emag.model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

public class UserPojo {
	
	private int customerID;
	private String name;
	private String email;
	private String phone;
	private LocalDate dateOfBirth;
	private String password;
	private String address;
	private boolean isAdmin;
	private Set<OrderPojo> orders;
	
	public UserPojo(String name, String email, String phone, LocalDate date, String password, String address,
			boolean isAdmin) {
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.dateOfBirth = date;
		this.password = password;
		this.address = address;
		this.isAdmin = isAdmin;
	}	
	
	public UserPojo(int customerID, String name, String email, String phone, LocalDate dateOfBirth, String password,
			String address, boolean isAdmin) {
		this(name, email, phone, dateOfBirth, password, address, isAdmin);
		this.customerID = customerID;
	}
	
	public UserPojo(String email,  String password) {
		this("", email, "", LocalDate.now(), password, "", false);
	}
	
	public Set<OrderPojo> getOrders() {
		return orders;
	}
	
	public void setOrders(Set<OrderPojo> orders) {
		this.orders = orders;
	}
	
	public void setPassword(String hashedPassword) {
      this.password = hashedPassword;		
	}

	public void setId(int id) {
		this.customerID = id;
	}

	public int getCustomerID() {
		return customerID;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public LocalDate getDateOfBirth(){
		return dateOfBirth;
	}

	public String getPassword() {
		return password;
	}

	public String getAddress() {
		return address;
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (isAdmin ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserPojo other = (UserPojo) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (isAdmin != other.isAdmin)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserPojo [custommerID=" + customerID + ", name=" + name + ", email=" + email + ", phone=" + phone
				+ ", dateOfBirth=" + dateOfBirth + ", password=" + password + ", address=" + address + ", isAdmin="
				+ isAdmin + "]";
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;		
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
