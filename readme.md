### How to use this spring-boot project

- Install packages with `mvn package`
- Run `mvn spring-boot:run` for starting the application (or use your IDE)

Application (with the embedded H2 database) is ready to be used ! You can access the url below for testing it :

- Swagger UI : http://localhost:8080/swagger-ui.html
- H2 UI : http://localhost:8080/h2-console

> Don't forget to set the `JDBC URL` value as `jdbc:h2:mem:testdb` for H2 UI.



### Instructions

- download the zip file of this project
- create a repository in your own github named 'java-challenge'
- clone your repository in a folder on your machine
- extract the zip file in this folder
- commit and push

- Enhance the code in any ways you can see, you are free! Some possibilities:
  - Add tests
  - Change syntax
  - Protect controller end points
  - Add caching logic for database calls
  - Improve doc and comments
  - Fix any bug you might find
- Edit readme.md and add any comments. It can be about what you did, what you would have done if you had more time, etc.
- Send us the link of your repository.

#### Restrictions
- use java 8


#### What we will look for
- Readability of your code
- Documentation
- Comments in your code 
- Appropriate usage of spring boot
- Appropriate usage of packages
- Is the application running as expected
- No performance issues

#### Your experience in Java

Please let us know more about your Java experience in a few sentences. For example:

- I have 3 years experience in Java and I started to use Spring Boot from last year
- I'm a beginner and just recently learned Spring Boot
- I know Spring Boot very well and have been using it for many years

#### My experience in Java

I had my first contact with Java in 1999 (Java 1.2) and have been using it since 2000 professionally, first
at par-time work, since 2003 as a full-time software developer.

I'm using JEE with WildFly since 2018 (including REST via RESTEasy, Swagger, Hibernate, etc.).

I use Spring 5 for my private projects, but on a very small scale as they are simply programs (Swing as UI,
Apache Derby as database). I'm currently playing around with Spring 6 as I'm planning to migrate my projects.
But it seems there are a lot of changes to be made as even a very simple example project showed a lot of
errors after switching from Spring 5 to 6. They were easy to fix, but for my other projects it definitely will
take more time.

So far I didn't have any experience with Sprint Boot (incl. Lombok), so it was an interesting experience. That
made me curious how big scale projects in Spring Boot might look like.

#### What have you done!? 😱😉

First I went through the program and enhanced the code with Javadoc, comments as well as some Swagger
annotations. While adding Swagger annotations, I realized the project was still on Swagger 1.x. I'm working
with Swagger 2.x and the annotations seemed to have changed a lot since then.

I modified the `EmployeeService` (respective `EmployeeServiceImpl`) to give the caller more information
about the result of the actions (e.g. when an entity should be deleted a flag is returned, if the entity
was really deleted or couldn't be deleted simply because it didn't exist). I also added one more method,
`saveOrUpdateEmployee` which acted like the previous implementation of the `saveEmployee` and
`updateEmployee` methods.

I also implemented a JUnit test which tests the `EmployeeController`.

I didn't implement any entity caching as Hibernate already contains some caching strategies, which also taking
the transaction handling into account.

#### What I would have done...

I definitely would have updated the project as it seems that there are updates for all used libraries
(Spring Boot v2 -> v3, SpringFox Swagger2 v2 -> v3, Lombok -> latest version). But therefore also the
condition only to use Java 8 must be removed as Spring Boot 3 needs Java 17.

As it is only an example program, it lacks of course everything a real REST application would have, like
authentication, user management including access rights.

Another thing is that e.g. the `getEmployees` method doesn't support paging. If there are enough employees
in the database, this could lead to timeout of other requests (because the transaction would block all other
access), maybe even to an OutOfMemory exception as Hibernate loads the whole table as objects into memory.
The REST interface should offer an `offset` and `limit` parameter and the service should also enforce a
maximum limit which is always used (means if the `limit` isn't defined or greater than the maximum). But that
also means that the response shouldn't just consist of the list of employees, but also should contain the
offset and limit which have been used as well as the number of total employees.
