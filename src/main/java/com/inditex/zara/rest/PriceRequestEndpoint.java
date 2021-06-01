package com.inditex.zara.rest;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.inditex.zara.model.PriceRequest;
import com.inditex.zara.services.async.ZaraAsyncPriceService;

@RestController
@RequestMapping("/api/prices")
public class PriceRequestEndpoint {

	private static Logger logger = LoggerFactory.getLogger(PriceRequestEndpoint.class);

	@Autowired
	ZaraAsyncPriceService priceService;

	@RequestMapping(value = "/getPrices", method = RequestMethod.GET)
	public @ResponseBody DeferredResult<ResponseEntity<?>> getPrices(Integer productId, Integer brandId, Long fechaAplicacion)  throws InterruptedException, ExecutionException, UnsupportedEncodingException {
		PriceRequest request = new PriceRequest(fechaAplicacion, productId, brandId);
	    logger.info("GOT PRICE-REQUEST: {}", request);
	    request.validateRequest();

	    logger.debug("Started Asynchronous request for Price @ {}", new Date().toString());

	    DeferredResult<ResponseEntity<?>> deferred = new DeferredResult<ResponseEntity<?>>();

	    priceService.getPricesByBrandProductFechaAplicacion(deferred, request);
	    logger.debug("{} -> Freeing Request Thread for Price Request:  {}", new Date().toString(), request);
	    return deferred;
	  }
	
	@RequestMapping(value = "/getCurrentPrice", method = RequestMethod.GET)
	public @ResponseBody DeferredResult<ResponseEntity<?>> getCurrentPrice(Integer productId, Integer brandId, Long fechaAplicacion)  throws InterruptedException, ExecutionException, UnsupportedEncodingException {
		PriceRequest request = new PriceRequest(fechaAplicacion, productId, brandId);
	    logger.info("GOT PRICE-REQUEST: {}", request);
	    request.validateRequest();

	    logger.debug("Started Asynchronous request for Price @ {}", new Date().toString());

	    DeferredResult<ResponseEntity<?>> deferred = new DeferredResult<ResponseEntity<?>>();

	    priceService.getCurrentPriceByBrandProductFechaAplicacion(deferred, request);
	    logger.debug("{} -> Freeing Request Thread for Price Request:  {}", new Date().toString(), request);
	    return deferred;
	  }
	
}
