package jpmorgan.stock;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TreeMap;

import org.apache.commons.math3.stat.StatUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Unit tests for {@link VendingMachine}
 */
@FixMethodOrder(MethodSorters.DEFAULT)
public class StockTest {
	
	Stock stock1 = null;
	Stock stock2 = null;
	Stock stock3 = null;
	Stock stock4 = null;
	Stock stock5 = null;
	public ArrayList<Trade> tradeList = new ArrayList<Trade>();
	public TreeMap<Calendar, Trade> tradeMap = new TreeMap<Calendar, Trade>();
	
	@Test
	public void createStock() {
		// Create Stock Object
		stock1 = new Stock("TEA",5.0,0.0,100.0);
		stock2 = new Stock("POP",8.0,0.0,100.0);
		stock3 = new Stock("ALE",23.0,0.0,60.0);
		stock4 = new Stock("GIN",8.0,2.0,100.0);
		stock5 = new Stock("JOE",13.0,0.0,250.0);
		assertNotNull(stock1);
		assertNotNull(stock2);
		assertNotNull(stock3);
		assertNotNull(stock4);
		assertNotNull(stock5);
	}
	
	@Test
	public void calculateDividend() {
		createStock();
		assertNotSame(0.0, stock1.calculate(45.0,stock1,"dividend"));
		assertNotSame(0.0, stock4.calculate(45.0,stock4,"dividend"));
	}
	
	@Test
	public void calculatePERatio() {
		createStock();
		assertNotSame(0.0, stock1.calculate(45.0,stock1,"peRatio"));
		assertNotSame(0.0, stock4.calculate(45.0,stock4,"peRatio"));
	}
	
	@Test
	public void calculateGeometricMean() {
		createStock();
		double[] stockPrices = new double[StockNames.values().length];
		
		stockPrices[0] = stock1.getStockValue();
		stockPrices[1] = stock2.getStockValue();
		stockPrices[2] = stock3.getStockValue();
		stockPrices[3] = stock4.getStockValue();
		stockPrices[4] = stock5.getStockValue();
		
		assertNotSame(0.0, StatUtils.geometricMean(stockPrices));
	}
	
	@Test
	public void calculateVolumeWeightedStockPrice() {
		createStock();
		
		stock1.doTrade(stock1, TradeMethod.BUY.toString());
		stock2.doTrade(stock2, TradeMethod.SELL.toString());
		stock3.doTrade(stock3, TradeMethod.BUY.toString());
		stock4.doTrade(stock4, TradeMethod.SELL.toString());
		stock5.doTrade(stock5, TradeMethod.BUY.toString());
		
		assertNotSame(0.0, stock1.calculate(0.0,null,"VolumeWeightedStockPrice"));
		assertNotSame(0.0, stock4.calculate(0.0,null,"VolumeWeightedStockPrice"));
	}
	
}
