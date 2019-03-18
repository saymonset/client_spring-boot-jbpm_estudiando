
1)  Bajar el kjar
     https://github.com/jesuino/hello-kie-server

      Compilarlo
          mvn clean package install

2-) Correr spring-boot
    #https://www.baeldung.com/spring-boot-command-line-arguments
    mvn spring-boot:run -Drun.arguments=org.mastertheboss.kieserver,hello-kie-server,1.0

3-)


Se Conecta con wildfly y se usa el cliente de  kieserver en jbpmconfiguration

            http://localhost:8180/evaluation/



Roles de kieserver
    kieserver=kie-server,rest-all

  application-roles.properties, application-users.properties, roles.properties,user.properties

Con add-user kieserver, usar ApplicationRealm  (Opcion b) ,  y pasar el role y usuario que se genero en application-users.properties, application-roles.properties a lo archivos users.properties, roles.properties


    usuario = kieserver
    password = kieserver

