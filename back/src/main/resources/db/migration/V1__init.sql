-- Users table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE
);
-- Themes table
CREATE TABLE theme (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT
);
-- User-Theme relationship table
CREATE TABLE user_themes (
    user_id BIGINT NOT NULL,
    theme_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, theme_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (theme_id) REFERENCES theme(id)
);
-- Articles table
CREATE TABLE article (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at DATETIME NOT NULL,
    author_id BIGINT NOT NULL,
    theme_id BIGINT NOT NULL,
    FOREIGN KEY (author_id) REFERENCES users(id),
    FOREIGN KEY (theme_id) REFERENCES theme(id)
);
-- Comments table
CREATE TABLE comment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    created_at DATETIME NOT NULL,
    author_id BIGINT NOT NULL,
    article_id BIGINT NOT NULL,
    FOREIGN KEY (author_id) REFERENCES users(id),
    FOREIGN KEY (article_id) REFERENCES article(id)
);