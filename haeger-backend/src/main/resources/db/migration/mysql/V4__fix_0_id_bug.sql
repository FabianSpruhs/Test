DELETE
FROM haeger_management.working_hourjpa
WHERE id = 14;

DELETE
FROM haeger_management.working_hourjpa
WHERE id = 17;

DELETE
FROM haeger_management.working_hourjpa
WHERE id = 11;

DELETE
FROM haeger_management.working_hourjpa
WHERE id = 13;

DELETE
FROM haeger_management.working_hourjpa
WHERE id = 12;

DELETE
FROM haeger_management.working_hourjpa
WHERE id = 15;

DELETE
FROM haeger_management.working_hourjpa
WHERE id = 16;

DELETE
FROM haeger_management.working_hourjpa
WHERE id = 17;

DELETE
FROM haeger_management.working_hourjpa
WHERE id = 18;

DELETE
FROM haeger_management.vacation_requestjpa
WHERE id = 9;

DELETE
FROM haeger_management.sick_notejpa
WHERE id = 7;

DELETE
FROM haeger_management.employee_to_project
WHERE id = 19;

DELETE
FROM haeger_management.projectjpa
WHERE id = 5;

DELETE
FROM haeger_management.clientjpa
WHERE id = 4;

DELETE
FROM haeger_management.login_user
WHERE id = 22;

DELETE
FROM haeger_management.employeejpa
WHERE id = 0;

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
        2,
        'STANDARD'
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
        2
       );

INSERT INTO projectjpa (ID,
                        NAME,
                        CLIENT_ID)
values (5,
        'Test Project 1',
        4);

INSERT INTO employee_to_project (ID,
                                 SCHEDULED_WORKING_HOURS,
                                 EMPLOYEE_ID,
                                 PROJECT_ID)
VALUES (19,
        40,
        2,
        5);

INSERT INTO sick_notejpa (ID,
                          BEGIN_DATE,
                          END_DATE,
                          EMPLOYEE_ID)
VALUES (7,
        '2022-11-01',
        '2022-11-02',
        2);

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
        2);

INSERT INTO login_user (id, username, password, user_role, employee_id) values (22, 'standard', '$2a$12$Cx3QPdzP89530AjHZeo25uiycCkJVNmVkYywgQkHwEMUEZX5.uRnK', 'STANDARD', 1);

INSERT INTO working_hourjpa (ID,
                             WORKING_DAY,
                             WORKING_HOUR_STATUS,
                             WORKING_HOURS,
                             EMPLOYEE_ID)
values (11,
        '2022-12-1',
        'BOOKED',
        2,
        2);

INSERT INTO working_hourjpa (ID,
                             WORKING_DAY,
                             WORKING_HOUR_STATUS,
                             WORKING_HOURS,
                             EMPLOYEE_ID)
values (12,
        '2022-12-2',
        'BOOKED',
        3,
        2);

INSERT INTO working_hourjpa (ID,
                             WORKING_DAY,
                             WORKING_HOUR_STATUS,
                             WORKING_HOURS,
                             EMPLOYEE_ID)
values (13,
        '2022-12-3',
        'FINALIZED',
        1,
        2);

INSERT INTO working_hourjpa (ID,
                             WORKING_DAY,
                             WORKING_HOUR_STATUS,
                             WORKING_HOURS,
                             EMPLOYEE_ID)
values (14,
        '2022-12-4',
        'FINALIZED',
        4,
        2);

INSERT INTO working_hourjpa (ID,
                             WORKING_DAY,
                             WORKING_HOUR_STATUS,
                             WORKING_HOURS,
                             EMPLOYEE_ID)
values (15,
        '2022-12-1',
        'BOOKED',
        2,
        2);

INSERT INTO working_hourjpa (ID,
                             WORKING_DAY,
                             WORKING_HOUR_STATUS,
                             WORKING_HOURS,
                             EMPLOYEE_ID)
values (17,
        '2022-12-3',
        'FINALIZED',
        1,
        2);
