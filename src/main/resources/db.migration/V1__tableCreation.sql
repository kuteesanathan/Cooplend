CREATE TABLE users
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name     VARCHAR(255) NOT NULL,
    email         VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role          VARCHAR(20)  NOT NULL, -- ADMIN, LOAN_OFFICER, MEMBER
    active        BOOLEAN      NOT NULL DEFAULT TRUE,
    CONSTRAINT uq_users_email UNIQUE (email)
);

CREATE TABLE members
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    member_number VARCHAR(255) NOT NULL,
    full_name     VARCHAR(255) NOT NULL,
    national_id   VARCHAR(255) NOT NULL,
    telephone     VARCHAR(255) NOT NULL,
    date_of_birth date         NOT NULL,
    CONSTRAINT pk_members PRIMARY KEY (id)
);

ALTER TABLE members
    ADD CONSTRAINT uc_members_member_number UNIQUE (member_number);

ALTER TABLE members
    ADD CONSTRAINT uc_members_national UNIQUE (national_id);

CREATE TABLE loan_products
(
    id                   BIGINT AUTO_INCREMENT NOT NULL,
    code                 VARCHAR(255)          NOT NULL,
    name                 VARCHAR(255)          NOT NULL,
    minimum_amount       DECIMAL(15, 2)        NOT NULL,
    maximum_amount       DECIMAL(15, 2)        NOT NULL,
    annual_interest_rate DECIMAL(5, 2)         NOT NULL,
    minimum_term_months  INT                   NOT NULL,
    maximum_term_months  INT                   NOT NULL,
    active               BIT(1)                NOT NULL,
    CONSTRAINT pk_loan_products PRIMARY KEY (id)
);

ALTER TABLE loan_products
    ADD CONSTRAINT uc_loan_products_code UNIQUE (code);

create table repayments
(
    id                    BIGINT auto_increment
        primary key,
    loan_id               BIGINT         not null,
    amount                DECIMAL(15, 2) not null,
    transaction_reference VARCHAR(100)   not null,
    source                VARCHAR(20)    not null,
    date                  DATETIME       not null,
    recorded_by           BIGINT         not null,
    constraint repayments_pk
        unique (transaction_reference),
    constraint repayments_loans_id_fk
        foreign key (loan_id) references loans (id),
    constraint repayments_users_id_fk
        foreign key (recorded_by) references users (id)
);
CREATE INDEX idx_repayments_loans_id ON repayments(loan_id);

create table loans
(
    id                  BIGINT auto_increment
        primary key,
    account_number      VARCHAR(50)                  not null,
    application_id      BIGINT                       not null,
    principal           DECIMAL(15, 2)               not null,
    interest            DECIMAL(15, 2)               not null,
    total_due           DECIMAL(15, 2)               not null,
    outstanding_balance DECIMAL(15, 2)               not null,
    start_date          DATE                         not null,
    end_date            DATE                         not null,
    status              VARCHAR(20) default 'ACTIVE' not null,
    constraint uq_loans_account_number
        unique (account_number),
    constraint uq_loans_application_id
        unique (application_id),
    constraint loans_loan_applications_id_fk
        foreign key (application_id) references loan_applications (id)
);
CREATE TABLE loan_applications
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id         BIGINT         NOT NULL,
    product_id        BIGINT         NOT NULL,
    amount            DECIMAL(15, 2) NOT NULL,
    term_months       INT            NOT NULL,
    purpose           VARCHAR(500)   NOT NULL,
    status            VARCHAR(20)    NOT NULL DEFAULT 'PENDING',
    assessment_result VARCHAR(30)    NOT NULL DEFAULT 'NOT_ASSESSED',
    review_reason     VARCHAR(500),
    submitted_date    DATETIME       NOT NULL,
    reviewed_date     DATETIME,
    reviewer_id       BIGINT,
    CONSTRAINT fk_loan_applications_member
        FOREIGN KEY (member_id) REFERENCES members (id),
    CONSTRAINT fk_loan_applications_product
        FOREIGN KEY (product_id) REFERENCES loan_products (id),
    CONSTRAINT fk_loan_applications_reviewer
        FOREIGN KEY (reviewer_id) REFERENCES users (id)
);

CREATE TABLE payment_requests
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    loan_id            BIGINT         NOT NULL,
    amount             DECIMAL(15, 2) NOT NULL,
    internal_reference VARCHAR(100)   NOT NULL,
    provider_reference VARCHAR(100),
    status             VARCHAR(20)    NOT NULL DEFAULT 'PENDING',
    created_date       DATETIME       NOT NULL,
    updated_date       DATETIME,
    CONSTRAINT uq_payment_requests_internal_reference UNIQUE (internal_reference),
    CONSTRAINT fk_payment_requests_loan
        FOREIGN KEY (loan_id) REFERENCES loans (id)
);

CREATE TABLE audit_entries(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    action VARCHAR(100) NOT NULL,
    entity_type VARCHAR(100) NOT NULL,
    actor_id BIGINT not null ,
    entity_id BIGINT NOT NULL,
    timestamp DATETIME NOT NULL,
    description VARCHAR(1000),
    CONSTRAINT fk_audit_entries_actor FOREIGN KEY (actor_id) REFERENCES users(id)
);


