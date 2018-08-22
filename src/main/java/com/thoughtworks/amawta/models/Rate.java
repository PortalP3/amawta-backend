package com.thoughtworks.amawta.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "rates")
public class Rate {

	@Id
	@GeneratedValue
	private Long id;
	private Integer qtt;

	@ManyToMany(mappedBy = "rates")
	@JsonIgnore
	private Set<Article> articles;

	public Rate(){}

	public Rate(final Integer qtt){
		this.qtt 	= qtt;
	}

	public Rate(final Long id, final Integer qtt){
		this.id 	= id;
		this.qtt 	= qtt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQtt() {
		return qtt;
	}

	public void setQtt(Integer qtt) {
		this.qtt = qtt;
	}

	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}
}
