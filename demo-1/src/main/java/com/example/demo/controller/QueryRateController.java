package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.req.QueryRateReq;
import com.example.demo.res.QueryRateRes;
import com.example.demo.service.QueryRateService;

@RestController
public class QueryRateController {
	@Autowired
	private QueryRateService queryRateService;

	@RequestMapping(value = "/queryRate", method = RequestMethod.POST)
	public Map<String, Object> queryRate(@RequestBody QueryRateReq request) {
		
		Map<String, Object> queryRateRes = queryRateService.mainService(request);

		return queryRateRes;
	}
}
