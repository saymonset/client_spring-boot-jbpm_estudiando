package org.jbpm.spring.boot;

import org.jbpm.kie.services.impl.KModuleDeploymentUnit;
import org.jbpm.services.api.DeploymentService;
import org.jbpm.services.api.model.DeployedUnit;
import org.jbpm.services.api.model.DeploymentUnit;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.TaskSummary;
import org.kie.internal.runtime.manager.context.EmptyContext;
import org.kie.internal.runtime.manager.context.ProcessInstanceIdContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by simon on 2/15/2019.
 */
@RestController
@RequestMapping("/deploymentest")
public class DeploymentControllerTest {
    Logger log = Logger.getLogger(this.getClass().getName());
    @Autowired
private DeploymentService deploymentService;
    @Autowired
    private RuntimeManager runtimeManager;
    @Inject
    TaskService taskService;
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




        RuntimeEngine runtimeEngine = runtimeManager.getRuntimeEngine(EmptyContext.get());
        KieSession ksession = runtimeEngine.getKieSession();

        ProcessInstance pI = ksession.startProcess("myExternalInteractionsProcess");

        log.info("-------------Cuando sea en la misma transaccion para los procesos y las task--------------------");
        //Debe estar en el mismo contexto cuando el taskService viene de runtime.getTaskService()
        {
            RuntimeEngine runtime = runtimeManager.getRuntimeEngine(ProcessInstanceIdContext.get()); // null context
            KieSession ksession1 = runtime.getKieSession();
            Map<String, Object> params = new HashMap<String, Object>();
            ProcessInstance pi = ksession1.startProcess("myExternalInteractionsProcess", params);
            System.out.println("A process instance started : pid = " + pi.getId());
            //Este taskService noes injectado
            TaskService taskService = runtime.getTaskService();
            List<TaskSummary> tasksAssignedAsPotentialOwner = taskService.getTasksAssignedAsPotentialOwner("john", "en-UK");

            TaskSummary taskSummary = tasksAssignedAsPotentialOwner.get(0);

            taskService.start(taskSummary.getId(), "john");
            taskService.complete(taskSummary.getId(), "john", null);
            log.info("------Fin-------Cuando sea en la misma transaccion para los procesos y las task--------------------");
            runtimeManager.disposeRuntimeEngine(runtime);
        }



        //Este taskService si es injectado y no es encerrado en bloque y puede ser asincronico .. pero no da error
        log.info((taskService==null) + " = saymons es nulo ?");

        List<TaskSummary> tasksAssignedAsPotentialOwner = taskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
        log.info((tasksAssignedAsPotentialOwner==null) + " = agai es nulo ?, tananio = " + tasksAssignedAsPotentialOwner.size());

        for (TaskSummary t: tasksAssignedAsPotentialOwner){
          if (t == null){
              log.info("Esto es nulo la tassk");
          }else{
              log.info(t.getDescription() + ", owner = " + t.getActualOwner());
              taskService.start(t.getId(), "john");
              log.info("Comenzado sin sicroniizar la tarea porque es Inject");
              taskService.complete(t.getId(), "john", null);
              log.info("Competado sin sicroniizar la tarea porque es Inject");
          }

      }
        log.info("-------------fin--------------------");

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
