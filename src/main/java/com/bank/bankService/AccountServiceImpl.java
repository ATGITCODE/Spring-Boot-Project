package com.bank.bankService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.bank.accountDetails.Account;
import com.bank.accountDetails.Customer;
import com.bank.accountRepository.AccountRepository;
import com.bank.accountRepository.CustomerRepository;
@Service
public class AccountServiceImpl implements AccountService{

	@Autowired
	AccountRepository repo;
	@Autowired
	CustomerRepository custrepo;
	
	@Transactional
	@Override
	public Account createAccount(Account account) {
		Customer cust=account.getCustomer();
		
		if((custrepo.existsByEmailOrPhoneno(cust.getEmail(), cust.getPhoneno()))) {
			throw new IllegalArgumentException("Customer has already an account!");
		}
		else {
			account.setCustomer(cust);
	        cust.setAccount(account);
	        custrepo.save(cust);
		}
		Account account_saved = repo.save(account);
		return account_saved;	
	}

	@Override
	public List<Account> findAllAccountDetails() {
		List<Account> allAccountDetails=repo.findAll();
		return allAccountDetails;
	}

	@Override
	public Account findAccountByID(Long accountNumber){
		Optional<Account> acc=repo.findById(accountNumber);
		if(acc.isEmpty()) {
			throw new RuntimeException("Account not present");
		}
		Account account_found=acc.get();
		return account_found;
	}

	@Override
	public List<Account> findByAnyField(String input) {
		String result = input.toLowerCase();
		List <Account> collectByField = repo.findAll().stream().filter(account-> String.valueOf(account.getCustomer().getPhoneno()).contains(result)||
				(String.valueOf(account.getAccount_number()).contains(result))||
				(String.valueOf(account.getCustomer().getId()).contains(result))||
				(account.getCustomer().getAccount_holder_name()!=null && account.getCustomer().getAccount_holder_name().toLowerCase().contains(result))||
				(account.getAccount_type().name().toLowerCase()!=null && account.getAccount_type().name().toLowerCase().contains(result))||
				(account.getCustomer().getEmail()!=null && account.getCustomer().getEmail().toLowerCase().contains(result))||
				String.valueOf(account.getBalance()).contains(result)).collect(Collectors.toList());
		return collectByField;
		
	}
//	@Override
//	public void deleteById(Long accountNumber) {
//		Account account_found=findAccountByID(accountNumber);
//		repo.deleteById(account_found.getAccount_number());
//	}
	@Override
	@Transactional
	public void deleteById(Long accountNumber) {

	    Account account = repo.findById(accountNumber)
	            .orElseThrow(() -> new IllegalArgumentException("Account not found"));

	    // Break the relationship
	    Customer customer = account.getCustomer();
	    if (customer != null) {
	        customer.setAccount(null);
	    }

	    repo.delete(account);
	}
	@Override
	public void deleteAll() {
		repo.deleteAll();		
	}

	@Override
	public Account updateAccount(Long accountNumber, Account updated) {
		Account account_found=findAccountByID(accountNumber);
		Customer updated_cust=account_found.getCustomer();
		account_found.setAccount_type(updated.getAccount_type());
	//	account_found.setBalance(updated.getBalance());
		if(account_found.getCustomer()!=null) {
			updated_cust.setAccount_holder_name(updated.getCustomer().getAccount_holder_name());
			updated_cust.setEmail(updated.getCustomer().getEmail());
			updated_cust.setPhoneno(updated.getCustomer().getPhoneno());
		}
		Account account_saved = repo.save(account_found);
		return account_saved;
	}
	
	@Transactional
	@Override
	public String transferFunds(Long fromaccountno, Long toaccountno, Double amount) {
		Account from=repo.findById(fromaccountno).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Source account not found"));
		Account to=repo.findById(toaccountno).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Destination account not found"));
		
		if(from.getBalance()<amount) {
			throw new RuntimeException("Insufficient fund");
		}
		from.setBalance(from.getBalance()-amount);
		to.setBalance(to.getBalance()+amount);
		
		repo.save(from);
		repo.save(to);
		return "Transfer successful from account " + from + " to " + to;
	}
	

}
