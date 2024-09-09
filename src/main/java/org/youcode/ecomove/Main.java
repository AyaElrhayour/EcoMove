package org.youcode.ecomove;

import org.youcode.ecomove.db.DBConnection;
import org.youcode.ecomove.submenu.ContractMenu;
import org.youcode.ecomove.submenu.DiscountMenu;
import org.youcode.ecomove.submenu.PartnerMenu;
import org.youcode.ecomove.submenu.TicketMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayMenu();
            int choice = getValidChoice(scanner);

            switch (choice) {
                case 1:
                    PartnerMenu.partnerManagementMenu();
                    break;
                case 2:
                    ContractMenu.contractManagementMenu();
                    break;
                case 3:
                    DiscountMenu.discountManagementMenu();
                    break;
                case 4:
                    TicketMenu.ticketManagementMenu();
                    break;
                case 5:
                    System.out.println("Thank you for your hard work! Have a good day!");
                    DBConnection.closeConnection();
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }
    }

    private static void displayMenu() {
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║                             ║");
        System.out.println("║           EcoMove           ║");
        System.out.println("║  1 : Partner Management     ║");
        System.out.println("║  2 : Contract Management    ║");
        System.out.println("║  3 : Discount Management    ║");
        System.out.println("║  4 : Ticket Management      ║");
        System.out.println("║  5 : EXIT                   ║");
        System.out.println("║                             ║");
        System.out.println("╚═════════════════════════════╝");

    }

    private static int getValidChoice(Scanner scanner) {
        while (true) {
            System.out.print("Enter Your Choice : ");
            String input = scanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= 5) {
                    return choice;
                } else {
                    System.out.println("Please enter a number between 1 and 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}