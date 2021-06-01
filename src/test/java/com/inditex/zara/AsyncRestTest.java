package com.inditex.zara;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Timestamp;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class AsyncRestTest {

	private static Logger log = LoggerFactory.getLogger(AsyncRestTest.class);
	private static final String URL_HOST = "http://localhost:8080/";
	private static final String TEST1_DATE = "2020-06-14 10:00:00";
	private static final String TEST2_DATE = "2020-06-14 16:00:00";
	private static final String TEST3_DATE = "2020-06-14 21:00:00";
	private static final String TEST4_DATE = "2020-06-15 10:00:00";
	private static final String TEST5_DATE = "2020-06-16 21:00:00";

	private MockMvc mvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	private String getCurrentTimestampFromDateString(String fecha) {
		Timestamp tmstmp = Timestamp.valueOf(fecha);
		return "" + tmstmp.getTime();
	};

	@Test
	public void test1() {
		HttpClient httpClient = HttpClientBuilder.create().build();
		String sFechaAplicacion = getCurrentTimestampFromDateString(TEST1_DATE);
		log.info("TEST-1 TIMESTAMP: " + sFechaAplicacion);
		try {
			URIBuilder builder = new URIBuilder(URL_HOST + "demo/api/prices/getCurrentPrice");
			builder.setParameter("productId", "35455").setParameter("brandId", "1")
				.setParameter("fechaAplicacion", sFechaAplicacion);

			HttpGet validateRequest = new HttpGet(builder.build());
			HttpResponse response = httpClient.execute(validateRequest);
			assertEquals(200, response.getStatusLine().getStatusCode());
			String responseString = new BasicResponseHandler().handleResponse(response);
			JSONObject validateResponse = new JSONObject(responseString);
			assertEquals(35455, validateResponse.get("productId"));
			assertEquals(1, validateResponse.get("brandId"));
			assertEquals(1, validateResponse.get("priceList"));
			assertEquals(35.5, validateResponse.get("price"));
			log.info("TEST-1 CURRENT PRICE: " + validateResponse.get("price"));
		} catch (Exception ex) {
			ex.printStackTrace();
			fail(ex.getMessage());
		} finally {
			// Deprecated
			// httpClient.getConnectionManager().shutdown();
		}
	}

	@Test
	public void test2() {
		HttpClient httpClient = HttpClientBuilder.create().build();
		String sFechaAplicacion = getCurrentTimestampFromDateString(TEST2_DATE);
		log.info("TEST-2 TIMESTAMP: " + sFechaAplicacion);
		try {
			URIBuilder builder = new URIBuilder(URL_HOST + "demo/api/prices/getCurrentPrice");
			builder.setParameter("productId", "35455").setParameter("brandId", "1").setParameter("fechaAplicacion",
					sFechaAplicacion);

			HttpGet validateRequest = new HttpGet(builder.build());
			HttpResponse response = httpClient.execute(validateRequest);
			assertEquals(200, response.getStatusLine().getStatusCode());
			String responseString = new BasicResponseHandler().handleResponse(response);
			JSONObject validateResponse = new JSONObject(responseString);
			assertEquals(35455, validateResponse.get("productId"));
			assertEquals(1, validateResponse.get("brandId"));
			assertEquals(2, validateResponse.get("priceList"));
			assertEquals(25.45, validateResponse.get("price"));
			log.info("TEST-2 CURRENT PRICE: " + validateResponse.get("price"));
		} catch (Exception ex) {
			ex.printStackTrace();
			fail(ex.getMessage());
		} finally {
			// Deprecated
			// httpClient.getConnectionManager().shutdown();
		}
	}

	@Test
	public void test3() {
		HttpClient httpClient = HttpClientBuilder.create().build();
		String sFechaAplicacion = getCurrentTimestampFromDateString(TEST3_DATE);
		log.info("TEST-3 TIMESTAMP: " + sFechaAplicacion);
		try {
			URIBuilder builder = new URIBuilder(URL_HOST + "demo/api/prices/getCurrentPrice");
			builder.setParameter("productId", "35455").setParameter("brandId", "1").setParameter("fechaAplicacion",
					sFechaAplicacion);

			HttpGet validateRequest = new HttpGet(builder.build());
			HttpResponse response = httpClient.execute(validateRequest);
			assertEquals(200, response.getStatusLine().getStatusCode());
			String responseString = new BasicResponseHandler().handleResponse(response);
			JSONObject validateResponse = new JSONObject(responseString);
			assertEquals(35455, validateResponse.get("productId"));
			assertEquals(1, validateResponse.get("brandId"));
			assertEquals(1, validateResponse.get("priceList"));
			assertEquals(35.5, validateResponse.get("price"));
			log.info("TEST-3 CURRENT PRICE: " + validateResponse.get("price"));
		} catch (Exception ex) {
			ex.printStackTrace();
			fail(ex.getMessage());
		} finally {
			// Deprecated
			// httpClient.getConnectionManager().shutdown();
		}
	}

	@Test
	public void test4() {
		HttpClient httpClient = HttpClientBuilder.create().build();
		String sFechaAplicacion = getCurrentTimestampFromDateString(TEST4_DATE);
		log.info("TEST-4 TIMESTAMP: " + sFechaAplicacion);
		try {
			URIBuilder builder = new URIBuilder(URL_HOST + "demo/api/prices/getCurrentPrice");
			builder.setParameter("productId", "35455").setParameter("brandId", "1").setParameter("fechaAplicacion",
					sFechaAplicacion);

			HttpGet validateRequest = new HttpGet(builder.build());
			HttpResponse response = httpClient.execute(validateRequest);
			assertEquals(200, response.getStatusLine().getStatusCode());
			String responseString = new BasicResponseHandler().handleResponse(response);
			JSONObject validateResponse = new JSONObject(responseString);
			assertEquals(35455, validateResponse.get("productId"));
			assertEquals(1, validateResponse.get("brandId"));
			assertEquals(3, validateResponse.get("priceList"));
			assertEquals(30.5, validateResponse.get("price"));
			log.info("TEST-4 CURRENT PRICE: " + validateResponse.get("price"));
		} catch (Exception ex) {
			ex.printStackTrace();
			fail(ex.getMessage());
		} finally {
			// Deprecated
			// httpClient.getConnectionManager().shutdown();
		}
	}

	@Test
	public void test5() {
		HttpClient httpClient = HttpClientBuilder.create().build();
		String sFechaAplicacion = getCurrentTimestampFromDateString(TEST5_DATE);
		log.info("TEST-5 TIMESTAMP: " + sFechaAplicacion);
		try {
			URIBuilder builder = new URIBuilder(URL_HOST + "demo/api/prices/getCurrentPrice");
			builder.setParameter("productId", "35455").setParameter("brandId", "1").setParameter("fechaAplicacion",
					sFechaAplicacion);

			HttpGet validateRequest = new HttpGet(builder.build());
			HttpResponse response = httpClient.execute(validateRequest);
			assertEquals(200, response.getStatusLine().getStatusCode());
			String responseString = new BasicResponseHandler().handleResponse(response);
			JSONObject validateResponse = new JSONObject(responseString);
			assertEquals(35455, validateResponse.get("productId"));
			assertEquals(1, validateResponse.get("brandId"));
			assertEquals(4, validateResponse.get("priceList"));
			assertEquals(38.95, validateResponse.get("price"));
			log.info("TEST-5 CURRENT PRICE: " + validateResponse.get("price"));
		} catch (Exception ex) {
			ex.printStackTrace();
			fail(ex.getMessage());
		} finally {
			// Deprecated
			// httpClient.getConnectionManager().shutdown();

		}
	}
}
