package com.sampleproject.wrenchmarketplace.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="IMAGE")
public class ImageRoute {

	@javax.persistence.Id
	@Column(name = "Id", nullable = false, unique = true)
	private int Id;
	
	public ImageRoute() {}

	public ImageRoute(int id, String imageRoute) {
		this.Id = id;
		this.imageRoute = imageRoute;
	}


	@Column(name="imageRoute")
	private String imageRoute;


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
