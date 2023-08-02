CREATE DATABASE gameDatabase;
USE gameDatabase;

CREATE TABLE Game (
  game_id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  game_title VARCHAR(20) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=24;

CREATE TABLE Player (
  player_id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(20) DEFAULT NULL,
  last_name VARCHAR(20) DEFAULT NULL,
  address VARCHAR(20) DEFAULT NULL,
  postal_code VARCHAR(20) DEFAULT NULL,
  province VARCHAR(20) DEFAULT NULL,
  phone_number VARCHAR(20) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=24;

CREATE TABLE PlayerAndGame (
  player_game_id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  game_id INT(11) DEFAULT NULL,
  player_id INT(11) DEFAULT NULL,
  playing_date DATE DEFAULT NULL,
  score VARCHAR(20) DEFAULT NULL,
  FOREIGN KEY (game_id) REFERENCES Game(game_id),
  FOREIGN KEY (player_id) REFERENCES Player(player_id)
) ENGINE=InnoDB AUTO_INCREMENT=24;

/*
SELECT * FROM Player;
SELECT * FROM Game;
SELECT * FROM PlayerAndGame;

INSERT INTO Player (first_name, last_name, address,	postal_code, province, phone_number) VALUES (?, ?, ?, ?, ?, ?) 
INSERT INTO Game (game_title) VALUES (?);

DELETE FROM Game WHERE game_id = 101;

SELECT player_game_id, PlayerAndGame.game_id, game_title, PlayerAndGame.player_id, score, playing_date, first_name, last_name, address, postal_code, province, phone_number 
FROM PlayerAndGame
JOIN Game ON PlayerAndGame.game_id = Game.game_id 
JOIN Player ON PlayerAndGame.player_id = Player.player_id 
ORDER BY player_game_id;
*/

