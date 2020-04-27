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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//With JPA it automatically maps to @Table("listing")
@Entity
public class Listing {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id", nullable = false, unique = true) // if id is not set as not null throws exception since it is
															// checked before a value is generated
	private int id;

	@Column(name = "title")
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String title;

	@Column(name = "price")
	@NotNull(message = "is required")
	private double price;

	@Column(name = "description")
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String description;

	@Column(name = "category_name")
	private String category;
	
	@Column(name = "phone_number")
	@NotNull(message = "is required")
	@Size(min = 1, message = "is requred")
	private String phoneNumber; // implement custom validator for a phone number: 08846321512 

	public Listing() {
	}

	public Listing(int id, String title, double price, String description, String category, String contactDetails) throws IOException {
		this.id = id;
		this.title = title;
		this.price = price;
		this.description = description;
		this.category = category;
		this.phoneNumber = contactDetails;
	}

	public Listing(String title, double price, String description, String category, String contactDetails) {
		this.title = title;
		this.price = price;
		this.description = description;
		this.category = category;
		this.phoneNumber = contactDetails;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "Listing ID #" + this.getId() + " ;Sold by: " + " ;Title: " + this.getTitle() + ";Price: "
				+ this.getPrice() + " \n Description: " + this.getDescription();
	}
}
