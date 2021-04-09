import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.products.BarcodedProduct;

public class RemovePurchasedItemsTest {
	SelfCheckoutStation station;
	BarcodedItem barcodedItem;
	Map<Barcode, BarcodedProduct> database;
	ScanItem scanner;
	BaggingArea bags;
	GiveChange transaction;
	PayWithBanknote banknotepayment;
	Banknote banknote;
	Coin coin;
	PayWithCoin coinpayment;
	RemovePurchasedItems removed;
	Item i;

	@Before
	public void setUp() throws Exception {
		//Creates a self checkout station
		Currency currency = Currency.getInstance("CAD");
		int[] noteDenominations = {5,10,20,50,100};
		BigDecimal[] coinDenomonations = {BigDecimal.valueOf(0.05) , BigDecimal.valueOf(0.10), BigDecimal.valueOf(0.25), 
				BigDecimal.valueOf(1.0), BigDecimal.valueOf(2.0)};
		int maxWeight = 23000;
		int scaleSensitivity = 10;
		station = new SelfCheckoutStation(currency, noteDenominations, coinDenomonations, maxWeight,scaleSensitivity);
		
		//Creates a barcoded item
		Barcode barcode = new Barcode("1");
		barcodedItem = new BarcodedItem(barcode, 50);
		BarcodedProduct product = new BarcodedProduct(barcode, "a barcoded item", BigDecimal.valueOf(5.25));
		
		//create database
		database = new HashMap<>();
		database.put(barcode, product);
		
		//create scanner
		scanner = new ScanItem(station, database);
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("1");
		
		// Scan the item that costs $5.25
		scanner.scanFromMain(barcodedItem);

		//create bagging area
		bags = new BaggingArea(station);

		
		//no more items will be added and we are finished
		FinishesAddingItems finished = new FinishesAddingItems(station, scanner, bags);
		
		//payment with banknote and coin 
		int value = 5;
		banknote = new Banknote(value, currency);
		PayWithBanknote banknotepayment = new PayWithBanknote(station);
		banknotepayment.pay(banknote);
		
		coin = new Coin(BigDecimal.valueOf(0.25), currency);
		PayWithCoin coinpayment = new PayWithCoin(station);
		coinpayment.pay(coin);
		
		//Give change 
		transaction = new GiveChange(station, currency, finished.getPrice(), banknotepayment, coinpayment);
		removed = new RemovePurchasedItems(station, scanner, bags, transaction);

	}

	@After
	public void tearDown() throws Exception {
		station = null;
	}
	
	@Test //test the constructor
	public void testRemovePurchasedItems() {
		//the constructor should enable the scanner and bagging area
		RemovePurchasedItems removed = new RemovePurchasedItems(station, scanner, bags, transaction);
		
		
		try { //station should not be null
			RemovePurchasedItems removed1 = new RemovePurchasedItems(null, scanner, bags, transaction);
			fail();
		}catch(Exception e) {
			assertTrue(e instanceof SimulationException);
		}
		
		try { //scanner should not be null
			RemovePurchasedItems removed3 = new RemovePurchasedItems(station, null, bags, transaction);
			fail();
		}catch(Exception e) {
			assertTrue(e instanceof SimulationException);
		}
		
		try { //bagging area should not be null
			RemovePurchasedItems removed3 = new RemovePurchasedItems(station, scanner, null, transaction);
			fail();
		}catch(Exception e) {
			assertTrue(e instanceof SimulationException);
		}
		try { //transaction should not be null
			RemovePurchasedItems removed4 = new RemovePurchasedItems(station, scanner, bags, null);
			fail();
		}catch(Exception e) {
			assertTrue(e instanceof SimulationException);
		}
		
	}
	
	@Test 
	public void getIsEnabledTest() {

		assertEquals(true, removed.getIsEnabled());
	}
	
	@Test(expected = SimulationException.class)
	public void itemsNotTakenTest() throws DisabledException {
		bags.addItem(i); // I can only add an item, not a barcoded item? 
		bags.setWeightBaggingArea(50.00); // Should we have to use the baggingarea.setweight funtion to set the weight to 50.00?
		assertEquals(false, removed.getItemsTaken());
	}
	
	@Test
	public void itemsTakenTest() {
		bags.setWeightBaggingArea(0.00);
		System.out.println(bags.getWeightBaggingArea());
		assertEquals(true, removed.getItemsTaken());
	}
	

}
