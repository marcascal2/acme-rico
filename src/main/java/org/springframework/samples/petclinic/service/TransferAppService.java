package org.springframework.samples.petclinic.service;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.TransferApplication;
import org.springframework.samples.petclinic.repository.TransferAppRepository;
import org.springframework.stereotype.Service;

@Service
public class TransferAppService {

	@Autowired
	private TransferAppRepository transferAppRepository;

	@Transactional
	public Collection<TransferApplication> findAllTransfersApplications() {
		return (Collection<TransferApplication>) this.transferAppRepository.findAll();

	}
	@Transactional
	public TransferApplication findTransferAppById(int transferAppId) {
		return this.transferAppRepository.findById(transferAppId).get();
	}
	@Transactional
	public void save(@Valid TransferApplication transferApp) throws DataAccessException {
		this.transferAppRepository.save(transferApp);
	}

}
