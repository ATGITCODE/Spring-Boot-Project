package com.bank.accountDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Customer {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column
	private String account_holder_name;
	@Column(unique = true)
	private String email;
	@Column(unique = true)
	private Long phoneno;
	@JsonBackReference
//	@OneToOne(mappedBy="customer",cascade=CascadeType.ALL)
	@OneToOne(mappedBy="customer")
	private Account account;

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Customer(Long id, String account_holder_name, String email, Long phoneno, Account account) {
		super();
		this.id = id;
		this.account_holder_name = account_holder_name;
		this.email = email;
		this.phoneno = phoneno;
		this.account = account;
	}


	public Long getId() {
		return id;
	}
	
	public String getAccount_holder_name() {
		return account_holder_name;
	}
	public void setAccount_holder_name(String account_holder_name) {
		this.account_holder_name = account_holder_name;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(Long phoneno) {
		this.phoneno = phoneno;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	
}
