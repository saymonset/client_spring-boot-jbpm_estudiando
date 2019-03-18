package org.jbpm.spring.boot.config;

import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.definition.QueryDefinition;
import org.kie.server.client.*;

import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;

import org.kie.server.api.marshalling.MarshallingFormat;
//import org.kie.server.client.CaseServicesClient;
//import org.kie.server.client.DMNServicesClient;
//import org.kie.server.client.DocumentServicesClient;
import org.kie.server.client.JobServicesClient;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.QueryServicesClient;
import org.kie.server.client.RuleServicesClient;
import org.kie.server.client.SolverServicesClient;
import org.kie.server.client.UIServicesClient;
import org.kie.server.client.UserTaskServicesClient;
import org.kie.server.api.model.instance.ProcessInstance;
import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.ReleaseId;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by simon on 2/16/2019.
 */
@Configuration
public class JbpmConfiguration  implements EnvironmentAware {

    Logger log = Logger.getLogger(this.getClass().getName());

    private static final String URL = "http://localhost:8080/kie-server/services/rest/server";
    private static final String USER = "kieserver";
    private static final String PASSWORD = "kieserver";



    private DecryptRelaxPropertyResolver propertyResolver;

    private static final String TARGET = "CUSTOM";
    private static final String SOURCE = "java:jboss/datasources/jbpmDS";
    private static final MarshallingFormat FORMAT = MarshallingFormat.JSON;

    private KieServicesClient kieServicesClient;
    // DMN client
    //private static DMNServicesClient dmnClient;

    // Planning client
    private static SolverServicesClient solverClient;


    public void setEnvironment(Environment environment) {
        this.propertyResolver = new DecryptRelaxPropertyResolver(environment, "jbpm.");
        try {
            this.conectarKieServer();
        } catch (MalformedURLException e) {
           // log.("Ocurrio un error al conectarse con JBPM", e);
        }
    }

    private void conectarKieServer() throws MalformedURLException {
        String url = URL;//this.propertyResolver.getProperty("url");
        String user = USER;//this.propertyResolver.getProperty("user");
        String password = PASSWORD;//this.propertyResolver.getProperty("password");

        System.setProperty("org.kie.server.bypass.auth.user", "true");

        KieServicesConfiguration configuration = KieServicesFactory.newRestConfiguration(url, user, password);
        configuration.setMarshallingFormat(MarshallingFormat.JSON);

        long timeout = 900000;// Long.parseLong(this.propertyResolver.getProperty("timeout"));
        configuration.setTimeout(timeout);

        Set<Class<?>> extraClass = new java.util.HashSet<>();

        configuration.addJaxbClasses(extraClass);
        kieServicesClient = KieServicesFactory.newKieServicesClient(configuration);
    }


    @Bean(name = "kieServicesClient")
    public KieServicesClient kieServicesClient() {
        return kieServicesClient;
    }
    @Bean(name = "processServicesClient")
    public ProcessServicesClient processServicesClient() {
        return kieServicesClient.getServicesClient(ProcessServicesClient.class);
    }

    /*@Bean(name = "queryServicesClient")
    public WissenQueryServicesClientImpl queryServicesClient() {
        return kieServicesClient.getServicesClient(WissenQueryServicesClientImpl.class);
    }*/

    @Bean(name = "userTaskServicesClient")
    public UserTaskServicesClient userTaskServicesClient() {
        return kieServicesClient.getServicesClient(UserTaskServicesClient.class);
    }

    @Bean(name = "uiServicesClient")
    public UIServicesClient uiServicesClient() {
        return kieServicesClient.getServicesClient(UIServicesClient.class);
    }

   /* @Bean(name = "jbpmEnvironment")
    public wissen.proyectoinversion.modelo.Environment getJBPMEnvironment() {
        wissen.proyectoinversion.modelo.Environment environment = new wissen.proyectoinversion.modelo.Environment();
        environment.setUrlBackendWissen(this.propertyResolver.getProperty("urlWissenBackend"));
        environment.setUrlFuse(this.propertyResolver.getProperty("urlFuse"));
        environment.setToken(this.propertyResolver.getProperty("token"));

        return environment;
    }*/


}
