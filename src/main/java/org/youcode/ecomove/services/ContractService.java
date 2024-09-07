package org.youcode.ecomove.services;

import org.youcode.ecomove.dao.ContractDao;
import org.youcode.ecomove.entities.Contract;
import org.youcode.ecomove.utils.ContractValidation;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ContractService {
    private final ContractDao contractDao;

    public ContractService(ContractDao contractDao) {
        this.contractDao = contractDao;
    }

    public void createContract(Contract contract) {
        Optional<Contract> createdContract = contractDao.create(contract);
        if (createdContract.isPresent()) {
            System.out.println("Contract created successfully!");
        } else {
            System.out.println("Failed to create contract.");
        }
    }

    public Contract findContractById(String contractId) {
        if (!ContractValidation.isUuidValid(contractId)) {
            System.out.println("Invalid UUID format.");
            return null;
        }

        UUID id = UUID.fromString(contractId);
        Optional<Contract> contract = contractDao.findById(id);
        return contract.orElse(null);
    }

    public List<Contract> getAllContracts() {
        return contractDao.getAll();
    }

    public List<Contract> findContractsByPartnerId(String partnerId) {
        if (!ContractValidation.isUuidValid(partnerId)) {
            throw new IllegalArgumentException("Invalid UUID format");
        }
        UUID id = UUID.fromString(partnerId);
        return contractDao.getContractsByPartnerId(id);
    }

    public boolean deleteContractById(String contractId) {
        if (!ContractValidation.isUuidValid(contractId)) {
            throw new IllegalArgumentException("Invalid UUID format");
        }
        UUID id = UUID.fromString(contractId);
        return contractDao.delete(id);
    }

    public Contract updateContract(String contractId, Contract contract) {
        if (!ContractValidation.isUuidValid(contractId)) {
            throw new IllegalArgumentException("Invalid UUID format");
        }

        UUID id = UUID.fromString(contractId);
        Optional<Contract> updatedContract = contractDao.update(id, contract);
        return updatedContract.orElse(null);
    }
}
