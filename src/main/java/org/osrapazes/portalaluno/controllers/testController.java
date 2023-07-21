package org.osrapazes.portalaluno.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//Controle apenas utilizado para testes
@RestController
@RequestMapping("api/v1/demo")
public class testController {

	@GetMapping("/get")
	public ResponseEntity<String> returnString() {
		return ResponseEntity.ok("TextTextTextTextText");
	}
}