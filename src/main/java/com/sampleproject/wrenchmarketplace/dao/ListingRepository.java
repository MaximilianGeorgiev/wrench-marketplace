package com.sampleproject.wrenchmarketplace.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sampleproject.wrenchmarketplace.entities.Listing;

// JPA built in functionality, no need to write anything
@Repository
public interface ListingRepository extends JpaRepository<Listing, Integer> {
}
