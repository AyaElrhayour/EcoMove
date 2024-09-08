package org.youcode.ecomove.views;

import org.youcode.ecomove.entities.Discount;
import org.youcode.ecomove.entities.Contract;
import org.youcode.ecomove.enums.DISCOUNTTYPE;
import org.youcode.ecomove.enums.OFFERSTATUS;
import org.youcode.ecomove.services.DiscountService;
import org.youcode.ecomove.services.ContractService;
import org.youcode.ecomove.utils.DiscountValidation;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class DiscountView {
    private final DiscountService discountService;
    private final ContractService contractService;
    private final Scanner sc;

    public DiscountView(DiscountService discountService, ContractService contractService) {
        this.discountService = discountService;
        this.contractService = contractService;
        this.sc = new Scanner(System.in);
    }


    public void createDiscount() {
        try {
            String contractId = DiscountValidation.getValidInput("Enter Contract ID:", "Contract ID");
            Contract contract = contractService.findContractById(contractId);

            if (contract == null) {
                System.out.println("Contract not found. Cannot create discount.");
                return;
            }

            String offerName = DiscountValidation.getValidInput("Enter Offer Name:", "Offer Name");
            String description = DiscountValidation.getValidInput("Enter Description:", "Description");
            LocalDate startDate = LocalDate.parse(DiscountValidation.getValidInput("Enter Start Date (YYYY-MM-DD):", "Start date"));
            LocalDate endDate = LocalDate.parse(DiscountValidation.getValidInput("Enter End Date (YYYY-MM-DD):", "End date"));
            DiscountValidation.validateDateRange(startDate, endDate);
            DISCOUNTTYPE discountType = DiscountValidation.getValidDiscountType();
            OFFERSTATUS offerStatus = DiscountValidation.getValidOfferStatus();
            String conditions = DiscountValidation.getValidInput("Enter Conditions:", "Conditions");

            Discount discount = new Discount(
                    UUID.randomUUID(),
                    offerName,
                    description,
                    startDate,
                    endDate,
                    discountType,
                    conditions,
                    offerStatus,
                    contract
            );

            discountService.createDiscount(discount);
            System.out.println("Discount created successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public void getAllDiscounts() {
        List<Discount> discounts = discountService.getAllDiscounts();

        if (discounts.isEmpty()) {
            System.out.println("No discounts available.");
        } else {
            System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                                   Discounts List                                                                                  ║");
            System.out.println("╠══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
            System.out.printf("║ %-36s │ %-15s │ %-15s │ %-15s │ %-15s │ %-15s ║%n",
                    "Discount ID", "Offer Name", "Start Date", "End Date", "Discount Type", "Offer Status");
            System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");

            for (Discount discount : discounts) {
                System.out.printf("║ %-36s │ %-15s │ %-15s │ %-15s │ %-15s │ %-15s ║%n",
                        discount.getDiscountId(),
                        discount.getOfferName(),
                        discount.getStartDate(),
                        discount.getEndDate(),
                        discount.getDiscountType(),
                        discount.getOfferStatus());
            }
        }
    }


    public void deleteDiscountById() {
        System.out.println("Enter Discount's ID You Want To Delete:");
        String discountId = sc.nextLine();

        try {
            if (discountService.deleteDiscountById(discountId)) {
                System.out.println("Discount deleted successfully.");
            } else {
                System.out.println("Discount with ID: " + discountId + " not found.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid ID");
        }
    }


    public void updateDiscount() {
        try {
            String discountId = DiscountValidation.getValidInput("Enter The Id Of The Discount You Wish To Update:", "Discount ID");
            Discount existingDiscount = discountService.findDiscountById(discountId);

            if (existingDiscount == null) {
                System.out.println("Discount not found.");
                return;
            }

            String contractId = DiscountValidation.getValidInput("Enter Contract ID:", "Contract ID");
            Contract contract = contractService.findContractById(contractId);

            if (contract == null) {
                System.out.println("Contract not found. Cannot update discount.");
                return;
            }

            String offerName = DiscountValidation.getValidInput("Enter Offer Name:", "Offer Name");
            String description = DiscountValidation.getValidInput("Enter Description:", "Description");
            LocalDate startDate = LocalDate.parse(DiscountValidation.getValidInput("Enter Start Date (YYYY-MM-DD):", "Start date"));
            LocalDate endDate = LocalDate.parse(DiscountValidation.getValidInput("Enter End Date (YYYY-MM-DD):", "End date"));
            DiscountValidation.validateDateRange(startDate, endDate);
            DISCOUNTTYPE discountType = DiscountValidation.getValidDiscountType();
            OFFERSTATUS offerStatus = DiscountValidation.getValidOfferStatus();
            String conditions = DiscountValidation.getValidInput("Enter Conditions:", "Conditions");

            Discount updatedDiscount = new Discount(
                    UUID.fromString(discountId),
                    offerName,
                    description,
                    startDate,
                    endDate,
                    discountType,
                    conditions,
                    offerStatus,
                    contract
            );

            Discount result = discountService.updateDiscount(discountId, updatedDiscount);

            if (result != null) {
                System.out.println("Discount updated successfully!");
            } else {
                System.out.println("Discount not found.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void findDiscountById() {
        System.out.println("Enter Discount ID to find:");
        String discountId = sc.nextLine();

        try {
            Discount discount = discountService.findDiscountById(discountId);
            if (discount != null) {
                displayDiscountDetails(discount);
            } else {
                System.out.println("Discount not found.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid ID");
        }
    }

    public void findDiscountsByContractId() {
        System.out.println("Enter Contract ID to find associated discounts:");
        String contractId = sc.nextLine();

        try {
            List<Discount> discounts = discountService.findDiscountsByContractId(contractId);
            if (discounts.isEmpty()) {
                System.out.println("No discounts found for this contract.");
            } else {
                System.out.println("Discounts for Contract ID: " + contractId);
                System.out.println("╔════════════════════════════════════════════════════════════════════════════════════╗");
                for (Discount discount : discounts) {
                    displayDiscountDetails(discount);
                    System.out.println("╠════════════════════════════════════════════════════════════════════════════════════╣");
                }
                System.out.println("╚════════════════════════════════════════════════════════════════════════════════════╝");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid Contract ID.");
        }
    }


    private void displayDiscountDetails(Discount discount) {
        System.out.println("╔══════════════════════════════════════════════════════════════════════════╗");
        System.out.printf("║ Discount ID: %-50s ║%n", discount.getDiscountId());
        System.out.printf("║ Offer Name: %-50s ║%n", discount.getOfferName());
        System.out.printf("║ Description: %-50s ║%n", discount.getDescription());
        System.out.printf("║ Start Date: %-52s ║%n", discount.getStartDate());
        System.out.printf("║ End Date: %-54s ║%n", discount.getEndDate());
        System.out.printf("║ Discount Type: %-50s ║%n", discount.getDiscountType());
        System.out.printf("║ Offer Status: %-50s ║%n", discount.getOfferStatus());
        System.out.printf("║ Conditions: %-50s ║%n", discount.getConditions());
        System.out.printf("║ Associated Contract ID: %-43s ║%n", discount.getContract().getContractId());
        System.out.println("╚══════════════════════════════════════════════════════════════════════════╝");
    }
}
