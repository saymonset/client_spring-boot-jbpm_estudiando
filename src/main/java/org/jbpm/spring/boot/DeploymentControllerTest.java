package org.jbpm.spring.boot;

import org.jbpm.kie.services.impl.KModuleDeploymentUnit;
import org.jbpm.services.api.DeploymentService;
import org.jbpm.services.api.model.DeployedUnit;
import org.jbpm.services.api.model.DeploymentUnit;
import org.kie.api.runtime.manager.RuntimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by simon on 2/15/2019.
 */
@RestController
@RequestMapping("/deploymentest")
public class DeploymentControllerTest {
    @Autowired
private DeploymentService deploymentService;

    @RequestMapping("/")
    public Collection<String> index() {
        Collection<DeployedUnit> deployed = deploymentService.getDeployedUnits();
        Collection<String> units = new ArrayList<String>();

        for (DeployedUnit dUnit : deployed) {
            units.add(dUnit.getDeploymentUnit().getIdentifier());
        }

        return units;
    }

    /*
     <groupId>uft</groupId>
  <artifactId>chapter02</artifactId>
  <version>1.0</version>*/

    @RequestMapping(value="/deploy", method= RequestMethod.GET)
    public String deploy() {
        String strategy = "SINGLETON";
        String id = "uft:chapter02:1.0";
        String outcome = "Deployment " + id + " deployed successfully";

        String[] gav = id.split(":");
//org.mastertheboss.kieserver,hello-kie-server,1.0
    //    KModuleDeploymentUnit unit = new KModuleDeploymentUnit("org.mastertheboss.kieserver", "hello-kie-server", "1.0");
//KModuleDeploymentUnit unit = new KModuleDeploymentUnit("uft", "chapter02", "1.0");
    //    KModuleDeploymentUnit unit = new KModuleDeploymentUnit(args[0], args[1], args[2]);
        //deploymentService.deploy(unit);
      //  deploymentService.deploy(unit);


        // create deployment unit by giving GAV
        DeploymentUnit deploymentUnit = new KModuleDeploymentUnit("uft", "chapter02", "1.0");
// deploy
        deploymentService.deploy(deploymentUnit);





        return outcome;
    }

    @RequestMapping(value="/undeploy", method=RequestMethod.POST)
    public String undeploy(@RequestParam("id")String id) {
        String outcome = "";
        DeployedUnit deployed = deploymentService.getDeployedUnit(id);
        if (deployed != null) {
            deploymentService.undeploy(deployed.getDeploymentUnit());
            outcome = "Deployment " + id + " undeployed successfully";
        } else {
            outcome = "No deployment " + id + " found";
        }
        return outcome;
    }
}
