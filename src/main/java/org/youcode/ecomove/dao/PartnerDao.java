package org.youcode.ecomove.dao;

import org.youcode.ecomove.entities.Partner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PartnerDao {

    Optional<Partner> create (Partner partner);
    Optional<Partner> findPartnerById(UUID id);
    List<Partner> getAllPartners();
    boolean deletePartner();

}
