package org.youcode.ecomove.views;

import org.youcode.ecomove.entities.Partner;
import org.youcode.ecomove.enums.PARTNERSHIPSTATUS;
import org.youcode.ecomove.enums.TRANSPORTTYPE;
import org.youcode.ecomove.services.PartnerService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class PartnerView {
    private final PartnerService partnerService;

    public PartnerView (PartnerService partnerService){
        this.partnerService = partnerService;
    }
    public void createPartner(){
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Company Name");
        String companyName = sc.nextLine();

        System.out.println("Enter Commercial Contact:");
        String commercialContact = sc.nextLine();

        System.out.println("Enter Transport Type (BUS, TRAIN, PLANE):");
        String transportType = sc.nextLine();

        System.out.println("Enter Geographic Area:");
        String geographicArea = sc.nextLine();

        System.out.println("Enter Special Conditions:");
        String specialConditions = sc.nextLine();

        System.out.println("Enter Partnership Status (ACTIVE, INACTIVE, SUSPENDED):");
        String partnershipStatus = sc.nextLine();

        Partner partner = new Partner(UUID.randomUUID(),companyName, commercialContact,
                TRANSPORTTYPE.valueOf(transportType), geographicArea, specialConditions,
                PARTNERSHIPSTATUS.valueOf(partnershipStatus));

        partnerService.createPartner(partner);
    }

    public void getAllPartners(){
        List<Partner> partners = partnerService.getAllPartners();

        if (partners.isEmpty()){
            System.out.println("No Partners in the database so far!");
        }else {
            System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                                                                 Partners List                                                                                               ║");
            System.out.println("╠═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
            System.out.printf("║ %-36s │ %-25s │ %-15s │ %-20s │ %-25s │ %-30s │ %-15s ║%n",
                    "Partner ID", "Company Name", "Transport Type", "Geographic Area", "Special Conditions", "Commercial Contact", "Partnership Status");
            System.out.println("╠═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");

            for (Partner partner : partners) {
                System.out.printf("║ %-36s │ %-25s │ %-15s │ %-20s │ %-25s │ %-30s │ %-15s ║%n",
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

}
