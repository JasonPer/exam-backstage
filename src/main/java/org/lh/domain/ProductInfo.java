package org.lh.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ProductInfo.
 */
@Entity
@Table(name = "product_info")
public class ProductInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "product_status")
    private String productStatus;

    @Column(name = "origin_price")
    private Integer originPrice;

    @ManyToOne
    @JsonIgnoreProperties("productInfos")
    private DiscountInfo discountInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public ProductInfo productName(String productName) {
        this.productName = productName;
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public ProductInfo productType(String productType) {
        this.productType = productType;
        return this;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public ProductInfo productStatus(String productStatus) {
        this.productStatus = productStatus;
        return this;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public Integer getOriginPrice() {
        return originPrice;
    }

    public ProductInfo originPrice(Integer originPrice) {
        this.originPrice = originPrice;
        return this;
    }

    public void setOriginPrice(Integer originPrice) {
        this.originPrice = originPrice;
    }

    public DiscountInfo getDiscountInfo() {
        return discountInfo;
    }

    public ProductInfo discountInfo(DiscountInfo discountInfo) {
        this.discountInfo = discountInfo;
        return this;
    }

    public void setDiscountInfo(DiscountInfo discountInfo) {
        this.discountInfo = discountInfo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductInfo)) {
            return false;
        }
        return id != null && id.equals(((ProductInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
            "id=" + getId() +
            ", productName='" + getProductName() + "'" +
            ", productType='" + getProductType() + "'" +
            ", productStatus='" + getProductStatus() + "'" +
            ", originPrice=" + getOriginPrice() +
            "}";
    }
}
