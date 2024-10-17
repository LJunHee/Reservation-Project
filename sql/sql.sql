CREATE TABLE `restaurant` (
	`restNo` INT NOT NULL AUTO_INCREMENT,
	`restName` VARCHAR(100) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`restStar` INT NOT NULL DEFAULT '5',
	`restReview` TEXT NULL DEFAULT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`restTime` DATETIME NULL DEFAULT NULL,
	PRIMARY KEY (`restNo`) USING BTREE,
	UNIQUE INDEX `restTime` (`restTime`) USING BTREE
)
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
AUTO_INCREMENT=4
;reservationproject