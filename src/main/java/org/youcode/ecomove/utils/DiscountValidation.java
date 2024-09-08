package org.youcode.ecomove.utils;

import org.youcode.ecomove.enums.DISCOUNTTYPE;
import org.youcode.ecomove.enums.OFFERSTATUS;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;

public class DiscountValidation {
    private static final Scanner sc = new Scanner(System.in);


    public static boolean isUuidValid(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }


    public static void validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
    }


    public static void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null.");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }
    }


    public static DISCOUNTTYPE getValidDiscountType() {
        while (true) {
            try {
                return validateDiscountType(getValidInput("Enter Discount Type (PERCENTAGE, FIXEDAMOUNT):", "Discount Type"));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static DISCOUNTTYPE validateDiscountType(String discountType) {
        try {
            return DISCOUNTTYPE.valueOf(discountType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid discount type: " + discountType);
        }
    }


    public static OFFERSTATUS getValidOfferStatus() {
        while (true) {
            try {
                return validateOfferStatus(getValidInput("Enter Offer Status (ACTIVE, EXPIRED, SUSPENDED):", "Offer Status"));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static OFFERSTATUS validateOfferStatus(String status) {
        try {
            return OFFERSTATUS.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid offer status: " + status);
        }
    }


    public static String getValidInput(String prompt, String fieldName) {
        System.out.println(prompt);
        String input = sc.nextLine();
        validateNotEmpty(input, fieldName);
        return input;
    }


    public static void validateConditions(String conditions) {
        validateNotEmpty(conditions, "Conditions");
    }
}
