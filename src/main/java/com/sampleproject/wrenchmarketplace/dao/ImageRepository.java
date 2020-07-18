package com.sampleproject.wrenchmarketplace.dao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sampleproject.wrenchmarketplace.entities.ImageRoute;

@Repository
public interface ImageRepository extends JpaRepository<ImageRoute, Integer> {

	@Transactional
	@Modifying
	@Query(value = "INSERT INTO LISTING_IMAGE (listing_Id, image_Id) VALUES (:listing_Id, :image_Id)", nativeQuery = true)
	public void insertIntoJoinedTable(@Param("listing_Id") Integer listing_Id, @Param("image_Id") Integer image_Id);

	/*
	 * LISTING_IMAGE PAIRS: {listing_Id : image_Id (path)} This query finds all
	 * image paths for one listing id and returns a list of ImageRoutes
	 */


	@Query(value = "SELECT l.image_Id FROM LISTING_IMAGE l WHERE l.listing_Id = :listing_Id", nativeQuery = true)
	public List<Integer> findByListingIdInJoinedTable(@Param("listing_Id") int listingId);


	public Optional<ImageRoute> findByImageRoute(String imageRoute);
}
