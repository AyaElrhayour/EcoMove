package org.youcode.ecomove.submenu;

import org.youcode.ecomove.dao.PartnerDao;
import org.youcode.ecomove.dao.daoImpl.PartnerDaoImpl;
import org.youcode.ecomove.services.PartnerService;
import org.youcode.ecomove.views.PartnerView;

import java.util.Scanner;

public class PartnerMenu {


    public static void partnerManagementMenu() {

        Scanner scanner = new Scanner(System.in);
        PartnerDaoImpl partnerDaoImpl = new PartnerDaoImpl();
        PartnerService partnerService = new PartnerService(partnerDaoImpl);
        PartnerView partnerView = new PartnerView(partnerService);


        while (true) {

            System.out.println(" -----------------------------");
            System.out.println("|                             |");
            System.out.println("|      Partner Management     |");
            System.out.println("|  1 : Add Partner            |");
            System.out.println("|  2 : List All Partner       |");
            System.out.println("|  3 : Remove Partner         |");
            System.out.println("|  4 : Find a Partner         |");
            System.out.println("|  5 : Modifier Partner       |");
            System.out.println("|  6 : Return                 |");
            System.out.println("|                             |");
            System.out.println(" -----------------------------");
            System.out.print("Enter Your Choice : ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    partnerView.createPartner();
                    break;
                case 2:
                    partnerView.getAllPartners();

                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }
    }
}
