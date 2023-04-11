package com.nsk.spring.Microservice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsk.spring.Microservice.response.Graph;
import com.nsk.spring.Microservice.response.RecipeResponse;

@Service
public class ScrapeService {
	
	@Value("${recipe.url}")
	private String recipeUrl;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public RecipeResponse getRecipe()throws IOException {
		// TODO Auto-generated method stub
		RecipeResponse recipeResponse = new RecipeResponse();
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(getRecipeHeaders());
		
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(recipeUrl, requestEntity, String.class);
		String test = responseEntity.toString();
		Document document = Jsoup.parseBodyFragment(test);
//		Element body = document.body();
		Elements el = document.select("script[type=application/ld+json]");
		Element elements = el.get(0);
		String testElement = elements.childNodes().get(0).toString();
		System.out.println(testElement);
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = (Map<String, Object>) mapper.readValue(testElement, new TypeReference<HashMap<String,Object>>(){});
		map.remove("@context");
		
		Map<String, Object> value = (Map<String, Object>) ((ArrayList<Element>) map.get("@graph")).get(0);
		value.remove("@type");
		value.remove("keywords");
		value.remove("name");
		value.remove("description");
		value.remove("recipeYield");
		value.remove("datePublished");
		value.remove("type");
		value.remove("prepTime");
		value.remove("cookTime");
		value.remove("totalTime");
		value.remove("author");
		value.remove("image");
		value.remove("nutrition");
		value.remove("aggregateRating");
		String json = mapper.writeValueAsString(value);
		System.out.println(json);
//		String maprecipe = value.toString();
//		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
				RecipeResponse recipeResponse1 = mapper.readValue(json, RecipeResponse.class);
		//		body.select("script[type=application/ld+json]");
		//		ResponseEntity<Document> document = restTemplate.postForEntity(recipeUrl, requestEntity, Document.class);
//		document.getElementById(id)
//		RecipeResponse recipeResponse1 = new ObjectMapper().readValue(responseEntity.getBody(), RecipeResponse.class);
//		if (200 == responseEntity.getStatusCodeValue() && !ObjectUtils.isEmpty(responseEntity.getBody())) {
//			recipeResponse = responseEntity.getBody();
//		}
				return recipeResponse1;
	}
	
	private HttpHeaders getRecipeHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.ALL));
		return headers;
	}

}
