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