package com.excilys.cdb.rest;

import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins="*")
@RestController("userController")
@RequestMapping("/api/user")
public class UserRESTController {
	
	Logger logger = LoggerFactory.getLogger(UserRESTController.class);
	
	 @GetMapping("/connect")
	  public ResponseEntity<Principal> user(Principal user) {
	    return new ResponseEntity<>(user,HttpStatus.OK);
	  }
}
