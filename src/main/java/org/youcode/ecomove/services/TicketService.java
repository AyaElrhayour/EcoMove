package org.youcode.ecomove.services;

import org.youcode.ecomove.dao.TicketDao;
import org.youcode.ecomove.entities.Ticket;
import org.youcode.ecomove.utils.TicketValidation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TicketService {
    private final TicketDao ticketDao;

    public TicketService(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    public void createTicket(Ticket ticket) {
        Optional<Ticket> createdTicket = ticketDao.create(ticket);
        if (createdTicket.isPresent()) {
            System.out.println("Ticket created successfully!");
        } else {
            System.out.println("Failed to create ticket.");
        }
    }

    public Ticket findTicketById(String ticketId) {
        if (!TicketValidation.isUuidValid(ticketId)) {
            System.out.println("Invalid UUID format.");
            return null;
        }

        UUID id = UUID.fromString(ticketId);
        Optional<Ticket> ticket = ticketDao.findById(id);
        return ticket.orElse(null);
    }

    public List<Ticket> getAllTickets() {
        return ticketDao.getAll();
    }

    public List<Ticket> getTicketsByContractId(String contractId) {
        if (!TicketValidation.isUuidValid(contractId)) {
            throw new IllegalArgumentException("Invalid UUID format");
        }
        UUID id = UUID.fromString(contractId);
        return ticketDao.getTicketsByContractId(id);
    }

    public boolean deleteTicketById(String ticketId) {
        if (!TicketValidation.isUuidValid(ticketId)) {
            throw new IllegalArgumentException("Invalid UUID format");
        }
        UUID id = UUID.fromString(ticketId);
        return ticketDao.delete(id);
    }

    public Ticket updateTicket(String ticketId, Ticket ticket) {
        if (!TicketValidation.isUuidValid(ticketId)) {
            throw new IllegalArgumentException("Invalid UUID format");
        }

        UUID id = UUID.fromString(ticketId);
        Optional<Ticket> updatedTicket = ticketDao.update(id, ticket);
        return updatedTicket.orElse(null);
    }
}
