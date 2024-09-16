package N_Securty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class BlockSite {

    public static void blockSite(String url) throws IOException {
    // Note that this code only works in Java 7+,
    // refer to the above link about appending files for more info

    // Get OS name
    String OS = System.getProperty("os.name").toLowerCase();

    // Use OS name to find correct location of hosts file
    String hostsFile = "";
    if ((OS.contains("win"))) {
        // Doesn't work before Windows 2000
        hostsFile = "C:\\Windows\\System32\\drivers\\etc\\hosts";
    } else if ((OS.contains("mac"))) {
        // Doesn't work before OS X 10.2
        hostsFile = "etc/hosts";
    } else if ((OS.contains("nux"))) {
        hostsFile = "/etc/hosts";
    } else {
        // Handle error when platform is not Windows, Mac, or Linux
        System.err.println("Sorry, but your OS doesn't support blocking.");
    }

    // Actually block site
    Files.write(Paths.get(hostsFile),
                ("127.0.0.1  \t\t\t\t\t" + url+"\n").getBytes(),
                StandardOpenOption.APPEND);
    
}
}
