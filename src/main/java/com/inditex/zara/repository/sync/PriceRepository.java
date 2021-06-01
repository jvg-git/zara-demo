package com.inditex.zara.repository.sync;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inditex.zara.entity.Price;
import com.inditex.zara.entity.PriceCompositeKey;

/**
 *Interface that allow Query by example for Price entity
 */
public interface PriceRepository extends JpaRepository<Price, PriceCompositeKey> {

    @Query("select c from Price c where c.priceCompositeKey.brandId=:brandId and c.priceCompositeKey.productId=:productId and c.startDate<=:fechaAplicacion and c.endDate>=:fechaAplicacion")
    public List<Price> findByBrandProductFechaAplicacion(@Param("brandId") Integer brandId, @Param("productId") Integer productId, @Param("fechaAplicacion") Timestamp fechaAplicacion);
    
    @Query(value = "select * from PRICES where BRAND_ID =:brandId AND PRODUCT_ID =:productId AND START_DATE <=:fechaAplicacion AND END_DATE >=:fechaAplicacion order by PRICE_LIST desc limit 1", nativeQuery = true)
    public Price findCurrentByBrandProductFechaAplicacion(@Param("brandId") Integer brandId, @Param("productId") Integer productId, @Param("fechaAplicacion") Timestamp fechaAplicacion);

}
