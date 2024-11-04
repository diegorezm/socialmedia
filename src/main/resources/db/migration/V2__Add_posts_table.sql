CREATE TABLE posts (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  user_id VARCHAR(36) NOT NULL,
  content TEXT NOT NULL,
  image VARCHAR(255),
  likes INT DEFAULT 0 NOT NULL ,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  updated_at timestamp,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT not_negative_likes CHECK (likes >= 0),
  CONSTRAINT no_empty_content CHECK (content <> '')
);
