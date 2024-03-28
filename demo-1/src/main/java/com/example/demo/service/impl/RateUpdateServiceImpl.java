package com.example.demo.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.repo.RateRepo;
import com.example.demo.service.RateUpdateService;
import com.example.demo.vo.DailyForeignExchangeRatesVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class RateUpdateServiceImpl implements RateUpdateService {

	@Autowired
	RateRepo rateRepo;

	@Override
	public void mainService() {
		// TODO Auto-generated method stub

		String message = "";
		try {

			disableSSLVerification();
			StringBuilder res = new StringBuilder();
			InputStream inputStream = null;
			InputStreamReader inputStreamReader = null;
			BufferedReader bufferedReader = null;
			URL url = new URL("https://openapi.taifex.com.tw/v1/DailyForeignExchangeRates");
			inputStream = url.openStream();
			inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			bufferedReader = new BufferedReader(inputStreamReader);
			while ((message = bufferedReader.readLine()) != null) {
				res.append(message);
			}
			bufferedReader.close();
			String json = res.toString();
			ObjectMapper objectMapper = new ObjectMapper();
			List<DailyForeignExchangeRatesVo> exchangeRates = objectMapper.readValue(json,
					new com.fasterxml.jackson.core.type.TypeReference<List<DailyForeignExchangeRatesVo>>() {
					});
			DailyForeignExchangeRatesVo exchangeRate = exchangeRates.get(exchangeRates.size() - 1);
			rateRepo.updateDateRate(exchangeRate);

		} catch (KeyManagementException |

				NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void disableSSLVerification() throws NoSuchAlgorithmException, KeyManagementException {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		// 禁用主機名驗證
		HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
	}
}
