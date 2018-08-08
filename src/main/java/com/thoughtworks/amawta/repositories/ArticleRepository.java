package com.thoughtworks.amawta.repositories;

import com.thoughtworks.amawta.models.Article;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface ArticleRepository extends CrudRepository<Article, Long> {
	Article findTopByOrderByIdDesc();

	Optional<Article> findById(Long id);

	boolean existsById(Long id);

	void deleteById(Long id);
}



