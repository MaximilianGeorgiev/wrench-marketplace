package com.sampleproject.wrenchmarketplace.dao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sampleproject.wrenchmarketplace.entities.Listing;

// JPA built in functionality, no need to write anything
@Repository
public interface ListingRepository extends JpaRepository<Listing, Integer> {
	
	public List<Listing> findBytitle(String title);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE LISTING l SET l.TITLE = :title WHERE l.Id = :Id", nativeQuery = true)
	public void editTitle(@Param("Id")Integer Id, @Param("title") String title);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE LISTING l SET l.PRICE = :price WHERE l.Id = :Id", nativeQuery = true)
	public void editPrice(@Param("Id")Integer Id, @Param("price") Double price);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE LISTING l SET l.DESCRIPTION = :description WHERE l.Id = :Id", nativeQuery = true)
	public void editDescription(@Param("Id")Integer Id, @Param("description") String description);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE LISTING l SET l.PHONE_NUMBER = :phoneNumber WHERE l.Id = :Id", nativeQuery = true)
	public void editPhoneNumber(@Param("Id")Integer Id, @Param("phoneNumber") String phoneNumber);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM LISTING_IMAGE l WHERE l.listing_Id = :listing_ID", nativeQuery = true)
	public void deleteListingFromListingImageJoinedTable(@Param("listing_ID") Integer listing_ID);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM LISTING_IMAGE l WHERE l.image_Id = :image_ID", nativeQuery = true)
	public void deleteImageFromListingImageJoinedTable(@Param("image_ID") Integer image_ID);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM USER_LISTING u WHERE u.listing_Id = :listing_ID", nativeQuery = true)
	public void deleteListingFromUserListingJoinedTable(@Param("listing_ID") Integer listing_ID);
	
	// find all listings ids that correspond to a given user id (used by findListingsByUserId in the ListingService)
	@Query(value = "SELECT * FROM USER_LISTING u WHERE u.user_Id = :user_ID", nativeQuery = true)
	public List<Integer> findListingIDsByUserID(@Param("user_ID") Integer user_ID);
}
