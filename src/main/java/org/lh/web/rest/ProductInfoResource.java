package org.lh.web.rest;

import org.lh.domain.ProductInfo;
import org.lh.service.ProductInfoService;
import org.lh.service.dto.ProductAvgDTO;
import org.lh.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.lh.domain.ProductInfo}.
 */
@RestController
@RequestMapping("/api")
public class ProductInfoResource {

    private final Logger log = LoggerFactory.getLogger(ProductInfoResource.class);

    private static final String ENTITY_NAME = "productInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductInfoService productInfoService;

    public ProductInfoResource(ProductInfoService productInfoService) {
        this.productInfoService = productInfoService;
    }

    /**
     * {@code POST  /product-infos} : Create a new productInfo.
     *
     * @param productInfo the productInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productInfo, or with status {@code 400 (Bad Request)} if the productInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-infos")
    public ResponseEntity<ProductInfo> createProductInfo(@RequestBody ProductInfo productInfo) throws URISyntaxException {
        log.debug("REST request to save ProductInfo : {}", productInfo);
        if (productInfo.getId() != null) {
            throw new BadRequestAlertException("A new productInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductInfo result = productInfoService.save(productInfo);
        return ResponseEntity.created(new URI("/api/product-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-infos} : Updates an existing productInfo.
     *
     * @param productInfo the productInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productInfo,
     * or with status {@code 400 (Bad Request)} if the productInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-infos")
    public ResponseEntity<ProductInfo> updateProductInfo(@RequestBody ProductInfo productInfo) throws URISyntaxException {
        log.debug("REST request to update ProductInfo : {}", productInfo);
        if (productInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductInfo result = productInfoService.save(productInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-infos} : get all the productInfos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productInfos in body.
     */
    @GetMapping("/product-infos")
    public ResponseEntity<List<ProductInfo>> getAllProductInfos(Pageable pageable) {
        log.debug("REST request to get a page of ProductInfos");
        Page<ProductInfo> page = productInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-infos/:id} : get the "id" productInfo.
     *
     * @param id the id of the productInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-infos/{id}")
    public ResponseEntity<ProductInfo> getProductInfo(@PathVariable Long id) {
        log.debug("REST request to get ProductInfo : {}", id);
        Optional<ProductInfo> productInfo = productInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productInfo);
    }

    /**
     * {@code DELETE  /product-infos/:id} : delete the "id" productInfo.
     *
     * @param id the id of the productInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-infos/{id}")
    public ResponseEntity<Void> deleteProductInfo(@PathVariable Long id) {
        log.debug("REST request to delete ProductInfo : {}", id);
        productInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * 按照商品销售状态查询商品列表
     * @param productStatus 商品状态
     * @param page 分页信息
     * @return
     */
    @GetMapping("/product-infos/status")
    public ResponseEntity findProductInfosByProductStatus(String productStatus,Pageable page){
        Page<ProductInfo> result = productInfoService.findProductInfosByProductStatus(productStatus,page);
        if (result.isEmpty()){
            return ResponseEntity.ok("");
        }else
            return ResponseEntity.ok(result);
    }

    /**
     * 按照商品类型统计商品平均原价
     * @param productType 商品类型
     * @return
     */
    @GetMapping("/product-infos/price")
    public ResponseEntity getProductAvgPriceByProductStatus(String productType){
        Optional<ProductAvgDTO> result = productInfoService.getProductAvgPriceByProductStatus(productType);
        if (result.isPresent()){
            return ResponseEntity.ok(result.get());
        }else
            return ResponseEntity.ok("");
    }

    /**
     * 按商品分类统计商品优惠后的平均售价
     * @param productType 商品类型
     * @return
     */
    @GetMapping("/product-infos/discount-price")
    public ResponseEntity getProductOnSaleAvgPriceByProductStatus(String productType){
        Optional<ProductAvgDTO> result = productInfoService.getProductOnSaleAvgPriceByProductStatus(productType);
        if (result.isPresent()){
            return ResponseEntity.ok(result.get());
        }else
            return ResponseEntity.ok("");
    }
}
