CREATE TABLE IF NOT EXISTS `students` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL UNIQUE,
  `name` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `device` varchar(150) DEFAULT NULL,
  `roll_number` int(11) DEFAULT NULL,
  `class_name` varchar(15) NOT NULL,
  `created_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_on` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  Primary Key(id)
);