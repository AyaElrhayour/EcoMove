package org.youcode.ecomove.submenu;

import org.youcode.ecomove.dao.daoImpl.ContractDaoImpl;
import org.youcode.ecomove.dao.daoImpl.PartnerDaoImpl;
import org.youcode.ecomove.services.ContractService;
import org.youcode.ecomove.services.PartnerService;
import org.youcode.ecomove.views.ContractView;

import java.util.Scanner;

public class ContractMenu {

    public static void contractManagementMenu() {
        Scanner scanner = new Scanner(System.in);
        ContractDaoImpl contractDaoImpl = new ContractDaoImpl();
        PartnerDaoImpl partnerDaoImpl = new PartnerDaoImpl();
        ContractService contractService = new ContractService(contractDaoImpl);
        PartnerService partnerService = new PartnerService(partnerDaoImpl);
        ContractView contractView = new ContractView(contractService, partnerService);

        while (true) {
            displayMenu();
            int choice = getValidChoice(scanner);

            switch (choice) {
                case 1:
                    contractView.createContract();
                    break;
                case 2:
                    contractView.getAllContracts();
                    break;
                case 3:
                    contractView.findContractsByPartnerId();
                    break;
                case 4:
                    contractView.findContractById();
                    break;
                case 5:
                    contractView.deleteContractById();
                    break;
                case 6:
                    contractView.updateContract();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }
    }

    private static void displayMenu() {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║                                       ║");
        System.out.println("║           Contract Management         ║");
        System.out.println("║  1 : Add Contract                     ║");
        System.out.println("║  2 : List All Contracts               ║");
        System.out.println("║  3 : Find Contracts By Partner ID     ║");
        System.out.println("║  4 : Find Contracts ID                ║");
        System.out.println("║  5 : Remove Contract                  ║");
        System.out.println("║  6 : Update Contract                  ║");
        System.out.println("║  7 : Return                           ║");
        System.out.println("║                                       ║");
        System.out.println("╚═══════════════════════════════════════╝");
    }

    private static int getValidChoice(Scanner scanner) {
        while (true) {
            System.out.print("Enter Your Choice : ");
            String input = scanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= 7) {
                    return choice;
                } else {
                    System.out.println("Please enter a number between 1 and 7.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}
