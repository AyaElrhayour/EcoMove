package org.youcode.ecomove.utils;

import org.youcode.ecomove.enums.TICKETSTATUS;
import org.youcode.ecomove.enums.TRANSPORTTYPE;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;

public class TicketValidation {
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

    public static void validatePrice(float price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
    }

    public static String getValidInput(String prompt, String fieldName) {
        System.out.println(prompt);
        String input = sc.nextLine();
        validateNotEmpty(input, fieldName);
        return input;
    }

    public static TICKETSTATUS getValidTicketStatus() {
        while (true) {
            try {
                return validateTicketStatus(getValidInput("Enter Ticket Status (SOLD, CANCELED, PENDING):", "Ticket status"));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static TICKETSTATUS validateTicketStatus(String status) {
        try {
            return TICKETSTATUS.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid ticket status: " + status);
        }
    }

    public static TRANSPORTTYPE getValidTransportType() {
        while (true) {
            try {
                return validateTransportType(getValidInput("Enter Transport Type (AIRPLAINE, TRAIN, BUS):", "Transport type"));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static TRANSPORTTYPE validateTransportType(String transportType) {
        try {
            return TRANSPORTTYPE.valueOf(transportType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid transport type: " + transportType);
        }
    }

    public static void validateResaleDate(LocalDate purchaseDate, LocalDate resaleDate) {
        if (resaleDate.isBefore(purchaseDate)) {
            throw new IllegalArgumentException("Resale date cannot be before the purchase date.");
        }
    }
}
