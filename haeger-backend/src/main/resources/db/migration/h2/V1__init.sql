CREATE SEQUENCE hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE employeejpa
(
    id                      INT          NOT NULL,
    first_name              VARCHAR(255) NOT NULL,
    last_name               VARCHAR(255) NOT NULL,
    mail                    VARCHAR(255),
    telephone_number        VARCHAR(255),
    status                  VARCHAR(255),
    role                    VARCHAR(255),
    scheduled_vacation_days FLOAT        NOT NULL,
    street                  VARCHAR(255),
    house_number            VARCHAR(255),
    postcode                INT          NOT NULL,
    city                    VARCHAR(255),
    CONSTRAINT pk_employeejpa PRIMARY KEY (id)
);
CREATE TABLE clientjpa
(
    id                  INT          NOT NULL,
    name                VARCHAR(255) NOT NULL,
    contact_employee_id INT          NOT NULL,
    street              VARCHAR(255),
    house_number        VARCHAR(255),
    postcode            INT          NOT NULL,
    city                VARCHAR(255),
    CONSTRAINT pk_clientjpa PRIMARY KEY (id)
);

ALTER TABLE clientjpa
    ADD CONSTRAINT FK_CLIENTJPA_ON_CONTACTEMPLOYEE FOREIGN KEY (contact_employee_id) REFERENCES employeejpa (id);

CREATE TABLE projectjpa
(
    id        INT          NOT NULL,
    client_id INT,
    name      VARCHAR(255) NOT NULL,
    CONSTRAINT pk_projectjpa PRIMARY KEY (id)
);

ALTER TABLE projectjpa
    ADD CONSTRAINT FK_PROJECTJPA_ON_CLIENT FOREIGN KEY (client_id) REFERENCES clientjpa (id);

CREATE TABLE sick_notejpa
(
    id          INT  NOT NULL,
    employee_id INT  NOT NULL,
    begin_date  date NOT NULL,
    end_date    date NOT NULL,
    CONSTRAINT pk_sicknotejpa PRIMARY KEY (id)
);

ALTER TABLE sick_notejpa
    ADD CONSTRAINT FK_SICKNOTEJPA_ON_EMPLOYEE FOREIGN KEY (employee_id) REFERENCES employeejpa (id);

CREATE TABLE vacation_requestjpa
(
    id            INT   NOT NULL,
    employee_id   INT,
    begin_date    date  NOT NULL,
    end_date      date  NOT NULL,
    status        VARCHAR(255),
    vacation_days FLOAT NOT NULL,
    CONSTRAINT pk_vacationrequestjpa PRIMARY KEY (id)
);

ALTER TABLE vacation_requestjpa
    ADD CONSTRAINT FK_VACATIONREQUESTJPA_ON_EMPLOYEE FOREIGN KEY (employee_id) REFERENCES employeejpa (id);

CREATE TABLE working_hourjpa
(
    id                  INT          NOT NULL,
    employee_id         INT          NOT NULL,
    working_day         date         NOT NULL,
    working_hours       FLOAT        NOT NULL,
    working_hour_status VARCHAR(255) NOT NULL,
    CONSTRAINT pk_workinghourjpa PRIMARY KEY (id)
);

ALTER TABLE working_hourjpa
    ADD CONSTRAINT FK_WORKINGHOURJPA_ON_EMPLOYEE FOREIGN KEY (employee_id) REFERENCES employeejpa (id);
CREATE TABLE employee_to_project
(
    id                      INT   NOT NULL,
    employee_id             INT,
    project_id              INT,
    scheduled_working_hours FLOAT NOT NULL,
    CONSTRAINT pk_employeetoproject PRIMARY KEY (id)
);

ALTER TABLE employee_to_project
    ADD CONSTRAINT FK_EMPLOYEETOPROJECT_ON_EMPLOYEE FOREIGN KEY (employee_id) REFERENCES employeejpa (id);

ALTER TABLE employee_to_project
    ADD CONSTRAINT FK_EMPLOYEETOPROJECT_ON_PROJECT FOREIGN KEY (project_id) REFERENCES projectjpa (id);