package com.inditex.zara.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PriceCompositeKey implements Serializable {
	
	private static final long serialVersionUID = -1625285827635644670L;

	@Column(name = "BrandId")
	private Integer brandId;
	
	@Column(name = "PriceList")
	private Integer priceList;
	
	@Column(name = "ProductId")
	private Integer productId;

	
	public PriceCompositeKey() {}
	
	public PriceCompositeKey(Integer brandId, Integer priceList, Integer productId) {
		super();
		this.brandId = brandId;
		this.priceList = priceList;
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

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
    @Override
    public boolean equals(final Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || getClass() != otherObject.getClass()) {
            return false;
        }

        final PriceCompositeKey other = (PriceCompositeKey) otherObject;
        if (!Objects.equals(this.brandId, other.brandId)) {
            return false;
        }
        if (!Objects.equals(this.priceList, other.priceList)) {
            return false;
        }
        if (!Objects.equals(this.productId, other.productId)) {
            return false;
        }
        
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.brandId);
        hash = 59 * hash + Objects.hashCode(this.priceList);
        hash = 59 * hash + Objects.hashCode(this.productId);
        return hash;
    }
	
}
