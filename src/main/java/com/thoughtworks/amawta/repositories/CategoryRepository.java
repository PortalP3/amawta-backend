package com.thoughtworks.amawta.repositories;

import com.thoughtworks.amawta.models.Category;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface CategoryRepository extends CrudRepository<Category, Long> {
	Category findTopByOrderByIdDesc();

	Optional<Category> findById(Long id);

	boolean existsById(Long id);

	void deleteById(Long id);
}
