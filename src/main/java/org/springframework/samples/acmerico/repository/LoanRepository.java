package org.springframework.samples.acmerico.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.acmerico.model.Loan;

public interface LoanRepository extends CrudRepository<Loan, Integer> {

}
