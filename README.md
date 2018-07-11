# Amawta Backend

## Que es?
- Backend de Amawta en Spring Boot y Java 8, en containers Docker para facilitar el desarrollo y puesta en produccion.

## Requerimientos
- Docker CE
- Docker-Compose (viene incluido en Docker CE)

## Stack
- Docker
- Java 1.8
- Spring Boot
- MySQL
- Nginx
- Maven
- Makefile

## Ejecutar
- Se puede ejecutar utilizando el Makefile, situado en la carpeta raiz del proyecto, usar `make run`
- Si no se ocupa el Makefile, entonces en la terminal, situado en la carpeta raiz del proyecto, utilizar `docker-compose up`
- Acceder a http://localhost/


## Ejecutar test
- Se puede ejecutar utilizando el Makefile, situado en la carpeta raiz del proyecto, usar `make test`
- Si no se ocupa el Makefile, entonces en la terminal, situado en la carpeta raiz del proyecto, utilizar `docker-compose run app mvn clean test`

## TODO
- Cambiar motor de BD de MySQL a PostgreSQL
