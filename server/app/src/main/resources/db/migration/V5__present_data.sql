CREATE TABLE IF NOT EXISTS `present_data` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `attend_record_id` BIGINT(20) NOT NULL,
  `student_id` BIGINT(20) NOT NULL,
  `created_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_on` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  Primary Key(id),
  Unique(attend_record_id,student_id),
  FOREIGN KEY(attend_record_id) REFERENCES attend_records(id) on UPDATE CASCADE on DELETE CASCADE,
  FOREIGN KEY(student_id) REFERENCES students(id) on UPDATE CASCADE on DELETE CASCADE
);