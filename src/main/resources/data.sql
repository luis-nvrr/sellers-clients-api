INSERT INTO User (creation_date, email, id, username)
VALUES (CURRENT_DATE, 'seller@meli.com', 1, 'seller'),
       (CURRENT_DATE, 'buyer@meli.com', 2, 'buyer');

INSERT INTO Seller (id, user)
VALUES (1, 1);

INSERT INTO Buyer (id, user)
VALUES ( 1, 2 );