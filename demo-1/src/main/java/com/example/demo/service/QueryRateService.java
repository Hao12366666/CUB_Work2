package com.example.demo.service;

import java.util.Map;

import com.example.demo.req.QueryRateReq;

public interface QueryRateService {
	public Map<String, Object> mainService(QueryRateReq queryRateReq);
}
