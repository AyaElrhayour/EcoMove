package org.youcode.ecomove.utils;

import java.util.UUID;

public class PartnerValidation {

    public PartnerValidation(){}

    public static boolean isValidUUID(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
