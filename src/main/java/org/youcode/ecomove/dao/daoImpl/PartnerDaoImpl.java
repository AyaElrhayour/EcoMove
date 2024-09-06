package org.youcode.ecomove.dao.daoImpl;

import org.youcode.ecomove.dao.PartnerDao;
import org.youcode.ecomove.db.DBConnection;
import org.youcode.ecomove.entities.Partner;
import org.youcode.ecomove.enums.PARTNERSHIPSTATUS;
import org.youcode.ecomove.enums.TRANSPORTTYPE;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PartnerDaoImpl implements PartnerDao {

    private final Connection conn ;
    private String partnerId;

    public PartnerDaoImpl () {
        conn = DBConnection.getInstance().establishConnection();
    }


    @Override
    public Optional<Partner> create(Partner partner) {
        String insertSQL = " INSERT INTO partner (partnerId, companyName, " +
                "commercialContact, transportType, geographicArea, specialConditions, partnershipStatus) " +
                "VALUES ( ?, ?, ?, ?::transportType, ?, ?, ?::partnershipStatus)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL)){
                preparedStatement.setObject(1,partner.getPartnerId());
                preparedStatement.setString(2,partner.getCompanyName());
                preparedStatement.setString(3,partner.getCommercialContact());
                preparedStatement.setString(4,partner.getTransportType().toString());
                preparedStatement.setString(5,partner.getGeographicArea());
                preparedStatement.setString(6,partner.getSpecialConditions());
                preparedStatement.setString(7,partner.getPartnershipStatus().toString());

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0) {
                    return Optional.empty();
                }
                return Optional.of(partner);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Partner> findById(UUID id) {
        String selectSQL = "SELECT * FROM partner WHERE partnerId = ?::uuid";

        try(PreparedStatement preparedStatement = conn.prepareStatement(selectSQL)){
            preparedStatement.setObject(1,id);

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    Partner partner = new Partner();
                    partner.setPartnerId(UUID.fromString(resultSet.getString("partnerId")));
                    partner.setCompanyName(resultSet.getString("companyName"));
                    partner.setCommercialContact(resultSet.getString("commercialContact"));
                    partner.setTransportType(TRANSPORTTYPE.valueOf(resultSet.getString("transportType")));
                    partner.setGeographicArea(resultSet.getString("geographicArea"));
                    partner.setSpecialConditions(resultSet.getString("specialConditions"));
                    partner.setPartnershipStatus(PARTNERSHIPSTATUS.valueOf(resultSet.getString("partnershipStatus")));
                    //partner.setContracts(List<Contract> contracts);

                    return Optional.of(partner);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Partner> getAll() {
        List<Partner> partners = new ArrayList<>();

        String selectAllSQL = "SELECT * FROM partner";

        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(selectAllSQL)) {
            while (resultSet.next()){
                Partner partner = new Partner();
                partner.setPartnerId(UUID.fromString(resultSet.getString("partnerId")));
                partner.setCompanyName(resultSet.getString("companyName"));
                partner.setCommercialContact(resultSet.getString("commercialContact"));
                partner.setTransportType(TRANSPORTTYPE.valueOf(resultSet.getString("transportType")));
                partner.setGeographicArea(resultSet.getString("geographicArea"));
                partner.setSpecialConditions(resultSet.getString("specialConditions"));
                partner.setPartnershipStatus(PARTNERSHIPSTATUS.valueOf(resultSet.getString("partnershipStatus")));
                partners.add(partner);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return partners;
    }

    @Override
    public boolean delete(UUID partnerId) {
        String deleteSQL = "DELETE FROM partner WHERE partnerId = ?::uuid";

        try (PreparedStatement preparedStatement =conn.prepareStatement(deleteSQL)){

            preparedStatement.setObject(1, String.valueOf(partnerId));

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Partner> update(UUID partnerId, Partner partner) {

        String updateSQL = "UPDATE partner SET companyName = ?, commercialContact = ?," +
                "transportType = ?::transportType ,  geographicArea = ? , specialConditions = ?, " +
                "partnershipStatus = ?::partnershipStatus WHERE partnerId = ?::uuid";

        try(PreparedStatement preparedStatement = conn.prepareStatement(updateSQL)){
            preparedStatement.setString(1, partner.getCompanyName());
            preparedStatement.setString(2, partner.getCommercialContact());
            preparedStatement.setString(3, partner.getTransportType().toString());
            preparedStatement.setString(4, partner.getGeographicArea());
            preparedStatement.setString(5, partner.getSpecialConditions());
            preparedStatement.setString(6, partner.getPartnershipStatus().toString());
            preparedStatement.setObject(7, partnerId);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0){
                return Optional.empty();
            }

            return Optional.of(partner);

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }

    }
}
