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

    public PartnerView (PartnerService partnerService){
        this.partnerService = partnerService;
    }
    public void createPartner() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Company Name:");
        String companyName = sc.nextLine();

        System.out.println("Enter Commercial Contact:");
        String commercialContact = sc.nextLine();


        String transportType = "";
        boolean validTransportType = false;
        while (!validTransportType) {
            System.out.println("Enter Transport Type (BUS, TRAIN, PLANE):");
            transportType = sc.nextLine();
            try {
                TRANSPORTTYPE.valueOf(transportType.toUpperCase());
                validTransportType = true; // Exit loop when valid
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Invalid transport type. Valid values are BUS, TRAIN, PLANE.");
            }
        }

        System.out.println("Enter Geographic Area:");
        String geographicArea = sc.nextLine();

        System.out.println("Enter Special Conditions:");
        String specialConditions = sc.nextLine();

        String partnershipStatus = "";
        boolean validPartnershipStatus = false;
        while (!validPartnershipStatus) {
            System.out.println("Enter Partnership Status (ACTIVE, INACTIVE, SUSPENDED):");
            partnershipStatus = sc.nextLine();
            try {
                PARTNERSHIPSTATUS.valueOf(partnershipStatus.toUpperCase());
                validPartnershipStatus = true; // Exit loop when valid
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Invalid partnership status. Valid values are ACTIVE, INACTIVE, SUSPENDED.");
            }
        }

        Partner partner = new Partner(
                UUID.randomUUID(),
                companyName,
                commercialContact,
                TRANSPORTTYPE.valueOf(transportType.toUpperCase()),
                geographicArea,
                specialConditions,
                PARTNERSHIPSTATUS.valueOf(partnershipStatus.toUpperCase())
        );

        partnerService.createPartner(partner);
        System.out.println("Partner created successfully!");
    }


    public void getAllPartners(){
        List<Partner> partners = partnerService.getAllPartners();

        if (partners.isEmpty()){
            System.out.println("No Partners in the database so far!");
        }else {
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
        Scanner sc = new Scanner(System.in);

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
        Scanner sc = new Scanner(System.in);


        System.out.println("Enter The Id Of The Partner You Wish To Update: ");
        String userInput = sc.nextLine();


        try {
            if (!PartnerValidation.isValidUUID(userInput)) {
                throw new IllegalArgumentException("Invalid UUID format");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }


        System.out.println("Enter Company Name:");
        String companyName = sc.nextLine();

        System.out.println("Enter Commercial Contact:");
        String commercialContact = sc.nextLine();


        String transportType = "";
        boolean validTransportType = false;
        while (!validTransportType) {
            System.out.println("Enter Transport Type (BUS, TRAIN, PLANE):");
            transportType = sc.nextLine();
            try {
                TRANSPORTTYPE.valueOf(transportType.toUpperCase());
                validTransportType = true; // Exit loop when valid
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Invalid transport type. Valid values are BUS, TRAIN, PLANE.");
            }
        }

        System.out.println("Enter Geographic Area:");
        String geographicArea = sc.nextLine();

        System.out.println("Enter Special Conditions:");
        String specialConditions = sc.nextLine();


        String partnershipStatus = "";
        boolean validPartnershipStatus = false;
        while (!validPartnershipStatus) {
            System.out.println("Enter Partnership Status (ACTIVE, INACTIVE, SUSPENDED):");
            partnershipStatus = sc.nextLine();
            try {
                PARTNERSHIPSTATUS.valueOf(partnershipStatus.toUpperCase());
                validPartnershipStatus = true; // Exit loop when valid
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Invalid partnership status. Valid values are ACTIVE, INACTIVE, SUSPENDED.");
            }
        }

        try {
            Partner partner = new Partner(
                    UUID.fromString(userInput),
                    companyName,
                    commercialContact,
                    TRANSPORTTYPE.valueOf(transportType.toUpperCase()),
                    geographicArea,
                    specialConditions,
                    PARTNERSHIPSTATUS.valueOf(partnershipStatus.toUpperCase())
            );

            Partner updatedPartner = partnerService.updatePartner(userInput, partner);

            if (updatedPartner != null) {
                System.out.println("Partner Updated successfully!");
            } else {
                System.out.println("Partner with ID: " + userInput + " Not found");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid ID");
        }
    }


    public void findPartnerById() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Partner ID:");
        String userInput = sc.nextLine();
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


}
