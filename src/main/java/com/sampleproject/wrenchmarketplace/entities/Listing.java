package com.sampleproject.wrenchmarketplace.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//With JPA it automatically maps to @Table("listing")
@Entity
public class Listing {
	
	@Id
	//@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="Id", nullable = false, unique = true) // if id is not set as not null throws exception since it is checked before a value is generated
	private int id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="price")
	private double price;
	
	/* it's a good practice to mark many-to-one side as the owning side. */
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seller_id", referencedColumnName = "Id")
	private User seller;
	
	@Column(name="description")
	private String description;
	
	/*
	@Column(name="category")
	private String category;
	*/
	
	//@Column(name="image")
	//This will be handled later
	

	public Listing() {}
	
	public Listing(int id, String title, double price, User seller, String description) {
		this.id = id;
		this.title = title;
		this.price = price;
		this.seller = seller;
		this.description = description;
	}
	
	//Seller is currently a string to test database as well as category
	public Listing(String title, double price, String description) {
		this.title = title;
		this.price = price;
		this.description = description;
	}
	

	/*
	public String getCategory() {
		return category;
	}
	*/

	/*
	public void setCategory(String category) {
		this.category = category;
	}
	*/
	
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

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	// use getSeller's getname method later when the entity is defined
	@Override
	public String toString() {
		return "Listing ID #" + this.getId() + " ;Sold by: " + this.getSeller() + " ;Title: "
	          + this.getTitle() + ";Price: " + this.getPrice()
	          + " \n Description: " + this.getDescription();
	}
}
