package N_Securty;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class IpScan {

    public String CapturePath;

    public ArrayList IpScanWin() throws IOException, InterruptedException {
        ArrayList winIp=new ArrayList();
        CapturePath = new Cmd().Create_tmpFile();
        // String out is used to store output of this command(process) 
        String out ;       
        // A compiled representation of a regular expression 
        Pattern pattern
                = Pattern.compile("^([01]?[0-9][0-9]?|2[0-2][0-3])\\." +
            "([01]?[0-9][0-9]?|2[0-4][0-9]|25[0-5])\\." +
            "([01]?[0-9][0-9]?|2[0-4][0-9]|25[0-5])\\." +
            "([01]?[0-9][0-9]?|2[0-4][0-9]|25[0-4])$");
  
        GetAllDevices devicesList = new GetAllDevices(new File(CapturePath));
        List<ConnectedDevice> L = devicesList.ReadFromFile();
        if (!L.isEmpty()) {
            for (ConnectedDevice device : L) {
                out =device.getIP_Address();
                 Matcher match = pattern.matcher(out);
                if ((match.find())) 
                {
                    winIp.add(out);
                }              
            }

        } else {
            JOptionPane.showMessageDialog(null, "Your PC Not Connected to NetWork", "Empty List",
                    JOptionPane.ERROR_MESSAGE);
        }
        return winIp;
    }
    public  void win(String ip){
        Thread t=new Thread(() -> {
            Cmd cm=new Cmd();
            try {
                cm.exec_Cmd("for /L %i IN (1,1,254) DO ping -n 1 "+ip+".%i");
            } catch (InterruptedException | IOException ex) {
                Logger.getLogger(IpScan.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        t.start();
    }

    public static ArrayList ListIPs(String ip) throws UnknownHostException, IOException, InterruptedException, ExecutionException {
        final ExecutorService es = Executors.newFixedThreadPool(25);
        final ArrayList<Future<ScanResult>> ipsSet = new ArrayList<>();
        int[] bounds = IpScan.rangeFromCidr(ip);
        for (int i = bounds[0] + 1; i <= bounds[1] - 1; i++) {
            String address = IpScan.InetRange.intToIp(i);
            ipsSet.add(ScaniP(es, address));
        }
        es.shutdown();
        return ipsSet;
    }

    public static Future<ScanResult> ScaniP(final ExecutorService es, final String address) throws UnknownHostException, IOException {
        return es.submit(() -> {
            InetAddress ip = InetAddress.getByName(address);
            if (ip.isReachable(300)) { // Try for two tenth of a second
                return (new ScanResult(ip.toString().replace("/", ""), true));
            } else {
                return new ScanResult(ip.toString().replace("/", ""), false);
            }
        });
    }

    public static int[] rangeFromCidr(String Ip) {
        int maskStub = 1 << 31;
        String[] atoms = Ip.split("/");
        int mask = Integer.parseInt(atoms[1]);
        int[] result = new int[2];
        result[0] = InetRange.ipToInt(atoms[0]) & (maskStub >> (mask - 1)); // lower bound
        result[1] = InetRange.ipToInt(atoms[0]); // upper bound
        return result;
    }

    static class InetRange {

        public static int ipToInt(String ipAddress) {
            try {
                byte[] bytes = InetAddress.getByName(ipAddress).getAddress();
                int octet1 = (bytes[0] & 0xFF) << 24;
                int octet2 = (bytes[1] & 0xFF) << 16;
                int octet3 = (bytes[2] & 0xFF) << 8;
                int octet4 = bytes[3] & 0xFF;
                int address = octet1 | octet2 | octet3 | octet4;
                return address;
            } catch (UnknownHostException e) {
                System.err.println(e);
                return 0;
            }
        }

        public static String intToIp(int ipAddress) {
            int octet1 = (ipAddress & 0xFF000000) >>> 24;
            int octet2 = (ipAddress & 0xFF0000) >>> 16;
            int octet3 = (ipAddress & 0xFF00) >>> 8;
            int octet4 = ipAddress & 0xFF;

            return new StringBuffer().append(octet1).append('.').append(octet2)
                    .append('.').append(octet3).append('.')
                    .append(octet4).toString();
        }
    }

    public static class ScanResult {

        private String ip;

        private boolean isReachable;

        public ScanResult() {

        }

        public ScanResult(String ip, boolean isReachable) {
            super();
            this.ip = ip;
            this.isReachable = isReachable;
        }

        public String getip() {
            return ip;
        }

        public void setip(String ip) {
            this.ip = ip;
        }

        public boolean isReachable() {
            return isReachable;
        }

        public void setReachable(boolean isReachable) {
            this.isReachable = isReachable;
        }

    }
}
