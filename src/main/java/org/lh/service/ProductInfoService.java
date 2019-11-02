package org.lh.service;

import org.lh.domain.ProductInfo;

import org.lh.service.dto.ProductAvgDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ProductInfo}.
 */
public interface ProductInfoService {

    /**
     * Save a productInfo.
     *
     * @param productInfo the entity to save.
     * @return the persisted entity.
     */
    ProductInfo save(ProductInfo productInfo);

    /**
     * Get all the productInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductInfo> findAll(Pageable pageable);


    /**
     * Get the "id" productInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductInfo> findOne(Long id);

    /**
     * Delete the "id" productInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * 按照商品销售状态查询商品列表
     * @param productStatus 商品状态
     * @param page 分页信息
     * @return
     */
    Page<ProductInfo> findProductInfosByProductStatus(String productStatus, Pageable page);

    /**
     * 按照商品类型统计商品平均原价
     * @param productType 商品类型
     * @return
     */
    Optional<ProductAvgDTO> getProductAvgPriceByProductStatus(String productType);

    /**
     * 按商品分类统计商品优惠后的平均售价
     * @param productType 商品分类
     * @return
     */
    Optional<ProductAvgDTO> getProductOnSaleAvgPriceByProductStatus(String productType);
}
