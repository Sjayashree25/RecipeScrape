package com.nsk.spring.Microservice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsk.spring.Microservice.response.RecipeResponse;

@Service
public class ScrapeService {
	
	@Value("${recipe.url}")
	private String recipeUrl;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public RecipeResponse getRecipe()throws IOException {
		// TODO Auto-generated method stub
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(getRecipeHeaders());

		ResponseEntity<String> responseEntity = restTemplate.postForEntity(recipeUrl, requestEntity, String.class);
		String test = responseEntity.toString();
		Document document = Jsoup.parseBodyFragment(test);
		Elements el = document.select("script[type=application/ld+json]");
		Element elements = el.get(0);
		String testElement = elements.childNodes().get(0).toString();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = (Map<String, Object>) mapper.readValue(testElement,
				new TypeReference<HashMap<String, Object>>() {
				});
		Map<String, Object> value = (Map<String, Object>) ((ArrayList<Element>) map.get("@graph")).get(0);
		String json = mapper.writeValueAsString(value);
		RecipeResponse recipeResponse = mapper.readValue(json, RecipeResponse.class);
		return recipeResponse;
	}
	
	private HttpHeaders getRecipeHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.ALL));
		return headers;
	}

}
