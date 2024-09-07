package org.youcode.ecomove.utils;

import org.youcode.ecomove.enums.TRANSPORTTYPE;
import org.youcode.ecomove.enums.PARTNERSHIPSTATUS;

import java.util.UUID;

public class PartnerValidation {

    public static boolean isUUIDValid(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static TRANSPORTTYPE validateTransportType(String transportType) {
        try {
            return TRANSPORTTYPE.valueOf(transportType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid transport type. Valid values are BUS, TRAIN, PLANE.");
        }
    }

    public static PARTNERSHIPSTATUS validatePartnershipStatus(String partnershipStatus) {
        try {
            return PARTNERSHIPSTATUS.valueOf(partnershipStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid partnership status. Valid values are ACTIVE, INACTIVE, SUSPENDED.");
        }
    }

    public static void validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
    }
}