-- valid 

CREATE DATABASE IF NOT EXISTS backoffice CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

use backoffice;

CREATE TABLE `User` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `admin` int  NOT NULL,
  `auth0_user_id` varchar(100)  NOT NULL,
  `email` varchar(50)  NOT NULL,
  PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `User` (`admin`, `auth0_user_id`, `email`) VALUES
(1, 'auth0|6426ddf4dc781398dca688df', 'john@gmail.com');


CREATE TABLE `Color` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `color_name` varchar(50) NOT NULL,
  `number_of_commands` int NOT NULL,
    PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `Color` (`color_name`, `number_of_commands`) VALUES
('black', 16),
('blue', 10),
('green', 20),
('grey', 6),
('red', 18),
('white', 6),
('yellow', 3);

CREATE TABLE `Statistic` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `number_of_user_connected` int DEFAULT NULL,
  `number_of_user_compte` int DEFAULT NULL,
  `number_of_user_commands` int DEFAULT NULL,
  `number_of_abandoned_bag` int DEFAULT NULL,
    PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `Statistic` (`number_of_user_connected`, `number_of_user_compte`, `number_of_user_commands`, `number_of_abandoned_bag`) VALUES
(30, 100, 50, 10);

