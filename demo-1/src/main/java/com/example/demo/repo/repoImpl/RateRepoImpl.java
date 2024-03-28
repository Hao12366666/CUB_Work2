package com.example.demo.repo.repoImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.repo.RateRepo;
import com.example.demo.req.QueryRateReq;
import com.example.demo.res.QueryRateRes;
import com.example.demo.res.QueryRateRes.currency;
import com.example.demo.vo.DailyForeignExchangeRatesVo;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Repository
public class RateRepoImpl implements RateRepo {

	@Autowired
	private MongoClient mongoClient;

	@Override
	public QueryRateRes queryRate(QueryRateReq queryRateReq) {

		QueryRateRes queryRateRes = new QueryRateRes();
		MongoDatabase database = mongoClient.getDatabase("rate");
		MongoCollection<Document> collection = database.getCollection("rate");
		try {

			// 所需日期轉換格式
			// 輸入時的格式
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			// 查詢時的格式
			SimpleDateFormat queryFormatter = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 輸出時的格式
			SimpleDateFormat formatterRes = new SimpleDateFormat("yyyyMMdd");
			Date startDate, endDate;
			startDate = formatter.parse(queryRateReq.getStartDate());
			endDate = formatter.parse(queryRateReq.getEndDate());

			// 創建一個Calendar實例，並設置為結束日期
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endDate);
			// 增加一天
			calendar.add(Calendar.DATE, 1);
			// 將結果設置回endDate
			endDate = calendar.getTime();

			String startDateFormatted = queryFormatter.format(startDate);
			String endDateFormatted = queryFormatter.format(endDate);
			// 將結束日期加上一天
			String rate = "";
			if ("USD".equals(queryRateReq.getCurrency().toUpperCase())) {
				rate = "USD/NTD";
			} else if ("RMB".equals(queryRateReq.getCurrency().toUpperCase())) {
				rate = "RMB/NTD";
			} else {
				return queryRateRes;
			}
			// 建立查詢條件
			Document query = new Document("Date",
					new Document("$gte", startDateFormatted).append("$lte", endDateFormatted))
					.append(rate, new Document("$ne", null));

			// 執行查詢
			FindIterable<Document> result = collection.find(query);

			List<currency> currencyList = new ArrayList<>();
			Date date = null;
			// 輸出結果
			for (Document doc : result) {
				currency currency = new currency();
				date = inputFormat.parse(doc.getString("Date"));
				currency.setDate(formatterRes.format(date));
				if ("USD".equals(queryRateReq.getCurrency().toUpperCase())) {
					currency.setRate(doc.getString("USD/NTD"));
				} else if ("RMB".equals(queryRateReq.getCurrency().toUpperCase())) {
					currency.setRate(doc.getString("RMB/NTD"));
				}
				currencyList.add(currency);
			}
			queryRateRes.setCurrency(currencyList);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryRateRes;
	}

	@Override
	public void updateDateRate(DailyForeignExchangeRatesVo dailyForeignExchangeRatesVo) {

		// 選擇要使用的資料庫
		MongoDatabase database = mongoClient.getDatabase("rate");
		// 選擇要使用的集合
		MongoCollection<Document> collection = database.getCollection("rate");

		SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		try {
			date = inputDateFormat.parse(dailyForeignExchangeRatesVo.getDate());
			String formattedDate = outputDateFormat.format(date);

			// 檢查是否存在相同日期的文件，如果存在，則刪除該文件
			collection.deleteMany(new Document("Date", formattedDate));
			// 新增要插入的資料
			Document newDocument = new Document("Date", formattedDate)
					.append("USD/NTD", dailyForeignExchangeRatesVo.getUsdToNtd())
					.append("RMB/NTD", dailyForeignExchangeRatesVo.getRmbToNtd())
					.append("EUR/USD", dailyForeignExchangeRatesVo.getEurToUsd())
					.append("USD/JPY", dailyForeignExchangeRatesVo.getUsdToJpy())
					.append("GBP/USD", dailyForeignExchangeRatesVo.getUsdToJpy())
					.append("AUD/USD", dailyForeignExchangeRatesVo.getUsdToJpy())
					.append("USD/HKD", dailyForeignExchangeRatesVo.getUsdToJpy())
					.append("USD/RMB", dailyForeignExchangeRatesVo.getUsdToJpy())
					.append("USD/ZAR", dailyForeignExchangeRatesVo.getUsdToJpy())
					.append("NZD/USD", dailyForeignExchangeRatesVo.getUsdToJpy());

			// 插入一筆新資料
			collection.insertOne(newDocument);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

}
