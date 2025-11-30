package com.bank.accountRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.accountDetails.Account;
import com.bank.accountDetails.Customer;

public interface AccountRepository extends JpaRepository<Account, Long>{
	
}
