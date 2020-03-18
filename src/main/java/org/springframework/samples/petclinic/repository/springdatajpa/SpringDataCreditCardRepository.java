package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.CreditCard;
import org.springframework.samples.petclinic.repository.CreditCardRepository;

public interface SpringDataCreditCardRepository extends CreditCardRepository, Repository<CreditCard, Integer> {

}
