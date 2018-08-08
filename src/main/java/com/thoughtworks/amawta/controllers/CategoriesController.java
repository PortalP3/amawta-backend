package com.thoughtworks.amawta.controllers;

import com.thoughtworks.amawta.models.Category;
import com.thoughtworks.amawta.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class CategoriesController {

	public static final String CATEGORIES = "/categories";
	private CategoryRepository repository;

	@Autowired
	public CategoriesController(CategoryRepository repository){
		this.repository = repository;
	}

	@GetMapping(CATEGORIES)
	public Iterable<Category> index(){
		return repository.findAll();
	}

	@GetMapping(CATEGORIES+"/{id}")
	public ResponseEntity<Category> show(@PathVariable("id") Long id){
		Optional<Category> categoryOptional = repository.findById(id);
		if(categoryOptional.isPresent()){
			return new ResponseEntity<Category>(categoryOptional.get(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(CATEGORIES)
	public ResponseEntity<Category> create(@Valid @RequestBody Category category){

		repository.save(category);
		return new ResponseEntity<>(category, HttpStatus.CREATED);
	}

	@PutMapping(CATEGORIES+"/{id}")
	public ResponseEntity<Category> update(@PathVariable("id") Long id, @RequestBody  @Valid Category category){
		Optional<Category> currentCategory = repository.findById(id);
		if(currentCategory.isPresent()){
			category.setId(id);
			repository.save(category);
			return new ResponseEntity<>(category, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(CATEGORIES+"/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id){
		if(repository.existsById(id)){
			repository.deleteById(id);
			if(!repository.existsById(id)){
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
			}
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
}