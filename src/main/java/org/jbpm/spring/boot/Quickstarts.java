package org.jbpm.spring.boot;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.logging.Logger;

/**
 * Created by simon on 2/23/2019.
 */
@RestController
@RequestMapping("/quickstarts")
public class Quickstarts {
    Logger log = Logger.getLogger(this.getClass().getName());



    @RequestMapping("/")
    public Collection<String> index() {


        return null;
    }

    /*
     <groupId>uft</groupId>
  <artifactId>chapter02</artifactId>
  <version>1.0</version>*/




    @RequestMapping(value="/deploy", method= RequestMethod.GET)
    public String quickstarts() {


        String outcome = "Deployment  deployed successfully";


        log.info("-------------Cuando sea en la misma transaccion para los procesos y las task--------------------");



        return outcome;
    }


    @RequestMapping(value="/undeploy", method=RequestMethod.POST)
    public String undeploy(@RequestParam("id")String id) {
        String outcome = "";

        return outcome;
    }
}
