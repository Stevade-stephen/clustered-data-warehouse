dev: build start

start:
	docker-compose up

build:
	chmod 700 ./mvnw
	./mvnw clean install

stop:
	docker-compose down