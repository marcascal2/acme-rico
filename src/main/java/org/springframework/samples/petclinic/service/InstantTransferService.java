package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.InstantTransfer;
import org.springframework.samples.petclinic.repository.InstantTransferRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InstantTransferService {

	@Autowired
	private InstantTransferRepository repository;

	@Transactional
	public void save(InstantTransfer entity) {
		this.repository.save(entity);
	}

	@Transactional
	public Integer countInstantTransfers() {
		return (int) this.repository.count();
	}

	public InstantTransfer createInstantTrans(Double transferAmount, String account_number_destination) {
		InstantTransfer res = new InstantTransfer();
		res.setAmount(transferAmount);
		res.setDestination(account_number_destination);
		return res;
	}

}
