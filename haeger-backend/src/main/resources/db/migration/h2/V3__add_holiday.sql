CREATE TABLE holidayjpa
(
    id           INT NOT NULL,
    name         VARCHAR(255),
    holiday_date date,
    CONSTRAINT pk_holidayjpa PRIMARY KEY (id)
);