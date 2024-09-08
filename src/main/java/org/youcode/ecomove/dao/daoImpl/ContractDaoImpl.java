package org.youcode.ecomove.dao.daoImpl;

import org.youcode.ecomove.dao.ContractDao;
import org.youcode.ecomove.db.DBConnection;
import org.youcode.ecomove.entities.Contract;
import org.youcode.ecomove.entities.Partner;
import org.youcode.ecomove.enums.CONTRACTSTATUS;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ContractDaoImpl implements ContractDao {
    private final Connection conn;

    public ContractDaoImpl() {
        conn = DBConnection.getInstance().establishConnection();
    }

    @Override
    public Optional<Contract> create(Contract contract) {
        String insertSQL = "INSERT INTO contract (contractId, startDate, endDate, specialPrice, " +
                "agreementConditions, renewable, contractStatus, partnerId) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?::contractStatus, ?::uuid)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL)) {
            preparedStatement.setObject(1, contract.getContractId());
            preparedStatement.setDate(2, Date.valueOf(contract.getStartDate()));
            preparedStatement.setDate(3, Date.valueOf(contract.getEndDate()));
            preparedStatement.setInt(4, contract.getSpecialPrice());
            preparedStatement.setString(5, contract.getAgreementConditions());
            preparedStatement.setBoolean(6, contract.getRenewable());
            preparedStatement.setString(7, contract.getContractStatus().toString());
            preparedStatement.setObject(8, contract.getPartner().getPartnerId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                return Optional.empty();
            }
            return Optional.of(contract);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Contract> findById(UUID id) {
        String selectSQL = "SELECT * FROM contract WHERE contractId = ?::uuid";
        try (PreparedStatement preparedStatement = conn.prepareStatement(selectSQL)) {
            preparedStatement.setObject(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Contract contract = mapRowToContract(resultSet);
                    return Optional.of(contract);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Contract> getAll() {
        List<Contract> contracts = new ArrayList<>();
        String selectAllSQL = "SELECT * FROM contract";
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(selectAllSQL)) {
            while (resultSet.next()) {
                contracts.add(mapRowToContract(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contracts;
    }

    @Override
    public List<Contract> getContractsByPartnerId(UUID partnerId) {
        List<Contract> contracts = new ArrayList<>();
        String selectSQL = "SELECT * FROM contract WHERE partnerId = ?::uuid";
        try (PreparedStatement preparedStatement = conn.prepareStatement(selectSQL)) {
            preparedStatement.setObject(1, partnerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    contracts.add(mapRowToContract(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contracts;
    }

    @Override
    public boolean delete(UUID contractId) {
        String deleteSQL = "DELETE FROM contract WHERE contractId = ?::uuid";
        try (PreparedStatement preparedStatement = conn.prepareStatement(deleteSQL)) {
            preparedStatement.setObject(1, contractId);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Contract> update(UUID contractId, Contract contract) {
        String updateSQL = "UPDATE contract SET startDate = ?, endDate = ?, specialPrice = ?, agreementConditions = ?, renewable = ?, contractStatus = ?::contractStatus, partnerId = ?::uuid WHERE contractId = ?::uuid";
        try (PreparedStatement preparedStatement = conn.prepareStatement(updateSQL)) {
            preparedStatement.setDate(1, Date.valueOf(contract.getStartDate()));
            preparedStatement.setDate(2, Date.valueOf(contract.getEndDate()));
            preparedStatement.setInt(3, contract.getSpecialPrice());
            preparedStatement.setString(4, contract.getAgreementConditions());
            preparedStatement.setBoolean(5, contract.getRenewable());
            preparedStatement.setString(6, contract.getContractStatus().toString());
            preparedStatement.setObject(7, contract.getPartner().getPartnerId().toString());
            preparedStatement.setObject(8, contractId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                return Optional.empty();
            }
            return Optional.of(contract);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Contract mapRowToContract(ResultSet resultSet) throws SQLException {
        Contract contract = new Contract();
        contract.setContractId(UUID.fromString(resultSet.getString("contractId")));
        contract.setStartDate(resultSet.getDate("startDate").toLocalDate());
        contract.setEndDate(resultSet.getDate("endDate").toLocalDate());
        contract.setSpecialPrice(resultSet.getInt("specialPrice"));
        contract.setAgreementConditions(resultSet.getString("agreementConditions"));
        contract.setRenewable(resultSet.getBoolean("renewable"));
        contract.setContractStatus(CONTRACTSTATUS.valueOf(resultSet.getString("contractStatus")));
        contract.setPartner(new PartnerDaoImpl().findById(UUID.fromString(resultSet.getString("partnerId"))).get());
        return contract;
    }
}
