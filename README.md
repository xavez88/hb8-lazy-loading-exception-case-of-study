# Hibernate LazyLoadingException case of study.

In this repository we're going to inspect what Hibernate LazyLoadingException is, under what circumstances is thrown in
a Spring Boot environment and what are the different options to fix it.

## System requirements:

In order to be able to run this case of study you will need to install the following tools:

- Java 11
- Maven
- Docker

## Loading initial data set:

In order to spin up a local MySQL database server, we'll use Docker. Simply run the following command on the project
root directory:

```shell
docker-compose up -d
```

As it will take a while for the database to index all the test data, grab a coffee 😉!!

To check all the test data has been indexed by the database engine, run the following commands:

```shell 
docker exec -it $(docker ps -q -f name=mysql-db) bash
```

Once you've had run the previous command, you'll be running a bash terminal in the local database server. 
Access the database tables and ensure all test data is already indexed:

```shell 
root@93fac46fe6d8:/# mysql -proot
```

```shell 
mysql> use db
```

```shell
mysql> select count(*) from users;
+----------+
| count(*) |
+----------+
|      500 |
+----------+
1 row in set (0.02 sec)
```

```shell
mysql> select count(*) from orders;
+----------+
| count(*) |
+----------+
|   250000 |
+----------+
1 row in set (0.28 sec)
```

Now, if you are able to retrieve those entities counts, your test data is succesfully set and you're ready to move on.

## Starting the application up

Launching the application should be the easiest step. As this is a study case, the application won't be containerized as
it would be in an enterprise environment. Simply run it from your IDE or via Maven using Spring Boot Maven plugin:

```shell
 mvn spring-boot:run
```

Once the application is up and running, you can check is working properly by sending a simple HTTP GET request to the
following endpoint:

```http://localhost:8080/api/v1/users/1```

#Case of Study

According to documentation, Hibernate's LazyInitializationException occurs when
there is an attempt to access a not-yet-fetched data outside of the session context.
For example, when a collection is accessed after the session is closed.

In the application we are going to use to illustrate this case of study we are going
to use Spring Boot to simplify configuration and launching processes.

## Open-In-View enabled by default in Spring Data JPA

**It is important to note that Spring Data JPA default configuration for spring.jpa.open-in-view is TRUE**.
In essence, this configuration makes Spring to open a new Hibernate session at the beginning of each request, and keeps it open
until the request is completed. The following readings can be useful to deeply understand the
Open In View Session(OSIV) patterns:

- https://www.baeldung.com/spring-open-session-in-view
- https://stackoverflow.com/questions/30549489/what-is-this-spring-jpa-open-in-view-true-property-in-spring-boot

## Force the LazyLoadingException

In order to force the LazyLoadingException in the example application, you can either:

- Checkout to ```scenario/open-in-view-disabled``` branch.
- Set ```spring.jpa.open-in-view=false``` in properties file.

Once you have made the change, run the Spring Boot application and send a GET request to users' endpoint
(localhost:8080/api/v1/users). The following error should pop up in the console:

``` shell
2021-03-20 23:33:41.526 DEBUG 53662 --- [nio-8080-exec-1] o.s.w.s.m.m.a.HttpEntityMethodProcessor  : Writing [org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: usr.ach (truncated)...]
2021-03-20 23:33:41.691  WARN 53662 --- [nio-8080-exec-1] .w.s.m.s.DefaultHandlerExceptionResolver : 
Resolved [org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: failed to lazily initialize a collection of role: usr.ach.lazyloading.model.User.orders, could not initialize proxy - no Session; nested exception is 
com.fasterxml.jackson.databind.JsonMappingException: failed to lazily initialize a collection of role: usr.ach.lazyloading.model.User.orders, could not initialize proxy - no Session (through reference chain: java.util.ArrayList[0]->usr.ach.lazyloading.model.User["orders"])]
```

## Possible fixes for LazyLoadingException

### JOIN FETCH

### The other one