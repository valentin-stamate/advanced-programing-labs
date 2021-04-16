DROP TABLE movie_directors;
DROP TABLE movie_actors;
DROP TABLE movie_genres;
DROP TABLE actors;
DROP TABLE directors;
DROP TABLE movies;
DROP TABLE genres;

CREATE TABLE movies(
    id SERIAL,
    id_movie VARCHAR(255) UNIQUE NOT NULL,
    title VARCHAR(255) UNIQUE NOT NULL,
    release_date VARCHAR NOT NULL,
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
    id_movie VARCHAR(255),
    id_genre INTEGER,
    PRIMARY KEY(id_movie, id_genre),
    FOREIGN KEY(id_movie) REFERENCES movies(id_movie) ON DELETE CASCADE,
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
    id_movie VARCHAR(255),
    id_actor INTEGER,
    PRIMARY KEY(id_movie, id_actor),
    FOREIGN KEY(id_movie) REFERENCES movies(id_movie),
    FOREIGN KEY(id_actor) REFERENCES actors(id)
);

CREATE TABLE movie_directors(
    id_movie VARCHAR(255),
    id_director INTEGER,
    PRIMARY KEY(id_movie, id_director),
    FOREIGN KEY(id_movie) REFERENCES movies(id_movie),
    FOREIGN KEY(id_director) REFERENCES directors(id)
);

SELECT * FROM movie_genres mg WHERE mg.id_movie = 'tt0004825';
SELECT * FROM movie_directors md WHERE md.id_movie = 'tt0004825';
SELECT * FROM movie_actors ma WHERE ma.id_movie = 'tt0004825';

DELETE FROM movie_directors;
DELETE FROM movie_actors;
DELETE FROM movie_genres;
DELETE FROM actors;
DELETE FROM directors;
DELETE FROM movies;
DELETE FROM genres;