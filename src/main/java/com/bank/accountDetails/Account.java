package com.bank.accountDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Account {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long account_number;
	 
	@Enumerated(EnumType.STRING)
	@Column
	private AccountType account_type;
	@Column
	private Double balance;
	
	@JsonManagedReference
	@OneToOne
	@JoinColumn(name="Customer_id",unique = true)
	private Customer customer;
	
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Account(AccountType account_type, Double balance,Customer customer) {
		super();
		this.account_type = account_type;
		this.balance = balance;
		this.customer=customer;
	}
	public Long getAccount_number() {
		return account_number;
	}
	public void setAccount_number(Long account_number) {
		this.account_number = account_number;
	}
	public AccountType getAccount_type() {
		return account_type;
	}
	public void setAccount_type(AccountType account_type) {
		this.account_type = account_type;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	@Override
	public String toString() {
		return "Account [account_number=" + account_number + ", account_type=" + account_type + ", balance=" + balance
				+ "]";
	}
	
	
}
