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
    private Contract contract;

    public Discount() {}

    public Discount(UUID discountId,String offerName, String description, LocalDate startDate, LocalDate endDate,
                    DISCOUNTTYPE discountType, String conditions, OFFERSTATUS offerStatus, Contract contract){
        this.discountId = discountId;
        this.offerName = offerName;
        this.description =description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountType = discountType;
        this.conditions = conditions;
        this.offerStatus = offerStatus;
        this.contract = contract;
    }

    public UUID getDiscountId() {
        return discountId;
    }

    public void setDiscountId (UUID discountId){
        this.discountId = discountId;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public DISCOUNTTYPE getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DISCOUNTTYPE discountType) {
        this.discountType = discountType;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public OFFERSTATUS getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(OFFERSTATUS offerStatus) {
        this.offerStatus = offerStatus;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
