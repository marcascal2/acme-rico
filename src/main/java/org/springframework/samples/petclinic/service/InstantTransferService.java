package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.InstantTransfer;
import org.springframework.samples.petclinic.repository.InstantTransferRepository;
import org.springframework.stereotype.Service;

@Service
public class InstantTransferService {
	
	@Autowired
	private InstantTransferRepository instantRepository;

	public void save(InstantTransfer entity) {
		this.instantRepository.save(entity);
		
	}

}
