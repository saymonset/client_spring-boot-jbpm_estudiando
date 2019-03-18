
1)  Bajar el kjar
     https://github.com/jesuino/hello-kie-server

      Compilarlo
          mvn clean package install

2-) Correr spring-boot
    #https://www.baeldung.com/spring-boot-command-line-arguments
    mvn spring-boot:run -Drun.arguments=org.mastertheboss.kieserver,hello-kie-server,1.0

3-)

test


    http://localhost:8080/deployment/

    http://localhost:8180/looping/deploy

    http://localhost:8180/signalProcess/deploy

   http://localhost:8180/evaluation/deploy


    usuario = john
    password = john1

