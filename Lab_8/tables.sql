DROP TABLE movie_directors;
DROP TABLE movie_actors;
DROP TABLE movie_genres;
DROP TABLE actors;
DROP TABLE directors;
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
    id SERIAL,
    name VARCHAR(255) UNIQUE NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE movie_genres(
    id_movie INTEGER,
    id_genre INTEGER,
    PRIMARY KEY(id_movie, id_genre),
    FOREIGN KEY(id_movie) REFERENCES movies(id) ON DELETE CASCADE,
    FOREIGN KEY(id_genre) REFERENCES genres(id)
);

CREATE TABLE actors(
    id SERIAL,
    name VARCHAR(255) UNIQUE NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE directors(
    id SERIAL,
    name VARCHAR(255) UNIQUE NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE movie_actors(
    id_movie INTEGER,
    id_actor INTEGER,
    PRIMARY KEY(id_movie, id_actor),
    FOREIGN KEY(id_movie) REFERENCES movies(id),
    FOREIGN KEY(id_actor) REFERENCES actors(id)
);

CREATE TABLE movie_directors(
    id_movie INTEGER,
    id_director INTEGER,
    PRIMARY KEY(id_movie, id_director),
    FOREIGN KEY(id_movie) REFERENCES movies(id),
    FOREIGN KEY(id_director) REFERENCES directors(id)
);
