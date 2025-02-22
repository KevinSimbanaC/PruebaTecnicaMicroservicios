# PruebaTecnicaMicroservicios
Resolucion de prueba
## Tecnologías usadas:
- Java 17
- SpringBoot 3.4.1
- Docker
- RabbitMQ
## Intrucciones
- Para correr independiente correr primero el servicio RabbitMQ
- Para correrlo en conjunto en docker primero se debe compilar los 2 microservicios de manera independiente para generar su .jar.
### Comandos Utiles:
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:management -- Para correrlo separado

docker-compose up --build

docker-compose down
