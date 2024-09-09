package org.youcode.ecomove.views;

import org.youcode.ecomove.entities.Ticket;
import org.youcode.ecomove.entities.Contract;
import org.youcode.ecomove.enums.TRANSPORTTYPE;
import org.youcode.ecomove.enums.TICKETSTATUS;
import org.youcode.ecomove.services.TicketService;
import org.youcode.ecomove.services.ContractService;
import org.youcode.ecomove.utils.TicketValidation;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class TicketView {
    private final TicketService ticketService;
    private final ContractService contractService;
    private final Scanner sc;

    public TicketView(TicketService ticketService, ContractService contractService) {
        this.ticketService = ticketService;
        this.contractService = contractService;
        this.sc = new Scanner(System.in);
    }

    public void createTicket() {
        try {
            String contractId = TicketValidation.getValidInput("Enter Contract ID:", "Contract ID");
            Contract contract = contractService.findContractById(contractId);

            if (contract == null) {
                System.out.println("Contract not found. Cannot create ticket.");
                return;
            }

            TRANSPORTTYPE transportType = TicketValidation.getValidTransportType();
            double purchasePrice = Double.parseDouble(TicketValidation.getValidInput("Enter Purchase Price:", "Purchase Price"));
            double resalePrice = Double.parseDouble(TicketValidation.getValidInput("Enter Resale Price:", "Resale Price"));
            LocalDate resaleDate = LocalDate.parse(TicketValidation.getValidInput("Enter Resale Date (YYYY-MM-DD):", "Resale Date"));
            TICKETSTATUS ticketStatus = TicketValidation.getValidTicketStatus();

            Ticket ticket = new Ticket(
                    UUID.randomUUID(),
                    transportType,
                    purchasePrice,
                    resalePrice,
                    resaleDate,
                    ticketStatus,
                    contract
            );

            ticketService.createTicket(ticket);
            System.out.println("Ticket created successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void getAllTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();

        if (tickets.isEmpty()) {
            System.out.println("No tickets available.");
        } else {
            System.out.println("╔════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                                   Tickets List                                                     ║");
            System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
            System.out.printf("║ %-36s │ %-15s │ %-15s │ %-15s │ %-15s │ %-15s ║%n",
                    "Ticket ID", "Transport Type", "Purchase Price", "Resale Price", "Resale Date", "Ticket Status");
            System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");

            for (Ticket ticket : tickets) {
                System.out.printf("║ %-36s │ %-15s │ %-15s │ %-15s │ %-15s │ %-15s ║%n",
                        ticket.getTicketId(),
                        ticket.getTransportType(),
                        ticket.getPurchasePrice(),
                        ticket.getResalePrice(),
                        ticket.getResaleDate(),
                        ticket.getTicketStatus());
            }
        }
    }

    public void deleteTicketById() {
        System.out.println("Enter Ticket ID You Want To Delete:");
        String ticketId = sc.nextLine();

        try {
            if (ticketService.deleteTicketById(ticketId)) {
                System.out.println("Ticket deleted successfully.");
            } else {
                System.out.println("Ticket with ID: " + ticketId + " not found.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid ID");
        }
    }

    public void updateTicket() {
        try {
            String ticketId = TicketValidation.getValidInput("Enter the ID of the Ticket You Wish to Update:", "Ticket ID");
            Ticket existingTicket = ticketService.findTicketById(ticketId);

            if (existingTicket == null) {
                System.out.println("Ticket not found.");
                return;
            }

            String contractId = TicketValidation.getValidInput("Enter Contract ID:", "Contract ID");
            Contract contract = contractService.findContractById(contractId);

            if (contract == null) {
                System.out.println("Contract not found. Cannot update ticket.");
                return;
            }

            TRANSPORTTYPE transportType = TicketValidation.getValidTransportType();
            double purchasePrice = Double.parseDouble(TicketValidation.getValidInput("Enter Purchase Price:", "Purchase Price"));
            double resalePrice = Double.parseDouble(TicketValidation.getValidInput("Enter Resale Price:", "Resale Price"));
            LocalDate resaleDate = LocalDate.parse(TicketValidation.getValidInput("Enter Resale Date (YYYY-MM-DD):", "Resale Date"));
            TICKETSTATUS ticketStatus = TicketValidation.getValidTicketStatus();

            Ticket updatedTicket = new Ticket(
                    UUID.fromString(ticketId),
                    transportType,
                    purchasePrice,
                    resalePrice,
                    resaleDate,
                    ticketStatus,
                    contract
            );

            Ticket result = ticketService.updateTicket(ticketId, updatedTicket);

            if (result != null) {
                System.out.println("Ticket updated successfully!");
            } else {
                System.out.println("Ticket not found.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void findTicketById() {
        System.out.println("Enter Ticket ID to find:");
        String ticketId = sc.nextLine();

        try {
            Ticket ticket = ticketService.findTicketById(ticketId);
            if (ticket != null) {
                displayTicketDetails(ticket);
            } else {
                System.out.println("Ticket not found.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid ID");
        }
    }

    public void findTicketsByContractId() {
        System.out.println("Enter Contract ID to find associated tickets:");
        String contractId = sc.nextLine();

        try {
            List<Ticket> tickets = ticketService.getTicketsByContractId(contractId);
            if (tickets.isEmpty()) {
                System.out.println("No tickets found for this contract.");
            } else {
                System.out.println("Tickets for Contract ID: " + contractId);
                System.out.println("╔════════════════════════════════════════════════════════════════════════════════════╗");
                for (Ticket ticket : tickets) {
                    displayTicketDetails(ticket);
                    System.out.println("╠════════════════════════════════════════════════════════════════════════════════════╣");
                }
                System.out.println("╚════════════════════════════════════════════════════════════════════════════════════╝");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid Contract ID.");
        }
    }

    private void displayTicketDetails(Ticket ticket) {
        System.out.println("╔══════════════════════════════════════════════════════════════════════════╗");
        System.out.printf("║ Ticket ID: %-50s ║%n", ticket.getTicketId());
        System.out.printf("║ Transport Type: %-45s ║%n", ticket.getTransportType());
        System.out.printf("║ Purchase Price: %-45s ║%n", ticket.getPurchasePrice());
        System.out.printf("║ Resale Price: %-47s ║%n", ticket.getResalePrice());
        System.out.printf("║ Resale Date: %-49s ║%n", ticket.getResaleDate());
        System.out.printf("║ Ticket Status: %-47s ║%n", ticket.getTicketStatus());
        System.out.printf("║ Associated Contract ID: %-39s ║%n", ticket.getContract().getContractId());
        System.out.println("╚══════════════════════════════════════════════════════════════════════════╝");
    }
}
