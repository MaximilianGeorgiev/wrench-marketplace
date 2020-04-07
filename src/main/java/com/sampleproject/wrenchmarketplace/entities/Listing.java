package com.sampleproject.wrenchmarketplace.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//With JPA it automatically maps to @Table("listing")
@Entity
public class Listing {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="price")
	private double price;
	
	@Column(name="seller")
	private String seller;
	
	@Column(name="description")
	private String description;
	
	//@Column(name="image")
	//This will be handled later
	
	public Listing() {}
	
	//Seller is currently a string to test database
	public Listing(double price, String seller, String description) {
		this.price = price;
		this.seller = seller;
		this.description = description;
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

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
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
		return "Listing ID #" + this.getId() + " ;Sold by: " + this.getSeller() + " ;Price: " + this.getPrice()
		+ " \n Description: " + this.getDescription();
	}
}
