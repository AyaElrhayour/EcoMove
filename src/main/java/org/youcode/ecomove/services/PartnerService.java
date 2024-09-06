package org.youcode.ecomove.services;

import org.youcode.ecomove.dao.PartnerDao;
import org.youcode.ecomove.entities.Partner;
import org.youcode.ecomove.utils.PartnerValidation;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class PartnerService {

    private final PartnerDao partnerDao;

    public PartnerService (PartnerDao partnerDao){
        this.partnerDao = partnerDao;
    }

    public void createPartner (Partner partner){
        if(partner.getCommercialContact() == null || partner.getCompanyName() == null ||
                partner.getGeographicArea() == null || partner.getTransportType() == null ||
                partner.getPartnershipStatus() == null || partner.getSpecialConditions() == null) {
            System.out.println("FIELDS UNFILLED!");
        }
        Optional<Partner> createdPartner = partnerDao.create(partner);
        if (createdPartner.isPresent())
            System.out.println("Partner Added Successfully!");
    }

    public List<Partner> getAllPartners () {

        return partnerDao.getAll();
    }

    public boolean deletePartnerById(String partnerIdInput) {

        if (!PartnerValidation.isValidUUID(partnerIdInput)) {
            throw new IllegalArgumentException("Invalid UUID format");
        }


        UUID partnerId = UUID.fromString(partnerIdInput);
        Optional<Partner> retrievedPartner = partnerDao.findById(partnerId);
        if (retrievedPartner.isPresent()) {
            return partnerDao.delete(partnerId);
        } else {
            return false;
        }
    }

    public Partner updatePartner(String partnerIdInput, Partner partner) {

        if (!PartnerValidation.isValidUUID(partnerIdInput)) {
            throw new IllegalArgumentException("Invalid UUID format");
        }


        UUID partnerId = UUID.fromString(partnerIdInput);
        Optional<Partner> retrievedPartner = partnerDao.findById(partnerId);
        if (retrievedPartner.isPresent()) {
            Optional<Partner> updatedPartner = partnerDao.update(partnerId, partner);
            return updatedPartner.orElse(null);
        } else {
            return null;
        }
    }

    public Partner findPartnerByID(String partnerIdInput){
        if (!PartnerValidation.isValidUUID(partnerIdInput)) {
            System.out.println("Invalid UUID format. Please enter a valid UUID.");
            return null;
        }

        UUID partnerId = UUID.fromString(partnerIdInput);
        Optional<Partner> retrievedPartner = partnerDao.findById(partnerId);
        return retrievedPartner.orElse(null);
    }



}
