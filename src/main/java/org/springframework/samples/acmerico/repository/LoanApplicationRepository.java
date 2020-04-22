package org.springframework.samples.acmerico.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.acmerico.model.LoanApplication;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanApplicationRepository extends CrudRepository<LoanApplication, Integer> {

}