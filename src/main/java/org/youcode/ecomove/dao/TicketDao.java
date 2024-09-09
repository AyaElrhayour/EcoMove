package org.youcode.ecomove.dao;

import org.youcode.ecomove.entities.Ticket;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketDao {

    Optional<Ticket> create(Ticket ticket);
    Optional<Ticket> findById(UUID ticketId);
    List<Ticket> getAll();
    List<Ticket> getTicketsByContractId(UUID contractId);
    boolean delete(UUID ticketId);
    Optional<Ticket> update(UUID ticketId, Ticket ticket);
}
