package org.youcode.ecomove.views;

import org.youcode.ecomove.entities.Partner;
import org.youcode.ecomove.enums.PARTNERSHIPSTATUS;
import org.youcode.ecomove.enums.TRANSPORTTYPE;
import org.youcode.ecomove.services.PartnerService;
import org.youcode.ecomove.utils.PartnerValidation;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class PartnerView {
    private final PartnerService partnerService;
    private final Scanner sc;

    public PartnerView(PartnerService partnerService) {
        this.partnerService = partnerService;
        this.sc = new Scanner(System.in);
    }

    public void createPartner() {
        try {
            String companyName = getValidInput("Enter Company Name:", "Company name");
            String commercialContact = getValidInput("Enter Commercial Contact:", "Commercial contact");
            TRANSPORTTYPE transportType = getValidTransportType();
            String geographicArea = getValidInput("Enter Geographic Area:", "Geographic area");
            String specialConditions = getValidInput("Enter Special Conditions:", "Special conditions");
            PARTNERSHIPSTATUS partnershipStatus = getValidPartnershipStatus();

            Partner partner = new Partner(
                    UUID.randomUUID(),
                    companyName,
                    commercialContact,
                    transportType,
                    geographicArea,
                    specialConditions,
                    partnershipStatus
            );

            partnerService.createPartner(partner);
            System.out.println("Partner created successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void getAllPartners() {
        List<Partner> partners = partnerService.getAllPartners();

        if (partners.isEmpty()) {
            System.out.println("No Partners in the database so far!");
        } else {
            System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                                                                 Partners List                                                                           ║");
            System.out.println("╠═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
            System.out.printf("║ %-36s │ %-15s │ %-15s │ %-20s │ %-25s │ %-20s │ %-15s ║%n",
                    "Partner ID", "Company Name", "Transport Type", "Geographic Area", "Special Conditions", "Commercial Contact", "Partnership Status");
            System.out.println("╚═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");

            for (Partner partner : partners) {
                System.out.printf("║ %-36s │ %-15s │ %-15s │ %-20s │ %-25s │ %-20s │ %-18s ║%n",
                        partner.getPartnerId(),
                        partner.getCompanyName(),
                        partner.getTransportType(),
                        partner.getGeographicArea(),
                        partner.getSpecialConditions(),
                        partner.getCommercialContact(),
                        partner.getPartnershipStatus());
            }
        }
    }

    public void deletePartnerById() {
        System.out.println("Enter Partner's ID You Want To Delete : ");
        String partnerId = sc.nextLine();

        try {
            if (partnerService.deletePartnerById(partnerId)) {
                System.out.println("Partner was deleted successfully");
            } else {
                System.out.println("Partner with ID: " + partnerId + " Not found");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid ID");
        }
    }

    public void updatePartner() {
        try {


            String partnerId = getValidInput("Enter The Id Of The Partner You Wish To Update:", "Partner ID");
            if (partnerService.findPartnerByID(String.valueOf(UUID.fromString(partnerId))) == null)
                throw new IllegalArgumentException("Partner not found");

            String companyName = getValidInput("Enter Company Name:", "Company name");
            String commercialContact = getValidInput("Enter Commercial Contact:", "Commercial contact");
            TRANSPORTTYPE transportType = getValidTransportType();
            String geographicArea = getValidInput("Enter Geographic Area:", "Geographic area");
            String specialConditions = getValidInput("Enter Special Conditions:", "Special conditions");
            PARTNERSHIPSTATUS partnershipStatus = getValidPartnershipStatus();

            Partner partner = new Partner(
                    UUID.fromString(partnerId),
                    companyName,
                    commercialContact,
                    transportType,
                    geographicArea,
                    specialConditions,
                    partnershipStatus
            );

            Partner updatedPartner = partnerService.updatePartner(partnerId, partner);

            if (updatedPartner != null) {
                System.out.println("Partner Updated successfully!");
            } else {
                System.out.println("Partner with ID: " + partnerId + " Not found");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void findPartnerById() {
        System.out.println("Enter Partner ID:");
        String userInput = sc.nextLine();
        System.out.println(userInput);
        Partner partner = partnerService.findPartnerByID(userInput);

        if (partner != null) {
            System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                                                                 Partner                                                                                 ║");
            System.out.println("╠═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
            System.out.printf("║ %-36s │ %-15s │ %-15s │ %-20s │ %-25s │ %-20s │ %-15s ║%n",
                    "Partner ID", "Company Name", "Transport Type", "Geographic Area", "Special Conditions", "Commercial Contact", "Partnership Status");
            System.out.println("╚═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");

            System.out.printf("║ %-36s │ %-15s │ %-15s │ %-20s │ %-25s │ %-20s │ %-18s ║%n",
                    partner.getPartnerId(),
                    partner.getCompanyName(),
                    partner.getTransportType(),
                    partner.getGeographicArea(),
                    partner.getSpecialConditions(),
                    partner.getCommercialContact(),
                    partner.getPartnershipStatus());
        } else {
            System.out.println("Partner Not Found");
        }
    }

    private String getValidInput(String prompt, String fieldName) {
        System.out.println(prompt);
        String input = sc.nextLine();
        PartnerValidation.validateNotEmpty(input, fieldName);
        return input;
    }

    private TRANSPORTTYPE getValidTransportType() {
        while (true) {
            try {
                return PartnerValidation.validateTransportType(getValidInput("Enter Transport Type (BUS, TRAIN, PLANE):", "Transport type"));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private PARTNERSHIPSTATUS getValidPartnershipStatus() {
        while (true) {
            try {
                return PartnerValidation.validatePartnershipStatus(getValidInput("Enter Partnership Status (ACTIVE, INACTIVE, SUSPENDED):", "Partnership status"));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}