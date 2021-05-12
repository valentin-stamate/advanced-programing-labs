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
    votes INTEGER NOT NULL,
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

SELECT m.title, g.name FROM movie_genres mg JOIN movies m ON m.id_movie = mg.id_movie JOIN genres g ON g.id = mg.id_genre WHERE mg.id_movie = 'tt0004825';
SELECT m.title, d.name FROM movie_directors md JOIN movies m ON m.id_movie = md.id_movie JOIN directors d on md.id_director = d.id WHERE md.id_movie = 'tt0004825';
SELECT m.title, a.name FROM movie_actors ma JOIN movies m ON m.id_movie = ma.id_movie JOIN actors a on ma.id_actor = a.id WHERE ma.id_movie = 'tt0004825';

SELECT * FROM movies votes WHERE votes > 1000000 ORDER BY score DESC LIMIT 10;
SELECT COUNT(ma.id_movie) activity, a.name FROM movie_actors ma JOIN actors a on ma.id_actor = a.id GROUP BY a.name ORDER BY activity DESC LIMIT 10;
SELECT COUNT(mg.id_movie) movie_count, g.name FROM movie_genres mg JOIN genres g on mg.id_genre = g.id GROUP BY g.name LIMIT 10;

DELETE FROM movie_directors;
DELETE FROM movie_actors;
DELETE FROM movie_genres;
DELETE FROM actors;
DELETE FROM directors;
DELETE FROM movies;
DELETE FROM genres;