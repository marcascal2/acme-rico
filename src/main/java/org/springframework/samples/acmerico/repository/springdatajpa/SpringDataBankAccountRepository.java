package org.springframework.samples.acmerico.repository.springdatajpa;

import org.springframework.data.repository.Repository;
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.repository.BankAccountRepository;

public interface SpringDataBankAccountRepository extends BankAccountRepository, Repository<BankAccount, Integer> {
	
}
