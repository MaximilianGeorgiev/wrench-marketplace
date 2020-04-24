package com.sampleproject.wrenchmarketplace.entities;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

//With JPA it automatically maps to @Table("listing")
@Entity
public class Listing {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id", nullable = false, unique = true) // if id is not set as not null throws exception since it is
															// checked before a value is generated
	private int id;

	@Column(name = "title")
	private String title;

	@Column(name = "price")
	private double price;

	@Column(name = "description")
	private String description;

	@Column(name = "category_name")
	private String category;

	public Listing() {
	}

	public Listing(int id, String title, double price, String description, String category) throws IOException {
		this.id = id;
		this.title = title;
		this.price = price;
		this.description = description;
		this.category = category;
	}

	public Listing(String title, double price, String description, String category) {
		this.title = title;
		this.price = price;
		this.description = description;
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Listing ID #" + this.getId() + " ;Sold by: " + " ;Title: " + this.getTitle() + ";Price: "
				+ this.getPrice() + " \n Description: " + this.getDescription();
	}
}
