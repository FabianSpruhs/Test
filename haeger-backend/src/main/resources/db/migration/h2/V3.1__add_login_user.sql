CREATE TABLE login_user
(
    id          INT          NOT NULL,
    username    VARCHAR(255) NOT NULL,
    password    VARCHAR(255),
    user_role   VARCHAR(255),
    employee_id INT,
    CONSTRAINT pk_loginuser PRIMARY KEY (id)
);

ALTER TABLE login_user
    ADD CONSTRAINT uc_loginuser_username UNIQUE (username);

ALTER TABLE login_user
    ADD CONSTRAINT FK_LOGINUSER_ON_EMPLOYEE FOREIGN KEY (employee_id) REFERENCES employeejpa (id);

INSERT INTO login_user (id, username, password, user_role, employee_id) values (22, 'standard', '$2a$12$Cx3QPdzP89530AjHZeo25uiycCkJVNmVkYywgQkHwEMUEZX5.uRnK', 'STANDARD', 0);
INSERT INTO login_user (id, username, password, user_role, employee_id) values (23, 'admin', '$2a$12$Cx3QPdzP89530AjHZeo25uiycCkJVNmVkYywgQkHwEMUEZX5.uRnK', 'ADMIN', 1);