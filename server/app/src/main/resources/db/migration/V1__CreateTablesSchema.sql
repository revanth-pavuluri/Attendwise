
CREATE TABLE IF NOT EXISTS `Student` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL UNIQUE,
  `name` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `device` varchar(150) DEFAULT NULL,
  `rollnumber` int(11) DEFAULT NULL,
  `classname` varchar(15) NOT NULL,
  `createdon` timestamp NOT NULL DEFAULT current_timestamp(),
  `updatedon` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  Primary Key(id)
);

CREATE TABLE IF NOT EXISTS `Faculty` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL UNIQUE,
  `name` varchar(50) NOT NULL,
  `password` varchar(32) NOT NULL,
  `role` varchar(15) NOT NULL,
  `createdon` timestamp NOT NULL DEFAULT current_timestamp(),
  `updatedon` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  Primary Key(id)
);

CREATE TABLE IF NOT EXISTS `Subject` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `code` varchar(15) NOT NULL,
  `createdon` timestamp NOT NULL DEFAULT current_timestamp(),
  `updatedon` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  Primary Key(id)
);


CREATE TABLE IF NOT EXISTS `AttendRecord` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `fid` int(10),
  `subid` int(10) NOT NULL,
  `classname` varchar(15) NOT NULL,
  `periods` int(10) DEFAULT 1,
  `status` int(10) DEFAULT 0,
  `type` varchar(10) NOT NULL,
  `data` varchar(20) DEFAULT NULL,
  `date` date NOT NULL,
  `createdon` timestamp NOT NULL DEFAULT current_timestamp(),
  `updatedon` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  Primary Key(id),
  FOREIGN KEY(fid) REFERENCES Faculty(id) on UPDATE CASCADE on DELETE set NULL,
  FOREIGN KEY(subid) REFERENCES Subject(id) on UPDATE CASCADE on DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS `TempRecord` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `aid` int(10) NOT NULL,
  `sid` int(10) NOT NULL,
  `createdon` timestamp NOT NULL DEFAULT current_timestamp(),
  `updatedon` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  Primary Key(Id),
  Unique(aid,sid),
  FOREIGN KEY(aid) REFERENCES AttendRecord(id) on UPDATE CASCADE on DELETE CASCADE,
  FOREIGN KEY(sid) REFERENCES Student(id) on UPDATE CASCADE on DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `Absentdata` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `aid` int(10) NOT NULL,
  `sid` int(10) NOT NULL,
  `createdon` timestamp NOT NULL DEFAULT current_timestamp(),
  Primary Key(id),
  Unique(aid,sid),
  FOREIGN KEY(aid) REFERENCES AttendRecord(id) on UPDATE CASCADE on DELETE CASCADE,
  FOREIGN KEY(sid) REFERENCES Student(id) on UPDATE CASCADE on DELETE CASCADE
);

