# Amawta Backend

## Que es?
- Backend de Amawta en Spring Boot y Java 8, en containers Docker para facilitar el desarrollo y puesta en produccion.

## Requerimientos
- Docker CE
- Docker-Compose (viene incluido en Docker CE)

## Stack
- Docker
- Java 1.8
- Spring Boot 1.5.8
- PostgreSQL 10.4
- Nginx 1.13
- Maven 3.5.4
- Makefile

## Ejecutar
- Se puede ejecutar utilizando el Makefile, situado en la carpeta raiz del proyecto, usar `make run`
- Si no se ocupa el Makefile, entonces en la terminal, situado en la carpeta raiz del proyecto, utilizar `docker-compose up`
- Acceder a http://localhost/


## Ejecutar test
- Se puede ejecutar utilizando el Makefile, situado en la carpeta raiz del proyecto, usar `make test`
- Si no se ocupa el Makefile, entonces en la terminal, situado en la carpeta raiz del proyecto, utilizar `docker-compose run app mvn clean test`

## Limpiar contenedores
- make docker-kill
- make docker-rm
