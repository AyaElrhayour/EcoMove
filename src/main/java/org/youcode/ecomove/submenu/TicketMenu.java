package org.youcode.ecomove.submenu;

import org.youcode.ecomove.dao.daoImpl.TicketDaoImpl;
import org.youcode.ecomove.dao.daoImpl.ContractDaoImpl;
import org.youcode.ecomove.services.TicketService;
import org.youcode.ecomove.services.ContractService;
import org.youcode.ecomove.views.TicketView;

import java.util.Scanner;

public class TicketMenu {

    public static void ticketManagementMenu() {
        Scanner scanner = new Scanner(System.in);
        TicketDaoImpl ticketDaoImpl = new TicketDaoImpl();
        ContractDaoImpl contractDaoImpl = new ContractDaoImpl();
        TicketService ticketService = new TicketService(ticketDaoImpl);
        ContractService contractService = new ContractService(contractDaoImpl);
        TicketView ticketView = new TicketView(ticketService, contractService);

        while (true) {
            displayMenu();
            int choice = getValidChoice(scanner);

            switch (choice) {
                case 1:
                    ticketView.createTicket();
                    break;
                case 2:
                    ticketView.getAllTickets();
                    break;
                case 3:
                    ticketView.findTicketById();
                    break;
                case 4:
                    ticketView.deleteTicketById();
                    break;
                case 5:
                    ticketView.updateTicket();
                    break;
                case 6:
                    ticketView.findTicketsByContractId();
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
        System.out.println("║           Ticket Management           ║");
        System.out.println("║  1 : Add Ticket                       ║");
        System.out.println("║  2 : List All Tickets                 ║");
        System.out.println("║  3 : Find Ticket By ID                ║");
        System.out.println("║  4 : Remove Ticket                    ║");
        System.out.println("║  5 : Update Ticket                    ║");
        System.out.println("║  6 : Find Tickets By Contract ID      ║");
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

