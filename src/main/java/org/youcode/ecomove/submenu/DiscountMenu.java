package org.youcode.ecomove.submenu;

import org.youcode.ecomove.dao.daoImpl.DiscountDaoImpl;
import org.youcode.ecomove.dao.daoImpl.ContractDaoImpl;
import org.youcode.ecomove.services.DiscountService;
import org.youcode.ecomove.services.ContractService;
import org.youcode.ecomove.views.DiscountView;

import java.util.Scanner;

public class DiscountMenu {

    public static void discountManagementMenu() {
        Scanner scanner = new Scanner(System.in);
        DiscountDaoImpl discountDaoImpl = new DiscountDaoImpl();
        ContractDaoImpl contractDaoImpl = new ContractDaoImpl();
        DiscountService discountService = new DiscountService(discountDaoImpl);
        ContractService contractService = new ContractService(contractDaoImpl);
        DiscountView discountView = new DiscountView(discountService, contractService);

        while (true) {
            displayMenu();
            int choice = getValidChoice(scanner);

            switch (choice) {
                case 1:
                    discountView.createDiscount();
                    break;
                case 2:
                    discountView.getAllDiscounts();
                    break;
                case 3:
                    discountView.findDiscountById();
                    break;
                case 4:
                    discountView.deleteDiscountById();
                    break;
                case 5:
                    discountView.updateDiscount();
                    break;
                case 6:
                    discountView.findDiscountsByContractId();
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
        System.out.println("║           Discount Management         ║");
        System.out.println("║  1 : Add Discount                     ║");
        System.out.println("║  2 : List All Discounts               ║");
        System.out.println("║  3 : Find Discount By ID              ║");
        System.out.println("║  4 : Remove Discount                  ║");
        System.out.println("║  5 : Update Discount                  ║");
        System.out.println("║  6 : Find Discounts By Contract ID    ║");
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
