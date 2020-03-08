package org.springframework.samples.petclinic.service;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Transfer;
import org.springframework.samples.petclinic.repository.TransferRepository;
import org.springframework.stereotype.Service;

@Service
public class TransferService {

	@Autowired
	private TransferRepository transferRepository;

	@Transactional
	public Collection<Transfer> findAllTransfers() {
		return (Collection<Transfer>) this.transferRepository.findAll();

	}

	@Transactional
	public int count() {
		return (int) this.transferRepository.count();
	}

	@Transactional
	public void save(@Valid Transfer transfer) throws DataAccessException {
		this.transferRepository.save(transfer);
	}

}
