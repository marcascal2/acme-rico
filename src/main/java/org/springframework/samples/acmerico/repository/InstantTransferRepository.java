package org.springframework.samples.acmerico.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.acmerico.model.InstantTransfer;

public interface InstantTransferRepository extends CrudRepository<InstantTransfer, Integer> {

}
