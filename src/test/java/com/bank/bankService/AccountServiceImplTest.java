package com.bank.bankService;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bank.accountDetails.Account;
import com.bank.accountDetails.Customer;
import com.bank.accountDetails.AccountType;
import com.bank.accountRepository.AccountRepository;
import com.bank.accountRepository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {
	
	@Mock
	private AccountRepository accountRepository;
	
	@Mock
	private CustomerRepository customerRepository;
	
	@InjectMocks
	private AccountServiceImpl accountService;
	
	private Customer cust1;
	private Customer cust2;
	private Account acc1;
	private Account acc2;
	
	@BeforeEach
	void setup() {
		cust1=new Customer();
		cust1.setId(2L);
		cust1.setAccount_holder_name("Anita");
		cust1.setEmail("anita1998@gmail.com");
		cust1.setPhoneno(9874563210L);
		
		acc1=new Account();
		acc1.setCustomer(cust1);
		acc1.setAccount_number(2451378L);
		acc1.setBalance(8000.00);
		acc1.setAccount_type(AccountType.CURRENT);
		
		cust2=new Customer();
		cust2.setId(5L);
		cust2.setAccount_holder_name("Priya Roy");
		cust2.setEmail("priya1999@gmail.com");
		cust2.setPhoneno(9874596330L);
		
		acc2=new Account();
		acc2.setCustomer(cust2);
		acc2.setAccount_number(89674352L);
		acc2.setBalance(10000.00);
		acc2.setAccount_type(AccountType.SAVINGS_JOINT);
		
	}
	
	@Test
	void testFindAllAccountDetails_Empty() {
		when(accountRepository.findAll()).thenReturn(Collections.emptyList());
		List<Account> accounts=accountService.findAllAccountDetails();
		assertTrue(accounts.isEmpty());
	}
	@Test
	void testSaveAccount() {
		Customer customer=new Customer();
		customer.setAccount_holder_name("Ram");
		customer.setEmail("ram877@gmail.com");
		customer.setPhoneno(Long.valueOf(8777234510L));
		
		Account account=new Account();
		account.setCustomer(customer);
		account.setAccount_type(AccountType.SAVINGS_INDIVIDUAL);
		account.setBalance(2000.00);
		when(customerRepository.existsByEmailOrPhoneno(customer.getEmail(), customer.getPhoneno())).thenReturn(false);
		when(accountRepository.save(account)).thenReturn(account);
		Account saved=accountService.createAccount(account);
		Mockito.verify(accountRepository).save(saved);
		assertTrue(saved != null);
	    assertTrue(saved.getCustomer().getEmail().equals("ram877@gmail.com"));
	}
	@Test
	void tesFindAll() {
		Account account1=new Account();
		Account account2=new Account();
		when(accountRepository.findAll()).thenReturn(List.of(account1,account2));
		List<Account> result=accountService.findAllAccountDetails();
		assertEquals(2,result.size());
	}
	@Test
	void testFindById() {
		Account ac=new Account();
		ac.setAccount_number(8L);
		when(accountRepository.findById(8L)).thenReturn(Optional.of(ac));
		Account result=accountService.findAccountByID(8L);
		assertEquals(8L,result.getAccount_number());
	}
	@Test
	void testFindByName() {
		when(accountRepository.findAll()).thenReturn(List.of(acc1,acc2));
		List<Account> acc=accountService.findByAnyField("Priya");
		assertEquals(1,acc.size());
		assertEquals(acc2,acc.get(0));
	}
	@Test
	void testFindByEmail() {
		when(accountRepository.findAll()).thenReturn(List.of(acc1,acc2));
		List<Account> acc=accountService.findByAnyField("anita1998@");
		assertEquals(1,acc.size());
		assertEquals(acc1,acc.get(0));
	}
	@Test
	void testByAccoutNo() {
		when(accountRepository.findAll()).thenReturn(List.of(acc1,acc2));
		List<Account> acc=accountService.findByAnyField("89674352");
		assertEquals(1,acc.size());
		assertEquals(acc2,acc.get(0));
	}
	@Test
	void testByPhoneno() {
		when(accountRepository.findAll()).thenReturn(List.of(acc1,acc2));
		List<Account> acc=accountService.findByAnyField("9874563210");
		assertEquals(1,acc.size());
		assertEquals(acc1,acc.get(0));
	}
	@Test
	void testByAnyFieldNoMatch() {
		when(accountRepository.findAll()).thenReturn(List.of(acc1,acc2));
		List<Account> acc=accountService.findByAnyField("AAbc");
		assertTrue(acc.isEmpty());
	}
	@Test
	void testDeleteById() {
	    when(accountRepository.findById(2451378L)).thenReturn(Optional.of(acc1));
	    doNothing().when(accountRepository).delete(acc1);
	    accountService.deleteById(2451378L);
	    verify(accountRepository).findById(2451378L);
	    verify(accountRepository).delete(acc1);
	    assertNull(acc1.getCustomer().getAccount());
	}

	@Test
	void testdeleteAll() {
		doNothing().when(accountRepository).deleteAll();
		accountService.deleteAll();
		verify(accountRepository).deleteAll();
	}
	@Test
	void testupdateByAccountType() {
		acc1.setAccount_type(AccountType.SAVINGS_INDIVIDUAL);
		
		Customer cust =new Customer();
		cust.setId(2L);
		cust.setAccount_holder_name("Anita");
		cust.setEmail("anita1998@gmail.com");
		cust.setPhoneno(9874563210L);
		
		Account acc=new Account();
		acc.setCustomer(cust);
		acc.setAccount_number(2451378L);
		acc.setBalance(8000.00);
		acc.setAccount_type(AccountType.SAVINGS_INDIVIDUAL);
		
		when(accountRepository.findById(2451378L)).thenReturn(Optional.of(acc1));
		when(accountRepository.save(acc1)).thenReturn(acc1);
		
		Account res=accountService.updateAccount(2451378L, acc);
		assertEquals(acc.getAccount_type(),res.getAccount_type());
	}
	
	
}
