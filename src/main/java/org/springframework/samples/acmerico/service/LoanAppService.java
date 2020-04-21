package org.springframework.samples.acmerico.service;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.acmerico.model.LoanApplication;
import org.springframework.samples.acmerico.repository.LoanApplicationRepository;
import org.springframework.stereotype.Service;

@Service
public class LoanAppService {

    @Autowired
    private LoanApplicationRepository loanAppRepository;

    @Autowired
	public LoanAppService(LoanApplicationRepository loanAppRepository) {
		this.loanAppRepository = loanAppRepository;
    }
    
    @Transactional
	public void save(@Valid LoanApplication loanApp) throws DataAccessException {
		this.loanAppRepository.save(loanApp);
	}

}