CREATE TABLE IF NOT EXISTS `bookmark` (
  `member_id` varchar(255) NOT NULL,
  `record_number` varchar(255) NOT NULL,
  `bookmark_yn` varchar(2) DEFAULT NULL,
  PRIMARY KEY (member_id, record_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;