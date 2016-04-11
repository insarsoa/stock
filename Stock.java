package jpmorgan.stock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.SortedMap;
import java.util.TreeMap;

public class Stock {
	
	String stockName;
	private Double lastDividend = 0.0;
	private Double fixedDividend = 0.0;
	private Double stockValue = 0.0;
	public ArrayList<Trade> tradeList = new ArrayList<Trade>();
	public TreeMap<Calendar, Trade> tradeMap = new TreeMap<Calendar, Trade>();
	
	Stock(String name, Double lastDividend, Double fixedDividend, Double stockValue){
		this.stockName = name;
		this.lastDividend = lastDividend;
		this.fixedDividend = fixedDividend;
		this.stockValue = stockValue;
	}
	
	public StockType getStockType(){
		
		StockType stockType = null;	
	   
		if(this.stockName.equals(StockNames.TEA.toString()) || this.stockName.equals(StockNames.POP.toString()) 
				|| this.stockName.equals(StockNames.ALE.toString()) || this.stockName.equals(StockNames.JOE.toString())){
			stockType = StockType.COMMON;
		}
		
		if(this.stockName.equals(StockNames.GIN.toString())){
			stockType = StockType.PREFERRED;
		}
		return stockType;
	}
	
	public Double calculate(Double price, Stock stock, String action) {
		if(action.equals("dividend")){
		switch(stock.getStockType().toString()) {
			case "COMMON":
				return stock.getLastDividend()/price;
			case "PREFERRED":
				return stock.getFixedDividend()*stock.getStockValue()/price;
			default:
				return 0.0;
		}
		}
		if(action.equals("peRatio")){
			return price/stock.getLastDividend();
		}
		if(action.equals("VolumeWeightedStockPrice")){
			// Date 15 minutes ago
			Calendar startTime = Calendar.getInstance();
			
			startTime.set(Calendar.MINUTE, startTime.get(Calendar.MINUTE) - 15);
			
			// Get trades for the last 15 minutes
			SortedMap<Calendar, Trade> trades = tradeMap.tailMap(startTime);
			Double volumeWeigthedStockPrice = 0.0;
			Integer totalQuantity = 0;
			for (Trade trade: trades.values()) {
				totalQuantity += trade.getQuantity();
				volumeWeigthedStockPrice += trade.getStock().getStockValue() * trade.getQuantity();
			}
			return volumeWeigthedStockPrice/totalQuantity;
		}
		return 0.0;
	}
	
	public void doTrade(Stock stock, String tradeType){
		Calendar now = Calendar.getInstance();
		Trade trade = new Trade();
		trade.setStock(stock);
		trade.setQuantity(5);
		trade.setTimestamp(now.getTime());
		trade.setTradeMethod(tradeType);
		tradeList.add(trade);
		tradeMap.put(now, trade);
	}
	
	public Double getLastDividend() {
		return lastDividend;
	}
	public void setLastDividend(Double lastDividend) {
		this.lastDividend = lastDividend;
	}
	public Double getStockValue() {
		return stockValue;
	}
	public void setStockValue(Double stockValue) {
		this.stockValue = stockValue;
	}
	public Double getFixedDividend() {
		return fixedDividend;
	}
	public void setFixedDividend(Double fixedDividend) {
		this.fixedDividend = fixedDividend;
	}
}
