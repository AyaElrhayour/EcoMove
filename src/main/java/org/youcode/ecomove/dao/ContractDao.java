package org.youcode.ecomove.dao;

import org.youcode.ecomove.entities.Contract;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContractDao {
    Optional<Contract> create(Contract contract);
    Optional<Contract> findById(UUID contractId);
    List<Contract> getAll();
    List<Contract> getContractsByPartnerId(UUID partnerId);
    boolean delete(UUID contractId);
    Optional<Contract> update(UUID contractId , Contract contract);
}
