package com.thoughworks.amawta.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.amawta.WebApplication;
import com.thoughtworks.amawta.controllers.ArticlesController;
import com.thoughtworks.amawta.controllers.CategoriesController;
import com.thoughtworks.amawta.models.Article;
import com.thoughtworks.amawta.models.Category;
import com.thoughtworks.amawta.repositories.ArticleRepository;
import com.thoughtworks.amawta.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={WebApplication.class})
@AutoConfigureMockMvc
public class ArticlesControllerTest {
	final ObjectMapper mapper = new ObjectMapper();
	Category feminism = new Category("feminism");
	Category socialJustice = new Category("social justice");
	Article validArticle = new Article("title", feminism, "article del feminism");
	Article validArticle2 = new Article("title2", "article del feminism 2");
	Article notValidArticle = new Article();

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ArticleRepository articleRepository;

	@Before
	public void setUp(){
		articleRepository.deleteAll();
		categoryRepository.deleteAll();
		categoryRepository.save(feminism);
		categoryRepository.save(socialJustice);
	}

	@Test
	public void shouldReturnTheListOfArticles() throws Exception {
		articleRepository.save(validArticle);
		this.mockMvc.perform(get(ArticlesController.ARTICLES)).andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(articleRepository.findAll())));
	}

	@Test
	public void shouldReturnTheArticleWithGivenId() throws Exception {
		Article savedArticle	= articleRepository.save(validArticle);
		this.mockMvc.perform(get(ArticlesController.ARTICLES+"/"+savedArticle.getId())).andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(savedArticle)));

		this.mockMvc.perform(get(ArticlesController.ARTICLES+"/-1")).andExpect(status().isNotFound());
	}


	@Test
	public void shouldReturnTheCreatedArticle() throws Exception {

		this.mockMvc.perform(post(CategoriesController.CATEGORIES+"/"+feminism.getId()+ArticlesController.ARTICLES)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(validArticle2)))
				.andExpect(status().isCreated())
				.andExpect(content().json(mapper.writeValueAsString(articleRepository.findTopByOrderByIdDesc())));
	}

	@Test
	public void shouldReturnAnError() throws Exception {

		String message = "Titulo no puede ser nulo";
		this.mockMvc.perform(post(CategoriesController.CATEGORIES+"/"+feminism.getId()+ArticlesController.ARTICLES)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(notValidArticle)))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(org.hamcrest.Matchers.containsString( message )));
	}

	@Test
	public void shouldReturnTheUpdatedArticle() throws Exception {
		Article savedArticle	= articleRepository.save(validArticle);
		Article validArticle3 = new Article("title3", "article del feminism 3");

		Article updatedArticle = new Article("title3",feminism, "article del feminism 3");
		updatedArticle.setId(savedArticle.getId());
		this.mockMvc.perform(put(CategoriesController.CATEGORIES+"/"+feminism.getId()+ArticlesController.ARTICLES+"/"+savedArticle.getId())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(validArticle3)))
				.andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(updatedArticle)));

		this.mockMvc.perform(put(CategoriesController.CATEGORIES+"/"+feminism.getId()+ArticlesController.ARTICLES+"/-1")
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(validArticle3)))
				.andExpect(status().isNotFound());
	}

	@Test
	public void shouldReturnTheStateForDeletion() throws Exception {
		Article savedArticle 	= articleRepository.save(validArticle);
		this.mockMvc.perform(delete(ArticlesController.ARTICLES+"/"+savedArticle.getId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		this.mockMvc.perform(delete(ArticlesController.ARTICLES+"/-1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
}
