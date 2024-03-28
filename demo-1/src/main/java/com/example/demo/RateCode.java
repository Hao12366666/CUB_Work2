package com.example.demo;

import org.springframework.beans.factory.annotation.Value;

public class RateCode {

	@Value("${mongodb.uri}")
	public final static String mongoUri = "";

	/** 成功 **/
	public final static String SUCCESS_STATUS_CODE = "0000";

	/** 日期區間不符 **/
	public final static String DATE_ERROR = "E001";

	/** 查無資料 **/
	public final static String RATE_ERROR = "E002";

}
