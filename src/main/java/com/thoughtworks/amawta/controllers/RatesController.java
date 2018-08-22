package com.thoughtworks.amawta.controllers;

import com.thoughtworks.amawta.models.Article;
import com.thoughtworks.amawta.models.Rate;
import com.thoughtworks.amawta.repositories.ArticleRepository;
import com.thoughtworks.amawta.repositories.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
