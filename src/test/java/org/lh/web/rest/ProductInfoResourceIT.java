package org.lh.web.rest;

import org.lh.ExamBackstageApp;
import org.lh.domain.ProductInfo;
import org.lh.repository.ProductInfoRepository;
import org.lh.service.ProductInfoService;
import org.lh.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.lh.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProductInfoResource} REST controller.
 */
@SpringBootTest(classes = ExamBackstageApp.class)
public class ProductInfoResourceIT {

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_STATUS = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORIGIN_PRICE = 1;
    private static final Integer UPDATED_ORIGIN_PRICE = 2;
    private static final Integer SMALLER_ORIGIN_PRICE = 1 - 1;

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restProductInfoMockMvc;

    private ProductInfo productInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductInfoResource productInfoResource = new ProductInfoResource(productInfoService);
        this.restProductInfoMockMvc = MockMvcBuilders.standaloneSetup(productInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductInfo createEntity(EntityManager em) {
        ProductInfo productInfo = new ProductInfo()
            .productName(DEFAULT_PRODUCT_NAME)
            .productType(DEFAULT_PRODUCT_TYPE)
            .productStatus(DEFAULT_PRODUCT_STATUS)
            .originPrice(DEFAULT_ORIGIN_PRICE);
        return productInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductInfo createUpdatedEntity(EntityManager em) {
        ProductInfo productInfo = new ProductInfo()
            .productName(UPDATED_PRODUCT_NAME)
            .productType(UPDATED_PRODUCT_TYPE)
            .productStatus(UPDATED_PRODUCT_STATUS)
            .originPrice(UPDATED_ORIGIN_PRICE);
        return productInfo;
    }

    @BeforeEach
    public void initTest() {
        productInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductInfo() throws Exception {
        int databaseSizeBeforeCreate = productInfoRepository.findAll().size();

        // Create the ProductInfo
        restProductInfoMockMvc.perform(post("/api/product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productInfo)))
            .andExpect(status().isCreated());

        // Validate the ProductInfo in the database
        List<ProductInfo> productInfoList = productInfoRepository.findAll();
        assertThat(productInfoList).hasSize(databaseSizeBeforeCreate + 1);
        ProductInfo testProductInfo = productInfoList.get(productInfoList.size() - 1);
        assertThat(testProductInfo.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testProductInfo.getProductType()).isEqualTo(DEFAULT_PRODUCT_TYPE);
        assertThat(testProductInfo.getProductStatus()).isEqualTo(DEFAULT_PRODUCT_STATUS);
        assertThat(testProductInfo.getOriginPrice()).isEqualTo(DEFAULT_ORIGIN_PRICE);
    }

    @Test
    @Transactional
    public void createProductInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productInfoRepository.findAll().size();

        // Create the ProductInfo with an existing ID
        productInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductInfoMockMvc.perform(post("/api/product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productInfo)))
            .andExpect(status().isBadRequest());

        // Validate the ProductInfo in the database
        List<ProductInfo> productInfoList = productInfoRepository.findAll();
        assertThat(productInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductInfos() throws Exception {
        // Initialize the database
        productInfoRepository.saveAndFlush(productInfo);

        // Get all the productInfoList
        restProductInfoMockMvc.perform(get("/api/product-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME.toString())))
            .andExpect(jsonPath("$.[*].productType").value(hasItem(DEFAULT_PRODUCT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].productStatus").value(hasItem(DEFAULT_PRODUCT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].originPrice").value(hasItem(DEFAULT_ORIGIN_PRICE)));
    }
    
    @Test
    @Transactional
    public void getProductInfo() throws Exception {
        // Initialize the database
        productInfoRepository.saveAndFlush(productInfo);

        // Get the productInfo
        restProductInfoMockMvc.perform(get("/api/product-infos/{id}", productInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productInfo.getId().intValue()))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME.toString()))
            .andExpect(jsonPath("$.productType").value(DEFAULT_PRODUCT_TYPE.toString()))
            .andExpect(jsonPath("$.productStatus").value(DEFAULT_PRODUCT_STATUS.toString()))
            .andExpect(jsonPath("$.originPrice").value(DEFAULT_ORIGIN_PRICE));
    }

    @Test
    @Transactional
    public void getNonExistingProductInfo() throws Exception {
        // Get the productInfo
        restProductInfoMockMvc.perform(get("/api/product-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductInfo() throws Exception {
        // Initialize the database
        productInfoService.save(productInfo);

        int databaseSizeBeforeUpdate = productInfoRepository.findAll().size();

        // Update the productInfo
        ProductInfo updatedProductInfo = productInfoRepository.findById(productInfo.getId()).get();
        // Disconnect from session so that the updates on updatedProductInfo are not directly saved in db
        em.detach(updatedProductInfo);
        updatedProductInfo
            .productName(UPDATED_PRODUCT_NAME)
            .productType(UPDATED_PRODUCT_TYPE)
            .productStatus(UPDATED_PRODUCT_STATUS)
            .originPrice(UPDATED_ORIGIN_PRICE);

        restProductInfoMockMvc.perform(put("/api/product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductInfo)))
            .andExpect(status().isOk());

        // Validate the ProductInfo in the database
        List<ProductInfo> productInfoList = productInfoRepository.findAll();
        assertThat(productInfoList).hasSize(databaseSizeBeforeUpdate);
        ProductInfo testProductInfo = productInfoList.get(productInfoList.size() - 1);
        assertThat(testProductInfo.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testProductInfo.getProductType()).isEqualTo(UPDATED_PRODUCT_TYPE);
        assertThat(testProductInfo.getProductStatus()).isEqualTo(UPDATED_PRODUCT_STATUS);
        assertThat(testProductInfo.getOriginPrice()).isEqualTo(UPDATED_ORIGIN_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductInfo() throws Exception {
        int databaseSizeBeforeUpdate = productInfoRepository.findAll().size();

        // Create the ProductInfo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductInfoMockMvc.perform(put("/api/product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productInfo)))
            .andExpect(status().isBadRequest());

        // Validate the ProductInfo in the database
        List<ProductInfo> productInfoList = productInfoRepository.findAll();
        assertThat(productInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductInfo() throws Exception {
        // Initialize the database
        productInfoService.save(productInfo);

        int databaseSizeBeforeDelete = productInfoRepository.findAll().size();

        // Delete the productInfo
        restProductInfoMockMvc.perform(delete("/api/product-infos/{id}", productInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductInfo> productInfoList = productInfoRepository.findAll();
        assertThat(productInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductInfo.class);
        ProductInfo productInfo1 = new ProductInfo();
        productInfo1.setId(1L);
        ProductInfo productInfo2 = new ProductInfo();
        productInfo2.setId(productInfo1.getId());
        assertThat(productInfo1).isEqualTo(productInfo2);
        productInfo2.setId(2L);
        assertThat(productInfo1).isNotEqualTo(productInfo2);
        productInfo1.setId(null);
        assertThat(productInfo1).isNotEqualTo(productInfo2);
    }
}
