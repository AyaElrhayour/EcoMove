package org.youcode.ecomove.dao.daoImpl;

import org.youcode.ecomove.dao.DiscountDao;
import org.youcode.ecomove.db.DBConnection;
import org.youcode.ecomove.entities.Discount;
import org.youcode.ecomove.enums.DISCOUNTTYPE;
import org.youcode.ecomove.enums.OFFERSTATUS;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DiscountDaoImpl implements DiscountDao {
    private final Connection conn;

    public DiscountDaoImpl() {
        conn = DBConnection.getInstance().establishConnection();
    }

    @Override
    public Optional<Discount> create(Discount discount) {
        String insertSQL = "INSERT INTO discount (discountId, offerName, description, startDate, endDate, " +
                "discountType, conditions, offerStatus, contractId) " +
                "VALUES (?, ?, ?, ?, ?, ?::discountType, ?, ?::offerStatus, ?::uuid)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL)) {
            preparedStatement.setObject(1, discount.getDiscountId());
            preparedStatement.setString(2, discount.getOfferName());
            preparedStatement.setString(3, discount.getDescription());
            preparedStatement.setDate(4, Date.valueOf(discount.getStartDate()));
            preparedStatement.setDate(5, Date.valueOf(discount.getEndDate()));
            preparedStatement.setString(6, discount.getDiscountType().toString());
            preparedStatement.setString(7, discount.getConditions());
            preparedStatement.setString(8, discount.getOfferStatus().toString());
            preparedStatement.setObject(9, discount.getContract().getContractId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                return Optional.empty();
            }
            return Optional.of(discount);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Discount> findById(UUID discountId) {
        String selectSQL = "SELECT * FROM discount WHERE discountId = ?::uuid";
        try (PreparedStatement preparedStatement = conn.prepareStatement(selectSQL)) {
            preparedStatement.setObject(1, discountId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Discount discount = mapRowToDiscount(resultSet);
                    return Optional.of(discount);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Discount> getAll() {
        List<Discount> discounts = new ArrayList<>();
        String selectAllSQL = "SELECT * FROM discount";
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(selectAllSQL)) {
            while (resultSet.next()) {
                discounts.add(mapRowToDiscount(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return discounts;
    }

    @Override
    public List<Discount> getDiscountsByContractId(UUID contractId) {
        List<Discount> discounts = new ArrayList<>();
        String selectSQL = "SELECT * FROM discount WHERE contractId = ?::uuid";
        try (PreparedStatement preparedStatement = conn.prepareStatement(selectSQL)) {
            preparedStatement.setObject(1, contractId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    discounts.add(mapRowToDiscount(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return discounts;
    }

    @Override
    public boolean delete(UUID discountId) {
        String deleteSQL = "DELETE FROM discount WHERE discountId = ?::uuid";
        try (PreparedStatement preparedStatement = conn.prepareStatement(deleteSQL)) {
            preparedStatement.setObject(1, discountId);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Discount> update(UUID discountId, Discount discount) {
        String updateSQL = "UPDATE discount SET offerName = ?, description = ?, startDate = ?, endDate = ?, " +
                "discountType = ?::discountType, conditions = ?, offerStatus = ?::offerStatus, contractId = ?::uuid " +
                "WHERE discountId = ?::uuid";
        try (PreparedStatement preparedStatement = conn.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, discount.getOfferName());
            preparedStatement.setString(2, discount.getDescription());
            preparedStatement.setDate(3, Date.valueOf(discount.getStartDate()));
            preparedStatement.setDate(4, Date.valueOf(discount.getEndDate()));
            preparedStatement.setString(5, discount.getDiscountType().toString());
            preparedStatement.setString(6, discount.getConditions());
            preparedStatement.setString(7, discount.getOfferStatus().toString());
            preparedStatement.setObject(8, discount.getContract().getContractId());
            preparedStatement.setObject(9, discountId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                return Optional.empty();
            }
            return Optional.of(discount);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Discount mapRowToDiscount(ResultSet resultSet) throws SQLException {
        Discount discount = new Discount();
        discount.setDiscountId(UUID.fromString(resultSet.getString("discountId")));
        discount.setOfferName(resultSet.getString("offerName"));
        discount.setDescription(resultSet.getString("description"));
        discount.setStartDate(resultSet.getDate("startDate").toLocalDate());
        discount.setEndDate(resultSet.getDate("endDate").toLocalDate());
        discount.setDiscountType(DISCOUNTTYPE.valueOf(resultSet.getString("discountType")));
        discount.setConditions(resultSet.getString("conditions"));
        discount.setOfferStatus(OFFERSTATUS.valueOf(resultSet.getString("offerStatus")));
        discount.setContract(new ContractDaoImpl().findById(UUID.fromString(resultSet.getString("contractId"))).get());
        return discount;
    }
}
