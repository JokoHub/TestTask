CREATE TABLE IF NOT EXISTS `taskschema`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `userName` VARCHAR(45) NOT NULL,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC));

  CREATE TABLE IF NOT EXISTS  `taskschema`.`tasks` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `taskName` VARCHAR(45) NOT NULL,
    `taskDescription` VARCHAR(45) NOT NULL,
    `userId` INT NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC),
    INDEX `userId_idx` (`userId` ASC),
    CONSTRAINT `userId`
      FOREIGN KEY (`userId`)
      REFERENCES `taskschema`.`users` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION);

INSERT IGNORE INTO tasks (taskName, taskDescription) VALUES ('Create dataBase', 'Set Up a working database on mySQL');
INSERT IGNORE INTO tasks (taskName, taskDescription) VALUES ('Add JDBC Template', 'Learn to use spring JDBC template');
INSERT IGNORE INTO tasks (taskName, taskDescription) VALUES ('Acquire ResultSet', 'Acquire a working RS');
INSERT IGNORE INTO users (userName, login, password) VALUES ('admin' , 'admin', 'admin')