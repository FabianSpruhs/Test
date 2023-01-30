INSERT INTO employeejpa (city,
                         house_number,
                         postcode,
                         street,
                         first_name,
                         last_name,
                         mail,
                         scheduled_vacation_days,
                         status,
                         telephone_number,
                         id,
                         role)
VALUES ('Bonn',
        '1a',
        10000,
        'Test Street',
        'Test First Name 1',
        'Test Last Name 1',
        'test@testen.com',
        30,
        'ACTIVE',
        '+49 221 111222',
        0,
        'STANDARD'
       );

INSERT INTO employeejpa (city,
                         house_number,
                         postcode,
                         street,
                         first_name,
                         last_name,
                         mail,
                         scheduled_vacation_days,
                         status,
                         telephone_number,
                         id,
                         role)
VALUES ('Berlin',
        '2a',
        20000,
        'Test Avenue',
        'Test First Name 2',
        'Test Last Name 2',
        'tests@testen.com',
        20,
        'VACATION',
        '+49 221 111222',
        1,
        'ADMIN'
       );

INSERT INTO clientjpa (ID,
                       CITY,
                       HOUSE_NUMBER,
                       POSTCODE,
                       STREET,
                       NAME,
                       CONTACT_EMPLOYEE_ID)
VALUES (3,
        'Hamburg',
        '3a',
        30000,
        'Testen Street',
        'Test Project 1',
        1
       );

INSERT INTO clientjpa (ID,
                       CITY,
                       HOUSE_NUMBER,
                       POSTCODE,
                       STREET,
                       NAME,
                       CONTACT_EMPLOYEE_ID)
VALUES (4,
        'Leipzig',
        '4a',
        40000,
        'Testen Street',
        'Test Project 2',
        0
       );

INSERT INTO projectjpa (ID,
                        NAME,
                        CLIENT_ID)
values (5,
        'Test Project 1',
        4);

INSERT INTO projectjpa (ID,
                        NAME,
                        CLIENT_ID)
values (6,
        'Test Project 1',
        3);

INSERT INTO sick_notejpa (ID,
                          BEGIN_DATE,
                          END_DATE,
                          EMPLOYEE_ID)
VALUES (7,
        '2022-11-01',
        '2022-11-02',
        0);

INSERT INTO vacation_requestjpa (ID,
                                 BEGIN_DATE,
                                 END_DATE,
                                 STATUS,
                                 VACATION_DAYS,
                                 EMPLOYEE_ID)
VALUES (8,
        '2022-11-01',
        '2022-11-02',
        'OPEN',
        1,
        1);


INSERT INTO vacation_requestjpa (ID,
                                 BEGIN_DATE,
                                 END_DATE,
                                 STATUS,
                                 VACATION_DAYS,
                                 EMPLOYEE_ID)
VALUES (9,
        '2022-10-01',
        '2022-10-03',
        'APPROVED',
        2,
        0);

INSERT INTO vacation_requestjpa (ID,
                                 BEGIN_DATE,
                                 END_DATE,
                                 STATUS,
                                 VACATION_DAYS,
                                 EMPLOYEE_ID)
VALUES (10,
        '2022-10-01',
        '2022-10-03',
        'APPROVED',
        2,
        1);

INSERT INTO working_hourjpa (ID,
                             WORKING_DAY,
                             WORKING_HOUR_STATUS,
                             WORKING_HOURS,
                             EMPLOYEE_ID)
values (11,
        '2022-12-1',
        'BOOKED',
        2,
        0);

INSERT INTO working_hourjpa (ID,
                             WORKING_DAY,
                             WORKING_HOUR_STATUS,
                             WORKING_HOURS,
                             EMPLOYEE_ID)
values (12,
        '2022-12-2',
        'BOOKED',
        3,
        0);

INSERT INTO working_hourjpa (ID,
                             WORKING_DAY,
                             WORKING_HOUR_STATUS,
                             WORKING_HOURS,
                             EMPLOYEE_ID)
values (13,
        '2022-12-3',
        'FINALIZED',
        1,
        0);

INSERT INTO working_hourjpa (ID,
                             WORKING_DAY,
                             WORKING_HOUR_STATUS,
                             WORKING_HOURS,
                             EMPLOYEE_ID)
values (14,
        '2022-12-4',
        'FINALIZED',
        4,
        0);

INSERT INTO working_hourjpa (ID,
                             WORKING_DAY,
                             WORKING_HOUR_STATUS,
                             WORKING_HOURS,
                             EMPLOYEE_ID)
values (15,
        '2022-12-1',
        'BOOKED',
        2,
        0);

INSERT INTO working_hourjpa (ID,
                             WORKING_DAY,
                             WORKING_HOUR_STATUS,
                             WORKING_HOURS,
                             EMPLOYEE_ID)
values (16,
        '2022-12-2',
        'BOOKED',
        3,
        0);

INSERT INTO working_hourjpa (ID,
                             WORKING_DAY,
                             WORKING_HOUR_STATUS,
                             WORKING_HOURS,
                             EMPLOYEE_ID)
values (17,
        '2022-12-3',
        'FINALIZED',
        1,
        0);

INSERT INTO working_hourjpa (ID,
                             WORKING_DAY,
                             WORKING_HOUR_STATUS,
                             WORKING_HOURS,
                             EMPLOYEE_ID)
values (18,
        '2022-12-4',
        'FINALIZED',
        4,
        0);

INSERT INTO employee_to_project (ID,
                                 SCHEDULED_WORKING_HOURS,
                                 EMPLOYEE_ID,
                                 PROJECT_ID)
VALUES (19,
        40,
        0,
        5);

INSERT INTO employee_to_project (ID,
                                 SCHEDULED_WORKING_HOURS,
                                 EMPLOYEE_ID,
                                 PROJECT_ID)
VALUES (20,
        50,
        1,
        6);

INSERT INTO login_user (id, username, password, user_role, employee_id) values (22, 'standard', '$2a$12$Cx3QPdzP89530AjHZeo25uiycCkJVNmVkYywgQkHwEMUEZX5.uRnK', 'STANDARD', 0);
INSERT INTO login_user (id, username, password, user_role, employee_id) values (23, 'admin', '$2a$12$Cx3QPdzP89530AjHZeo25uiycCkJVNmVkYywgQkHwEMUEZX5.uRnK', 'ADMIN', 1);