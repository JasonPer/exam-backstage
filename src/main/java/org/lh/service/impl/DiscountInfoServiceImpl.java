package org.lh.service.impl;

import org.lh.service.DiscountInfoService;
import org.lh.domain.DiscountInfo;
import org.lh.repository.DiscountInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DiscountInfo}.
 */
@Service
@Transactional
public class DiscountInfoServiceImpl implements DiscountInfoService {

    private final Logger log = LoggerFactory.getLogger(DiscountInfoServiceImpl.class);

    private final DiscountInfoRepository discountInfoRepository;

    public DiscountInfoServiceImpl(DiscountInfoRepository discountInfoRepository) {
        this.discountInfoRepository = discountInfoRepository;
    }

    /**
     * Save a discountInfo.
     *
     * @param discountInfo the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DiscountInfo save(DiscountInfo discountInfo) {
        log.debug("Request to save DiscountInfo : {}", discountInfo);
        return discountInfoRepository.save(discountInfo);
    }

    /**
     * Get all the discountInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DiscountInfo> findAll(Pageable pageable) {
        log.debug("Request to get all DiscountInfos");
        return discountInfoRepository.findAll(pageable);
    }


    /**
     * Get one discountInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DiscountInfo> findOne(Long id) {
        log.debug("Request to get DiscountInfo : {}", id);
        return discountInfoRepository.findById(id);
    }

    /**
     * Delete the discountInfo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DiscountInfo : {}", id);
        discountInfoRepository.deleteById(id);
    }
}
