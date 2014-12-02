import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Marist User on 12/2/2014.
 */
public class LockFrame extends JDialog {

    String unlockPhrase = "unlock";
    int phraseIDX = 0;
    Robot rob;
    JLabel bg;
    ImageIcon bsod, screenShot;

    LockFrame(){
        setAlwaysOnTop(true);
        setUndecorated(true);
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        setBounds(0, 0, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
        addMouseListener(mkMouseAdapter());
        addMouseMotionListener(mkMouseAdapter());
        addKeyListener(mkKeyadapter());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setFocusable(true);
        requestFocus();
        hideCursor();
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


        bg = new JLabel(screenShot);
        bg.setBounds(0,0,getWidth(),getHeight());
        add(bg);
        setVisible(true);
    }//..

    protected void startThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    repaint();
                    try {
                        Thread.sleep(20);
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
                bg.setIcon(bsod);
                robotControls();

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

    protected void hideCursor(){
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");
        setCursor(blankCursor);
    }//..

    protected void showCursor(){
        setCursor(Cursor.getDefaultCursor());
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