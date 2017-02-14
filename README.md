


# North-American City Finder (na-city-finder)

Simple Java implementation of a suggestion api for north-american cities that have a population larger than 5000. 
This is for the Coveo Coding Challenge, which is the origin of the fork. The original text is at the bottom of this page.

## Implementation Details

### Hosting and services

The api is hosted by Heroku at https://na-city-finder.herokuapp.com. The api uses an Heroku-managed PostgreSQL to store cities
and execute search statement based on queries.

### Frameworks

All dependancies are managed through Maven.

- **Spark** for API routes
- **Hibernate** for object relationnal mapping to database
- **PostgreSQL** for JDBC connection drivers

### City Informations

All city information is fetched from the database. However, a local version can be ran using an "in-memory" city registry through
the use of a convenient interface. The CityFinder uses whatever CityRegistry is passed during construction.

## Build and deployment

Different Heroku Procfile are included, for different purposes :

Normal build :
- Procfile : for standard deployment to Heroku
- Procfile_local : for deployment on Windows machines (dev pc)

Populate Database build :
- Procfile_populateDb : for deployment on Windows machines

This second build loads an in-memory registry and batch-inserts the cities in the database registry. NOTE : this does not delete 
rows from the database. To clear the database, uncomment
```xml 
<property name="hbm2ddl.auto">create</property> 
```
in hibernate.cfg.xml.
After the database is populated (it should take around a minute), do not forget to remove the property from the config file.




# (Original Text) Coveo Backend Coding Challenge
(inspired by https://github.com/busbud/coding-challenge-backend-c)

## Requirements

Design an API endpoint that provides auto-complete suggestions for large cities.

- The endpoint is exposed at `/suggestions`
- The partial (or complete) search term is passed as a querystring parameter `q`
- The caller's location can optionally be supplied via querystring parameters `latitude` and `longitude` to help improve relative scores
- The endpoint returns a JSON response with an array of scored suggested matches
    - The suggestions are sorted by descending score
    - Each suggestion has a score between 0 and 1 (inclusive) indicating confidence in the suggestion (1 is most confident)
    - Each suggestion has a name which can be used to disambiguate between similarly named locations
    - Each suggestion has a latitude and longitude

## "The rules"

- *You can use the language and technology of your choosing.* It's OK to try something new (tell us if you do), but feel free to use something you're comfortable with. We don't care if you use something we don't; the goal here is not to validate your knowledge of a particular technology.
- End result should be deployed on a public Cloud (Heroku, AWS etc. all have free tiers you can use).

## Advices

- **Try to design and implement your solution as you would do for real production code**. Show us how you create clean, maintainable code that does awesome stuff. Build something that we'd be happy to contribute to. This is not a programming contest where dirty hacks win the game.
- Feel free to add more features! Really, we're curious about what you can think of. We'd expect the same if you worked with us.
- Documentation and maintainability is a plus.
- Don't you forget those unit tests.
- We don’t want to know if you can do exactly as asked (or everybody would have the same result). We want to know what **you** bring to the table when working on a project, what is your secret sauce. More features? Best solution? Thinking outside the box?

## Sample responses

These responses are meant to provide guidance. The exact values can vary based on the data source and scoring algorithm

**Near match**

    GET /suggestions?q=Londo&latitude=43.70011&longitude=-79.4163

```json
{
  "suggestions": [
    {
      "name": "London, ON, Canada",
      "latitude": "42.98339",
      "longitude": "-81.23304",
      "score": 0.9
    },
    {
      "name": "London, OH, USA",
      "latitude": "39.88645",
      "longitude": "-83.44825",
      "score": 0.5
    },
    {
      "name": "London, KY, USA",
      "latitude": "37.12898",
      "longitude": "-84.08326",
      "score": 0.5
    },
    {
      "name": "Londontowne, MD, USA",
      "latitude": "38.93345",
      "longitude": "-76.54941",
      "score": 0.3
    }
  ]
}
```

**No match**

    GET /suggestions?q=SomeRandomCityInTheMiddleOfNowhere

```json
{
  "suggestions": []
}
```

## References

- Geonames provides city lists Canada and the USA http://download.geonames.org/export/dump/readme.txt

## Getting Started

Begin by forking this repo and cloning your fork. GitHub has apps for [Mac](http://mac.github.com/) and
[Windows](http://windows.github.com/) that make this easier.
