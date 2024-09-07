package org.youcode.ecomove.views;

import org.youcode.ecomove.entities.Contract;
import org.youcode.ecomove.entities.Partner;
import org.youcode.ecomove.enums.CONTRACTSTATUS;
import org.youcode.ecomove.services.ContractService;
import org.youcode.ecomove.services.PartnerService;
import org.youcode.ecomove.utils.ContractValidation;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ContractView {
    private final ContractService contractService;
    private final PartnerService partnerService;
    private final Scanner sc;

    public ContractView(ContractService contractService, PartnerService partnerService) {
        this.contractService = contractService;
        this.partnerService = partnerService;
        this.sc = new Scanner(System.in);
    }

    public void createContract() {

        try {
            String partnerId = ContractValidation.getValidInput("Enter Partner ID:", "Partner ID");
            Partner partner = partnerService.findPartnerByID(partnerId);

            if (partner == null) {
                System.out.println("Partner not found. Cannot create contract.");
                return;
            }

            LocalDate startDate = LocalDate.parse(ContractValidation.getValidInput("Enter Start Date (YYYY-MM-DD):", "Start date"));
            LocalDate endDate = LocalDate.parse(ContractValidation.getValidInput("Enter End Date (YYYY-MM-DD):", "End date"));
            int specialPrice = Integer.parseInt(ContractValidation.getValidInput("Enter Special Price:", "Special price"));
            String agreementConditions = ContractValidation.getValidInput("Enter Agreement Conditions:", "Agreement conditions");
            boolean renewable = Boolean.parseBoolean(ContractValidation.getValidInput("Is the Contract Renewable? (true/false):", "Renewable status"));
            CONTRACTSTATUS contractStatus = ContractValidation.getValidContractStatus();

            Contract contract = new Contract(
                    UUID.randomUUID(),
                    startDate,
                    endDate,
                    specialPrice,
                    agreementConditions,
                    renewable,
                    contractStatus,
                    partner
            );

            contractService.createContract(contract);
            System.out.println("Contract created successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void getAllContracts() {
        List<Contract> contracts = contractService.getAllContracts();

        if (contracts.isEmpty()) {
            System.out.println("No contracts in the database.");
        } else {
            System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                                   Contracts List                                                                                  ║");
            System.out.println("╠══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
            System.out.printf("║ %-36s │ %-15s │ %-15s │ %-15s │ %-15s │ %-10s │ %-10s ║%n",
                    "Contract ID", "Start Date", "End Date", "Special Price", "Agreement Conditions", "Renewable", "Contract Status");
            System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");

            for (Contract contract : contracts) {
                System.out.printf("║ %-36s │ %-15s │ %-15s │ %-15d │ %-25s │ %-10s │ %-15s ║%n",
                        contract.getContractId(),
                        contract.getStartDate(),
                        contract.getEndDate(),
                        contract.getSpecialPrice(),
                        contract.getAgreementConditions(),
                        contract.getRenewable(),
                        contract.getContractStatus());
            }
        }
    }

    public void deleteContractById() {
        System.out.println("Enter Contract's ID You Want To Delete:");
        String contractId = sc.nextLine();

        try {
            if (contractService.deleteContractById(contractId)) {
                System.out.println("Contract deleted successfully.");
            } else {
                System.out.println("Contract with ID: " + contractId + " not found.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid ID");
        }
    }

    public void updateContract() {
        try {
            String contractId = ContractValidation.getValidInput("Enter The Id Of The Contract You Wish To Update:", "Contract ID");
            Contract existingContract = contractService.findContractById(contractId);

            if (existingContract == null) {
                System.out.println("Contract not found.");
                return;
            }

            String partnerId = ContractValidation.getValidInput("Enter Partner ID:", "Partner ID");
            Partner partner = partnerService.findPartnerByID(partnerId);

            if (partner == null) {
                System.out.println("Partner not found. Cannot create contract.");
                return;
            }

            LocalDate startDate = LocalDate.parse(ContractValidation.getValidInput("Enter Start Date (YYYY-MM-DD):", "Start date"));
            LocalDate endDate = LocalDate.parse(ContractValidation.getValidInput("Enter End Date (YYYY-MM-DD):", "End date"));
            int specialPrice = Integer.parseInt(ContractValidation.getValidInput("Enter Special Price:", "Special price"));
            String agreementConditions = ContractValidation.getValidInput("Enter Agreement Conditions:", "Agreement conditions");
            boolean renewable = Boolean.parseBoolean(ContractValidation.getValidInput("Is the Contract Renewable? (true/false):", "Renewable status"));
            CONTRACTSTATUS contractStatus = ContractValidation.getValidContractStatus();

            Contract contract = new Contract(
                    UUID.fromString(contractId),
                    startDate,
                    endDate,
                    specialPrice,
                    agreementConditions,
                    renewable,
                    contractStatus,
                    partner
            );

            Contract updatedContract = contractService.updateContract(contractId, contract);

            if (updatedContract != null) {
                System.out.println("Contract updated successfully!");
            } else {
                System.out.println("Contract not found.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void findContractsByPartnerId() {
        System.out.println("Enter Partner ID to find associated contracts:");
        String partnerId = sc.nextLine();

        try {
            List<Contract> contracts = contractService.findContractsByPartnerId(partnerId);
            if (contracts.isEmpty()) {
                System.out.println("No contracts found for this partner.");
            } else {
                System.out.println("Contracts for Partner ID: " + partnerId);
                System.out.println("╔══════════════════════════════════════════════════════════════════════════╗");
                for (Contract contract : contracts) {
                    displayContractDetails(contract);
                    System.out.println("╠══════════════════════════════════════════════════════════════════════════╣");
                }
                System.out.println("╚══════════════════════════════════════════════════════════════════════════╝");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid Partner ID.");
        }
    }

    public void findContractById() {
        System.out.println("Enter Contract ID to find:");
        String contractId = sc.nextLine();

        try {
            Contract contract = contractService.findContractById(contractId);
            if (contract != null) {
                displayContractDetails(contract);
            } else {
                System.out.println("Contract not found.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid ID");
        }
    }

    private void displayContractDetails(Contract contract) {
        System.out.println("╔══════════════════════════════════════════════════════════════════════════╗");
        System.out.printf("║ Contract ID: %-50s ║%n", contract.getContractId());
        System.out.printf("║ Start Date: %-52s ║%n", contract.getStartDate());
        System.out.printf("║ End Date: %-54s ║%n", contract.getEndDate());
        System.out.printf("║ Special Price: %-50d ║%n", contract.getSpecialPrice());
        System.out.printf("║ Agreement Conditions: %-43s ║%n", contract.getAgreementConditions());
        System.out.printf("║ Renewable: %-53s ║%n", contract.getRenewable());
        System.out.printf("║ Contract Status: %-47s ║%n", contract.getContractStatus());
        System.out.printf("║ Partner Company Name: %-47s ║%n", contract.getPartner().getCompanyName());
        System.out.println("╚══════════════════════════════════════════════════════════════════════════╝");
    }
}
