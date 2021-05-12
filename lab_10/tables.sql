DROP TABLE friendships;
DROP TABLE messages;
DROP TABLE users;

CREATE TABLE users(
    id SERIAL,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE friendships(
    id SERIAL,
    user_a INT NOT NULL,
    user_b INT NOT NULL,
    UNIQUE (user_a, user_b),
    PRIMARY KEY (id),
    FOREIGN KEY (user_a) REFERENCES users(id),
    FOREIGN KEY (user_b) REFERENCES users(id)
);

CREATE TABLE messages(
    id SERIAL,
    user_from INT NOT NULL,
    user_ INT NOT NULL,
    message VARCHAR(255) NOT NULL,
    time_created TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_from) REFERENCES users(id),
    FOREIGN KEY (user_) REFERENCES users(id)
);

SELECT * FROM friendships WHERE user_a = 1;
SELECT * FROM messages WHERE