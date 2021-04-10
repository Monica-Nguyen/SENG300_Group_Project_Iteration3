import org.lsmr.selfcheckout.devices.*;
import org.lsmr.selfcheckout.devices.listeners.*;
import java.util.ArrayList;
import org.lsmr.selfcheckout.*;

public class ReturnsToAddingItems {
	
	public SelfCheckoutStation aSelfCheckoutStation;
	public boolean isScanned;
	public boolean isEnabled;
	
	 private BarcodeScannerListener bsl = new BarcodeScannerListener() {
		 
	        @Override
	        public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {
	        }

	        @Override
	        public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {
	        }
		 
	        @Override
	        public void barcodeScanned(BarcodeScanner barcodeScanner, Barcode barcode) {
	            // TODO Auto-generated method stub
	        	isScanned = true;  	    
	        }
	    };
	
	public void ReturnsToAddingItems(SelfCheckoutStation station) {
		
		if(aStation==null) {
    			throw new SimulationException(new NullPointerException("ERROR! No station to work with"));
		}
    	
    		aSelfCheckoutStation=station;
		
    		aSelfCheckoutStation.mainScanner.register(bsl);
    		aSelfCheckoutStation.handheldScanner.register(bsl);
		
		aSelfCheckoutStation.mainScanner.enable();
    		aSelfCheckoutStation.handheldScanner.enable();
    		aSelfCheckoutStation.baggingArea.enable();
    		aSelfCheckoutStation.scale.enable();
    
    }
	   	    	
}
