import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Vector;

/**
 * Created by Kevin Jayne on 12/2/2014.
 */
public class LockFrame extends JDialog {

    String unlockPhrase = "unlock";
    int phraseIDX = 0;
    Robot rob;
    JLabel bg;
    ImageIcon bsod, screenShot;
    static BufferedReader conf;
    GraphicsDevice gd;


    LockFrame(){
        readConfigFile();
        setAlwaysOnTop(true);
        setUndecorated(true);

        gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        setBounds(0, 0, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());

        addMouseListener(mkMouseAdapter());
        addMouseMotionListener(mkMouseAdapter());
        addKeyListener(mkKeyadapter());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setFocusable(true);
        requestFocus();
        hideCursor();

        createRobot();

        if(Options.showBanner){
            showBanner();
        }

        if(Options.showClock){
            add(new JClock_Panel(), BorderLayout.CENTER);
        }

        if(Options.showDesktop) {
            setBackground(new Color(0, 0, 0, 0));
            bg = new JLabel(screenShot);
        }else {
            setBackground(Color.BLACK);
            bg = new JLabel(" ");
        }


        bg.setBounds(0,0,getWidth(),getHeight());
        add(bg);
        setVisible(true);
    }//..

    protected void readConfigFile(){
        try {
            String line;
            Vector<String> entries = new Vector<String>();
            conf = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/config/jlock.conf")));

            while ((line = conf.readLine()) != null){// load all config file entries into vector
                entries.add(line);
            }

            for(String s:entries){
                String[] setting = s.split("=");// parse config file entries into useable parts
                if(setting[0].equals("unlockphrase")){
                    unlockPhrase=setting[1];
                }
                if(setting[0].equals("showBSOD")){
                    Options.showBSOD=Boolean.valueOf(setting[1]);
                }
                if(setting[0].equals("showClock")){
                    Options.showClock=Boolean.valueOf(setting[1]);
                }
                if(setting[0].equals("clockFontSize")){
                    Options.clockFontSize =Float.parseFloat(setting[1]);
                }
                if(setting[0].equals("showDesktop")){
                    Options.showDesktop=Boolean.valueOf(setting[1]);
                }
                if(setting[0].equals("showBanner")){
                    Options.showBanner=Boolean.valueOf(setting[1]);
                }
                if(setting[0].equals("bannerMessage")){
                    Options.bannerMessage=setting[1];
                }
                if(setting[0].equals("bannerFontSize")){
                    Options.bannerFontSize=Float.parseFloat(setting[1]);
                }
            }

            System.out.println("Passphrase: "+unlockPhrase);
            System.out.println("showDesktop: "+Options.showDesktop);
            System.out.println("showBSOD: "+Options.showBSOD);
            System.out.println("showClock: "+Options.showClock);
            System.out.println("showBanner: "+Options.showBanner);
            System.out.println("bannerMessage: "+Options.bannerMessage);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//..

    protected void startThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    repaint();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }//..

    protected KeyAdapter mkKeyadapter(){
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                robotControls();

                if(Options.showBSOD)
                    bg.setIcon(bsod);

                if( unlockPhrase.charAt(phraseIDX)== e.getKeyChar()){
                    phraseIDX++;
                    if(phraseIDX == unlockPhrase.length()){
                        System.exit(0);
                    }
                }else{
                    phraseIDX=0;
                }
                /*if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    System.exit(0);
                }//*/
            }
        };
    }//..

    protected MouseAdapter mkMouseAdapter(){
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                robotControls();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                robotControls();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                robotControls();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                robotControls();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                robotControls();
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                robotControls();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                robotControls();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                robotControls();
            }
        };
    }//..

    protected void showBanner(){
        JLabel banner = new JLabel(Options.bannerMessage);
        banner.setBounds(gd.getDisplayMode().getWidth() / 2 - ((int) (Options.bannerFontSize * (Options.bannerMessage.length()/2))/2),
                200,
                (int) (Options.bannerFontSize * Options.bannerMessage.length()),
                (int) (Options.bannerFontSize)+(int)(Options.bannerFontSize*.25));
        banner.setFont (banner.getFont ().deriveFont (Options.bannerFontSize));//change font size
        add(banner);
    }//..

    protected void hideCursor(){
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");
        setCursor(blankCursor);
    }//..

    protected void showCursor(){
        setCursor(Cursor.getDefaultCursor());
    }//..

    protected void createRobot(){// creates robot and takes a screenshot
        try {
            rob = new Robot();

            String screenshotLocation = System.getProperty("user.home")+"\\AppData\\screenShot.png";
            System.out.println(screenshotLocation);
            BufferedImage image = rob.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ImageIO.write(image, "png", new File(screenshotLocation));//*/
            screenShot = new ImageIcon(screenshotLocation);

            int rand = (int)(Math.random()*2)+1;
            bsod = new ImageIcon(ImageIO.read(getClass().getResource("/img/"+rand+".png")));

            startThread();

        } catch (Exception e){}
    }//..

    protected void robotControls(){
        rob.mouseMove(getWidth()/2, getHeight()/2);
        rob.keyRelease(KeyEvent.VK_ALT);
        rob.keyRelease(KeyEvent.VK_TAB);
        rob.keyRelease(KeyEvent.VK_WINDOWS);
        rob.keyRelease(KeyEvent.VK_CONTROL);
    }//...

    public static void main(String[] args) {
        new LockFrame();
    }
}
