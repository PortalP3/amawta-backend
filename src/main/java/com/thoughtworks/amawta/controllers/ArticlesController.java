package com.thoughtworks.amawta.controllers;

import com.thoughtworks.amawta.models.Article;
import com.thoughtworks.amawta.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ArticlesController{

	private static final String ARTICLES = "/articles";
	private ArticleRepository repository;

	@Autowired
	public ArticlesController(ArticleRepository repository){
		this.repository = repository;
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

	@PostMapping(ARTICLES)
	public ResponseEntity<Article> create(@RequestBody Article article){
		repository.save(article);
		return new ResponseEntity<>(article, HttpStatus.CREATED);
	}

	@PutMapping(ARTICLES+"/{id}")
	public ResponseEntity<Article> update(@PathVariable("id") Long id, @RequestBody Article article){
		Optional<Article> currentArticle = repository.findById(id);
		if(currentArticle.isPresent()){
			article.setId(id);
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