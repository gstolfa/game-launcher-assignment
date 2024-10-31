CREATE TABLE IF NOT EXISTS `game_session` (
  `customer_id` int AUTO_INCREMENT  PRIMARY KEY,
  `game_code` varchar(100) NOT NULL,
  `session_id` varchar(36) NOT NULL,
  `created_at` datetime NOT NULL
 );