package de.hska.iwii.stockquotes.model;

import java.util.Date;


import de.hska.iwii.stockquotes.net.StockData.CurrentPriceChange;

public class TableData {
	private String _companyShortName;
	private String _companyName;
	private Double _currentPrice;
	private Double _dayHighPrice;
	private Double _dayLowPrice;
	private Date _date;
	private CurrentPriceChange _change;

	public TableData(String companyShortName, String companyName, Double currentPrice, Double dayHighPrice,
			Double dayLowPrice, Date date, CurrentPriceChange change) {
		_companyShortName = companyShortName;
		_companyName = companyName;
		_currentPrice = currentPrice;
		_dayHighPrice = dayHighPrice;
		_dayLowPrice = dayLowPrice;
		_date = date;
		_change = change;
	}

	public String getCompanyShortName() {
		return _companyShortName;
	}

	public void setCompanyShortName(String companyShortName) {
		this._companyShortName = companyShortName;
	}

	public String getCompanyName() {
		return _companyName;
	}

	public void setCompanyName(String companyName) {
		this._companyName = companyName;
	}

	public Double getCurrentPrice() {
		return _currentPrice;
	}

	public void setCurrentPrice(Double currentPrice) {
		this._currentPrice = currentPrice;
	}

	public Double getDayHighPrice() {
		return _dayHighPrice;
	}

	public void setDayHighPrice(Double dayHighPrice) {
		this._dayHighPrice = dayHighPrice;
	}

	public Double getDayLowPrice() {
		return _dayLowPrice;
	}

	public void setDayLowPrice(Double dayLowPrice) {
		this._dayLowPrice = dayLowPrice;
	}

	public Date getDate() {
		return _date;
	}

	public void setDate(Date date) {
		this._date = date;
	}
	
	public CurrentPriceChange getCurrentPriceChange() {
		return this._change;
	}
}
