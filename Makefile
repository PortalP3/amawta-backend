run:
	docker-compose up

test:
	docker-compose run app mvn clean test
