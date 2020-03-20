package org.springframework.samples.acmerico.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.acmerico.model.InstantTransfer;
import org.springframework.samples.acmerico.repository.InstantTransferRepository;
import org.springframework.stereotype.Service;

@Service
public class InstantTransferService {
	
	@Autowired
	private InstantTransferRepository instantRepository;

	public void save(InstantTransfer entity) {
		this.instantRepository.save(entity);
		
	}

}
