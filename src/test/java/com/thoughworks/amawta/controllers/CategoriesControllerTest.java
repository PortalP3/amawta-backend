package com.thoughworks.amawta.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.amawta.WebApplication;
import com.thoughtworks.amawta.controllers.CategoriesController;
import com.thoughtworks.amawta.controllers.ErrorDetail;
import com.thoughtworks.amawta.models.Category;
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
public class CategoriesControllerTest {

    final ObjectMapper mapper = new ObjectMapper();
    Category feminism = new Category("feminism");
    Category socialJustice = new Category("social justice");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
	CategoryRepository repository;

    @Before
    public void setUp(){
        repository.deleteAll();
    }

    @Test
    public void shouldReturnTheListOfCategories() throws Exception {
        repository.save(feminism);
        repository.save(socialJustice);
        this.mockMvc.perform(get(CategoriesController.CATEGORIES)).andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(repository.findAll())));
    }

    @Test
    public void shouldReturnTheCategoryWithGivenId() throws Exception {
        Category savedCategory = repository.save(feminism);
        this.mockMvc.perform(get(CategoriesController.CATEGORIES+"/"+savedCategory.getId())).andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(savedCategory)));

        this.mockMvc.perform(get(CategoriesController.CATEGORIES+"/-1")).andExpect(status().isNotFound());
    }


    @Test
    public void shouldReturnTheCreatedCategory() throws Exception {

        Category category = new Category("category");
        this.mockMvc.perform(post(CategoriesController.CATEGORIES).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(category)))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(repository.findTopByOrderByIdDesc())));
    }

	@Test
	public void shouldReturnAnError() throws Exception {

		Category category = new Category();
		String message = "Nombre no puede ser nulo";
		this.mockMvc.perform(post(CategoriesController.CATEGORIES).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(category)))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("\"details\":\"" + message + "\"")));
	}

    @Test
    public void shouldReturnTheUpdatedCategory() throws Exception {
        Category savedCategory	= repository.save(feminism);
        Category category = new Category("category");
        Category updatedCategory = new Category(savedCategory.getId(), "category");
        this.mockMvc.perform(put(CategoriesController.CATEGORIES+"/"+savedCategory.getId()).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(category)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(updatedCategory)));

        this.mockMvc.perform(put(CategoriesController.CATEGORIES+"/-1").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(category)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnTheStateForDeletion() throws Exception {
        Category savedCategory	= repository.save(feminism);
        this.mockMvc.perform(delete(CategoriesController.CATEGORIES+"/"+savedCategory.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        this.mockMvc.perform(delete(CategoriesController.CATEGORIES+"/-1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
