package com.thoughtworks.amawta.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "articles")
public class Article {
	@Id
	@GeneratedValue
	private Long id;
	@NotNull(message = "Titulo no puede ser nulo")
	private String title;

	@ManyToOne
	@JoinColumn(name="category_id", nullable=false)
	private Category category;
	@NotNull
	private String body;
	private Integer claps;

	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinTable(
			name = "article_rate",
			joinColumns = { @JoinColumn(name = "rate_id", referencedColumnName = "id") },
			inverseJoinColumns = { @JoinColumn(name = "article_id")}
	)
	@JsonIgnore
	private Set<Rate> rates;

	public Article(){}

	public Article(final String title, final String body){
		this.title		= title;
		this.body		= body;
	}

	public Article(final String title, final Category category, final String body){
		this.title		= title;
		this.category	= category;
		this.body		= body;
	}

	public Article(final Long id, final String title, final Category category, final String body){
		this.id			= id;
		this.title		= title;
		this.category	= category;
		this.body		= body;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Integer getClaps() {
		return claps;
	}

	public void setClaps(Integer claps) {
		this.claps = claps;
	}

	public Set<Rate> getRates() {
		return rates;
	}

	public void setRates(Set<Rate> rates) {
		this.rates = rates;
	}

}
