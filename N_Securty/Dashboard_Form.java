package N_Securty;

import DAO.UserDao;
import Entities.UsersItems;
import ds.desktop.notify.DesktopNotify;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.Timer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

public final class Dashboard_Form extends javax.swing.JFrame {

    private static final long serialVersionUID = 10L;
    public String username = "hi";
    //list and list modle
    DefaultListModel listModel = new DefaultListModel();
    DefaultListModel listModelPort = new DefaultListModel();
    DefaultListModel listModelSites = new DefaultListModel();
    DefaultListModel listModelPrograms = new DefaultListModel();

    // default border for the menu items
    Border default_border = BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(0, 0, 110));

    // yellow border for the menu items
    Border yellow_border = BorderFactory.createMatteBorder(1, 0, 1, 0, Color.YELLOW);

    // create an array of jlabels
    JLabel[] menuLabels = new JLabel[2];

    // create an array of jpanels
    JPanel[] panels = new JPanel[2];

    public Dashboard_Form() {
        initComponents();
        // center this form
        this.setLocationRelativeTo(null);
        //set program Icon
        ImageIcon img = new ImageIcon(getClass().getResource("/IMAGES/appLogo.png"));
        this.setIconImage(img.getImage());
        // set icons
        jLabel_appLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/appLogo.png")));
        jLabel_close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/x.png")));

        // set borders
        // panel logo border
        Border panelBorder = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.lightGray);
        jPanel_logoANDname.setBorder(panelBorder);
        // panel container border
        Border containerBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0, 0, 110));
        jPanel_container.setBorder(containerBorder);

        // populate the menuLabels array
        menuLabels[0] = jLabel_menuItem_Status;
        //menuLabels[1] = jLabel_menuItem2;
        menuLabels[1] = jLabel_menuItem4;

        setLabelBackround(jLabel_menuItem_Status);
        // populate the panels array
        panels[0] = jPanel_status;
        // panels[1] = jPanel_users;      
        panels[1] = jPanel_tools;

        showPanel(jPanel_status);

        addActionToMenuLabels();
        Ip_port_Refresh();
        BlockSites();
        networkIPs();
        showIP();
        Date_Time();
        BlockProgam();       
    }

    public void Name(UsersItems item) throws ClassNotFoundException {
        String m = item.getUserNam();
        this.username = (m);
        getUsers();
    }

    private ArrayList ipslist = new ArrayList();

    private void refresh() {

        try {
            Scanner sc=new Scanner(new File("Nip.nSec"));
        this.jTextField_N_IP.setText(sc.nextLine());
        this.jTextField_N_IP1.setText(sc.nextLine());
        this.jTextField_N_IP2.setText(sc.nextLine());
        } catch (FileNotFoundException e) {
        }
        //get ip address
        String hostIp = this.jTextField_N_IP.getText() + "." + this.jTextField_N_IP1.getText() + "." + this.jTextField_N_IP2.getText()
                + "." + this.jTextField_N_IP3.getText() + "/" + this.jTextField_N_IP4.getText();
        String Ip = this.jTextField_N_IP.getText() + "." + this.jTextField_N_IP1.getText() + "." + this.jTextField_N_IP2.getText();

        // Get OS name
        String OS = System.getProperty("os.name").toLowerCase();
        if ((OS.contains("win"))) {
            if (!(this.jTextField_N_IP.getText().equals("0"))) {
                Thread ips;
                ips = new Thread(() -> {
                    try {
                        // Doesn't work before Windows 2000
                        IpScan scan = new IpScan();
                        scan.win(Ip);
                        Thread.sleep(1000);
                        ipslist = scan.IpScanWin();

                    } catch (IOException | InterruptedException ex) {
                        Logger.getLogger(Dashboard_Form.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    number0fPC.setText(String.valueOf(ipslist.size()));
                    for (int i = 0; i < ipslist.size(); i++) {
                        if (!(listModel.contains(ipslist.get(i)))) {
                            DesktopNotify.showDesktopMessage(
                                    "WARNING",
                                    "UNknewn Ip " + ipslist.get(i),
                                    DesktopNotify.WARNING, 6000);
                        }
                    }
                });
                ips.start();
            }
        } else {
            //start new thread to scan ip in network 
            Thread ip;
            ip = new Thread(() -> {
                ArrayList<Future<IpScan.ScanResult>> ips;
                try {
                    ips = IpScan.ListIPs(hostIp);
                    for (final Future<IpScan.ScanResult> f : ips) {
                        if (f.get().isReachable()) {
                            ipslist.add(f.get().getip());
                            System.out.println(f.get().getip());
                        }

                    }
                    number0fPC.setText(String.valueOf(ipslist.size()));
                } catch (IOException | InterruptedException | ExecutionException ex) {
                    Logger.getLogger(Dashboard_Form.class.getName()).log(Level.SEVERE, null, ex);
                }
                for (int i = 0; i < ipslist.size(); i++) {
                    if (!(listModel.contains(ipslist.get(i)))) {
                        DesktopNotify.showDesktopMessage(
                                "WARNING",
                                "UNknewn Ip " + ipslist.get(i),
                                DesktopNotify.WARNING, 6000);
                    }
                }
            });

            ip.start();
        }
    }

    public void getUsers() throws ClassNotFoundException {
        UsersItems item = new UsersItems();
        item.setUserNam(this.username);
        UserDao dao = new UserDao();
        ArrayList<UsersItems> Item = dao.getUsers(item);
        for (UsersItems f : Item) {
            if (!(f.isAlwod_IPs())) {
                this.jBtn_add.setEnabled(false);
                this.jBtn_add1.setEnabled(false);
            }
            if (!(f.isNetWork_IP())) {
                this.jButton1.setEnabled(false);
            }
            if (!(f.isSites())) {
                this.jBtn_add8.setEnabled(false);
                this.jBtn_add9.setEnabled(false);
            }
            if (!(f.isPrograms())) {
                this.jBtn_add10.setEnabled(false);
                this.jBtn_add11.setEnabled(false);
            }
            if (!(f.isUsers())) {
                this.popup.remove(1);
                this.popup.remove(0);
            }
            if (!(f.isClose_Port())) {
                this.jBtn_add3.setEnabled(false);
            }
        }
    }

    private void npotrScan() {
        listModelPort.removeAllElements();
        //start new thread to scan ip in network 
        Thread t;
        t = new Thread(() -> {
            ArrayList ar;
            PortScan.portlist.clear();
            try {
                ar = PortScan.ScanPort();
                for (int i = 0; i < ar.size(); i++) {
                    listModelPort.addElement(ar.get(i));
                }
                jList_port_scan.setModel(listModelPort);
            } catch (InterruptedException | ExecutionException e) {
                JOptionPane.showMessageDialog(null, e, " error", JOptionPane.ERROR_MESSAGE);
            }
        });

        t.start();

    }

    // create a function to set the label background color
    private void setLabelBackround(JLabel label) {
        // reset labels to their default design
        for (JLabel menuItem : menuLabels) {
            // change the jlabel background color 
            menuItem.setBackground(new Color(0, 0, 110));
            // change the jlabel Foreground color
            menuItem.setForeground(Color.white);
        }
        // change the jlabel background color to white
        label.setBackground(Color.white);
        // change the jlabel Foreground color to blue
        label.setForeground(Color.blue);
    }

    private void networkIPs() {
        try {
            try (Scanner ip = new Scanner(new File("ips.nSec"))) {
                while (ip.hasNext()) {
                    listModel.addElement(ip.nextLine());
                }
                jList_network_alowed_ips.setModel(listModel);
            }

        } catch (FileNotFoundException e) {
        }
    }

    private void BlockSites() {
        try {
            try (Scanner site = new Scanner(new File("site.nSec"))) {
                while (site.hasNext()) {
                    listModelSites.addElement(site.nextLine());
                }
                jList_blockSites.setModel(listModelSites);
            }

        } catch (FileNotFoundException e) {
        }
    }

    private void BlockProgam() {
        try {
            try (Scanner prog = new Scanner(new File("prog.nSec"))) {
                while (prog.hasNext()) {
                    listModelPrograms.addElement(prog.nextLine());
                }
                jList_blockPrograms.setModel(listModelPrograms);
            }

        } catch (FileNotFoundException e) {
        }
    }

    // create a function to show the selected panel
    private void showPanel(JPanel panel) {
        // hide panels
        for (JPanel pnl : panels) {
            pnl.setVisible(false);
        }

        // and show only this panel
        panel.setVisible(true);
    }

    private void showIP() {
        try {
            String localhostIp = InetAddress.getLocalHost().getHostAddress();
            String localhostName = InetAddress.getLocalHost().getHostName();
            this.localIP.setText(localhostIp);
            this.hostName.setText(localhostName);
        } catch (UnknownHostException e) {
        }

    }

    private void Date_Time() {
        LocalDate date = LocalDate.now(); // Create a date object
        this.jLabel_Date.setText(date.toString());
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                String string = new SimpleDateFormat("hh:mm:ss aa").format(new Date());
                jLabel_Time.setText(string);
                for (int i = 0; i < listModelPrograms.getSize(); i++) {
                    try {
                        BlockPrograms.blockPrograms(100, listModelPrograms.getElementAt(i).toString());
                    } catch (IOException ex) {
                        Logger.getLogger(Dashboard_Form.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }, 0, 1000);
    }

    private void Ip_port_Refresh() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                refresh();
                npotrScan();
            }
        }, 0, 5000 * 60);
    }

    // create a popup menu
    PopupMenu popup;

    //create a function to use SystemTray
    public void Tray() {
        //this.setVisible(false);
        final TrayIcon trayIcon;
        if (SystemTray.isSupported()) {
            // get the SystemTray instance
            SystemTray tray = SystemTray.getSystemTray();
            // load an image
            Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/IMAGES/appLogo.png"));
            popup = new PopupMenu();
            // construct a TrayIcon
            trayIcon = new TrayIcon(image, "N-Securty", popup);
            // create menu item for the default action

            MenuItem AddUsers = new MenuItem("AddUsers");
            AddUsers.addActionListener((ActionEvent ae) -> {
                Users u = new Users();
                u.setVisible(true);
                u.setLocationRelativeTo(null);
                u.pack();

            });
            popup.add(AddUsers);

            MenuItem UpdateUsers = new MenuItem("UpdateUsers");
            UpdateUsers.addActionListener((ActionEvent ae) -> {
                UpdateUsers u = new UpdateUsers();
                u.setVisible(true);
                u.setLocationRelativeTo(null);
                u.pack();

            });
            popup.add(UpdateUsers);

            MenuItem exit = new MenuItem("Exit");
            exit.addActionListener((ActionEvent ae) -> {
                tray.remove(trayIcon);
                System.exit(0);
            });
            popup.add(exit);

            //Let the system resize the image if needed
            trayIcon.setImageAutoSize(true);
            //add object from Dashboard_Form to control Visiblty  when mouse clicked
            //Dashboard_Form f = new Dashboard_Form();
            // set MouseListener for left button
            trayIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        //tray.remove(trayIcon);
                        // check if Jframe is visible
                        if (isVisible()) {
                            toFront();
                            // Display info notification:
                            trayIcon.displayMessage("Action Event",
                                    "N-Securty is Already Opened!",
                                    TrayIcon.MessageType.INFO);
                        } else {
                            setVisible(true);
                        }
                    }
                }
            });

            try {
                //check if there is anther Tray opend
                if (tray.getTrayIcons().length == 0) {
                    tray.add(trayIcon);
                } else {
                    tray.remove(trayIcon);
                }
            } catch (AWTException e) {
                System.err.println(e);
            }
        } else {
            // disable tray option in your application or
            // perform other actions

        }
    }

    private void addActionToMenuLabels() {
        // get labels in the jpanel menu
        Component[] components = jPanel_menu.getComponents();

        for (Component component : components) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;

                label.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                        // change the jlabel background and Foreground
                        setLabelBackround(label);

                        // disply the selected panel
                        switch (label.getText().trim()) {
                            case "Status":
                                showPanel(jPanel_status);
                                break;

                            case "Users":
                                showPanel(jPanel_users);
                                // jPanel_users.setBackground(Color.red);
                                break;

                            case "Tools":
                                showPanel(jPanel_tools);
                                // jPanel_settings.setBackground(Color.GRAY);
                                break;
                        }

                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                        // set the border to yellow
                        label.setBorder(yellow_border);

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                        // reset to the default border
                        label.setBorder(default_border);

                    }
                });

            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_container = new javax.swing.JPanel();
        jPanel_menu = new javax.swing.JPanel();
        jPanel_logoANDname = new javax.swing.JPanel();
        jLabel_appLogo = new javax.swing.JLabel();
        jLabel_menuItem_Status = new javax.swing.JLabel();
        //jLabel_menuItem2 = new javax.swing.JLabel();
        jLabel_menuItem4 = new javax.swing.JLabel();
        jPanel_status = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        number0fPC = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        hostName = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel_Time = new javax.swing.JLabel();
        jLabel_Date = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        localIP = new javax.swing.JLabel();
        jPanel_users = new javax.swing.JPanel();
        jPanel_tools = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jTextField_N_IP2 = new javax.swing.JTextField();
        jTextField_N_IP3 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField_N_IP = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField_N_IP4 = new javax.swing.JTextField();
        jTextField_N_IP1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList_network_alowed_ips = new javax.swing.JList<>();
        jBtn_add = new javax.swing.JButton();
        jBtn_add1 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList_port_scan = new javax.swing.JList<>();
        jBtn_add2 = new javax.swing.JButton();
        jBtn_add3 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jList_blockSites = new javax.swing.JList<>();
        jBtn_add8 = new javax.swing.JButton();
        jBtn_add9 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jList_blockPrograms = new javax.swing.JList<>();
        jBtn_add10 = new javax.swing.JButton();
        jBtn_add11 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel_close = new javax.swing.JLabel();
        jLabel_appName = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel_container.setBackground(new java.awt.Color(255, 255, 255));

        jPanel_menu.setBackground(new java.awt.Color(0, 0, 110));

        jPanel_logoANDname.setBackground(new java.awt.Color(0, 0, 110));

        jLabel_appLogo.setBackground(new java.awt.Color(153, 255, 204));
        jLabel_appLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/appLogo.png"))); // NOI18N
        jLabel_appLogo.setOpaque(true);
        jLabel_appLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel_appLogoMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_logoANDnameLayout = new javax.swing.GroupLayout(jPanel_logoANDname);
        jPanel_logoANDname.setLayout(jPanel_logoANDnameLayout);
        jPanel_logoANDnameLayout.setHorizontalGroup(
                jPanel_logoANDnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel_logoANDnameLayout.createSequentialGroup()
                                .addGap(66, 66, 66)
                                .addComponent(jLabel_appLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_logoANDnameLayout.setVerticalGroup(
                jPanel_logoANDnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_logoANDnameLayout.createSequentialGroup()
                                .addContainerGap(28, Short.MAX_VALUE)
                                .addComponent(jLabel_appLogo)
                                .addGap(22, 22, 22))
        );

        jLabel_menuItem_Status.setBackground(new java.awt.Color(0, 0, 110));
        jLabel_menuItem_Status.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel_menuItem_Status.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_menuItem_Status.setText("  Status");
        jLabel_menuItem_Status.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_menuItem_Status.setOpaque(true);
        /*
        jLabel_menuItem2.setBackground(new java.awt.Color(0, 0, 110));
        jLabel_menuItem2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel_menuItem2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_menuItem2.setText("  ");
        jLabel_menuItem2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_menuItem2.setOpaque(true);
         */
        jLabel_menuItem4.setBackground(new java.awt.Color(0, 0, 110));
        jLabel_menuItem4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel_menuItem4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_menuItem4.setText("  Tools");
        jLabel_menuItem4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_menuItem4.setOpaque(true);

        javax.swing.GroupLayout jPanel_menuLayout = new javax.swing.GroupLayout(jPanel_menu);
        jPanel_menu.setLayout(jPanel_menuLayout);
        jPanel_menuLayout.setHorizontalGroup(
                jPanel_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel_logoANDname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel_menuItem_Status, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                        .addComponent(jLabel_menuItem4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        //            .addComponent(jLabel_menuItem2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel_menuLayout.setVerticalGroup(
                jPanel_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel_menuLayout.createSequentialGroup()
                                .addComponent(jPanel_logoANDname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)
                                .addComponent(jLabel_menuItem_Status, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44)
                                //               .addComponent(jLabel_menuItem2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(jLabel_menuItem4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 232, Short.MAX_VALUE))
        );

        jPanel_status.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(211, 84, 0));

        jLabel9.setBackground(new java.awt.Color(230, 126, 34));
        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("  Connected Devices");
        jLabel9.setOpaque(true);
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel9MousePressed(evt);
            }
        });

        number0fPC.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        number0fPC.setForeground(new java.awt.Color(255, 255, 255));
        number0fPC.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        number0fPC.setText("Refresh");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                        .addComponent(number0fPC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(number0fPC, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(38, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(68, 108, 179));

        jLabel10.setBackground(new java.awt.Color(65, 131, 215));
        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("  Firewall Status");
        jLabel10.setOpaque(true);

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Refresh");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                        .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(39, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(1, 152, 117));

        jLabel11.setBackground(new java.awt.Color(42, 187, 155));
        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("  Host Name ");
        jLabel11.setOpaque(true);

        hostName.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        hostName.setForeground(new java.awt.Color(255, 255, 255));
        hostName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hostName.setText("Refresh");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                        .addComponent(hostName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(hostName, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(39, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(108, 122, 137));

        jLabel12.setBackground(new java.awt.Color(103, 128, 159));
        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("   Date and Time");
        jLabel12.setOpaque(true);

        jLabel_Time.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel_Time.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Time.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_Time.setText("Refresh");

        jLabel_Date.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel_Date.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Date.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_Date.setText("Refresh");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                        .addComponent(jLabel_Time, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel_Date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel_Time, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel_Date, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(150, 54, 148));

        jLabel14.setBackground(new java.awt.Color(142, 68, 173));
        jLabel14.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("  Local IP Address");
        jLabel14.setOpaque(true);

        localIP.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        localIP.setForeground(new java.awt.Color(255, 255, 255));
        localIP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        localIP.setText("Refresh");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                        .addComponent(localIP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(localIP, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(46, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel_statusLayout = new javax.swing.GroupLayout(jPanel_status);
        jPanel_status.setLayout(jPanel_statusLayout);
        jPanel_statusLayout.setHorizontalGroup(
                jPanel_statusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel_statusLayout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25))
                        .addGroup(jPanel_statusLayout.createSequentialGroup()
                                .addGap(136, 136, 136)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(99, 99, 99)
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_statusLayout.setVerticalGroup(
                jPanel_statusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_statusLayout.createSequentialGroup()
                                .addContainerGap(92, Short.MAX_VALUE)
                                .addGroup(jPanel_statusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(79, 79, 79)
                                .addGroup(jPanel_statusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(64, 64, 64))
        );
        /*
        jPanel_users.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_users.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Users", javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 16))); // NOI18N

        javax.swing.GroupLayout jPanel_usersLayout = new javax.swing.GroupLayout(jPanel_users);
        jPanel_users.setLayout(jPanel_usersLayout);
        jPanel_usersLayout.setHorizontalGroup(
            jPanel_usersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 947, Short.MAX_VALUE)
        );
        jPanel_usersLayout.setVerticalGroup(
            jPanel_usersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 516, Short.MAX_VALUE)
        );
         */
        jPanel_tools.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_tools.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "Tools", javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 15))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Network IP Address", javax.swing.border.TitledBorder.TRAILING, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 16))); // NOI18N
        jPanel1.setEnabled(false);

        jTextField_N_IP2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField_N_IP2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_N_IP2.setText("0");
        jTextField_N_IP2.addActionListener((java.awt.event.ActionEvent evt) -> {
            jTextField_N_IP2ActionPerformed(evt);
        });

        jTextField_N_IP3.setEditable(false);
        jTextField_N_IP3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField_N_IP3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_N_IP3.setText("254");
        jTextField_N_IP3.addActionListener((java.awt.event.ActionEvent evt) -> {
            jTextField_N_IP3ActionPerformed(evt);
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText(".");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jTextField_N_IP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField_N_IP.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_N_IP.setText("0");
        jTextField_N_IP.addActionListener((java.awt.event.ActionEvent evt) -> {
            jTextField_N_IPActionPerformed(evt);
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText(".");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText(".");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("/");
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jTextField_N_IP4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField_N_IP4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_N_IP4.setText("24");
        jTextField_N_IP4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_N_IP4ActionPerformed(evt);
            }
        });

        jTextField_N_IP1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField_N_IP1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_N_IP1.setText("0");
        jTextField_N_IP1.addActionListener((java.awt.event.ActionEvent evt) -> {
            jTextField_N_IP1ActionPerformed(evt);
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Ok");
        jButton1.addActionListener((java.awt.event.ActionEvent evt) -> {
            jButton1ActionPerformed(evt);
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jTextField_N_IP, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextField_N_IP1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel4)
                                                .addGap(6, 6, 6)
                                                .addComponent(jTextField_N_IP2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jButton1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addGap(6, 6, 6)
                                .addComponent(jTextField_N_IP3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addGap(6, 6, 6)
                                .addComponent(jTextField_N_IP4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextField_N_IP, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField_N_IP1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField_N_IP2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField_N_IP3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField_N_IP4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Network Alowed IPs", javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 16))); // NOI18N

        jList_network_alowed_ips.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jList_network_alowed_ips.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jList_network_alowed_ips);

        jBtn_add.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtn_add.setText("Add");
        jBtn_add.addActionListener((java.awt.event.ActionEvent evt) -> {
            jBtn_addActionPerformed(evt);
        });

        jBtn_add1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtn_add1.setText("Delete");
        jBtn_add1.addActionListener((java.awt.event.ActionEvent evt) -> {
            jBtn_add1ActionPerformed(evt);
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jBtn_add)
                                .addGap(18, 18, 18)
                                .addComponent(jBtn_add1)
                                .addGap(83, 83, 83))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                                .addGap(22, 22, 22))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jBtn_add)
                                        .addComponent(jBtn_add1))
                                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Open Ports", javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 16))); // NOI18N

        jList_port_scan.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jList_port_scan.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(jList_port_scan);

        jBtn_add2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtn_add2.setText("Scan");
        jBtn_add2.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_add2ActionPerformed(evt);
            }
        });

        jBtn_add3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtn_add3.setText("Delete");
        jBtn_add3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_add3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addGap(68, 68, 68)
                                                .addComponent(jBtn_add2)
                                                .addGap(18, 18, 18)
                                                .addComponent(jBtn_add3)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jBtn_add2)
                                        .addComponent(jBtn_add3))
                                .addContainerGap())
        );

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "blocked Sites", javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 16))); // NOI18N

        jList_blockSites.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jList_blockSites.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane5.setViewportView(jList_blockSites);

        jBtn_add8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtn_add8.setText("Add");
        jBtn_add8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_add8ActionPerformed(evt);
            }
        });

        jBtn_add9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtn_add9.setText("Delete");
        jBtn_add9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_add9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
                jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jBtn_add8)
                                .addGap(18, 18, 18)
                                .addComponent(jBtn_add9)
                                .addGap(83, 83, 83))
                        .addGroup(jPanel11Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
                jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jBtn_add8)
                                        .addComponent(jBtn_add9))
                                .addGap(13, 13, 13))
        );

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "blocked Programs", javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 16))); // NOI18N

        jList_blockPrograms.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jList_blockPrograms.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane6.setViewportView(jList_blockPrograms);

        jBtn_add10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtn_add10.setText("Add");
        jBtn_add10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_add10ActionPerformed(evt);
            }
        });

        jBtn_add11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBtn_add11.setText("Delete");
        jBtn_add11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_add11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
                jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel13Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel13Layout.createSequentialGroup()
                                                .addGap(46, 46, 46)
                                                .addComponent(jBtn_add10)
                                                .addGap(18, 18, 18)
                                                .addComponent(jBtn_add11)))
                                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
                jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jBtn_add10)
                                        .addComponent(jBtn_add11))
                                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout jPanel_toolsLayout = new javax.swing.GroupLayout(jPanel_tools);
        jPanel_tools.setLayout(jPanel_toolsLayout);
        jPanel_toolsLayout.setHorizontalGroup(
                jPanel_toolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel_toolsLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel_toolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel_toolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_toolsLayout.setVerticalGroup(
                jPanel_toolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel_toolsLayout.createSequentialGroup()
                                .addGroup(jPanel_toolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel_toolsLayout.createSequentialGroup()
                                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                        .addGroup(jPanel_toolsLayout.createSequentialGroup()
                                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(0, 0, 102));

        jLabel_close.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGES/x.png"))); // NOI18N
        jLabel_close.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel_closeMousePressed(evt);
            }
        });

        jLabel_appName.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        jLabel_appName.setForeground(new java.awt.Color(255, 255, 0));
        jLabel_appName.setText("N-Securty");
        jLabel_appName.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
                jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel12Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel_appName, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 793, Short.MAX_VALUE)
                                .addComponent(jLabel_close)
                                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
                jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel_close, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                        .addComponent(jLabel_appName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel_containerLayout = new javax.swing.GroupLayout(jPanel_container);
        jPanel_container.setLayout(jPanel_containerLayout);
        jPanel_containerLayout.setHorizontalGroup(
                jPanel_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel_containerLayout.createSequentialGroup()
                                .addComponent(jPanel_menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel_status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_containerLayout.createSequentialGroup()
                                        .addGap(0, 203, Short.MAX_VALUE)
                                        .addComponent(jPanel_users, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_containerLayout.createSequentialGroup()
                                        .addGap(0, 206, Short.MAX_VALUE)
                                        .addComponent(jPanel_tools, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel_containerLayout.setVerticalGroup(
                jPanel_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel_menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel_containerLayout.createSequentialGroup()
                                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel_status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(jPanel_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_containerLayout.createSequentialGroup()
                                        .addGap(0, 30, Short.MAX_VALUE)
                                        .addComponent(jPanel_users, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_containerLayout.createSequentialGroup()
                                        .addGap(0, 29, Short.MAX_VALUE)
                                        .addComponent(jPanel_tools, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel_container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel_container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel_closeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_closeMousePressed
        this.setVisible(false);
    }//GEN-LAST:event_jLabel_closeMousePressed

    private void jLabel9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MousePressed

    }//GEN-LAST:event_jLabel9MousePressed

    private void jLabel_appLogoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_appLogoMousePressed
        // TODO add your handling code here:

    }//GEN-LAST:event_jLabel_appLogoMousePressed

    private void jTextField_N_IPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_N_IPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_N_IPActionPerformed

    private void jTextField_N_IP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_N_IP1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_N_IP1ActionPerformed

    private void jTextField_N_IP2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_N_IP2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_N_IP2ActionPerformed

    private void jTextField_N_IP3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_N_IP3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_N_IP3ActionPerformed

    private void jTextField_N_IP4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_N_IP4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_N_IP4ActionPerformed

    private void jBtn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_addActionPerformed
        String input = JOptionPane.showInputDialog(this, "Add new IP to Alowed Rule :");
        Pattern pat = Pattern.compile("[0-2]?[0-9]?[0-9].[0-2]?[0-9]?[0-9].[0-2]?[0-9]?[0-9].[0-2]?[0-9]?[0-9]");
        if (input != null) {
            Matcher mat = pat.matcher(input);
            if (mat.matches()) {
                try {
                    String filename = "ips.nSec";
                    try (FileWriter fw = new FileWriter(filename, true)) {
                        ArrayList all_IPs = new ArrayList();
                        for (int i = 0; i < listModel.getSize(); i++) {
                            all_IPs.add(listModel.getElementAt(i).toString().trim());
                        }
                        if (all_IPs.contains(input.trim())) {
                            JOptionPane.showMessageDialog(null,
                                    "this IP Adress is already exist",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            listModel.addElement(input);
                            jList_network_alowed_ips.setModel(listModel);
                            fw.write(input + "\n");
                        }
                    }
                } catch (IOException e) {
                    System.err.println("IOException: " + e.getMessage());
                }

            } else {
                JOptionPane.showMessageDialog(rootPane, "Please enter a valid IP Adress");
            }
        }
    }//GEN-LAST:event_jBtn_addActionPerformed

    private void jBtn_add1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_add1ActionPerformed
        int selectedIndex = jList_network_alowed_ips.getSelectedIndex();
        String str2delete = jList_network_alowed_ips.getSelectedValue();
        File ips = new File("ips.nSec");
        File tmpips = new File("tmp.nSec");
        try {
            BufferedWriter file_writer;
            try (Scanner file_reader = new Scanner(ips)) {
                file_writer = new BufferedWriter(new FileWriter(tmpips));
                ArrayList input = new ArrayList();
                while (file_reader.hasNext()) {
                    input.add(file_reader.nextLine().trim());
                }
                if (input.contains(str2delete)) {
                    input.remove(str2delete);
                }
                for (int i = 0; i < input.size(); i++) {
                    file_writer.write(input.get(i) + "\n");
                }
                file_reader.close();
            }
            file_writer.close();
            ips.delete();
            tmpips.renameTo(ips);

        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
        if (selectedIndex != -1) {
            listModel.removeElementAt(selectedIndex);
        }
    }//GEN-LAST:event_jBtn_add1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        number0fPC.setText("Refreshing");
        String input = this.jTextField_N_IP.getText();
         String input2 = this.jTextField_N_IP1.getText();
         String input3 =this.jTextField_N_IP2.getText();
        try {
            String filename = "Nip.nSec";
            try (FileWriter fw = new FileWriter(filename)) {
                fw.write(input+"\n"+input2+"\n"+input3);
                fw.close();
            }
            refresh();
        } catch (IOException io) {

        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jBtn_add3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_add3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtn_add3ActionPerformed

    private void jBtn_add2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_add2ActionPerformed
        npotrScan();
    }//GEN-LAST:event_jBtn_add2ActionPerformed

    private void jBtn_add8ActionPerformed(java.awt.event.ActionEvent evt) {//GAdd new Site to block Rules
        String input = JOptionPane.showInputDialog(this, "Add new Site to block Rules :");
        if (input != null) {
            try {
                String filename = "site.nSec";
                try (FileWriter fw = new FileWriter(filename, true)) {
                    ArrayList all_sites = new ArrayList();
                    for (int i = 0; i < listModelSites.getSize(); i++) {
                        all_sites.add(listModelSites.getElementAt(i).toString().trim());
                    }
                    if (all_sites.contains(input.trim())) {
                        JOptionPane.showMessageDialog(null,
                                "this  Adress is already exist",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        listModelSites.addElement(input);
                        jList_blockSites.setModel(listModelSites);
                        fw.write(input + "\n");
                        fw.close();
                        //block site 
                        BlockSite.blockSite(input.trim());

                    }
                }
            } catch (IOException e) {
                System.err.println("IOException: " + e.getMessage());
            }

        } else {
            JOptionPane.showMessageDialog(rootPane, "Please enter a valid IP Adress");
        }
    }//GEN-LAST:event_jBtn_add8ActionPerformed

    private void jBtn_add9ActionPerformed(java.awt.event.ActionEvent evt) {//delete  Site from block Rules
        int selectedIndex = jList_blockSites.getSelectedIndex();
        String str2delete = jList_blockSites.getSelectedValue();
        File site = new File("C:\\Windows\\System32\\drivers\\etc\\hosts");
        File tmp = new File("C:\\Windows\\System32\\drivers\\etc\\hosts.nsec");
        try {
            BufferedWriter file_writer;
            try (Scanner file_reader = new Scanner(site)) {
                file_writer = new BufferedWriter(new FileWriter(tmp));
                ArrayList input = new ArrayList();
                while (file_reader.hasNext()) {
                    input.add(file_reader.nextLine().trim());
                }
                if (input.contains(str2delete)) {
                    input.remove(str2delete);
                }
                for (int i = 0; i < input.size(); i++) {
                    file_writer.write(input.get(i) + "\n");
                }
                file_reader.close();
            }
            file_writer.close();
            site.delete();
            tmp.renameTo(site);

        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
        if (selectedIndex != -1) {
            listModelSites.removeElementAt(selectedIndex);
        }
    }//delete  Site from block Rules

    private void jBtn_add10ActionPerformed(java.awt.event.ActionEvent evt) {//Add new Program to block Rules
        String input = JOptionPane.showInputDialog(this, "Add new Program to block Rules :");
        if (input != null) {
            try {
                String filename = "prog.nSec";
                try (FileWriter fw = new FileWriter(filename, true)) {
                    ArrayList all_prog = new ArrayList();
                    for (int i = 0; i < listModelPrograms.getSize(); i++) {
                        all_prog.add(listModelPrograms.getElementAt(i).toString().trim());
                    }
                    if (all_prog.contains(input.trim())) {
                        JOptionPane.showMessageDialog(null,
                                "this  Adress is already exist",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        listModelPrograms.addElement(input);
                        jList_blockPrograms.setModel(listModelPrograms);
                        fw.write(input + "\n");
                        fw.close();
                        //block programs 
                        BlockPrograms.blockPrograms(100, input.trim());
                    }
                }
            } catch (IOException e) {
                System.err.println("IOException: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Please enter a valid program Name");
        }
    }//Add new Program to block Rules

    private void jBtn_add11ActionPerformed(java.awt.event.ActionEvent evt) {//Delete Program
        int selectedIndex = jList_blockPrograms.getSelectedIndex();
        String str2delete = jList_blockPrograms.getSelectedValue();
        File prog = new File("prog.nSec");
        File tmp = new File("tmp.nSec");
        try {
            BufferedWriter file_writer;
            try (Scanner file_reader = new Scanner(prog)) {
                file_writer = new BufferedWriter(new FileWriter(tmp));
                ArrayList input = new ArrayList();
                while (file_reader.hasNext()) {
                    input.add(file_reader.nextLine().trim());
                }
                if (input.contains(str2delete)) {
                    input.remove(str2delete);
                }
                for (int i = 0; i < input.size(); i++) {
                    file_writer.write(input.get(i) + "\n");
                }
                file_reader.close();
            }
            file_writer.close();
            prog.delete();
            tmp.renameTo(prog);

        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
        if (selectedIndex != -1) {
            listModelPrograms.removeElementAt(selectedIndex);
        }
    }//Delete Program

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>
        Dashboard_Form d = new Dashboard_Form();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            d.Tray();
            d.setVisible(true);
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel hostName;
    private javax.swing.JButton jBtn_add;
    private javax.swing.JButton jBtn_add1;
    private javax.swing.JButton jBtn_add10;
    private javax.swing.JButton jBtn_add11;
    private javax.swing.JButton jBtn_add2;
    private javax.swing.JButton jBtn_add3;
    private javax.swing.JButton jBtn_add8;
    private javax.swing.JButton jBtn_add9;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_Date;
    private javax.swing.JLabel jLabel_Time;
    private javax.swing.JLabel jLabel_appLogo;
    private javax.swing.JLabel jLabel_appName;
    private javax.swing.JLabel jLabel_close;
    private javax.swing.JLabel jLabel_menuItem2;
    private javax.swing.JLabel jLabel_menuItem4;
    private javax.swing.JLabel jLabel_menuItem_Status;
    private javax.swing.JList<String> jList_blockPrograms;
    private javax.swing.JList<String> jList_blockSites;
    private javax.swing.JList<String> jList_network_alowed_ips;
    private javax.swing.JList<String> jList_port_scan;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel_container;
    private javax.swing.JPanel jPanel_logoANDname;
    private javax.swing.JPanel jPanel_menu;
    private javax.swing.JPanel jPanel_status;
    private javax.swing.JPanel jPanel_tools;
    private javax.swing.JPanel jPanel_users;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextField jTextField_N_IP;
    private javax.swing.JTextField jTextField_N_IP1;
    private javax.swing.JTextField jTextField_N_IP2;
    private javax.swing.JTextField jTextField_N_IP3;
    private javax.swing.JTextField jTextField_N_IP4;
    private javax.swing.JLabel localIP;
    private javax.swing.JLabel number0fPC;
    // End of variables declaration//GEN-END:variables
}
