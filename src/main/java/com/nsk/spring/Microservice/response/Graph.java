package com.nsk.spring.Microservice.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Graph {
	
	@JsonProperty(value = "recipeInstructions")
	private RecipeInstructions[] instructions;
	
	@JsonProperty(value = "recipeIngredient")
	private String ingredients;
}
