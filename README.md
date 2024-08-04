# Java Backend Technical Test

Welcome to the Java Backend Technical Test! This test is designed to assess your knowledge and skills in backend development.

The detailed instructions and requirements for this test are defined in the file `CHALLENGE.md` file. Please read it carefully before starting.

Good luck!



# Developer Guide

## Environment Variables
A set of variables are been setup across the project and could be customize either setting them
on the OS Environment Variables or passing them thru the Java Options.


| Variable                                     | Default Value                                                    | Comments                         |
|----------------------------------------------|:-----------------------------------------------------------------|----------------------------------| 
| DATABASE_URL                                 | jdbc:postgresql://localhost:5432/product                         | Postgresql URL database          |
| DATABASE_USERNAME                            | product_user                                                     | Postgresql database's username   |
| DATABASE_PASSWORD                            | EPQ4xbRrgA*zwd                                                   | Postgresql password's database   |
| JWT_SECRET                                   | 3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b | Secret key what will you use JWT |
| JWT_EXPIRATION                               | 3600000                                                          | token lifetime                   |

## Tools
### Flyway

Flyway has been setup to handle incremental changes over the database, the incremental scripts should
be placed on `src/main/resources/db/migration` and must follow the naming convention as:

```
V{version}__{description}
    where:
        -version: has the structure of major version and minor version. i.e 1_0
        -description a snake case short description of the new version
```

### Quick guide to set Up locally
#### Pre-requistes
* Install [Intellij IDEA](https://www.jetbrains.com/idea/)
* Install [Docker](https://docs.docker.com/get-docker/) and [Docker Compose](https://docs.docker.com/compose/install/)

Into the project exists a docker-compose file, in there you are going to find the following services: db and app. So, follow the nexts steps to run locally.

1. Run all services
    ```bash
        docker-compose up
    ```
2. Run the project and go to http://localhost:8080/swagger-ui/index.html# to see swagger docs.

3. Attached the Postman collection for testing with the name `Product collection.postman_collection.json`

#### How to use the services
1. To use the service that is documented in swagger and postman it is necessary to authenticate, by default a user is created with the following credentials:
```bash
        {
             "username": "jose",
             "password": "1234"
        }
   ```
Then, with the generated token, it can be used in all the endpoints that the postman and the documentation enable.