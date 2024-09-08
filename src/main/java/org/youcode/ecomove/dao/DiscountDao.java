package org.youcode.ecomove.dao;

import org.youcode.ecomove.entities.Discount;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DiscountDao {
    Optional<Discount> create(Discount discount);
    Optional<Discount> findById(UUID discountId);
    List<Discount> getAll();
    List<Discount> getDiscountsByContractId(UUID contractId);  // Method to get all discounts for a specific contract
    boolean delete(UUID discountId);
    Optional<Discount> update(UUID discountId, Discount discount);
}
