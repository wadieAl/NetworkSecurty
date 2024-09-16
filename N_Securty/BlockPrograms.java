package N_Securty;

import java.io.IOException;


public class BlockPrograms {

    /**
     *
     * @param timeout
     * @param programs
     * @throws IOException
     */
    public static void blockPrograms(int timeout, String...programs) throws IOException {
    // Get OS name
    String OS = System.getProperty("os.name").toLowerCase();

    // Identify correct blocking command for OS
    String command = "";
    if ((OS.contains("win"))) {
        command = "taskkill /f /im ";
    } else if ((OS.contains("mac")) || (OS.contains("nux"))) {
        command = "killall ";
    } else {
        // Handle error when platform is not Windows, Mac, or Linux
        System.err.println("Sorry, but your OS doesn't support blocking.");
    }

    // Start blocking!
    
        // Cycle through programs list
        for (String program : programs) {
            // Block program
            Runtime.getRuntime().exec(command + program+".exe");
        }
        // Timeout
        try { Thread.sleep(timeout); } catch(InterruptedException e) {
        System.out.println(e);
        }
    
}
    
}
