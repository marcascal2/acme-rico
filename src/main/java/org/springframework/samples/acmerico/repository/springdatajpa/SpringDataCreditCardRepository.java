package org.springframework.samples.acmerico.repository.springdatajpa;

import org.springframework.data.repository.Repository;
import org.springframework.samples.acmerico.model.CreditCard;
import org.springframework.samples.acmerico.repository.CreditCardRepository;

public interface SpringDataCreditCardRepository extends CreditCardRepository, Repository<CreditCard, Integer> {

}
