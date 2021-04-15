DROP TABLE movie_genres;
DROP TABLE movies;
DROP TABLE genres;

CREATE TABLE movies(
    id SERIAL,
    title VARCHAR(255) UNIQUE NOT NULL,
    release_date TIMESTAMP NOT NULL,
    duration VARCHAR(255) NOT NULL,
    score REAL NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE genres(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE movie_genres(
    movie_id INTEGER,
    genre_id INTEGER,
    PRIMARY KEY(movie_id, genre_id),
    FOREIGN KEY(movie_id) REFERENCES movie(id) ON DELETE CASCADE,
    FOREIGN KEY(genre_id) REFERENCES genres(id)
);