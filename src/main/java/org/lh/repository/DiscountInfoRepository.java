package org.lh.repository;

import org.lh.domain.DiscountInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DiscountInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiscountInfoRepository extends JpaRepository<DiscountInfo, Long> {

}
