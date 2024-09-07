package org.youcode.ecomove.utils;

import org.youcode.ecomove.enums.CONTRACTSTATUS;

import java.util.Scanner;
import java.util.UUID;

public class ContractValidation {
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

    public static void validatePrice(int price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Special price must be positive.");
        }
    }


    public static String getValidInput(String prompt, String fieldName) {
        System.out.println(prompt);
        String input = sc.nextLine();
        validateNotEmpty(input, fieldName);
        return input;
    }


    public static CONTRACTSTATUS getValidContractStatus() {
        while (true) {
            try {
                return validateContractStatus(getValidInput("Enter Contract Status (INPROGRESS, COMPLETED, SUSPENDED):", "Contract status"));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static CONTRACTSTATUS validateContractStatus(String status) {
        try {
            return CONTRACTSTATUS.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid contract status: " + status);
        }
    }
}
