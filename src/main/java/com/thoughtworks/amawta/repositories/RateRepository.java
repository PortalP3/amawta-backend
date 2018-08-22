package com.thoughtworks.amawta.repositories;

import com.thoughtworks.amawta.models.Article;
import com.thoughtworks.amawta.models.Rate;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface RateRepository extends BaseRepository<Rate, Long> {
}



