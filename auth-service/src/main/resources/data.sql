DROP TABLE IF EXISTS user;

CREATE TABLE user (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(50) NOT NULL,
  role VARCHAR(50) NOT NULL
);

INSERT INTO user (username, password, role) VALUES
('omkar', '12345', 'admin'),
('rohit', '54321', 'admin'),
('pradnya', '12345', 'admin');
