package org.springframework.samples.petclinic.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Transfer;
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

}
