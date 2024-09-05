package org.youcode.ecomove.services;

import org.youcode.ecomove.dao.PartnerDao;
import org.youcode.ecomove.entities.Partner;

import java.util.List;
import java.util.Optional;

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

}
