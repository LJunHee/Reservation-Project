reservationprojectrestaurantCREATE TABLE restaurant (
    id INT AUTO_INCREMENT PRIMARY KEY,
    restName VARCHAR(100) NOT NULL,
    restReview TEXT,
    restTime DATETIME NOT NULL UNIQUE
);