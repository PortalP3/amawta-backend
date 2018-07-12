run:
	docker-compose up

test:
	docker-compose run app mvn clean test

docker-kill:
	docker kill $(docker ps -q)

docker-rm:
	docker rm $(docker ps -a -q)
