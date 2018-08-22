package com.thoughworks.amawta.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.amawta.WebApplication;
import com.thoughtworks.amawta.controllers.ArticlesController;
import com.thoughtworks.amawta.controllers.CategoriesController;
import com.thoughtworks.amawta.controllers.RatesController;
import com.thoughtworks.amawta.models.Article;
import com.thoughtworks.amawta.models.Category;
import com.thoughtworks.amawta.models.Rate;
import com.thoughtworks.amawta.repositories.ArticleRepository;
import com.thoughtworks.amawta.repositories.CategoryRepository;
import com.thoughtworks.amawta.repositories.RateRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.thoughtworks.amawta.controllers.RatesController.RATES;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={WebApplication.class})
@AutoConfigureMockMvc
public class RatesControllerTest {
	final ObjectMapper mapper 	= new ObjectMapper();
	Rate invalidRate 			= new Rate();
	Rate validRate				= new Rate(4);
	Category feminism 			= new Category("feminism");
	Article validArticle 		= new Article("title", feminism, "article del feminism");

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	RateRepository rateRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ArticleRepository articleRepository;

	@Before
	public void setUp(){
		categoryRepository.deleteAll();
		articleRepository.deleteAll();
		rateRepository.deleteAll();
		categoryRepository.save(feminism);
		articleRepository.save(validArticle);
	}

	@Test
	public void shouldReturnTheListOfRates() throws Exception {
		rateRepository.save(validRate);
		this.mockMvc.perform(get(RatesController.RATES)).andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(rateRepository.findAll())));
	}

	@Test
	public void shouldReturnTheCreatedRate() throws Exception {

		this.mockMvc.perform(post(RATES)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(validRate)))
				.andExpect(status().isCreated())
				.andExpect(content().json(mapper.writeValueAsString(rateRepository.findTopByOrderByIdDesc())));
	}

	@Test
	public void shouldReturnTheSuccessStatus() throws Exception {
		rateRepository.save(validRate);
		this.mockMvc.perform(post(ArticlesController.ARTICLES+"/"+validArticle.getId()+RATES+"/"+validRate.getId()+"/add")
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(validRate)))
				.andExpect(status().isCreated());
	}

	@Test
	public void shouldReturnTheUpdatedRate() throws Exception {
		final Integer newQtt = 2;
		Rate updatedRate = new Rate(newQtt);
		rateRepository.save(validRate);
		validRate.setQtt(newQtt);
		this.mockMvc.perform(put(RATES+"/"+validRate.getId())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(updatedRate)))
				.andExpect(content().json(mapper.writeValueAsString(validRate)));
	}

	@Test
	public void shouldReturnTheStateForDeletion() throws Exception {
		Rate savedRate 	= rateRepository.save(validRate);
		this.mockMvc.perform(delete(RatesController.RATES+"/"+savedRate.getId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		this.mockMvc.perform(delete(RatesController.RATES+"/-1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

}
