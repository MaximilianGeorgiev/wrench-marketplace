package com.sampleproject.wrenchmarketplace.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity
@Table(name = "IMAGE")
public class ImageRoute {

	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id", nullable = false, unique = true)
	private int Id;

	@Column(name = "imageRoute")
	private String imageRoute;

	public ImageRoute() {
	}

	public ImageRoute(String imageRoute) {
		this.imageRoute = imageRoute;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getImageRoute() {
		return imageRoute;
	}

	public void setImageRoute(String imageRoute) {
		this.imageRoute = imageRoute;
	}

}
