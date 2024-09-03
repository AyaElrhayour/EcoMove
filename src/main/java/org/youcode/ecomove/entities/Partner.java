package org.youcode.ecomove.entities;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.youcode.ecomove.enums.PARTNERSHIPSTATUS;
import org.youcode.ecomove.enums.TRANSPORTTYPE;

public class Partner {

        private UUID partnerId;
        private String companyName;
        private String commercialContact;
        private TRANSPORTTYPE transportType;
        private String geographicArea;
        private String specialConditions;
        private PARTNERSHIPSTATUS partnershipStatus;
        private List<Contract> contracts;

        public Partner() {}

        public Partner(UUID partnerId,String companyName, String commercialContact, TRANSPORTTYPE transportType,
                       String geographicArea, String specialConditions, PARTNERSHIPSTATUS partnershipStatus) {
            this.partnerId = partnerId;
            this.companyName = companyName;
            this.commercialContact = commercialContact;
            this.transportType = transportType;
            this.geographicArea = geographicArea;
            this.specialConditions = specialConditions;
            this.partnershipStatus = partnershipStatus;
        }

        public UUID getPartnerId() {
            return partnerId;
        }

        public void setPartnerId(UUID partnerId) {
            this.partnerId = partnerId;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getCommercialContact() {
            return commercialContact;
        }

        public void setCommercialContact(String commercialContact) {
            this.commercialContact = commercialContact;
        }

        public TRANSPORTTYPE getTransportType() {
            return transportType;
        }

        public void setTransportType(TRANSPORTTYPE transportType) {
            this.transportType = transportType;
        }

        public String getGeographicArea() {
            return geographicArea;
        }

        public void setGeographicArea(String geographicArea) {
            this.geographicArea = geographicArea;
        }

        public String getSpecialConditions() {
            return specialConditions;
        }

        public void setSpecialConditions(String specialConditions) {
            this.specialConditions = specialConditions;
        }

        public PARTNERSHIPSTATUS getPartnershipStatus() {
            return partnershipStatus;
        }

        public void setPartnershipStatus(PARTNERSHIPSTATUS partnershipStatus) {
            this.partnershipStatus = partnershipStatus;
        }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }
}

