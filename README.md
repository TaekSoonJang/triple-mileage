### Description
Please refer to the [docs](docs/README.md) on how requirements are met.

### Remarks
This project are the implementation of the requirements using Java, Spring and Hibernate.  
To see the test results, please do the follwing:
1. Set MySQL  up and running by referring to the [properties](src/test/resources/application-test.properties) 
and create schema by running [schema.sql](docs/schema.sql).
If you have docker installed on your machine, just run the following:
    ```
    $ script/run_local_mysql_docker.sh
    ```
2. Run tests.
    ```
    $ ./mvnw test
    ```