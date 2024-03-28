package com.example.demo.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.example.demo.RateCode;
import com.example.demo.repo.RateRepo;
import com.example.demo.req.QueryRateReq;
import com.example.demo.res.QueryRateRes;
import com.example.demo.res.QueryRateRes.currency;
import com.example.demo.res.QueryRateRes.error;
import com.example.demo.service.QueryRateService;

@Service
public class QueryRateServiceImpl implements QueryRateService {

	@Autowired
	RateRepo rateRepo;

	@Autowired
	private MessageSource messageSource;

	@Override
	public Map<String, Object> mainService(QueryRateReq queryRateReq) {

		Map<String, Object> response = new LinkedHashMap<>();
		error error = new error();
		if (!checkDate(queryRateReq)) {
			error.setCode(RateCode.DATE_ERROR);
			error.setMessage(getMessage(RateCode.DATE_ERROR));
			response.put("error", error);
			return response;
		}

		QueryRateRes queryRateRes = rateRepo.queryRate(queryRateReq);
		if (queryRateRes == null || queryRateRes.getCurrency() == null || queryRateRes.getCurrency().size() == 0) {
			error.setCode(RateCode.RATE_ERROR);
			error.setMessage(getMessage(RateCode.RATE_ERROR));
			response.put("error", error);
		} else {
			error.setCode(RateCode.SUCCESS_STATUS_CODE);
			error.setMessage(getMessage(RateCode.SUCCESS_STATUS_CODE));
			response.put("error", error);

			List<Map<String, String>> modifiedCurrencyList = new ArrayList<>();
			for (currency currency : queryRateRes.getCurrency()) {
				Map<String, String> modifiedCurrency = new LinkedHashMap<>();
				modifiedCurrency.put("date", currency.getDate());
				modifiedCurrency.put(queryRateReq.getCurrency(), currency.getRate());
				modifiedCurrencyList.add(modifiedCurrency);
			}
			response.put("currency", modifiedCurrencyList);

		}
		return response;
	}

	public Boolean checkDate(QueryRateReq queryRateReq) {

		try {
			// 設定日期格式
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

			// 解析開始日期與結束日期
			LocalDate startDate = LocalDate.parse(queryRateReq.getStartDate(), formatter);
			LocalDate endDate = LocalDate.parse(queryRateReq.getEndDate(), formatter);

			// 取得今天的日期
			LocalDate today = LocalDate.now();

			// 檢核日期是否在一年內到今天的前一天之間
			LocalDate oneYearAgo = today.minusYears(1);
			LocalDate yesterday = today.minusDays(1);

			if ((startDate.isEqual(endDate) || startDate.isBefore(endDate))
					&& (startDate.isEqual(oneYearAgo) || startDate.isAfter(oneYearAgo))
					&& (endDate.isBefore(yesterday) || endDate.isEqual(yesterday))) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

	}

	public String getMessage(String code) {
		return messageSource.getMessage(code, null, null);
	}

}
