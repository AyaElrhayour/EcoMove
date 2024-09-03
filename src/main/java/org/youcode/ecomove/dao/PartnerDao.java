package org.youcode.ecomove.dao;

import org.youcode.ecomove.entities.Partner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PartnerDao {

    Optional<Partner> create(Partner partner);
    Optional<Partner> findById(UUID id);
    List<Partner> getAll();
    boolean delete();
    Optional<Partner> update(UUID id ,Partner partner);

}
