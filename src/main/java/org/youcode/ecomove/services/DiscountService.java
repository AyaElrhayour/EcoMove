package org.youcode.ecomove.services;

import org.youcode.ecomove.dao.DiscountDao;
import org.youcode.ecomove.entities.Discount;
import org.youcode.ecomove.utils.DiscountValidation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DiscountService {
    private final DiscountDao discountDao;

    public DiscountService(DiscountDao discountDao) {
        this.discountDao = discountDao;
    }

    public void createDiscount(Discount discount) {
        Optional<Discount> createdDiscount = discountDao.create(discount);
        if (createdDiscount.isPresent()) {
            System.out.println("Discount created successfully!");
        } else {
            System.out.println("Failed to create discount.");
        }
    }

    public Discount findDiscountById(String discountId) {
        if (!DiscountValidation.isUuidValid(discountId)) {
            System.out.println("Invalid UUID format.");
            return null;
        }

        UUID id = UUID.fromString(discountId);
        Optional<Discount> discount = discountDao.findById(id);
        return discount.orElse(null);
    }

    public List<Discount> getAllDiscounts() {
        return discountDao.getAll();
    }

    public List<Discount> findDiscountsByContractId(String contractId) {
        if (!DiscountValidation.isUuidValid(contractId)) {
            throw new IllegalArgumentException("Invalid UUID format");
        }

        UUID id = UUID.fromString(contractId);
        return discountDao.getDiscountsByContractId(id);
    }

    public boolean deleteDiscountById(String discountId) {
        if (!DiscountValidation.isUuidValid(discountId)) {
            throw new IllegalArgumentException("Invalid UUID format");
        }

        UUID id = UUID.fromString(discountId);
        return discountDao.delete(id);
    }

    public Discount updateDiscount(String discountId, Discount discount) {
        if (!DiscountValidation.isUuidValid(discountId)) {
            throw new IllegalArgumentException("Invalid UUID format");
        }

        UUID id = UUID.fromString(discountId);
        Optional<Discount> updatedDiscount = discountDao.update(id, discount);
        return updatedDiscount.orElse(null);
    }
}
