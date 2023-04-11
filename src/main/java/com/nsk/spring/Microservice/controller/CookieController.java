package com.nsk.spring.Microservice.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nsk.spring.Microservice.response.RecipeResponse;
import com.nsk.spring.Microservice.service.ScrapeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@RestController
public class CookieController {
	
	@Autowired
	private ScrapeService scrapeService;
	
	@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping(value = "/scrape", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public RecipeResponse getScrape(@RequestHeader HttpHeaders headers) throws Exception {
		RecipeResponse recipeResponse = scrapeService.getRecipe();
		return recipeResponse;
		
	}
}
