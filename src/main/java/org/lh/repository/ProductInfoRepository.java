package org.lh.repository;

import org.lh.domain.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long> {
    /**
     * 按照商品销售状态查询商品列表
     * @param productStatus 商品状态
     * @param page 分页信息
     * @return
     */
    Page<ProductInfo> findProductInfosByProductStatus(String productStatus, Pageable page);
}
