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
	@Query(value = "DELETE FROM LISTING_IMAGE l WHERE l.listing_Id = :listing_ID", nativeQuery = true)
	public void deleteListingFromListingImageJoinedTable(@Param("listing_ID") Integer listing_ID);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM USER_LISTING u WHERE u.listing_Id = :listing_ID", nativeQuery = true)
	public void deleteListingFromUserListingJoinedTable(@Param("listing_ID") Integer listing_ID);
}
