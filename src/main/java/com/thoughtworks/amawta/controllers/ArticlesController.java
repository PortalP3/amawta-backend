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
import java.util.logging.Logger;

@SuppressWarnings("MVCPathVariableInspection")
@RestController
public class ArticlesController {

	public static final String ARTICLES = "/articles";
	private final Logger LOGGER = Logger.getLogger(this.getClass().getName());
	private final ArticleRepository articleRepository;
	private final CategoryRepository categoryRepository;

	@Autowired
	public ArticlesController(ArticleRepository articleRepository, CategoryRepository categoryRepository) {
		this.articleRepository = articleRepository;
		this.categoryRepository = categoryRepository;
	}

	@GetMapping(ARTICLES)
	public Iterable<Article> getAllArticles() {
		LOGGER.info("Finding all articles");
		return articleRepository.findAll();
	}

	@GetMapping(ARTICLES + "/{id}")
	public ResponseEntity<Article> getArticleById(@PathVariable("id") Long id) {
		LOGGER.info("Finding article by id");
		Optional<Article> articleOptional = articleRepository.findById(id);

		return articleOptional
				.map(article -> ResponseEntity.ok().body(article))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping(CategoriesController.CATEGORIES + "/{categoryId}" + ARTICLES)
	public ResponseEntity<Article> createArticle(@RequestBody @Valid Article article, @PathVariable("categoryId") Long categoryId) {
		LOGGER.info("Creating article in the category with id: ".concat(categoryId.toString()));
		Optional<Category> category = categoryRepository.findById(categoryId);
		if (category.isPresent()) {
			article.setCategory(category.get());
			articleRepository.save(article);
			return ResponseEntity.status(HttpStatus.CREATED).body(article);
		}
		return ResponseEntity.badRequest().build();
	}

	@PutMapping(CategoriesController.CATEGORIES + "/{categoryId}" + ARTICLES + "/{id}")
	public ResponseEntity<Article> updateArticle(@PathVariable("id") Long id, @PathVariable("categoryId") Long categoryId, @RequestBody Article article) {
		Optional<Category> currentCategory = categoryRepository.findById(categoryId);
		Optional<Article> currentArticle = articleRepository.findById(id);
		if (currentArticle.isPresent() && currentCategory.isPresent()) {
			article.setId(id);
			article.setCategory(currentCategory.get());
			articleRepository.save(article);
			return ResponseEntity.ok().body(article);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping(ARTICLES + "/{id}")
	public ResponseEntity<Object> deleteArticle(@PathVariable("id") Long id) {
		if (articleRepository.existsById(id)) {
			articleRepository.deleteById(id);
			Optional<Article> articleOptional = articleRepository.findById(id);
			if (!articleOptional.isPresent()) {
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
			}
		} else {
			return ResponseEntity.notFound().build();
		}

	}
}