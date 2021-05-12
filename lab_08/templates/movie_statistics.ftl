<!DOCTYPE html>
<html>
<head>
    <title>Movie Statistics</title>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
    <style>
        :root {
            font-family: 'Roboto', sans-serif;
        }
    </style>
</head>
<body>

<h1>Top rated movies (Over 1M votes):</h1>

<ul>
<#list bestMovies as movie>
    <li>
        <div>Title: <b>${movie.title}</b> Score: <b>${movie.score}</b> <a href="https://www.imdb.com/title/${movie.idMovie}/" target="_blank">IMDB Link</a></div>
    </li>
</#list>
</ul>

<h1>Worst rated movies (Over 100k votes):</h1>

<ul>
    <#list worstMovies as movie>
        <li>
            <div>Title: <b>${movie.title}</b> Score: <b>${movie.score}</b> <a href="https://www.imdb.com/title/${movie.idMovie}/" target="_blank">IMDB Link</a></div>
        </li>
    </#list>
</ul>

<h1>Top active actors:</h1>

<ul>
    <#list activeActors as actor>
        <li>
            <div>Actor name: <b>${actor.actorName}</b> Movies played: <b>${actor.activity}</b></div>
        </li>
    </#list>
</ul>

<h1>Top genres:</h1>

<ul>
    <#list topGenres as genre>
        <li>
            <div><b>${genre.name}</b></div>
        </li>
    </#list>
</ul>

</body>
</html>