package org.jbpm.spring.boot;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class UserTaskController {




	
	@RequestMapping(value = "/complete", method = RequestMethod.POST)
	public String completeTask(@RequestParam String id, @RequestParam Map<String,String> allRequestParams) {		
		String userId = getAuthUser();


			return "Task " + id + " completed successfully";

 
	}
	
	@RequestMapping(value = "/claim", method = RequestMethod.POST)
	public String claimTask(@RequestParam String id) {
		return "Task " + id + " completed successfully";


	}
	
	@RequestMapping(value = "/release", method = RequestMethod.POST)
	public String releaseTask(@RequestParam String id) {
		return "Task " + id + " completed successfully";

	}
	
	@RequestMapping(value = "/start", method = RequestMethod.POST)
	public String startTask(@RequestParam String id) {
		return "Task " + id + " completed successfully";


	}
	
	protected String getAuthUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String userId = auth.getName();
	    
	    return userId;
	}
}
