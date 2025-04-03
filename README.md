
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-336791?style=for-the-badge&logo=postgresql&logoColor=white)
![Liquibase](https://img.shields.io/badge/Liquibase-2962FF?style=for-the-badge&logo=liquibase&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![ELK](https://img.shields.io/badge/ELK-005571?style=for-the-badge&logo=elastic&logoColor=white)
![Resilience4j](https://img.shields.io/badge/Resilience4j-6DB33F?style=for-the-badge&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white)
![OpenAPI](https://img.shields.io/badge/OpenAPI-6BA539?style=for-the-badge&logo=openapi-initiative&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
![Multithreading](https://img.shields.io/badge/Multithreading-007396?style=for-the-badge&logo=java&logoColor=white)
![Caching](https://img.shields.io/badge/Caching-FF6F00?style=for-the-badge&logoColor=white)




# EventsService

EventsService — это проект на базе Spring Boot, который позваоляет получать информацию о событиях через Kudago API.

## Структура проекта

- **Event Service**: Основной сервис для получения информации о событиях по заданным параметрам, информацио о локациях и категорях событий. Использует API Kudago (https://kudago.com/) и собственный сервис Currency Service.
- **Currency Service**: Сервис для конвертации валюты и получения курсов валют, использует API Центрального банка РФ (http://www.cbr.ru/).
- **Execution Time Logger Starter**: Стартер для логирования времени выполнения методов.

