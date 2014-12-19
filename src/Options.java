import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Vector;

public final class Options {

    // Toggles
	static boolean showBSOD =false,
			       showClock=false,
	               showDesktop=true,
                   showBanner=false,
                   enableTimer=false;

    // Meassages
    static String bannerMessage="";


    // Font sizes
	static float clockFontSize = 12,
                 bannerFontSize = 12;

    // Font colors
    static Color clockColor = Color.BLACK,
            bannerColor = Color.RED;

    // Timer Format
    static int countDown = 0;

    // Clock Format
    static int formatNum = 2;
    final static String timeFormat[] = {"h:mm","hh:mm","h:mm a","hh:mm a"};
	static SimpleDateFormat clockStyle = new SimpleDateFormat(timeFormat[formatNum]);

    public static void setWaitTime(String waitTime){
        try {
            String t[] = waitTime.split(":");
            if(t.length == 1){
                countDown=Integer.parseInt(t[0]);
            }else if(t.length==2){
                countDown=Integer.parseInt(t[0])*60;
                countDown+=Integer.parseInt(t[1]);
            }
        }catch (Exception e){
            countDown = 1;
        }

    }//..

    public static Color mkColor(String rgb){
        try {
            String c[] = rgb.split(",");
            return new Color(Float.parseFloat(c[0]), Float.parseFloat(c[1]), Float.parseFloat(c[2]));
        }catch (Exception e){
            return Color.black;
        }
    }//..

}// end Options
