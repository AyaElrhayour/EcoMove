package org.youcode.ecomove.entities;

import org.youcode.ecomove.enums.CONTRACTSTATUS;

import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

public class Contract {

    private UUID contractId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int specialPrice;
    private String agreementConditions;
    private boolean renewable;
    private CONTRACTSTATUS contractstatus;
    private Partner partner;
    private List<Ticket> tickets;
    private List<Discount> discounts;

    public Contract() {}

    public Contract (UUID contractId,LocalDate startDate, LocalDate endDate, int specialPrice, String agreementConditions,
                     boolean renewable, CONTRACTSTATUS contractstatus ){
        this.contractId = contractId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.specialPrice = specialPrice;
        this.agreementConditions = agreementConditions;
        this.renewable = renewable;
        this.contractstatus = contractstatus;
    }

    public UUID getContractId() {
        return contractId;
    }

    public void setContractId(UUID contractId) {
        this.contractId = contractId;
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


    public int getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(int specialPrice) {
        this.specialPrice = specialPrice;
    }

    public String getAgreementConditions() {
        return agreementConditions;
    }

    public void setAgreementConditions(String agreementConditions) {
        this.agreementConditions = agreementConditions;
    }

    public boolean isRenewable() {
        return renewable;
    }

    public void setRenewable(boolean renewable) {
        this.renewable = renewable;
    }

    public CONTRACTSTATUS getContractstatus() {
        return contractstatus;
    }

    public void setContractStatus(CONTRACTSTATUS contractstatus) {
        this.contractstatus = contractstatus;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<Discount> getDiscounts(){
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }
}
