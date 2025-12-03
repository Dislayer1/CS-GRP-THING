import swiftbot.Button;
import swiftbot.SwiftBotAPI;
import swiftbot.Underlight;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class test {
    
    // --- API & Game State ---
    static SwiftBotAPI swiftbot = SwiftBotAPI.INSTANCE;
    static int score = 0;
    static int round = 1;
    static List<String> sequence = new ArrayList<>();
    static boolean isGameRunning = true;
    static Scanner scanner = new Scanner(System.in);

    // --- 1. Color (LED) Definitions (From test.java preferences) ---
    // Using the specific RGB values requested: Pink, Blue, Purple, Red
    public static final int[] RED    = {255, 0, 0};
    public static final int[] BLUE   = {0, 0, 255};
    public static final int[] PINK   = {255, 105, 180};
    public static final int[] PURPLE = {128, 0, 128};

    public static final String[] allColours = {"red", "blue", "pink", "purple"};

    // --- 2. Mapping Structures ---
    static HashMap<String, int[]> cMap = new HashMap<>();
    static HashMap<String, Underlight> lightMap = new HashMap<>();
    // Map to link physical button letters to colour names
    static HashMap<String, String> buttonColorMap = new HashMap<>();

    public static void main(String[] args) {
        
        // Initialize Mappings (Integrated from GameSetup style)
        initializeMapping();

        System.out.println("Welcome to my Swiftbot! The memory challenge.");

        while (isGameRunning) {
            System.out.println("\n-------------------------------------------");
            System.out.println("Score: " + score + " | Round: " + round);
            System.out.println("--- ROUND " + round + " ---");            
            System.out.println("-------------------------------------------");
            
            addNewColourToSequence();
            displaySequence();
            
            boolean matchFound = getUserInputAndCheckMatch();

            if (matchFound) {
                score++;
                round++;
                
                // Prompt to quit every 5 rounds
                if (round > 1 && (round - 1) % 5 == 0) {
                    if (promptUserToQuit()) {
                        isGameRunning = false;
                    }
                }
            } else {
                // Game Over
                isGameRunning = false;
                endGame(true);
            }
        }
        
        if (!isGameRunning && round > 0) {
            // Clean exit if needed
        }
    }

    // --- 3. Initialization Method ---
    public static void initializeMapping() {
        // Map String names to RGB arrays
        cMap.put("pink", PINK);
        cMap.put("purple", PURPLE);
        cMap.put("red", RED);
        cMap.put("blue", BLUE);

        // Map String names to LED positions
        lightMap.put("pink", Underlight.FRONT_RIGHT);
        lightMap.put("purple", Underlight.BACK_RIGHT);
        lightMap.put("red", Underlight.FRONT_LEFT);
        lightMap.put("blue", Underlight.BACK_LEFT);
        
        // Populate button Map (A=Red, B=Blue, X=Pink, Y=Purple)
        buttonColorMap.put("A", "red");
        buttonColorMap.put("B", "blue");
        buttonColorMap.put("X", "pink");
        buttonColorMap.put("Y", "purple");
        
        System.out.println("System: Colors, Buttons, and LEDs mapped successfully.");
    }

    // --- 4. Game Logic Methods ---

    public static void addNewColourToSequence() {
        int rndIndex = ThreadLocalRandom.current().nextInt(0, allColours.length);
        sequence.add(allColours[rndIndex]);
    }

    public static void displaySequence() {
        System.out.println("Watch the sequence Now");

        for(String colour : sequence){
            Underlight led = lightMap.get(colour);
            int[] rgb = cMap.get(colour);

            System.out.println("Displaying: " + colour);
            swiftbot.setUnderlight(led, rgb);
            
            sleep();
        }
        System.out.println("Now repeat the sequence");
    }

    public static void sleep() {
        try {
            Thread.sleep(1000); // Wait for 1 second 
            swiftbot.disableUnderlights();
            Thread.sleep(250); // Short pause after light turns off 
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static boolean getUserInputAndCheckMatch() {
        // Note: Enabling buttons purely for visual effect if using Scanner, 
        // or purely to allow hardware interactions if modified later.
        swiftbot.enableAllButtons();
        
        try {
            for (int j = 0; j < sequence.size(); j++) {
                String requiredColor = sequence.get(j);
                String buttonInput = "";
                
                System.out.print("Input " + (j + 1) + "/" + sequence.size() + " (A,B,X,Y): ");
                
                // Input Validation Loop
                do {
                    if (scanner.hasNext()) {
                        buttonInput = scanner.next().toUpperCase();
                    }
                    if (!buttonInput.matches("[ABXY]")) {
                        System.out.print("Invalid button! Press A, B, X, or Y: ");
                    }
                } while (!buttonInput.matches("[ABXY]"));
                
                String inputColor = buttonColorMap.get(buttonInput);
                
                // Check Match
                if (!inputColor.equals(requiredColor)) {
                    System.out.println("\n*** INCORRECT! You pressed " + buttonInput + " (Color: " + inputColor + ") ***");
                    System.out.println("*** Correct color was " + requiredColor + " ***");
                    return false;
                }
                
                System.out.println("...Correct!");
            }
            return true;
        } finally {
            swiftbot.disableAllButtons();
        }
    }

    public static boolean promptUserToQuit() {
        String choice = "";
        System.out.println("\n-------------------------------------------");
        System.out.println("You completed Round " + (round - 1) + "!");
        System.out.print("Quit and see stats? (Y/N): ");
        
        // Input Validation Loop
        do {
            choice = scanner.next().toUpperCase();
            if (!(choice.equals("Y") || choice.equals("N"))) {
                System.out.print("Invalid! Enter 'Y' or 'N': ");
            }
        } while (!(choice.equals("Y") || choice.equals("N")));
        
        if (choice.equals("Y")) {
            endGame(false); // False means just a quit, not a mismatch
            return true;
        }
        return false;
    }

    public static void endGame(boolean isMismatch) {
        if (isMismatch) {
            System.out.println("\n*********************************");
            System.out.println("           GAME OVER!");
            System.out.println("*********************************");
        } else {
            System.out.println("\n*********************************");
            System.out.println("      SEE YOU AGAIN CHAMP!");
            System.out.println("*********************************");
        }
        
        System.out.println("Final Score: " + score);
        System.out.println("Final Round: " + (round - 1));
        
        if (score >= 5) {
            System.out.println("!!! Doing Celebration Dive !!!");
            // celebrationDivc(); 
        }
        
        System.exit(0);
    }
}
