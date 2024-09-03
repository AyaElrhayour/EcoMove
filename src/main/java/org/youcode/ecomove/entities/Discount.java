package org.youcode.ecomove.entities;

import org.youcode.ecomove.enums.DISCOUNTTYPE;
import org.youcode.ecomove.enums.OFFERSTATUS;

import java.time.LocalDate;
import java.util.UUID;

public class Discount {

    private UUID discountId;
    private String offerName;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private DISCOUNTTYPE discountType;
    private String conditions;
    private OFFERSTATUS offerStatus;

}
