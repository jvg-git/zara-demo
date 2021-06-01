package com.inditex.zara.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.inditex.zara.model.PriceUpdate;


@Entity
@Table(name = "prices")
//@IdClass(PriceCompositeKey.class)
public class Price implements Serializable {

	private static final long serialVersionUID = -5286146938244890002L;
	
	@EmbeddedId
    private PriceCompositeKey priceCompositeKey;
	
	@Column(name = "StartDate")
	private Timestamp startDate;
	
	@Column(name = "EndDate")
	private Timestamp endDate;
	
	@Column(name = "Priority")
	private Integer priority;
	
	@Column(name = "Price")
	private Double price;
	
	@Column(name = "Currency")
	private String currency;
	
	@Column(name = "LastUpdate")
	private Timestamp lastUpdate;
	
	@Column(name = "LastUpdateBy")
	private String lastUpdateBy;
	
	
	public Price() {}
	
	public Price(PriceCompositeKey priceCompositeKey, Timestamp startDate, Timestamp endDate, Integer priority, Double price,
			String currency, Timestamp lastUpdate, String lastUpdateBy) {
		super();
		this.priceCompositeKey = priceCompositeKey;
		this.startDate = startDate;
		this.endDate = endDate;
		this.priority = priority;
		this.price = price;
		this.currency = currency;
		this.lastUpdate = lastUpdate;
		this.lastUpdateBy = lastUpdateBy;
	}

	public Price(Integer brandId, Integer priceList, Integer productId, Timestamp startDate, Timestamp endDate, Integer priority, Double price,
			String currency, Timestamp lastUpdate, String lastUpdateBy) {
		super();
		this.priceCompositeKey = new PriceCompositeKey(brandId, priceList, productId);
		this.startDate = startDate;
		this.endDate = endDate;
		this.priority = priority;
		this.price = price;
		this.currency = currency;
		this.lastUpdate = lastUpdate;
		this.lastUpdateBy = lastUpdateBy;
	}

	public Price(PriceUpdate priceUpdate) {
		this.priceCompositeKey = new PriceCompositeKey(priceUpdate.getBrandId(), priceUpdate.getPriceList(), priceUpdate.getProductId());
		this.startDate = priceUpdate.getStartDate();
		this.endDate = priceUpdate.getEndDate();
		this.priority = priceUpdate.getPriority();
		this.price = priceUpdate.getPrice();
		this.currency = priceUpdate.getCurrency();
		this.lastUpdate = priceUpdate.getLastUpdate();
		this.lastUpdateBy = priceUpdate.getLastUpdateBy();
	}

	
	public PriceCompositeKey getPriceCompositeKey() {
		return priceCompositeKey;
	}

	public void setPriceCompositeKey(PriceCompositeKey priceCompositeKey) {
		this.priceCompositeKey = priceCompositeKey;
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


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

}
