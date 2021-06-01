package com.inditex.zara.model;

import java.sql.Timestamp;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.inditex.zara.common.exceptions.RequiredParamNotFound;

public class PriceRequest {

	private Timestamp fechaAplicacion;
	private Integer productId;
	private Integer brandId;

	
	public PriceRequest() {
	}

	public PriceRequest(Timestamp fechaAplicacion, Integer productId, Integer brandId) {
		super();
		this.fechaAplicacion = fechaAplicacion;
		this.productId = productId;
		this.brandId = brandId;
	}

	public PriceRequest(Long lFechaAplicacion, Integer productId, Integer brandId) {
		super();
		this.fechaAplicacion = new Timestamp(lFechaAplicacion);
		this.productId = productId;
		this.brandId = brandId;
	}

	
	public Timestamp getFechaAplicacion() {
		return fechaAplicacion;
	}

	public void setFechaAplicacion(Timestamp fechaAplicacion) {
		this.fechaAplicacion = fechaAplicacion;
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

	public void validateRequest() {
		if (brandId == null) {
			throw new RequiredParamNotFound("Required param 'brandId' not found in your request",
					Stream.of("Required param not found", "Please update your request and submit again")
							.collect(Collectors.toList()));
		}
		if (productId == null) {
			throw new RequiredParamNotFound("Required param 'productId' not found in your request",
					Stream.of("Required param 'productId' not found", "Please update your request and submit again")
							.collect(Collectors.toList()));
		}
		if (fechaAplicacion == null) {
			throw new RequiredParamNotFound("Required param 'fechaAplicacion' not found in your request", Stream
					.of("Required param 'fechaAplicacion' not found", "Please update your request and submit again")
					.collect(Collectors.toList()));
		}

	}

	public String toJson() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
