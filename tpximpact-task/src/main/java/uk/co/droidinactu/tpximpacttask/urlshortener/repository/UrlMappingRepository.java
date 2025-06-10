package uk.co.droidinactu.tpximpacttask.urlshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.co.droidinactu.tpximpacttask.urlshortener.model.UrlMapping;

/**
 * Repository interface for UrlMapping entity.
 * Provides CRUD operations for the UrlMapping entity.
 */
@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, String> {

}