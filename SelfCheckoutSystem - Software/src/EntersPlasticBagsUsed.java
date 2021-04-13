import java.util.Scanner;

public class EntersPlasticBagsUsed {
		

	private double pricePerPlasticBag; 
	private int totalPlasticBagsUsed;
	private double totalPlasticBagsUsedPrice;
	
	public EntersPlasticBagsUsed(int bagsUsed) {
		this.pricePerPlasticBag = 0.10; //10 cents per bag
		setTotalPlasticBagsUsed(bagsUsed);
	}
	//sets the number of bags used
	private void setTotalPlasticBagsUsed(int bagsUsed) {
		if(bagsUsed >= 0) {
			this.totalPlasticBagsUsed = bagsUsed;
		}
		//if the inputed number is negative set bags to zero
		else {
			this.totalPlasticBagsUsed = 0;
		}
	}
	
	public int getTotalPlasticBagsUsed() {
		return totalPlasticBagsUsed;
	}
	
	
	/*
	 * This function will return the total charge for this respective # of the plastic bags used by the customer
	 * Use of this --> Increment this amount into the Total Price of all customer items in the GUI
	 */
	public double calculateTotalPlasticBagsUsedPrice() {
	
		totalPlasticBagsUsedPrice = pricePerPlasticBag * this.totalPlasticBagsUsed;
		return totalPlasticBagsUsedPrice;
	
	}

}
