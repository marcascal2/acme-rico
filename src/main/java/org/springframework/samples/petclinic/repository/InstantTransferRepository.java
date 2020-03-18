package org.springframework.samples.petclinic.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.InstantTransfer;

public interface InstantTransferRepository extends CrudRepository<InstantTransfer, Integer> {


}
