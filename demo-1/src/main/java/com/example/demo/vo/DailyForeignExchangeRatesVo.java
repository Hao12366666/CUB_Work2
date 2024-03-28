package com.example.demo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DailyForeignExchangeRatesVo {
	@JsonProperty("Date")
	private String date;
	@JsonProperty("USD/NTD")
	private String usdToNtd;
	@JsonProperty("RMB/NTD")
	private String rmbToNtd;
	@JsonProperty("EUR/USD")
	private String eurToUsd;
	@JsonProperty("USD/JPY")
	private String usdToJpy;
	@JsonProperty("GBP/USD")
	private String gbpToUsd;
	@JsonProperty("AUD/USD")
	private String audToUsd;
	@JsonProperty("USD/HKD")
	private String usdToHkd;
	@JsonProperty("USD/RMB")
	private String usdToRmb;
	@JsonProperty("USD/ZAR")
	private String usdToZar;
	@JsonProperty("NZD/USD")
	private String nzdToUsd;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUsdToNtd() {
		return usdToNtd;
	}

	public void setUsdToNtd(String usdToNtd) {
		this.usdToNtd = usdToNtd;
	}

	public String getRmbToNtd() {
		return rmbToNtd;
	}

	public void setRmbToNtd(String rmbToNtd) {
		this.rmbToNtd = rmbToNtd;
	}

	public String getEurToUsd() {
		return eurToUsd;
	}

	public void setEurToUsd(String eurToUsd) {
		this.eurToUsd = eurToUsd;
	}

	public String getUsdToJpy() {
		return usdToJpy;
	}

	public void setUsdToJpy(String usdToJpy) {
		this.usdToJpy = usdToJpy;
	}

	public String getGbpToUsd() {
		return gbpToUsd;
	}

	public void setGbpToUsd(String gbpToUsd) {
		this.gbpToUsd = gbpToUsd;
	}

	public String getAudToUsd() {
		return audToUsd;
	}

	public void setAudToUsd(String audToUsd) {
		this.audToUsd = audToUsd;
	}

	public String getUsdToHkd() {
		return usdToHkd;
	}

	public void setUsdToHkd(String usdToHkd) {
		this.usdToHkd = usdToHkd;
	}

	public String getUsdToRmb() {
		return usdToRmb;
	}

	public void setUsdToRmb(String usdToRmb) {
		this.usdToRmb = usdToRmb;
	}

	public String getUsdToZar() {
		return usdToZar;
	}

	public void setUsdToZar(String usdToZar) {
		this.usdToZar = usdToZar;
	}

	public String getNzdToUsd() {
		return nzdToUsd;
	}

	public void setNzdToUsd(String nzdToUsd) {
		this.nzdToUsd = nzdToUsd;
	}

}
