CREATE TABLE IF NOT EXISTS `attend_records` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `faculty_id` BIGINT(20),
  `subject_id` BIGINT(20) NOT NULL,
  `class_name` varchar(15) NOT NULL,
  `periods` int(10) DEFAULT 1,
  `status` ENUM('ACTIVE', 'EXPIRED', 'FINALIZED') DEFAULT 'ACTIVE',
  `date` date NOT NULL,
  `created_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_on` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  Primary Key(id),
  FOREIGN KEY(faculty_id) REFERENCES faculties(id) on UPDATE CASCADE on DELETE set NULL,
  FOREIGN KEY(subject_id) REFERENCES subjects(id) on UPDATE CASCADE on DELETE CASCADE
);
