package org.youcode.ecomove.entities;

import org.youcode.ecomove.enums.TICKETSTATUS;
import org.youcode.ecomove.enums.TRANSPORTTYPE;

import java.time.LocalDate;
import java.util.UUID;

public class Ticket {

    private UUID ticketId;
    private TRANSPORTTYPE transportType;
    private double purchasePrice;
    private double resalePrice;
    private LocalDate resaleDate;
    private TICKETSTATUS TicketStatus;
    private Contract contract;

    public Ticket () {}

    public Ticket (UUID ticketId,TRANSPORTTYPE transportType, double purchasePrice, double resalePrice,LocalDate resaleDate,
                    TICKETSTATUS ticketStatus, Contract contract) {
        this.ticketId = ticketId;
        this.transportType = transportType;
        this.purchasePrice = purchasePrice;
        this.resalePrice =resalePrice;
        this.resaleDate = resaleDate;
        this.TicketStatus =ticketStatus;
        this.contract = contract;
    }

    public UUID getTicketId() {
        return ticketId;
    }

    public void setTicketId(UUID ticketId) {
        this.ticketId = ticketId;
    }

    public TRANSPORTTYPE getTransportType() {
        return transportType;
    }

    public void setTransportType(TRANSPORTTYPE transportType) {
        this.transportType = transportType;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getResalePrice() {
        return resalePrice;
    }

    public void setResalePrice(float resalePrice) {
        this.resalePrice = resalePrice;
    }

    public LocalDate getResaleDate() {
        return resaleDate;
    }

    public void setResaleDate(LocalDate resaleDate) {
        this.resaleDate = resaleDate;
    }

    public TICKETSTATUS getTicketStatus() {
        return TicketStatus;
    }

    public void setTicketStatus(TICKETSTATUS ticketStatus) {
        TicketStatus = ticketStatus;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
