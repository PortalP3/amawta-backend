package com.thoughtworks.amawta.repositories;

import com.sun.xml.internal.bind.v2.model.core.ID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@NoRepositoryBean
public interface BaseRepository<T, Long> extends CrudRepository<T, ID> {

	T findTopByOrderByIdDesc();

	Optional<T> findById(java.lang.Long id);

	boolean existsById(java.lang.Long id);

	void deleteById(java.lang.Long id);
}
