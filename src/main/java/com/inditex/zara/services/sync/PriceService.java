package com.inditex.zara.services.sync;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.async.DeferredResult;

import com.inditex.zara.entity.Price;
import com.inditex.zara.model.PriceRequest;
import com.inditex.zara.model.PriceResponse;
import com.inditex.zara.repository.sync.PriceRepository;

import io.reactivex.Observable;

/**
 * Service class for managing prices
 */
@Service
@Transactional
public class PriceService {

	private static Logger logger = LoggerFactory.getLogger(PriceService.class);
	
	private PriceRepository priceRepository;
	
	public PriceService(PriceRepository priceRepository) {
		this.priceRepository = priceRepository;
	}
	
	@Transactional(readOnly = true)
	public List<Price> findByBrandProductFechaAplicacion(PriceRequest request) {
		return priceRepository.findByBrandProductFechaAplicacion(request.getBrandId(), request.getProductId(), request.getFechaAplicacion());
	}
	
	@Transactional(readOnly = true)
	public List<Price> findByBrandProductFechaAplicacion(Integer brandId, Integer productId, Timestamp fechaAplicacion) {
		return priceRepository.findByBrandProductFechaAplicacion(brandId, productId, fechaAplicacion);
	}
	
	/*
	 * Los siguientes métodos permiten recuperar de forma asíncrona las entidades a partir de un repositorio síncrono...
	 * Este código se deja aquí como testimonio de como se puede consultar un repositorio síncrono de forma asíncrona
	 * sin la necesidad de las anotaciones de Spring... 
	 */
	
	
	
	public void getPricesByBrandProductFechaAplicacion(DeferredResult<ResponseEntity<?>> deferred, PriceRequest request)
			throws InterruptedException, ExecutionException {
		CompletableFuture<List<Price>> c = getPricesByBrandProductFechaAplicacionAsync(request.getBrandId(), request.getProductId(), request.getFechaAplicacion());
		c.whenComplete((res, err) -> {
			if (err != null) {
				logger.error("Error getting List of Prices for request: {}", request);
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
	
	public void getCurrentPriceByBrandProductFechaAplicacion(DeferredResult<ResponseEntity<?>> deferred, PriceRequest request)
			throws InterruptedException, ExecutionException {
		CompletableFuture<Price> c = getCurrentPrice(request);
		c.whenComplete((res, err) -> {
			if (err != null) {
				logger.error("Error getting List of Prices for request: {}", request);
			} else {
				PriceResponse priceDto = new PriceResponse(res);
				logger.debug("priceDto: {}", priceDto);
				ResponseEntity<PriceResponse> responseEntity = new ResponseEntity<>(priceDto, HttpStatus.OK);
				deferred.setResult(responseEntity);
			}

		});
	}
	
	
	/**
	 * 	Gets the list of Prices associated with the brandId, productId and fechaAplicacion
	 * @param brandId - Integer id identifying the brand of the Price {@link Price}
	 * @param productId - Integer id identifying the product of the Price {@link Price}
	 * @param fechaAplicacion - Timestamp for range search between startDate nad endDate of the Price {@link Price}
	 * @return CompletableFuture that holds the list of the Price asociated with passed params
	 */
	@Async
	private CompletableFuture<List<Price>> getPricesByBrandProductFechaAplicacionAsync(Integer brandId, Integer productId, Timestamp fechaAplicacion) {
		return CompletableFuture.supplyAsync(() -> {
			return findByBrandProductFechaAplicacionAsync(brandId, productId, fechaAplicacion).doOnComplete(() -> {
				logger.info("Obtained List of prices for brand[{}], product[{}] and fechaAplicacion[{}]", brandId, productId, fechaAplicacion);
			}).doOnError(t -> {
				logger.error("Error {} getting Async List of prices for for brand[{}], product[{}] and fechaAplicacion[{}]", brandId, productId, fechaAplicacion);
			}).blockingFirst();
		});
	}

	/**
	 * Find the List of the Prices associated by the brandId, productId of  Price and fechaAplicacion
	 * @param brandId - Integer id identifying the brand of the Price {@link Price}
	 * @param productId - Integer id identifying the product of the Price {@link Price}
	 * @param fechaAplicacion - Timestamp for range search between startDate nad endDate of the Price {@link Price}
	 * @return - Observable that will receive completion, or exception if error occurs.
	 */
    @Transactional(readOnly = true)
    private Observable<List<Price>> findByBrandProductFechaAplicacionAsync(Integer brandId, Integer productId, Timestamp fechaAplicacion) {
        return Observable.create(inSource -> {
            try {
                final List<Price> theEntitiesList = StreamSupport.stream(priceRepository.findByBrandProductFechaAplicacion(brandId, productId, fechaAplicacion).spliterator(), false)
                    .collect(Collectors.toList());
                inSource.onNext(theEntitiesList);
                inSource.onComplete();
            } catch (final Exception theException) {
                inSource.onError(theException);
            }
        });
    }
    

	
	@Async
	private CompletableFuture<Price> getCurrentPrice(PriceRequest request) {
		return CompletableFuture.supplyAsync(() -> {
			return findByPriceRequestMaxPriority(request).doOnComplete(() -> {
				logger.info("Obtained Price for priceRequest: {}", request);
			}).doOnError(t -> {
				logger.error("Error {} getting Async List of Prices for request: {}", t, request);
			}).blockingFirst();
		});
	}

	
	@Transactional(readOnly = true)
	private Observable<Price> findByPriceRequestMaxPriority(PriceRequest request) {
		return Observable.create(inSource -> {
			try {
				final Price theEntitie = priceRepository.findCurrentByBrandProductFechaAplicacion(request.getBrandId(),
								request.getProductId(), request.getFechaAplicacion());
				inSource.onNext(theEntitie);
				inSource.onComplete();
			} catch (final Exception theException) {
				inSource.onError(theException);
			}
		});
	}
		
}
