package org.lh.service.dto;

/**
 * 商品平均价格返回通用DTO
 * @author:liuhuan
 * @date:2019/11/2,10:41
 * @version:1.0
 */
public class ProductAvgDTO {
    private String productType;
    private Integer averagePrice;

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Integer getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Integer averagePrice) {
        this.averagePrice = averagePrice;
    }
}
