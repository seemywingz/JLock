import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Vector;

public final class Options {

	static boolean showBSOD = true,
			       showClock=false,
	               showDesktop=true;

	static int formatNum = 2;
	static float fontSize = 60;

	final static String timeFormat[] = {"h:mm","hh:mm","h:mm a","hh:mm a"};
	
	static SimpleDateFormat clockStyle = new SimpleDateFormat(timeFormat[formatNum]);

    static Color color = Color.BLACK;

}// end Options
