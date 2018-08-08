package com.thoughtworks.amawta.controllers;

import com.thoughtworks.amawta.models.Article;
import com.thoughtworks.amawta.models.Category;
import com.thoughtworks.amawta.repositories.ArticleRepository;
import com.thoughtworks.amawta.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class ArticlesController{

	public static final String ARTICLES = "/articles";
	private ArticleRepository repository;
	private CategoryRepository categoryRepository;

	@Autowired
	public ArticlesController(ArticleRepository repository, CategoryRepository categoryRepository){
		this.repository = repository;
		this.categoryRepository = categoryRepository;
	}

	@GetMapping(ARTICLES)
	public Iterable<Article> index(){
		return repository.findAll();
	}

	@GetMapping(ARTICLES+"/{id}")
	public ResponseEntity<Article> show(@PathVariable("id") Long id){
		Optional<Article> articleOptional = repository.findById(id);
		if(articleOptional.isPresent()){
			return new ResponseEntity<Article>(articleOptional.get(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(CategoriesController.CATEGORIES+"/{categoryId}"+ARTICLES)
	public ResponseEntity<Article> create(@RequestBody @Valid Article article, @PathVariable("categoryId") Long categoryId){
		Optional<Category> category = categoryRepository.findById(categoryId);
		if (category.isPresent()){
			article.setCategory(category.get());
			repository.save(article);
			return new ResponseEntity<>(article, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}

	@PutMapping(CategoriesController.CATEGORIES+"/{categoryId}"+ARTICLES+"/{id}")
	public ResponseEntity<Article> update(@PathVariable("id") Long id, @PathVariable("categoryId") Long categoryId, @RequestBody Article article){
		Optional<Category> currentCategory = categoryRepository.findById(categoryId);
		Optional<Article> currentArticle = repository.findById(id);
		if(currentArticle.isPresent() && currentCategory.isPresent()){
			article.setId(id);
			article.setCategory(currentCategory.get());
			repository.save(article);
			return new ResponseEntity<>(article, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(ARTICLES+"/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id){
		if(repository.existsById(id)){
			repository.deleteById(id);
			Optional<Article> articleOptional = repository.findById(id);
			if(!articleOptional.isPresent()){
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
			}
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
}