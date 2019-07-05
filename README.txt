
This is a Spring boot rest micro service which does the following,
1. load the data from csv file in local resource folder
2. query the movie list based on year and genre

Database Setup
    Option A: if you don't have docker installed,
    1. Install mongodb and give permission to mongodb data location
    Run the following linux command in terminal
        brew install mongodb
        mkdir -p /data/db
        sudo chmod 0755 /data/db

    2. Log into mongodb and create composite key on title and director fields of movie collection to uniquely identify a movie
    In another terminal run 'mongo' and run the following command in the mongodb terminal once logged in
        use dev
        db.movie.drop()
        db.movie.createIndex( { title: 1, director: 1 }, { unique: true } )
    Option B:  if you have docker installed locally
    Run the following command, it will download the my public docker image longhai/my-first-repo:mongo which has mongodb installed
    ./gradlew startDockerDB

Run the app
    1. build the project
    Run './gradlew clean build -x test'.
    2. start the app
    Run './gradlew bootRun'

User Story Location:
/movies-service/features/UserStory-Upload movie data from scv file
/movies-service/features/UserStory-Create an endpoint to query movies by year
/movies-service/features/UserStory-Modify movies enpoint to accept the genre

End Point:
    Please see the self-explanatory Swagger UI for detailed data contract
    1. POST /csv
        query parameters:
            String fileName: This is the name of the csv file in the resource folder. Put your csv file under resources/csv/

        sample request
        http://localhost:8080/csv?fileName=movieList1
        http://localhost:8080/csv?fileName=IMDB-Movie-Data_Assignment

        sample response:
        Your data load request have been submitted successfully, your ticket number is 93910251-41fe-48aa-9ffc-45cb890a77c6

        Note:
        In this specific case below, since movieList1 contains the first 10 records of the IMDB-Movie-Data_Assignment.csv file,
        we will see the following log (after adding async processing no longer able to see in the response) if we first load movieList1.csv before IMDB-Movie-Data_Assignment.csv
        {
          "message": [
            "990 movies inserted. Skip the following duplicates since they are already exists"
          ],
          "movies": [
            {
              "title": "Guardians of the Galaxy",
              "genre": [
                "Action",
                "Adventure",
                "Sci-Fi"
              ],
              "description": "A group of intergalactic criminals are forced to work together to stop a fanatical warrior from taking control of the universe.",
              "year": 2014,
              "runtime": 121,
              "rating": 8.1
            },
            {
              "title": "Prometheus",
              "genre": [
                "Adventure",
                "Mystery",
                "Sci-Fi"
              ],
              "description": "Following clues to the origin of mankind, a team finds a structure on a distant moon, but they soon realize they are not alone.",
              "year": 2012,
              "runtime": 124,
              "rating": 7.0
            },
            {
              "title": "Split",
              "genre": [
                "Horror",
                "Thriller"
              ],
              "description": "Three girls are kidnapped by a man with a diagnosed 23 distinct personalities. They must try to escape before the apparent emergence of a frightful new 24th.",
              "year": 2016,
              "runtime": 117,
              "rating": 7.3
            },
            {
              "title": "Sing",
              "genre": [
                "Animation",
                "Comedy",
                "Family"
              ],
              "description": "In a city of humanoid animals, a hustling theater impresario's attempt to save his theater with a singing competition becomes grander than he anticipates even as its finalists' find that their lives will never be the same.",
              "year": 2016,
              "runtime": 108,
              "rating": 7.2
            },
            {
              "title": "Suicide Squad",
              "genre": [
                "Action",
                "Adventure",
                "Fantasy"
              ],
              "description": "A secret government agency recruits some of the most dangerous incarcerated super-villains to form a defensive task force. Their first mission: save the world from the apocalypse.",
              "year": 2016,
              "runtime": 123,
              "rating": 6.2
            },
            {
              "title": "The Great Wall",
              "genre": [
                "Action",
                "Adventure",
                "Fantasy"
              ],
              "description": "European mercenaries searching for black powder become embroiled in the defense of the Great Wall of China against a horde of monstrous creatures.",
              "year": 2016,
              "runtime": 103,
              "rating": 6.1
            },
            {
              "title": "La La Land",
              "genre": [
                "Comedy",
                "Drama",
                "Music"
              ],
              "description": "A jazz pianist falls for an aspiring actress in Los Angeles.",
              "year": 2016,
              "runtime": 128,
              "rating": 8.3
            },
            {
              "title": "Mindhorn",
              "genre": [
                "Comedy"
              ],
              "description": "A has-been actor best known for playing the title character in the 1980s detective series \"Mindhorn\" must work with the police when a serial killer says that he will only speak with Detective Mindhorn, whom he believes to be a real person.",
              "year": 2016,
              "runtime": 89,
              "rating": 6.4
            },
            {
              "title": "The Lost City of Z",
              "genre": [
                "Action",
                "Adventure",
                "Biography"
              ],
              "description": "A true-life drama, centering on British explorer Col. Percival Fawcett, who disappeared while searching for a mysterious city in the Amazon in the 1920s.",
              "year": 2016,
              "runtime": 141,
              "rating": 7.1
            },
            {
              "title": "Passengers",
              "genre": [
                "Adventure",
                "Drama",
                "Romance"
              ],
              "description": "A spacecraft traveling to a distant colony planet and transporting thousands of people has a malfunction in its sleep chambers. As a result, two passengers are awakened 90 years early.",
              "year": 2016,
              "runtime": 116,
              "rating": 7.0
            }
          ]
        }

    2. GET /movies
        query parameters:
            Integer year: 1900~2000, default to 2016 if missing
            Integer from: 1900~2000 and smaller than to
            Integer to: 1900~2000 and bigger than from
            String genre: default to return all genres if missing

        sample request:
        http://localhost:8080/movies?from=2015&to=2017&genre=Comedy

        sample response:
        {
            "message": [
                "3 movies found."
            ],
            "movies": [
                {
                    "title": "Mindhorn",
                    "genre": [
                        "Comedy"
                    ],
                    "description": "A has-been actor best known for playing the title character in the 1980s detective series \"Mindhorn\" must work with the police when a serial killer says that he will only speak with Detective Mindhorn, whom he believes to be a real person.",
                    "year": 2016,
                    "runtime": 89,
                    "rating": 6.4
                },
                {
                    "title": "Sing",
                    "genre": [
                        "Animation",
                        "Comedy",
                        "Family"
                    ],
                    "description": "In a city of humanoid animals, a hustling theater impresario's attempt to save his theater with a singing competition becomes grander than he anticipates even as its finalists' find that their lives will never be the same.",
                    "year": 2016,
                    "runtime": 108,
                    "rating": 7.2
                },
                {
                    "title": "La La Land",
                    "genre": [
                        "Comedy",
                        "Drama",
                        "Music"
                    ],
                    "description": "A jazz pianist falls for an aspiring actress in Los Angeles.",
                    "year": 2016,
                    "runtime": 128,
                    "rating": 8.3
                }
            ]
        }

Nice-to-haves:
    1. Use async processing for the data loading (java future, spring event framework or kafka) - Checked
    2. Use multi processing for the data loading (executorService or threadPool) - Checked
    3. Use map reduce to divide the work load for the data loading.
    4. Use mongodb pre-installed Docker image and build the complete portal docker image for the project
    5. Automate the database setup task with shell or python

Credits:
https://spring.io/guides/gs/accessing-data-mongodb/#initial
https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
https://www.baeldung.com/spring-data-mongodb-tutorial

This-and-that:
    1. I used mongodb because it is preferred in the assignment, but I prefer relational databases like Oracle or Mysql.
        a, data is highly structured
        b, not going to be changing that frequently
        c, movies is not expected to increase exponentially each year.
        d, relation databases can have less data redundancy and high integrity
        e, I am more familiar with relational database :)

    2. I downloaded the latest spring project from io spring initializer and try to make to make it work with my old libraries and code,
       I spent less than 1 day for implementation but more than 1 day to make all incompatible libraries work with each other,
       still not able to run the spring integration tests, which seems fine in my old projects.
       Wish Spring Boot can keep backward comparability while introducing new versions, so that we can save some time!

