package org.springframework.samples.petclinic.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.TransferApplication;

public interface TransferAppRepository extends CrudRepository<TransferApplication, Integer> {

//	@Query("SELECT tapp FROM TransferApplication tapp WHERE tapp.bankAccount.id =:bank_account_id")
//	Collection<TransferApplication> findTransferApplicationsByAccountId(int bankAccountId)
//			throws DataAccessException;

}
