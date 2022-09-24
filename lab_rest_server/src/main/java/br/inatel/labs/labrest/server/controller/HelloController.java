package br.inatel.labs.labrest.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HelloController {

	
	@GetMapping
	public MyMessage processarGetHello() {
		MyMessage msg = new MyMessage();
		return msg;
	}
	
}
