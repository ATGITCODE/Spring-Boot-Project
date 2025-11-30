package com.bank.accountRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.accountDetails.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
	boolean existsByEmailOrPhoneno(String email, Long phoneno);
	Customer findByEmailOrPhoneno(String email, Long phoneno);
}
