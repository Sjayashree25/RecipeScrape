package com.nsk.spring.Microservice.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty(value = "recipeIngredient")
	private String[] recipeIngredient;

	@JsonProperty(value = "recipeInstructions")
	private String instructions;
}
