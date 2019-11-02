package org.lh.domain;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A DiscountInfo.
 */
@Entity
@Table(name = "discount_info")
public class DiscountInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "discount_name")
    private String discountName;

    @Column(name = "discount_type")
    private String discountType;

    @Column(name = "discount_value")
    private Integer discountValue;

    @Column(name = "discount_status")
    private String discountStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiscountName() {
        return discountName;
    }

    public DiscountInfo discountName(String discountName) {
        this.discountName = discountName;
        return this;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public String getDiscountType() {
        return discountType;
    }

    public DiscountInfo discountType(String discountType) {
        this.discountType = discountType;
        return this;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public Integer getDiscountValue() {
        return discountValue;
    }

    public DiscountInfo discountValue(Integer discountValue) {
        this.discountValue = discountValue;
        return this;
    }

    public void setDiscountValue(Integer discountValue) {
        this.discountValue = discountValue;
    }

    public String getDiscountStatus() {
        return discountStatus;
    }

    public DiscountInfo discountStatus(String discountStatus) {
        this.discountStatus = discountStatus;
        return this;
    }

    public void setDiscountStatus(String discountStatus) {
        this.discountStatus = discountStatus;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DiscountInfo)) {
            return false;
        }
        return id != null && id.equals(((DiscountInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DiscountInfo{" +
            "id=" + getId() +
            ", discountName='" + getDiscountName() + "'" +
            ", discountType='" + getDiscountType() + "'" +
            ", discountValue=" + getDiscountValue() +
            ", discountStatus='" + getDiscountStatus() + "'" +
            "}";
    }
}
