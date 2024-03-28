package com.example.demo.repo;

import com.example.demo.req.QueryRateReq;
import com.example.demo.res.QueryRateRes;
import com.example.demo.vo.DailyForeignExchangeRatesVo;

public interface RateRepo {
	public void updateDateRate(DailyForeignExchangeRatesVo dailyForeignExchangeRatesVo);

	public QueryRateRes queryRate(QueryRateReq queryRateReq);

}
