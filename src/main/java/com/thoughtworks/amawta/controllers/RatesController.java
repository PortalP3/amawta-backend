package com.thoughtworks.amawta.controllers;

import com.thoughtworks.amawta.models.Article;
import com.thoughtworks.amawta.models.Rate;
import com.thoughtworks.amawta.repositories.ArticleRepository;
import com.thoughtworks.amawta.repositories.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class RatesController {
	public static final String RATES = "/rates";

	private ArticleRepository articleRepository;

	private RateRepository rateRepository;

	@Autowired
	public RatesController(ArticleRepository articleRepository, RateRepository rateRepository) {
		this.articleRepository = articleRepository;
		this.rateRepository = rateRepository;
	}

	@GetMapping(RATES)
	public Iterable<Rate> index(){
		return rateRepository.findAll();
	}

	@PostMapping(RATES)
	public ResponseEntity<Rate> create(@Valid @RequestBody Rate rate){
		Rate createdRate = rateRepository.save(rate);
		return new ResponseEntity<>(createdRate, HttpStatus.CREATED);
	}

	@PostMapping(ArticlesController.ARTICLES+"/{articleId}"+RATES+"/{rateId}/add")
	public ResponseEntity<Object> addRate(@PathVariable("articleId") Long articleId, @PathVariable("rateId") Long rateId){
		Optional<Article> articleOptional = articleRepository.findById(articleId);
		Optional<Rate> rateOptional = rateRepository.findById(rateId);
		if (articleOptional.isPresent() && rateOptional.isPresent()){
			final Article article = articleOptional.get();
			article.getRates().add(rateOptional.get());
			articleRepository.save(article);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}

	}

	@PutMapping(RATES+"/{id}")
	public ResponseEntity<Rate> update(@PathVariable("id") Long id, @RequestBody @Valid Rate rate){
		Optional<Rate> currentRate = rateRepository.findById(id);
		if(currentRate.isPresent()){
			rate.setId(id);
			rateRepository.save(rate);
			return new ResponseEntity<>(rate, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(RATES+"/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id){
		if(rateRepository.existsById(id)){
			rateRepository.deleteById(id);
			if(!rateRepository.existsById(id)){
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
			}
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
}
