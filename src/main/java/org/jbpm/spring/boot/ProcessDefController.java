package org.jbpm.spring.boot;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/processdef")
public class ProcessDefController {
	




	@RequestMapping(value = "/", method = RequestMethod.POST)
	public Long newProcessInstance(@RequestParam String deploymentId, @RequestParam String processId,
			@RequestParam Map<String,String> allRequestParams) {
		

		return null;
 
	}
}
