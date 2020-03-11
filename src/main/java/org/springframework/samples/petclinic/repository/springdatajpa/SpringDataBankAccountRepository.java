package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.BankAccount;
import org.springframework.samples.petclinic.repository.BankAccountRepository;

public interface SpringDataBankAccountRepository extends BankAccountRepository, Repository<BankAccount, Integer> {
	
}
