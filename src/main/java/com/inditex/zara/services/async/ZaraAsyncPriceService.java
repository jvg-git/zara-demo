package com.inditex.zara.services.async;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import com.inditex.zara.common.exceptions.AsyncMethodErrorException;
import com.inditex.zara.entity.Price;
import com.inditex.zara.model.PriceRequest;
import com.inditex.zara.model.PriceResponse;
import com.inditex.zara.model.PriceUpdate;
import com.inditex.zara.repository.async.PriceRepositoryRxJ;

/**
 * Service that performs  asynchronous CRUD operations for Price entity.
 * The implementation uses RxJava.
 */

@Service
public class ZaraAsyncPriceService extends AbstractAsyncBaseService<Price> {

	private static Logger logger = LoggerFactory.getLogger(ZaraAsyncPriceService.class);
	
	private PriceRepositoryRxJ priceRepositoryRxJ;
		
    /**
     * Creates a service instance that will use the supplied asynchronous Price repository
     * {@link PriceRepositoryRxJ} for entity.
     * @param priceRepositoryRxJ PriceRepositoryRxJ  {@link PriceRepositoryRxJ} repository.
     */
    public ZaraAsyncPriceService(final PriceRepositoryRxJ priceRepositoryRxJ) {
        super(priceRepositoryRxJ);
        this.priceRepositoryRxJ = priceRepositoryRxJ;
    }
	

	public void getPricesByBrandProductFechaAplicacion(DeferredResult<ResponseEntity<?>> deferred, PriceRequest request) throws InterruptedException, ExecutionException {
		priceRepositoryRxJ.asyncFindByBrandProductFechaAplicacion(request.getBrandId(), request.getProductId(), request.getFechaAplicacion())
			.whenComplete((res, err) -> {
				if (err != null) {
					logger.error("Error getting List of Prices for request: {}", request);
					throw new AsyncMethodErrorException("Error getting List of Prices for request: " + request.toJson());
				} else {
					List<PriceResponse> priceDto = res.stream().map(PriceResponse::new).map(d -> {
						logger.debug("priceDto: {}", d);
						return d;
					}).collect(Collectors.toList());
					ResponseEntity<List<PriceResponse>> responseEntity = new ResponseEntity<>(priceDto, HttpStatus.OK);
					deferred.setResult(responseEntity);
				}

			});
	}
	
	public void getCurrentPriceByBrandProductFechaAplicacion(DeferredResult<ResponseEntity<?>> deferred, PriceRequest request) throws InterruptedException, ExecutionException {
		priceRepositoryRxJ.asyncFindCurrentByBrandProductFechaAplicacion(request.getBrandId(), request.getProductId(), request.getFechaAplicacion())
			.whenComplete((res, err) -> {
				if (err != null) {
					logger.error("Error getting List of Prices for request: {}", request);
					throw new AsyncMethodErrorException("Error getting List of Prices for request: " + request.toJson());
				} else {
					PriceResponse priceDto = new PriceResponse(res);
					logger.debug("priceDto: {}", priceDto);
					ResponseEntity<PriceResponse> responseEntity = new ResponseEntity<>(priceDto, HttpStatus.OK);
					deferred.setResult(responseEntity);
				}
			});
	}
	
	@Async
	public void updatePrice(PriceUpdate priceToUpdate) {
		Price price = priceToUpdate.toDb();
		CompletableFuture<Price> futureTask = CompletableFuture.supplyAsync(() -> {
			return update(price).doOnComplete(() -> {
				logger.debug("Correctly updated price in db: {}", priceToUpdate);
			}).blockingFirst();
		});
		futureTask.whenComplete((res, ex) -> {
			if (ex != null) {
				logger.error("Error updating Price {}: {}", priceToUpdate, ToStringBuilder.reflectionToString(ex, ToStringStyle.DEFAULT_STYLE));
			} else {
				logger.debug("Correctly Updated Price in db: {}", res);
			}
		});
	}
    
}
