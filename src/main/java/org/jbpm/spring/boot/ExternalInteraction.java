package org.jbpm.spring.boot;

import org.jbpm.services.api.DeploymentService;
import org.jbpm.services.api.model.DeployedUnit;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.TaskSummary;
import org.kie.internal.runtime.manager.context.EmptyContext;
import org.kie.internal.runtime.manager.context.ProcessInstanceIdContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by simon on 2/16/2019.
 */
@RestController
@RequestMapping("/external")
public class ExternalInteraction  {
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



    @RequestMapping(value="/interaction", method= RequestMethod.GET)
    public String interaction() {

        RuntimeEngine runtimeEngine = runtimeManager.getRuntimeEngine(EmptyContext.get());
        KieSession ksession = runtimeEngine.getKieSession();
      // ProcessInstance pI = ksession.startProcess("myExternalInteractionsProcess");




        //Este taskService si es injectado y no es encerrado en bloque y puede ser asincronico .. pero no da error

        List<TaskSummary> tasksAssignedAsPotentialOwner = taskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
        log.info((tasksAssignedAsPotentialOwner==null) + " = agai es nulo ?, tananio = " + tasksAssignedAsPotentialOwner.size());

        int cont = 0;
        for (TaskSummary t: tasksAssignedAsPotentialOwner){
            ++cont;
                log.info(t.getDescription() + ", owner = " + t.getActualOwner());
                taskService.start(t.getId(), "john");
                log.info("Comenzado sin sicroniizar la tarea porque es Inject, cont =" + cont);
                taskService.complete(t.getId(), "john", null);
                log.info("Competado sin sicroniizar la tarea porque es Inject, cont =" + cont);
        }
        log.info("-------------fin--------------------");

        return "";
    }


}
