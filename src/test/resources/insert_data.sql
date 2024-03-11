INSERT INTO users (login, password, email, phone, full_name, birthday)
VALUES ('Sanzhar', '123', 'shakrat.sg@gmail.com', '111', 'Shakrat Sanzhar', '1999-11-09'),
       ('Bekzat', '123', 'koychibaev.bk@gmail.com', '222', 'Koychibaev Bekzat', '1999-11-24'),
       ('Ivan', '123', 'ivanov.ii@gmail.com', '333', 'Ivanov Ivan', '2000-11-15');

INSERT INTO account (user_id, balance, initial_deposit)
VALUES ((SELECT id FROM users WHERE login='Sanzhar'),200.0,200.0),
       ((SELECT id FROM users WHERE login='Bekzat'),100.0,100.0),
       ((SELECT id FROM users WHERE login='Ivan'),50.0,50.0);