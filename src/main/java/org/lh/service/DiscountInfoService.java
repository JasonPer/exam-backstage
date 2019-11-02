package org.lh.service;

import org.lh.domain.DiscountInfo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link DiscountInfo}.
 */
public interface DiscountInfoService {

    /**
     * Save a discountInfo.
     *
     * @param discountInfo the entity to save.
     * @return the persisted entity.
     */
    DiscountInfo save(DiscountInfo discountInfo);

    /**
     * Get all the discountInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DiscountInfo> findAll(Pageable pageable);


    /**
     * Get the "id" discountInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DiscountInfo> findOne(Long id);

    /**
     * Delete the "id" discountInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
