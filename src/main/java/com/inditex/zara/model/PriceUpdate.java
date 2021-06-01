package com.inditex.zara.model;

import java.sql.Timestamp;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.inditex.zara.entity.Price;
import com.opencsv.bean.CsvBindByName;

public class PriceUpdate {

	@CsvBindByName(column = "BRAND_ID")
    private Integer brandId;

	@CsvBindByName(column = "START_DATE")
    private Timestamp startDate;

	@CsvBindByName(column = "END_DATE")
    private Timestamp endDate;

	@CsvBindByName(column = "PRICE_LIST")
    private Integer priceList;
    
	@CsvBindByName(column = "PRODUCT_ID")
    private Integer productId;
    
	@CsvBindByName(column = "PRIORITY")
    private Integer priority;
    
	@CsvBindByName(column = "PRICE")
    private Double price;
    
	@CsvBindByName(column = "CURRENCY")
    private String currency;
    
	@CsvBindByName(column = "LAST_UPDATE")
    private Timestamp lastUpdate;

	@CsvBindByName(column = "LAST_UPDATE_BY")
    private String lastUpdateBy;
    
	@CsvBindByName(column = "SYNC")
    private Integer needSync;


    public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public Integer getPriceList() {
		return priceList;
	}

	public void setPriceList(Integer priceList) {
		this.priceList = priceList;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public Integer getNeedSync() {
		return needSync;
	}

	public void setNeedSync(Integer needSync) {
		this.needSync = needSync;
	}

	public Price toDb() {
		return new Price(this);
	}
	public String toJson() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
    
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
    
    
}
