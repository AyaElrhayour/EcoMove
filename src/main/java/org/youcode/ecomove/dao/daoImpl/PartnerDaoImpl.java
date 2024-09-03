package org.youcode.ecomove.dao.daoImpl;

import org.youcode.ecomove.dao.PartnerDao;
import org.youcode.ecomove.db.DBConnection;
import org.youcode.ecomove.entities.Partner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PartnerDaoImpl implements PartnerDao {

    private final Connection conn ;

    public PartnerDaoImpl () {
        conn = DBConnection.getInstance().establishConnection();
    }


    @Override
    public Optional<Partner> create(Partner partner) {
        String insertSQL = " INSERT INTO partner (partnerId, companyName, " +
                "comercialContact, transportType, geographicArea, specialConditions, partnershipStatus) " +
                "VALUES ( ?, ?, ?, ?::transportType, ?, ?, ?::partnershipStatus)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL)){
                preparedStatement.setString(1,String.valueOf(partner.getPartnerId()));
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
        return Optional.empty();
    }

    @Override
    public List<Partner> getAll() {
        return List.of();
    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public Optional<Partner> update(UUID id, Partner partner) {
        return Optional.empty();
    }
}
