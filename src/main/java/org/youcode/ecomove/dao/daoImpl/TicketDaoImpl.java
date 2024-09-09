package org.youcode.ecomove.dao.daoImpl;

import org.youcode.ecomove.dao.TicketDao;
import org.youcode.ecomove.db.DBConnection;
import org.youcode.ecomove.entities.Contract;
import org.youcode.ecomove.entities.Ticket;
import org.youcode.ecomove.enums.TICKETSTATUS;
import org.youcode.ecomove.enums.TRANSPORTTYPE;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TicketDaoImpl implements TicketDao {
    private final Connection conn;

    public TicketDaoImpl() {
        conn = DBConnection.getInstance().establishConnection();
    }

    @Override
    public Optional<Ticket> create(Ticket ticket) {
        String insertSQL = "INSERT INTO ticket (ticketId, transportType, purchasePrice, resalePrice, resaleDate, ticketStatus, contractId) " +
                "VALUES (?, ?::transportType, ?, ?, ?, ?::ticketStatus, ?::uuid)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL)) {
            preparedStatement.setObject(1, ticket.getTicketId());
            preparedStatement.setString(2, ticket.getTransportType().toString());
            preparedStatement.setDouble(3, ticket.getPurchasePrice());
            preparedStatement.setDouble(4, ticket.getResalePrice());
            preparedStatement.setDate(5, Date.valueOf(ticket.getResaleDate()));
            preparedStatement.setString(6, ticket.getTicketStatus().toString());
            preparedStatement.setObject(7, ticket.getContract().getContractId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                return Optional.empty();
            }
            return Optional.of(ticket);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Ticket> findById(UUID ticketId) {
        String selectSQL = "SELECT * FROM ticket WHERE ticketId = ?::uuid";
        try (PreparedStatement preparedStatement = conn.prepareStatement(selectSQL)) {
            preparedStatement.setObject(1, ticketId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Ticket ticket = mapRowToTicket(resultSet);
                    return Optional.of(ticket);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Ticket> getAll() {
        List<Ticket> tickets = new ArrayList<>();
        String selectAllSQL = "SELECT * FROM ticket";
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(selectAllSQL)) {
            while (resultSet.next()) {
                tickets.add(mapRowToTicket(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tickets;
    }

    @Override
    public List<Ticket> getTicketsByContractId(UUID contractId) {
        List<Ticket> tickets = new ArrayList<>();
        String selectSQL = "SELECT * FROM ticket WHERE contractId = ?::uuid";
        try (PreparedStatement preparedStatement = conn.prepareStatement(selectSQL)) {
            preparedStatement.setObject(1, contractId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    tickets.add(mapRowToTicket(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tickets;
    }

    @Override
    public boolean delete(UUID ticketId) {
        String deleteSQL = "DELETE FROM ticket WHERE ticketId = ?::uuid";
        try (PreparedStatement preparedStatement = conn.prepareStatement(deleteSQL)) {
            preparedStatement.setObject(1, ticketId);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Ticket> update(UUID ticketId, Ticket ticket) {
        String updateSQL = "UPDATE ticket SET transportType = ?::transportType, purchasePrice = ?, resalePrice = ?, resaleDate = ?, ticketStatus = ?::ticketStatus, contractId = ?::uuid WHERE ticketId = ?::uuid";
        try (PreparedStatement preparedStatement = conn.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, ticket.getTransportType().toString());
            preparedStatement.setDouble(2, ticket.getPurchasePrice());
            preparedStatement.setDouble(3, ticket.getResalePrice());
            preparedStatement.setDate(4, Date.valueOf(ticket.getResaleDate()));
            preparedStatement.setString(5, ticket.getTicketStatus().toString());
            preparedStatement.setObject(6, ticket.getContract().getContractId());
            preparedStatement.setObject(7, ticketId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                return Optional.empty();
            }
            return Optional.of(ticket);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Ticket mapRowToTicket(ResultSet resultSet) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setTicketId(UUID.fromString(resultSet.getString("ticketId")));
        ticket.setTransportType(TRANSPORTTYPE.valueOf(resultSet.getString("transportType")));
        ticket.setPurchasePrice(resultSet.getFloat("purchasePrice"));
        ticket.setResalePrice(resultSet.getFloat("resalePrice"));
        ticket.setResaleDate(resultSet.getDate("resaleDate").toLocalDate());
        ticket.setTicketStatus(TICKETSTATUS.valueOf(resultSet.getString("ticketStatus")));
        ticket.setContract(new ContractDaoImpl().findById(UUID.fromString(resultSet.getString("contractId"))).get());
        return ticket;
    }
}
