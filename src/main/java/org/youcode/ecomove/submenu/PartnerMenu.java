package org.youcode.ecomove.submenu;

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
            displayMenu();
            int choice = getValidChoice(scanner);

            switch (choice) {
                case 1:
                    partnerView.createPartner();
                    break;
                case 2:
                    partnerView.getAllPartners();
                    break;
                case 3:
                    partnerView.deletePartnerById();
                    break;
                case 4:
                    partnerView.findPartnerById();
                    break;
                case 5:
                    partnerView.updatePartner();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }
    }

    private static void displayMenu() {
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║                             ║");
        System.out.println("║      Partner Management     ║");
        System.out.println("║  1 : Add Partner            ║");
        System.out.println("║  2 : List All Partner       ║");
        System.out.println("║  3 : Remove Partner         ║");
        System.out.println("║  4 : Find a Partner         ║");
        System.out.println("║  5 : Modifier Partner       ║");
        System.out.println("║  6 : Return                 ║");
        System.out.println("║                             ║");
        System.out.println("╚═════════════════════════════╝");
    }

    private static int getValidChoice(Scanner scanner) {
        while (true) {
            System.out.print("Enter Your Choice : ");
            String input = scanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= 6) {
                    return choice;
                } else {
                    System.out.println("Please enter a number between 1 and 6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}