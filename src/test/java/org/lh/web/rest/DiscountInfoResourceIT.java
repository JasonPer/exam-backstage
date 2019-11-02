package org.lh.web.rest;

import org.lh.ExamBackstageApp;
import org.lh.domain.DiscountInfo;
import org.lh.repository.DiscountInfoRepository;
import org.lh.service.DiscountInfoService;
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
 * Integration tests for the {@link DiscountInfoResource} REST controller.
 */
@SpringBootTest(classes = ExamBackstageApp.class)
public class DiscountInfoResourceIT {

    private static final String DEFAULT_DISCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISCOUNT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISCOUNT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DISCOUNT_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_DISCOUNT_VALUE = 1;
    private static final Integer UPDATED_DISCOUNT_VALUE = 2;
    private static final Integer SMALLER_DISCOUNT_VALUE = 1 - 1;

    private static final String DEFAULT_DISCOUNT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_DISCOUNT_STATUS = "BBBBBBBBBB";

    @Autowired
    private DiscountInfoRepository discountInfoRepository;

    @Autowired
    private DiscountInfoService discountInfoService;

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

    private MockMvc restDiscountInfoMockMvc;

    private DiscountInfo discountInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DiscountInfoResource discountInfoResource = new DiscountInfoResource(discountInfoService);
        this.restDiscountInfoMockMvc = MockMvcBuilders.standaloneSetup(discountInfoResource)
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
    public static DiscountInfo createEntity(EntityManager em) {
        DiscountInfo discountInfo = new DiscountInfo()
            .discountName(DEFAULT_DISCOUNT_NAME)
            .discountType(DEFAULT_DISCOUNT_TYPE)
            .discountValue(DEFAULT_DISCOUNT_VALUE)
            .discountStatus(DEFAULT_DISCOUNT_STATUS);
        return discountInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiscountInfo createUpdatedEntity(EntityManager em) {
        DiscountInfo discountInfo = new DiscountInfo()
            .discountName(UPDATED_DISCOUNT_NAME)
            .discountType(UPDATED_DISCOUNT_TYPE)
            .discountValue(UPDATED_DISCOUNT_VALUE)
            .discountStatus(UPDATED_DISCOUNT_STATUS);
        return discountInfo;
    }

    @BeforeEach
    public void initTest() {
        discountInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiscountInfo() throws Exception {
        int databaseSizeBeforeCreate = discountInfoRepository.findAll().size();

        // Create the DiscountInfo
        restDiscountInfoMockMvc.perform(post("/api/discount-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discountInfo)))
            .andExpect(status().isCreated());

        // Validate the DiscountInfo in the database
        List<DiscountInfo> discountInfoList = discountInfoRepository.findAll();
        assertThat(discountInfoList).hasSize(databaseSizeBeforeCreate + 1);
        DiscountInfo testDiscountInfo = discountInfoList.get(discountInfoList.size() - 1);
        assertThat(testDiscountInfo.getDiscountName()).isEqualTo(DEFAULT_DISCOUNT_NAME);
        assertThat(testDiscountInfo.getDiscountType()).isEqualTo(DEFAULT_DISCOUNT_TYPE);
        assertThat(testDiscountInfo.getDiscountValue()).isEqualTo(DEFAULT_DISCOUNT_VALUE);
        assertThat(testDiscountInfo.getDiscountStatus()).isEqualTo(DEFAULT_DISCOUNT_STATUS);
    }

    @Test
    @Transactional
    public void createDiscountInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = discountInfoRepository.findAll().size();

        // Create the DiscountInfo with an existing ID
        discountInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiscountInfoMockMvc.perform(post("/api/discount-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discountInfo)))
            .andExpect(status().isBadRequest());

        // Validate the DiscountInfo in the database
        List<DiscountInfo> discountInfoList = discountInfoRepository.findAll();
        assertThat(discountInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDiscountInfos() throws Exception {
        // Initialize the database
        discountInfoRepository.saveAndFlush(discountInfo);

        // Get all the discountInfoList
        restDiscountInfoMockMvc.perform(get("/api/discount-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discountInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].discountName").value(hasItem(DEFAULT_DISCOUNT_NAME.toString())))
            .andExpect(jsonPath("$.[*].discountType").value(hasItem(DEFAULT_DISCOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].discountValue").value(hasItem(DEFAULT_DISCOUNT_VALUE)))
            .andExpect(jsonPath("$.[*].discountStatus").value(hasItem(DEFAULT_DISCOUNT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getDiscountInfo() throws Exception {
        // Initialize the database
        discountInfoRepository.saveAndFlush(discountInfo);

        // Get the discountInfo
        restDiscountInfoMockMvc.perform(get("/api/discount-infos/{id}", discountInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(discountInfo.getId().intValue()))
            .andExpect(jsonPath("$.discountName").value(DEFAULT_DISCOUNT_NAME.toString()))
            .andExpect(jsonPath("$.discountType").value(DEFAULT_DISCOUNT_TYPE.toString()))
            .andExpect(jsonPath("$.discountValue").value(DEFAULT_DISCOUNT_VALUE))
            .andExpect(jsonPath("$.discountStatus").value(DEFAULT_DISCOUNT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDiscountInfo() throws Exception {
        // Get the discountInfo
        restDiscountInfoMockMvc.perform(get("/api/discount-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiscountInfo() throws Exception {
        // Initialize the database
        discountInfoService.save(discountInfo);

        int databaseSizeBeforeUpdate = discountInfoRepository.findAll().size();

        // Update the discountInfo
        DiscountInfo updatedDiscountInfo = discountInfoRepository.findById(discountInfo.getId()).get();
        // Disconnect from session so that the updates on updatedDiscountInfo are not directly saved in db
        em.detach(updatedDiscountInfo);
        updatedDiscountInfo
            .discountName(UPDATED_DISCOUNT_NAME)
            .discountType(UPDATED_DISCOUNT_TYPE)
            .discountValue(UPDATED_DISCOUNT_VALUE)
            .discountStatus(UPDATED_DISCOUNT_STATUS);

        restDiscountInfoMockMvc.perform(put("/api/discount-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDiscountInfo)))
            .andExpect(status().isOk());

        // Validate the DiscountInfo in the database
        List<DiscountInfo> discountInfoList = discountInfoRepository.findAll();
        assertThat(discountInfoList).hasSize(databaseSizeBeforeUpdate);
        DiscountInfo testDiscountInfo = discountInfoList.get(discountInfoList.size() - 1);
        assertThat(testDiscountInfo.getDiscountName()).isEqualTo(UPDATED_DISCOUNT_NAME);
        assertThat(testDiscountInfo.getDiscountType()).isEqualTo(UPDATED_DISCOUNT_TYPE);
        assertThat(testDiscountInfo.getDiscountValue()).isEqualTo(UPDATED_DISCOUNT_VALUE);
        assertThat(testDiscountInfo.getDiscountStatus()).isEqualTo(UPDATED_DISCOUNT_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingDiscountInfo() throws Exception {
        int databaseSizeBeforeUpdate = discountInfoRepository.findAll().size();

        // Create the DiscountInfo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiscountInfoMockMvc.perform(put("/api/discount-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discountInfo)))
            .andExpect(status().isBadRequest());

        // Validate the DiscountInfo in the database
        List<DiscountInfo> discountInfoList = discountInfoRepository.findAll();
        assertThat(discountInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDiscountInfo() throws Exception {
        // Initialize the database
        discountInfoService.save(discountInfo);

        int databaseSizeBeforeDelete = discountInfoRepository.findAll().size();

        // Delete the discountInfo
        restDiscountInfoMockMvc.perform(delete("/api/discount-infos/{id}", discountInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DiscountInfo> discountInfoList = discountInfoRepository.findAll();
        assertThat(discountInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiscountInfo.class);
        DiscountInfo discountInfo1 = new DiscountInfo();
        discountInfo1.setId(1L);
        DiscountInfo discountInfo2 = new DiscountInfo();
        discountInfo2.setId(discountInfo1.getId());
        assertThat(discountInfo1).isEqualTo(discountInfo2);
        discountInfo2.setId(2L);
        assertThat(discountInfo1).isNotEqualTo(discountInfo2);
        discountInfo1.setId(null);
        assertThat(discountInfo1).isNotEqualTo(discountInfo2);
    }
}
