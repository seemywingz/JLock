import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by Kevin Jayne on 12/19/2014.
 */
public class LockTimer extends JPanel{

    JLabel timerLabel;

    LockTimer(){

        if(Options.showTimer){
            setLayout(null);
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

            setBounds(gd.getDisplayMode().getWidth() / 2 - ((int) (Options.timerFontSize * 4)/2),
                    (int)(250+Options.bannerFontSize),
                    (int) (Options.timerFontSize * 5),
                    (int) (Options.timerFontSize));

            setBackground(new Color(0, 0, 0, 0));
            setOpaque(false);

            timerLabel = new JLabel(convertSecondsToClockFormat(Options.countDown));
            timerLabel.setBounds(1, 0, getWidth(),getHeight());
            timerLabel.setFont(timerLabel.getFont().deriveFont(Options.timerFontSize));//change font size
            timerLabel.setForeground(Options.timerColor);
            timerLabel.setBackground(new Color(0,0,0,0));
            timerLabel.setOpaque(false);
            add(timerLabel);
        }

        startThread();
    }//..

    protected String convertSecondsToClockFormat(int seconds){
        int m = seconds/60,
            s = seconds-(60*m);

        if(s>=10)
            return m+":"+s;
        else
            return m+":0"+s;
    }//..

    protected void shutdown(){
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("shutdown -s -t 0");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//..

    protected void startThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        Options.countDown--;
                        if(Options.countDown <= 0){
                            if(Options.shutdownOnTimeout)
                                shutdown();
                            System.exit(0);
                        }
                        timerLabel.setText(convertSecondsToClockFormat(Options.countDown));
                        Thread.sleep(1000);
                    }catch (Exception e){

                    }
                }
            }
        }).start();
    }//..

}// end LockTimer
