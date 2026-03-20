import java.io.IOException;

import swiftbot.SwiftBotAPI;

public class Range implements Runnable {
    double r;
    
    Range(double range) {
        this.r = range;
    }

    @Override
    public void run() {
        testUltrasound(r);
    }

    public static void testUltrasound(double range) {
        double distanceToObject = 0.0;
        System.out.println("Turning on Ultrasound...");
      
            while(true) {
            	 try {
            	        distanceToObject = SwiftBotAPI.INSTANCE.useUltrasound();
            	        if(distanceToObject <= range) {
            	            Assignment.objectDetected = true;
            	            break;
            	        }
            	        Thread.sleep(100);

            	    } catch(InterruptedException e) {
            	        Thread.currentThread().interrupt();
            	        break;
            	    } catch(Exception f) {
            	        try {
            	            Thread.sleep(200);
            	        } catch(InterruptedException e) {
            	            Thread.currentThread().interrupt();
            	            break;
            	        }
            	    }
}
    }
}