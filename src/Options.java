import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Vector;

public final class Options {

	static boolean showBSOD =false,
			       showClock=false,
	               showDesktop=false,
                   showBanner=false;

    static String bannerMessage="";

	static int formatNum = 2;

	static float clockFontSize = 12,
                 bannerFontSize = 12;

	final static String timeFormat[] = {"h:mm","hh:mm","h:mm a","hh:mm a"};
	
	static SimpleDateFormat clockStyle = new SimpleDateFormat(timeFormat[formatNum]);

    static Color clockColor = Color.BLACK,
                 bannerColor = Color.BLACK;

    public static Color mkColor(String rgb){
        String c[] = rgb.split(",");
        return new Color(Float.parseFloat(c[0]),Float.parseFloat(c[1]),Float.parseFloat(c[2]));
    }//..

}// end Options
