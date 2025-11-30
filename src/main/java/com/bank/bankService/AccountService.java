package com.bank.bankService;

import java.util.List;

import com.bank.accountDetails.Account;

public interface AccountService {
	public Account createAccount(Account account);
	public List<Account> findAllAccountDetails();
	public Account findAccountByID(Long accountNumber);
	public List<Account> findByAnyField(String input);
	public void deleteById(Long accountNumber);
	public void deleteAll();
	public Account updateAccount(Long accountNumber,Account updated);
	public String transferFunds(Long fromaccountno, Long toaccountno, Double amount);
}
