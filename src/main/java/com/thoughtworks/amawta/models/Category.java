package com.thoughtworks.amawta.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

	@Id
	@GeneratedValue
	private Long id;
	@NotNull(message = "Nombre no puede ser nulo")
	private String name;

	@OneToMany(mappedBy = "category")
	private List<Article> articles;

	public Category(){}

	public Category(final String name){
		this.name = name;
	}

	public Category(final Long id, final String name){
		this.id 	= id;
		this.name 	= name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
