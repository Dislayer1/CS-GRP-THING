

import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.Scanner;

import swiftbot.Button;
import swiftbot.SwiftBotAPI;
import swiftbot.Underlight;

public class Assignment {
	// In Assignment.java, add this field:
	static volatile boolean objectDetected;
	static double Larea = 0;
    static String Lshape;
    static String fs;
    static double at;
    static double ts;
    static double range;
    static String res = "Y";
   static ArrayList<String> ValidSquares = new ArrayList<String>();
	static ArrayList<String> ValidTriangles = new ArrayList<String>();	
  
	static SwiftBotAPI swiftBot;
public static void main(String[] args) {
		swiftBot = SwiftBotAPI.INSTANCE;
		Larea = 0;
		Lshape = "";
		fs = "";
		at=0;
		ts = 0;
		
		 
	Scanner scanner = new Scanner(System.in);
	// Welcome Screen
	Writer wri = new Writer(false);
	wri.write();
	String ascii = "=====================================================\r\n"
			+ ".----------------.  .----------------.  .----------------.  .----------------.  .----------------.  .----------------.  .----------------.  .----------------. \r\n"
			+ "| .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |\r\n"
			+ "| |    _______   | || | _____  _____ | || |     _____    | || |  _________   | || |  _________   | || |   ______     | || |     ____     | || |  _________   | |\r\n"
			+ "| |   /  ___  |  | || ||_   _||_   _|| || |    |_   _|   | || | |_   ___  |  | || | |  _   _  |  | || |  |_   _ \\    | || |   .'    `.   | || | |  _   _  |  | |\r\n"
			+ "| |  |  (__ \\_|  | || |  | | /\\ | |  | || |      | |     | || |   | |_  \\_|  | || | |_/ | | \\_|  | || |    | |_) |   | || |  /  .--.  \\  | || | |_/ | | \\_|  | |\r\n"
			+ "| |   '.___`-.   | || |  | |/  \\| |  | || |      | |     | || |   |  _|      | || |     | |      | || |    |  __'.   | || |  | |    | |  | || |     | |      | |\r\n"
			+ "| |  |`\\____) |  | || |  |   /\\   |  | || |     _| |_    | || |  _| |_       | || |    _| |_     | || |   _| |__) |  | || |  \\  `--'  /  | || |    _| |_     | |\r\n"
			+ "| |  |_______.'  | || |  |__/  \\__|  | || |    |_____|   | || | |_____|      | || |   |_____|    | || |  |_______/   | || |   `.____.'   | || |   |_____|    | |\r\n"
			+ "| |              | || |              | || |              | || |              | || |              | || |              | || |              | || |              | |\r\n"
			+ "| '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |\r\n"
			+ "'----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  \r\n"
			+ "                                                                           \r\n"
			+ "                                                                           \r\n"
			+ "                                                                                    \r\n"
			+ "      SwiftBot Shape Drawing System v1.0\r\n"
			+ "===================================================== ";
	System.out.println(ascii);
	/////////////////////////////////////////////////////////////////////////////////////
		
	// Start up screen
		System.out.println("[INFO] System Initialising...\r\n\n"
				+ "[INFO] Motors: OK | Camera: OK | LEDs: OK\r\n"
				+ "\r\n\n");
		try {
			Thread.sleep(1000);
		}catch(Exception e) {
			
		}
				System.out.println("-----------------------------------------------------\r\n"
						+ "SETUP: Detection Range Configuration\r\n"
						+ "-----------------------------------------------------\r\n");
				try {
					Thread.sleep(1000);
				}catch(Exception e) {
					
				}
		
		//////////////////////////////////////////////////////////////////////////////
		
		while(res.equals("Y")) {
		// Detection Range input
		System.out.println("Please enter QR detection range (5 to 20 cm):");
		  
		 try {
			 String input = scanner.nextLine().trim();
		        range = Double.parseDouble(input);
			 if(range < 5 || range > 20) {
					System.out.println("Please enter a valid range");
				}else {
					 
					
					System.out.printf("\n[SUCCESS] Detection range set to %.2f cm\r\n", range);
					try {
						Thread.sleep(500);
					}catch(Exception e) {
						
					}
							System.out.printf("\n[INFO] Proceeding to scan mode...\n", range); 
							try {
								Thread.sleep(500);
							}catch(Exception e) {
								
							}
					// Scanning
					testQRCodeDetection();
					res = cont();
				}
		 }catch(NumberFormatException e) {
			 System.out.println("Invalid range");
			 continue;
		 }
		
	}
		System.out.println("[SUCCESS] All shapes drawn successfully\r\n");
		try {
			Thread.sleep(500);
		}catch(Exception e) {
			
		}
				System.out.println("[INFO] Logging session data...");
	
		Writer ww = new Writer(at/ts,fs, Lshape, Larea, true);
		ww.write();
		Writer end = new Writer(4);
		end.write();
		System.out.println("BYE!");
	System.exit(0);
}
////////////////////////////////////////////////////////////////////////////////////////////////

// Function for detecting the distance to the nearest object

public static String cont() {

	Scanner scanner = new Scanner(System.in);
	System.out.print("Would you like to continue? Press 'Y' for yes and 'X' for no \n");
	res = "N";
	try {
		swiftBot.enableButton(Button.Y, () -> {
			System.out.println("\n Y BUTTON DETECTED!");
			swiftBot.disableButton(Button.X);
			swiftBot.disableButton(Button.Y);
	        res = "Y";
	        return;
		});
		swiftBot.enableButton(Button.X, () -> {
			System.out.println("\n X BUTTON DETECTED!");
			swiftBot.disableButton(Button.X);
			swiftBot.disableButton(Button.Y);
			System.out.println("Exiting...");
		});
	} catch (Exception e) {
		System.out.println("ERROR occurred when setting up buttons.");
		e.printStackTrace();
		System.exit(5);
	}
	try {
		Thread.sleep(10000);
	}catch(Exception e){
		
	}
	return res;
}
public static void testQRCodeDetection() {


	int attempts = 0;
	try {
		Thread.sleep(500);
	}catch(Exception e) {
		
	}
	System.out.println("Initiating Scanner...");
	try {
		Thread.sleep(500);
	}catch(Exception e) {
		
	}
	System.out.println("Tip: Keep the QR code 10 - 15 cm away from the camera ");
	while(attempts <=10) {
		
		

		try {

			BufferedImage img = swiftBot.getQRImage();
			String decodedMessage = swiftBot.decodeQRImage(img);

			if (!decodedMessage.isEmpty()) {
				System.out.println("[INFO] QR Code detected\n");
				Thread.sleep(500);
				System.out.println("[INFO] Decoding data...\n");
				QRvalidation(decodedMessage);
				break;
			}
			
			++attempts;

		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Error! Try again...");
			
			++attempts;
		}
	}
}

public static void QRvalidation(String s) {
	 ArrayList<String> shapes = new ArrayList<>();
	s = s.trim();
	s = s.replaceAll("\\s+", "");
	 ArrayList<String> squares = new ArrayList<>();
	 ArrayList<String> triangles = new ArrayList<>();
	  int num = 0;
	 // Handling multiples shapes
	if(s.contains("&")) {
		String[] shape = s.split("&");
		for(String i : shape) {
			num++;
			if(num<= 5) {
			if(i.contains(":")) {
				i = i.toUpperCase();
				String shap = i.substring(0, i.indexOf(":"));
				if(shap.equals("S") || shap.equals("T") ) {
					shapes.add(i);
					if (shap.equals("S")){
						squares.add(i);
					}else {
						triangles.add(i);
					}
				}else {
					System.out.println("Contains an invalid shape");
					try {
						Thread.sleep(500);
					}catch(Exception e) {
						
					}
					System.out.println("[ACTION] Drawing aborted\r\n"
							+ "[INFO] Visual error indicator activated (RED LEDs)");
					try {
					InvalidLights();
					return;
					}catch(Exception e) {
						System.out.println(e);
					}
					return;
				}
			}else {
				System.out.println("Invalid format");
				try {
					Thread.sleep(500);
				}catch(Exception e) {
					
				}
				System.out.println("[ACTION] Drawing aborted\r\n"
						+ "[INFO] Visual error indicator activated (RED LEDs)");
				
				try {
					InvalidLights();
					return;
					}catch(Exception e) {
						System.out.println(e);
					}
				return;
			}
		}else {
			System.out.println("Too many shapes");
			try {
				Thread.sleep(500);
			}catch(Exception e) {
				
			}
			System.out.println("[ACTION] Drawing aborted\r\n"
					+ "[INFO] Visual error indicator activated (RED LEDs)");
			try {
				InvalidLights();
				}catch(Exception e) {
					System.out.println(e);
				}
			return;
		}
		}
		System.out.println("[INFO] Shapes detected: " +shapes.size() );
		try {
			Thread.sleep(500);
		}catch(Exception e) {
			
		}
		System.out.println("-----------------------------------------------------\r\n"
				+ "RAW DATA:\r\n"
				+ shapes+"\r\n"
				+ "-----------------------------------------------------\r\n"
				+ "");
		try {
			Thread.sleep(500);
		}catch(Exception e) {
			
		}
	}else {
		// Handling a single shape
	if(s.contains(":")) {
		 
		s = s.toUpperCase();
		String shape = s.substring(0, s.indexOf(":"));
		if(shape.equals("S") || shape.equals("T") ) {
			shapes.add(s);
			if (shape.equals("S")){
				squares.add(s);
			}else {
				triangles.add(s);
			}
		}else {
			System.out.println("Contains invalid shape");
			System.out.println("[ACTION] Drawing aborted\r\n"
					+ "[INFO] Visual error indicator activated (RED LEDs)");
			try {
				InvalidLights();
				}catch(Exception e) {
					System.out.println(e);
				}
			return;
		}
		System.out.println("[INFO] Shapes detected: " + 1);
		try {
			Thread.sleep(500);
		}catch(Exception e) {
			
		}
		System.out.println("-----------------------------------------------------\r\n"
				+ "RAW DATA:\r\n"
				+ shapes+"\r\n"
				+ "-----------------------------------------------------\r\n"
				+ "");
	}else {
		System.out.println("Invalid format or Wrong string");
		try {
			Thread.sleep(500);
		}catch(Exception e) {
			
		}
		System.out.println("[ACTION] Drawing aborted\r\n"
				+ "[INFO] Visual error indicator activated (RED LEDs)");
		try {
			InvalidLights();
			}catch(Exception e) {
				System.out.println(e);
			}
		return;
	}
}
	ArrayList<String> shape2 = new ArrayList<>();
	
	//-----------------------------------------------------------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------------------------------------------------------
	for(int k = 0; k<shapes.size();k++) {
		String m = shapes.get(k).substring(0, shapes.get(k).indexOf(":"));
		if(m.equals("S")) {
			boolean valid = SquareValidation(shapes.get(k), k+1);
			if(valid) {
				shape2.add(shapes.get(k));
				ValidSquares.add(shapes.get(k));
			}
		}else if(m.equals("T")) {
			boolean valid = TriangleValidation(shapes.get(k), k+1);
			if(valid) {
				shape2.add(shapes.get(k));
				ValidTriangles.add(shapes.get(k));
			}
		}	
	}

	if(!ValidSquares.isEmpty()) {
		System.out.println("[SUCCESS] QR data validated successfully\r\n"
				+ "# All side lengths within 15–85 cm\r"
				);
		try {
			Thread.sleep(500);
		}catch(Exception e) {
			
		}
		System.out.println("The Squares to be drawn are: " + ValidSquares + "\n");
		try {
			Thread.sleep(500);
		}catch(Exception e) {
			
		}
	}
	if(!ValidTriangles.isEmpty()) {
		System.out.println("[SUCCESS] QR data validated successfully\r\n"
				+ "# All side lengths within 15–85 cm\r"
				);
		try {
			Thread.sleep(500);
		}catch(Exception e) {
			
		}
		System.out.println("The Triangles to be drawn are: " + ValidTriangles);
		try {
			Thread.sleep(500);
		}catch(Exception e) {
			
		}
	}
	
	if(!ValidTriangles.isEmpty()) {
		System.out.println("# All triangle inequalities satisfied");
		try {
			Thread.sleep(500);
		}catch(Exception e) {
			
		}
	}
	for(int i = 0; i < shape2.size(); i++) {
		String m = shape2.get(i).substring(0, shape2.get(i).indexOf(":"));
		System.out.println("=====================================================\r\n"
				+ "[EXECUTION] DRAWING IN PROGRESS\r\n"
				+ "=====================================================");
		try {
			Thread.sleep(900);
		}catch(Exception e) {
			
		}
		if(m.equals("S")) {
			String side = shape2.get(i).substring(shape2.get(i).indexOf(":") + 1);
			System.out.printf("Sequence Progress:\r\n"
					+ "- Drawing Shape %d of %d: SQUARE\r\n"
					+ "-----------------------------------------------------\r\n"
					+ "Side Length: " + side + " cm\r\n"
					+ "Turn Angle = 90 degree\r\n"
					+ "Motor Status = ACTIVE\r\n"
					+ "\r\n"
					+ "[INFO] Movement in progress...\n",i + 1,shape2.size());
			DrawSquare(shape2.get(i));
		}else if(m.equals("T")) {
			System.out.printf("- Drawing Shape %d of %d: TRIANGLE\r\n"
					+ "-----------------------------------------------------\r\n",i+1,shape2.size());
			DrawTriangle(shape2.get(i));
		}
	}
	//-----------------------------------------------------------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------------------------------------------------------

	
	if(ValidSquares.size() == 0 && ValidTriangles.size() == 0) {
	    fs = "No valid shapes found!";
	} else if(ValidTriangles.size() > ValidSquares.size()) {
	    fs = "Triangle: " + ValidTriangles.size() + " times";
	} else if(ValidSquares.size() > ValidTriangles.size()) {
	    fs = "Square: " + ValidSquares.size() + " times";
	} else {
	    fs = "Equal: Squares and Triangles drawn " + ValidSquares.size() + " times each";
	}
	ts += ValidTriangles.size() + ValidSquares.size();
}

public static boolean SquareValidation(String square, int index) {
	try {
		String side = square.substring(square.indexOf(":") + 1);
		int s1 = Integer.parseInt(side);	
		if(15<=s1 && s1<=85) {
			System.out.printf("Square #%d validated succesfully\n\n", index);
			try {
				Thread.sleep(500);
			}catch(Exception e) {
				
			}
			try {
				ValidLights();
			}catch(Exception e) {
				System.out.println(e);
			}
			return true;
		}else if( s1<15) {
			System.out.printf("Square #%d side too small\n\n", index);
			try {
				Thread.sleep(500);
			}catch(Exception e) {
				
			}
			System.out.println("[ACTION] Drawing aborted\r\n"
					+ "[INFO] Visual error indicator activated (RED LEDs)");
			try {
				InvalidLights();
				}catch(Exception e) {
					System.out.println(e);
				}
			return false;
		}else if(s1> 85) {
			System.out.printf("Square #%d side too big\n", index);
			try {
				Thread.sleep(500);
			}catch(Exception e) {
				
			}
			System.out.println("[ACTION] Drawing aborted\r\n"
					+ "[INFO] Visual error indicator activated (RED LEDs)");
			try {
				InvalidLights();
				}catch(Exception e) {
					System.out.println(e);
				}
			return false;
		}
	}catch(Exception e) {
		System.out.printf("Invalid or too many sides\n", index);
		try {
			Thread.sleep(500);
		}catch(Exception f) {
			
		}
		System.out.println("[ACTION] Drawing aborted\r\n"
				+ "[INFO] Visual error indicator activated (RED LEDs)");
		try {
			InvalidLights();
			}catch(Exception j) {
				System.out.println(e);
			}
	}
	return false;
}

public static boolean TriangleValidation(String triangle, int index) {
	System.out.printf("Triangle #%d:\n", index);
	ArrayList<String> ValidTriangles = new ArrayList<String>();
	ArrayList<Integer> validsides = new ArrayList<>();
	if(triangle!=null && !triangle.isEmpty()) {
	String[] side = triangle.split(":");
	if(side.length > 4) {
		System.out.println("Too many sides");
		try {
			Thread.sleep(500);
		}catch(Exception e) {
			
		}
		System.out.println("[ACTION] Drawing aborted\r\n"
				+ "[INFO] Visual error indicator activated (RED LEDs)");
		try {
			InvalidLights();
			}catch(Exception e) {
				System.out.println(e);
			}
		return false;
	}
	for(int i = 1; i < 4; i++) {
		try {
			int s = Integer.parseInt(side[i]);
			if(15 <= s && s <=85) {
				System.out.printf("side #%d validated succesfully\n", i);
				try {
					Thread.sleep(500);
				}catch(Exception e) {
					
				}
				validsides.add(s);
				try {
					ValidLights();
				}catch(Exception e) {
					System.out.println(e);
				}
			}else if(s<15) {
				System.out.printf("side #%d is too short\n", i);
				try {
					Thread.sleep(500);
				}catch(Exception e) {
					
				}
				System.out.println("[ACTION] Drawing aborted\r\n"
						+ "[INFO] Visual error indicator activated (RED LEDs)");
				try {
					InvalidLights();
					}catch(Exception e) {
						System.out.println(e);
					}
			}else if(s > 85){
				System.out.printf("side #%d is too big\n", i);
				try {
					Thread.sleep(500);
				}catch(Exception e) {
					
				}
				System.out.println("[ACTION] Drawing aborted\r\n"
						+ "[INFO] Visual error indicator activated (RED LEDs)");
				try {
					InvalidLights();
					}catch(Exception e) {
						System.out.println(e);
					}
			}
		}catch(Exception e) {
			System.out.printf("side #%d invalid\n", i);
			try {
				Thread.sleep(500);
			}catch(Exception f) {
				
			}
			System.out.println("[ACTION] Drawing aborted\r\n"
					+ "[INFO] Visual error indicator activated (RED LEDs)");
			try {
				InvalidLights();
				}catch(Exception j) {
					System.out.println(e);
				}
		}
	}
	if(!validsides.isEmpty() && validsides.size() == 3) {
	if(validsides.get(0) + validsides.get(1) > validsides.get(2)) {
		if(validsides.get(1) + validsides.get(2) > validsides.get(0)) {
			if(validsides.get(2) + validsides.get(0) > validsides.get(1)) {
				System.out.printf("Triangle #%d valid\n\n", index);
				try {
					Thread.sleep(500);
				}catch(Exception e) {
					
				}
				try {
					ValidLights();
				}catch(Exception e) {
					System.out.println(e);
				}
				return true;
			}else {
				System.out.println("Given sides do not constitute a valid triangle");
				try {
					Thread.sleep(500);
				}catch(Exception e) {
					
				}
				System.out.println("[ACTION] Drawing aborted\r\n"
						+ "[INFO] Visual error indicator activated (RED LEDs)");
				try {
					InvalidLights();
					}catch(Exception e) {
						System.out.println(e);
					}
			}
		}else {
			System.out.println("Given sides do not constitute a valid triangle");
			try {
				Thread.sleep(500);
			}catch(Exception e) {
				
			}
			System.out.println("[ACTION] Drawing aborted\r\n"
					+ "[INFO] Visual error indicator activated (RED LEDs)");
			try {
				InvalidLights();
				}catch(Exception e) {
					System.out.println(e);
				}
		}
	}else {
		System.out.println("Given sides do not constitute a valid triangle");
		try {
			Thread.sleep(500);
		}catch(Exception e) {
			
		}
		System.out.println("[ACTION] Drawing aborted\r\n"
				+ "[INFO] Visual error indicator activated (RED LEDs)");
		try {
			InvalidLights();
			}catch(Exception e) {
				System.out.println(e);
			}
	}
}else {
	return false;
}
}
	return false;
}
//ArrayList<Integer> ValidSquares
public static void DrawSquare(String a) {
	 Assignment.objectDetected = false;
	
	int sides = 4;
	String side = a.substring(a.indexOf(":") + 1);
	int s1 = Integer.parseInt(side);
	double area = s1*s1;
	if(area > Larea) {
		Larea = area;
		Lshape = "Square: " + side;
	}
	double time = (s1/32.0)*1000;
	String t =String.valueOf(time);
	t = t.substring(0,t.indexOf("."));
	int tt = Integer.parseInt(t);
	long start = System.currentTimeMillis();
	Range r = new Range(range);
	Thread t1 = new Thread(r);
	t1.start();
	
	for(int i = 1; i < 5; i++) {
		try {
			if(Assignment.objectDetected) {
				System.out.println("OBJECT DETECTED!...Terminating the program...");
				Thread.sleep(1000);
				System.exit(0);
			}
	Thread.sleep(1000);
	swiftBot.move(100,100,tt);
	if(Assignment.objectDetected) {
		System.out.println("OBJECT DETECTED!...Terminating the program...");
		Thread.sleep(1000);
		System.exit(0);
	}
	Thread.sleep(1000);
	swiftBot.move(100,0,700);
	
	if(i == 4) {
		try {
			t1.interrupt();
		}catch(Exception e) {
			
		}
		long end = System.currentTimeMillis();
		long exectime = end - start;
		at += exectime/1000;
		Writer w = new Writer(exectime/1000, s1, true);
		
		w.write();
		ValidLights();
		swiftBot.move(-100, -100, 500);
		Thread.sleep(500);
	}
}catch(InterruptedException e) {
	System.out.println(e);
}finally {
    t1.interrupt(); 
}
	}
}

public static void DrawTriangle(String t) {
	 Assignment.objectDetected = false;
	ArrayList<Integer> newSide = new ArrayList<>();
	ArrayList<Integer> tt = new ArrayList<>();
	ArrayList<Integer> tt2 = new ArrayList<>();

	int sides = 3;
	String side = t.substring(t.indexOf(":") + 1);
	String[] news = side.split(":");
	for(int i = 0; i < news.length; i++) {
		newSide.add(Integer.parseInt(news[i]));
	}
	double a = newSide.get(0);
	double b = newSide.get(1);
	double c = newSide.get(2);
	
	double semiP = (a + b + c)/2;
System.out.println(newSide);
	double area = Math.sqrt(semiP*(semiP - a)*(semiP - b)*(semiP - c));
	if(area > Larea) {
		Larea = area;
		Lshape = "Triangle: " + side;
	}
	double A = Math.toDegrees(Math.acos((b*b + c*c - a*a)/(2*b*c)));
	double B = Math.toDegrees(Math.acos((c*c + a*a - b*b)/(2*a*c)));
	double C = Math.toDegrees(Math.acos((b*b + a*a - c*c)/(2*b*a)));
	
	int aa = newSide.get(0);
	int bb = newSide.get(1);
	int cc = newSide.get(2);

	tt.add(Math.toIntExact(Math.round((180-A)*9)));
	tt.add(Math.toIntExact(Math.round((180-B)*9)));
	tt.add(Math.toIntExact(Math.round((180-C)*9)));
	for(int i = 0; i < newSide.size(); i++) {
	tt2.add(Integer.parseInt(String.valueOf(Math.round((newSide.get(i)/32.0)*1000))));
	}
	System.out.printf("Sides: %d cm, %d cm, %d cm\r\n"
						+ "Calculated Interior Angles:\r\n"
						+ "- A = %.2f degree\r\n"
						+ "- B = %.2f degree\r\n"
						+ "- C = %.2f degree\r\n"
						+ "\r\n"
						+ "[INFO] Movement in progress...\n\n", aa,bb,cc,A,B,C);
	long start = System.currentTimeMillis();
	Range r = new Range(range);
	Thread t1 = new Thread(r);
	t1.start();
	for(int i = 0; i < newSide.size(); i++) {
		try {
				if(objectDetected) {
					System.out.println("OBJECT DETECTED!...Terminating the program...");
					Thread.sleep(1000);
					System.exit(0);
				}
		swiftBot.move(100, 100,tt2.get(i));
		Thread.sleep(1000);
		swiftBot.move(0,100,tt.get(i));
		if(objectDetected) {
			System.out.println("OBJECT DETECTED!...Terminating the program...");
			Thread.sleep(1000);
			System.exit(0);
		}
		Thread.sleep(1000);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
try {
	long end = System.currentTimeMillis();
	long exectime = end - start;
	at += exectime/1000;
	Writer w = new Writer(exectime/1000, A, B, C, aa, bb, cc, true);
	w.write();
	ValidLights();
	swiftBot.move(-100, -100, 500);
}catch(Exception e) {
	System.out.println(e);
}
}

public static void ValidLights() throws InterruptedException {
	int[] green = new int[] { 0, 255, 0 };
	try {
		// Declaring an array of under lights.
		Underlight[] underlights = new Underlight[] { Underlight.BACK_LEFT, Underlight.BACK_RIGHT,
				Underlight.MIDDLE_LEFT,
				Underlight.MIDDLE_RIGHT, Underlight.FRONT_LEFT, Underlight.FRONT_RIGHT };

		for (Underlight underlight : underlights) { // Iterates through the array of under light
			swiftBot.setUnderlight(underlight, green);
		}
	} catch (Exception e) {
		e.printStackTrace();
		System.out.println("ERROR: Unable to set under light");
		System.exit(5);
	}
	Thread.sleep(2000);
	swiftBot.disableUnderlights();
}
public static void InvalidLights() throws InterruptedException {
	// Declaring three variables containing the RGB values for red, green and blue.
	int[] red = new int[] { 255, 0, 0 };
	try {
		// Declaring an array of under lights.
		Underlight[] underlights = new Underlight[] { Underlight.BACK_LEFT, Underlight.BACK_RIGHT,
				Underlight.MIDDLE_LEFT,
				Underlight.MIDDLE_RIGHT, Underlight.FRONT_LEFT, Underlight.FRONT_RIGHT };

		for (Underlight underlight : underlights) { // Iterates through the array of under lights
			swiftBot.setUnderlight(underlight, red);
		}
	} catch (Exception e) {
		e.printStackTrace();
		System.out.println("ERROR: Unable to set under light");
		System.exit(5);
	}
	Thread.sleep(2000);
	swiftBot.disableUnderlights();
}

}
;

