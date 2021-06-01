package com.inditex.zara.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.inditex.zara.entity.Price;

public class PriceResponse {
	
	private Integer productId;
	private Integer brandId;
	private Integer priceList;
	private Integer priority;
	private String startDate;
	private String endDate;
	private Double price;
	private String currency;
	private String lastUpdate;
	private String lastUpdateBy;

	private SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
	
	
	public PriceResponse() {}
	
	public PriceResponse(Integer productId, Integer brandId, Integer priceList, Integer priority, Timestamp startDate, Timestamp endDate,
			Double price, String currency, String lastUpdate, String lastUpdateBy) {
		super();
		this.productId = productId;
		this.brandId = brandId;
		this.priceList = priceList;
		this.priority = priority; 
		this.startDate = parseTimestamp(startDate);
		this.endDate = parseTimestamp(endDate);
		this.price = price;
		this.currency = currency;
		this.lastUpdate = lastUpdate;
		this.lastUpdateBy = lastUpdateBy;
	}

	public PriceResponse(Price price) {
		super();
		this.productId = price.getPriceCompositeKey().getProductId();
		this.brandId = price.getPriceCompositeKey().getBrandId();
		this.priceList = price.getPriceCompositeKey().getPriceList();
		this.priority = price.getPriority();
		this.startDate = parseTimestamp(price.getStartDate());
		this.endDate = parseTimestamp(price.getEndDate());
		this.price = price.getPrice();
		this.currency = price.getCurrency();
		this.lastUpdate = parseTimestamp(price.getLastUpdate());
		this.lastUpdateBy = price.getLastUpdateBy();
	}

	
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Integer getPriceList() {
		return priceList;
	}

	public void setPriceList(Integer priceList) {
		this.priceList = priceList;
	}
	
	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
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

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	private String parseTimestamp(Timestamp timestamp) {
		return sdf.format(timestamp);
	}

	
	public String toJson() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
	
	
}
