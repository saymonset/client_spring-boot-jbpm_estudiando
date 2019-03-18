package org.jbpm.spring.boot;


import org.kie.server.api.model.KieContainerResource;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.ProcessServicesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by simon on 2/20/2019.
 */
@RestController
@RequestMapping("/evaluation")
public class Evaluation  {
    Logger log = Logger.getLogger(this.getClass().getName());
    @Inject
    private KieServicesClient kieServicesClient;


    @RequestMapping("/")
    public Collection<String> index() {
        log.info(kieServicesClient.getServerInfo().toString());

        List<KieContainerResource> kieContainers = kieServicesClient.listContainers().getResult().getContainers();
        if (kieContainers.size() == 0) {
            System.out.println("No containers available...");
        }else{
            // Dispose KIE container
            KieContainerResource container = kieContainers.get(0);
            String containerId = container.getContainerId();
            log.info("saymons containerId = " +containerId);
        }

        return null;
    }


    @RequestMapping(value="/deploy", method= RequestMethod.GET)
    public String deploy() {


        String outcome = "Deployment  deployed successfully";



        return outcome;
    }


    @RequestMapping(value="/undeploy", method=RequestMethod.POST)
    public String undeploy(@RequestParam("id")String id) {
        String outcome = "";

        return outcome;
    }
}
