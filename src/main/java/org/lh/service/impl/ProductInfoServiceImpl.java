package org.lh.service.impl;

import com.alibaba.fastjson.util.TypeUtils;
import org.lh.service.ProductInfoService;
import org.lh.domain.ProductInfo;
import org.lh.repository.ProductInfoRepository;
import org.lh.service.dto.ProductAvgDTO;
import org.lh.service.dto.ProductOnSaleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service Implementation for managing {@link ProductInfo}.
 */
@Service
@Transactional
public class ProductInfoServiceImpl implements ProductInfoService {

    private final Logger log = LoggerFactory.getLogger(ProductInfoServiceImpl.class);

    private final ProductInfoRepository productInfoRepository;

    private final JdbcTemplate template;

    public ProductInfoServiceImpl(ProductInfoRepository productInfoRepository, JdbcTemplate template) {
        this.productInfoRepository = productInfoRepository;
        this.template = template;
    }

    /**
     * Save a productInfo.
     *
     * @param productInfo the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductInfo save(ProductInfo productInfo) {
        log.debug("Request to save ProductInfo : {}", productInfo);
        return productInfoRepository.save(productInfo);
    }

    /**
     * Get all the productInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductInfo> findAll(Pageable pageable) {
        log.debug("Request to get all ProductInfos");
        return productInfoRepository.findAll(pageable);
    }


    /**
     * Get one productInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductInfo> findOne(Long id) {
        log.debug("Request to get ProductInfo : {}", id);
        return productInfoRepository.findById(id);
    }

    /**
     * Delete the productInfo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductInfo : {}", id);
        productInfoRepository.deleteById(id);
    }

    @Override
    public Page<ProductInfo> findProductInfosByProductStatus(String productStatus, Pageable page) {
        Page<ProductInfo> pageResult = productInfoRepository.findProductInfosByProductStatus(productStatus,page);
        if (pageResult.isEmpty()){
            return null;
        }else
            return pageResult;
    }

    @Override
    public Optional<ProductAvgDTO> getProductAvgPriceByProductStatus(String productType) {
        ProductAvgDTO avgDTO = new ProductAvgDTO();
        String sql = "SELECT a.product_type,ROUND(AVG(a.origin_price),0) AS average_price FROM product_info AS a " +
            "WHERE a.product_type = '" +productType+ "'";
        Map<String,Object> queryResult = template.queryForMap(sql);
        if (queryResult.isEmpty()){
            return Optional.empty();
        }else
            avgDTO.setProductType(TypeUtils.castToString(queryResult.get("product_type")));
            avgDTO.setAveragePrice(TypeUtils.castToInt(queryResult.get("average_price")));
            return Optional.of(avgDTO);
    }

    @Override
    public Optional<ProductAvgDTO> getProductOnSaleAvgPriceByProductStatus(String productType) {
        List<ProductOnSaleDTO> onSaleList = new ArrayList<>();
        Map<Integer,Float> priceMap = new HashMap<>();

        String sql = "SELECT a.product_type,a.origin_price,b.discount_type,b.discount_value,b.discount_status FROM product_info AS a,discount_info AS b " +
            "WHERE a.product_status = 'onsale' " +
            "AND a.discount_info_id = b.id " +
            "AND a.product_type = '" +productType+"'";

        List<Map<String,Object>> queryResult = template.queryForList(sql);
        if (queryResult.isEmpty()){
            return Optional.empty();
        }else {
            for (Map<String,Object> item:queryResult) {
                ProductOnSaleDTO onSaleDTO = new ProductOnSaleDTO();

                onSaleDTO.setProductType(TypeUtils.castToString(item.get("product_type")));
                onSaleDTO.setOriginPrice(TypeUtils.castToInt(item.get("origin_price")));
                onSaleDTO.setDiscountType(TypeUtils.castToString(item.get("discount_type")));
                onSaleDTO.setDiscountValue(TypeUtils.castToInt(item.get("discount_value")));
                onSaleDTO.setDiscountStatus(TypeUtils.castToString(item.get("discount_status")));

                onSaleList.add(onSaleDTO);
            }

            for (int i = 0; i < onSaleList.size(); i++) {
                if (onSaleList.get(i).getDiscountType().equals("比例") && onSaleList.get(i).getDiscountStatus().equals("启用")){
                    float price = onSaleList.get(i).getOriginPrice() * ((float)(onSaleList.get(i).getDiscountValue())/10);
                    System.out.println("价格1："+price);
                    priceMap.put(i,price);
                }else if (onSaleList.get(i).getDiscountType().equals("比例") && onSaleList.get(i).getDiscountStatus().equals("停用")){
                    float price = (float)onSaleList.get(i).getOriginPrice();
                    System.out.println("价格："+price);
                    priceMap.put(i,price);
                }else if (onSaleList.get(i).getDiscountType().equals("金额") && onSaleList.get(i).getDiscountStatus().equals("启用")){
                    float price = (float) (onSaleList.get(i).getOriginPrice() - onSaleList.get(i).getDiscountValue());
                    System.out.println("价格："+price);
                    priceMap.put(i,price);
                }else if (onSaleList.get(i).getDiscountType().equals("金额") && onSaleList.get(i).getDiscountStatus().equals("停用")){
                    float price = (float)(onSaleList.get(i).getOriginPrice());
                    System.out.println("价格："+price);
                    priceMap.put(i,price);
                }
            }

            float totalPrice = 0f;

            if (priceMap.isEmpty()){
                return Optional.empty();
            }else {
                for (Integer i:priceMap.keySet()) {
                    totalPrice += priceMap.get(i);
                }
            }

            Integer avgPrice = Math.round(totalPrice / (float)priceMap.size());

            ProductAvgDTO dto = new ProductAvgDTO();
            dto.setProductType(productType);
            dto.setAveragePrice(avgPrice);

            return Optional.of(dto);
        }
    }
}
