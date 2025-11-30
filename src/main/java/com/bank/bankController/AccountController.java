package com.bank.bankController;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.accountDetails.Account;
import com.bank.bankService.AccountService;


@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	AccountService service;
	
	@PostMapping("/create")
	public ResponseEntity<?> createAccount(@RequestBody Account account) {
		try {
			Account newaccount=service.createAccount(account);
			return ResponseEntity.status(HttpStatus.CREATED).body(newaccount);	
		}
		catch(IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@GetMapping("/{accountNumber}")
	public Account findAccountByID(@PathVariable Long accountNumber) {
		Account account=service.findAccountByID(accountNumber);
		return account;
	}
	@GetMapping("/getallaccounts")
	public List<Account> findAllAccountDetails(){
		List<Account> allAccountDetails=service.findAllAccountDetails();
		return allAccountDetails;
	}
	@GetMapping("/getByInput")
	public List<Account> findByAnyField(@RequestParam String input){
		List<Account> allAccountSpecific=service.findByAnyField(input);
		return allAccountSpecific;
	}
	@DeleteMapping("/deleteallaccount")
	public ResponseEntity<String> deleteAccount(){
		service.deleteAll();
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("All accounts deleted");
}
//	@DeleteMapping("/delete/{accountNumber}")
//	public ResponseEntity<String> deleteAccountById(@PathVariable Long accountNumber){
//		service.deleteById(accountNumber);
//		return ResponseEntity.status(HttpStatus.OK).body("Account closed for this account number "+accountNumber);
//	}
	@DeleteMapping("/delete/{accountNumber}")
	public ResponseEntity<String> deleteAccountById(@PathVariable Long accountNumber){
	    try {
	        service.deleteById(accountNumber);
	        return ResponseEntity.ok("Account closed for account number " + accountNumber);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Error deleting account: " + e.getMessage());
	    }
	}
	@PutMapping("/update/{accountNumber}")
	public ResponseEntity<Account> updateAccount(@PathVariable Long accountNumber,@RequestBody Account account){
		Account updated=service.updateAccount(accountNumber, account);
		return ResponseEntity.status(HttpStatus.OK).body(updated);
	}
	@PostMapping("/transfer")
	public ResponseEntity<?> transferFunds(@RequestParam Long fromaccountno,@RequestParam Long toaccountno,@RequestParam Double amount){
		try {
			String message=service.transferFunds(fromaccountno,toaccountno,amount);
			
			return ResponseEntity.status(HttpStatus.OK).body(Map.of("message",message));//Return 200 OK
		}
		catch(RuntimeException e) {
			//return 400 Bad Request for business logic error
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error",e.getMessage()));
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error","Unexpected error occurred"));
		}
	}
}
