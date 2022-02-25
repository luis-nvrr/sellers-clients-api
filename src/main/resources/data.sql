INSERT INTO User (creation_date, email, id, username)
VALUES (CURRENT_DATE, 'seller@meli.com', 1, 'seller'),
       (CURRENT_DATE, 'buyer@meli.com', 2, 'buyer'),
       (CURRENT_DATE, 'seller1@meli.com', 3, 'seller1'),
       (CURRENT_DATE, 'buyer1@meli.com', 4, 'buyer1'),
       (CURRENT_DATE, 'seller2@meli.com', 5, 'seller2'),
       (CURRENT_DATE, 'buyer2@meli.com', 6, 'buyer2');

INSERT INTO Seller (id, user)
VALUES (1, 1),
       (2, 3),
       (3, 5);

INSERT INTO Buyer (id, user)
VALUES ( 1, 2 ),
       ( 2, 4 ),
       ( 3, 6 );