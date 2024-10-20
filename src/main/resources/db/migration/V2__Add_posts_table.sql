CREATE TABLE posts (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  user_id VARCHAR(32) NOT NULL,
  content TEXT NOT NULL,
  image VARCHAR(255),
  likes INT NOT NULL DEFAULT 0,
  created_at timestamp NOT NULL,
  updated_at timestamp,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT not_negative_likes CHECK (likes >= 0),
  CONSTRAINT no_empty_content CHECK (content <> '')
);
