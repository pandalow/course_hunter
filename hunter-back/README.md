# Course Hunter - Back End



## Outline

Consturcting...



## Dependency

| Dependencies         | Explaination                                                 | Version | Official website                                |
| -------------------- | ------------------------------------------------------------ | ------- | ----------------------------------------------- |
| Java                 | basic language                                               | 21.0    | https://www.oracle.com/java/technologies/javase |
| SpringBoot           | Web Application Development Framework                        | 3.2.5   | https://spring.io/projects/spring-boot          |
| Lombok               | Java Language Enhancement Library                            | 1.18.32 | https://github.com/rzwitserloot/lombok          |
| Swagger-UI           | API Documentation Generation Tool                            | 2.5.0   | https://github.com/swagger-api/swagger-ui       |
| Hibernate            | ORM                                                          |         | http://hibernate.org                            |
| MySQL                | My SQL Connector                                             |         |                                                 |
| Redis                | in-memory database                                           | 7.2.5   | https://redis.io/                               |
| Lettuce              | scalable [Redis](https://redis.io/) client                   | 6.3.2   | https://lettuce.io/                             |
| Spring-redis-starter | Spring framework client of redis                             | 3.3.0   |                                                 |
| Gson                 | can be used to convert Java Objects into their JSON representation | 32.1.2  | https://github.com/google/gson                  |

## Structure

1. Hunter back [Parent module]
   1. course-hunter-server [Server module: response for VIEW & CONTROLLER] 
      1. `anno` :  Annotation package
      2. `aspect`:  AOP package [Aspect of programming in Spring Framework]
      3. `config` : Configuration package
      4. `controller` Controller of  REST Api [Handling HttpRequest]
      5. `handler` : Globally exception handler
      6. `Interceptor` Filter or Interceptor for handling User validation
      7. `dao`  DAO package [Handling Database CRUD]
      8. `service`  Service package [Hadling Service between Controller layer & DAO layer] 
   2. course-hunter-pojo [Entity module: represent of Entity in the database & Entities that interact with the front end] 
      1. `entity`  Entity in the database [1-to-1 correspondence] 
      2. `DTO`  The full entity requested in the httpRequest [Can be used to transfer data in service and dao]
      3. `VO` The returned data entities can be wrapped and responded to by the front-end.
   3. course-hunter-common []
      1. `constant` Package for storing constants
      2. `context`  Used to process thread information
      3. `enumeration`  Enum information package
      4. `exception`  User-defined exception handling
      5. `properties`  Tool Class Configuration Class Information Storage
      6. `result`  Return result information, including paging information and general result information.
      7. `utils`  Tool class methods, providing custom tooling capabilities