package org.lh.web.rest;

import org.lh.domain.DiscountInfo;
import org.lh.service.DiscountInfoService;
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
 * REST controller for managing {@link org.lh.domain.DiscountInfo}.
 */
@RestController
@RequestMapping("/api")
public class DiscountInfoResource {

    private final Logger log = LoggerFactory.getLogger(DiscountInfoResource.class);

    private static final String ENTITY_NAME = "discountInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiscountInfoService discountInfoService;

    public DiscountInfoResource(DiscountInfoService discountInfoService) {
        this.discountInfoService = discountInfoService;
    }

    /**
     * {@code POST  /discount-infos} : Create a new discountInfo.
     *
     * @param discountInfo the discountInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new discountInfo, or with status {@code 400 (Bad Request)} if the discountInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/discount-infos")
    public ResponseEntity<DiscountInfo> createDiscountInfo(@RequestBody DiscountInfo discountInfo) throws URISyntaxException {
        log.debug("REST request to save DiscountInfo : {}", discountInfo);
        if (discountInfo.getId() != null) {
            throw new BadRequestAlertException("A new discountInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiscountInfo result = discountInfoService.save(discountInfo);
        return ResponseEntity.created(new URI("/api/discount-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /discount-infos} : Updates an existing discountInfo.
     *
     * @param discountInfo the discountInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated discountInfo,
     * or with status {@code 400 (Bad Request)} if the discountInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the discountInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/discount-infos")
    public ResponseEntity<DiscountInfo> updateDiscountInfo(@RequestBody DiscountInfo discountInfo) throws URISyntaxException {
        log.debug("REST request to update DiscountInfo : {}", discountInfo);
        if (discountInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DiscountInfo result = discountInfoService.save(discountInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, discountInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /discount-infos} : get all the discountInfos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of discountInfos in body.
     */
    @GetMapping("/discount-infos")
    public ResponseEntity<List<DiscountInfo>> getAllDiscountInfos(Pageable pageable) {
        log.debug("REST request to get a page of DiscountInfos");
        Page<DiscountInfo> page = discountInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /discount-infos/:id} : get the "id" discountInfo.
     *
     * @param id the id of the discountInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the discountInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/discount-infos/{id}")
    public ResponseEntity<DiscountInfo> getDiscountInfo(@PathVariable Long id) {
        log.debug("REST request to get DiscountInfo : {}", id);
        Optional<DiscountInfo> discountInfo = discountInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(discountInfo);
    }

    /**
     * {@code DELETE  /discount-infos/:id} : delete the "id" discountInfo.
     *
     * @param id the id of the discountInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/discount-infos/{id}")
    public ResponseEntity<Void> deleteDiscountInfo(@PathVariable Long id) {
        log.debug("REST request to delete DiscountInfo : {}", id);
        discountInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
