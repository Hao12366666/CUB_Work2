package com.example.demo.res;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "responseHeader", "resBody" })
public class QueryRateRes {

	private List<error> error; // 訊息狀態
	private List<currency> currency; // 幣值

	public List<error> getError() {
		return error;
	}

	public void setError(List<error> error) {
		this.error = error;
	}

	public List<currency> getCurrency() {
		return currency;
	}

	public void setCurrency(List<currency> currency) {
		this.currency = currency;
	}

	public static class error {
		private String code; // 狀態碼
		private String message; // 狀態訊息

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

	}

	public static class currency {
		private String date; // 日期
		private String Rate; // 幣值

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getRate() {
			return Rate;
		}

		public void setRate(String rate) {
			Rate = rate;
		}
	}
}
