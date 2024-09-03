CREATE DATABASE ecomove;


CREATE TYPE partnershipStatus AS ENUM ('ACTIVE', 'INACTIVE', 'SUSPENDED');
CREATE TYPE transportType AS ENUM ('AIRPLANE', 'TRAIN', 'BUS');
CREATE TYPE ticketStatus AS ENUM ('SOLD', 'CANCELLED', 'PENDING');
CREATE TYPE contractStatus AS ENUM ('IN PROGRESS', 'COMPLETED', 'SUSPENDED');
CREATE TYPE offerStatus AS ENUM ('ACTIVE', 'EXPIRED', 'SUSPENDED');
CREATE TYPE discountType AS ENUM ('PERCENTAGE', 'FIXED AMOUNT');

CREATE TABLE Partner (
    partnerId UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    companyName VARCHAR(50),
    comercialContact VARCHAR(500),
    transportType transportType,
    geographicArea VARCHAR(255),
    specialConditions VARCHAR(255),
    partnershipStatus partnershipStatus
);

CREATE TABLE Contract (
    contractId UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    startDate DATE,
    endDate DATE,
    specialPrice FLOAT,
    agreementConditions VARCHAR(500),
    renewable BOOLEAN,
    contractStatus contractStatus,
    partnerId UUID,
    FOREIGN KEY (partnerId) REFERENCES Partner(partnerId)
);

CREATE TABLE Ticket (
    ticketId UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    transportType transportType,
    purchasePrice FLOAT,
    resalePrice FLOAT,
    resaleDate DATE,
    ticketStatus ticketStatus,
    contractId UUID,
    FOREIGN KEY (contractId) REFERENCES Contract(contractId)
);

CREATE TABLE Discount (
    discountId UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    offerName VARCHAR(50),
    description VARCHAR(500),
    startDate DATE,
    endDate DATE,
    discountType discountType,
    discountValue FLOAT,
    conditions VARCHAR(500),
    contractId UUID,
    FOREIGN KEY (contractId) REFERENCES Contract(contractId)
);
