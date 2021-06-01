package com.inditex.zara.repository.async;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import com.inditex.zara.entity.Price;
import com.inditex.zara.repository.base.PriceJpaRepository;

/**
 *Interface that allow Async Query by example for Price entity
 */
public interface PriceRepositoryRxJ extends PriceJpaRepository<Price> {
	
    @Async
    @Query("select c from Price c where c.priceCompositeKey.brandId=:brandId and c.priceCompositeKey.productId=:productId and c.startDate<=:fechaAplicacion and c.endDate>=:fechaAplicacion")
    public CompletableFuture<List<Price>> asyncFindByBrandProductFechaAplicacion(@Param("brandId") Integer brandId, @Param("productId") Integer productId, @Param("fechaAplicacion") Timestamp fechaAplicacion);
    
    @Async
    @Query(value = "select * from PRICES where BRAND_ID =:brandId AND PRODUCT_ID =:productId AND START_DATE <=:fechaAplicacion AND END_DATE >=:fechaAplicacion order by PRICE_LIST desc limit 1", nativeQuery = true)
    public CompletableFuture<Price> asyncFindCurrentByBrandProductFechaAplicacion(@Param("brandId") Integer brandId, @Param("productId") Integer productId, @Param("fechaAplicacion") Timestamp fechaAplicacion);
	
}
