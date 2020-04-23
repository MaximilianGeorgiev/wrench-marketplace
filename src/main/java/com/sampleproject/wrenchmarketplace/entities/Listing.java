package com.sampleproject.wrenchmarketplace.entities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

//With JPA it automatically maps to @Table("listing")
@Entity
public class Listing {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "Id", nullable = false, unique = true) // if id is not set as not null throws exception since it is
															// checked before a value is generated
	private int id;

	@Column(name = "title")
	private String title;

	@Column(name = "price")
	private double price;

	/* it's a good practice to mark many-to-one side as the owning side. */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "seller_id", referencedColumnName = "Id")
	private User seller;

	@Column(name = "description")
	private String description;

	/*
	 * @Column(name="category") private String category;
	 */

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "LISTING_IMAGE", joinColumns = @JoinColumn(name = "listing_Id"), inverseJoinColumns = @JoinColumn(name = "image_Id"))
	private Collection<ImageRoute> imageAPIs;

	public Listing() {
	}

	public Listing(int id, String title, double price, User seller, String description) throws IOException {
		this.id = id;
		this.title = title;
		this.price = price;
		this.seller = seller;
		this.description = description;
		this.imageAPIs = new ArrayList<ImageRoute>();
	}

	// Seller is currently a string to test database as well as category
	public Listing(String title, double price, String description) {
		this.title = title;
		this.price = price;
		this.description = description;
	}

	/*
	 * public String getCategory() { return category; }
	 */

	/*
	 * public void setCategory(String category) { this.category = category; }
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

	public Collection<ImageRoute> getImageAPIs() {
		return imageAPIs;
	}

	public void setImageAPIs(Collection<ImageRoute> image) {
		this.imageAPIs = image;
	}

	@Override
	public String toString() {
		return "Listing ID #" + this.getId() + " ;Sold by: " + this.getSeller().getUsername() + " ;Title: "
				+ this.getTitle() + ";Price: " + this.getPrice() + " \n Description: " + this.getDescription();
	}
}
