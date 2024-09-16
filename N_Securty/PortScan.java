package N_Securty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

 

public class PortScan{
    public static ArrayList portlist=new ArrayList();

    public static ArrayList ScanPort() throws InterruptedException, ExecutionException  {
        
    final ExecutorService es = Executors.newFixedThreadPool(150);
    final String ip = "127.0.0.1";
    final int timeout = 100;
    final ArrayList<Future<ScanResult>> futures = new ArrayList<>();
    for (int port = 1; port <= 20000; port++) {
        futures.add(portIsOpen(es, ip, port, timeout));
    }
      es.shutdown();
    for (final Future<ScanResult> f : futures) {
        if (f.get().isOpen()) {          
            portlist.add(f.get().getPort());
        } 
    }
        return portlist;
}

public static Future<ScanResult> portIsOpen(final ExecutorService es, final String ip, final int port,
        final int timeout) {
    return es.submit(() -> {
        try {
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(ip, port), timeout);
            }
            return new ScanResult(port, true);
        } catch (IOException ex) {
            return new ScanResult(port, false);
        }
    });
}
public static class ScanResult {
    private int port;

    private boolean isOpen;

    public ScanResult(int port, boolean isOpen) {
        super();
        this.port = port;
        this.isOpen = isOpen;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

}
}
