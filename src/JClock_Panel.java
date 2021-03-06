

import java.awt.*;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class JClock_Panel extends JPanel {

	static JLabel timeLabel;	
	Date now = new Date();
	
	JClock_Panel() {
		setLayout(null);
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

		setBounds(gd.getDisplayMode().getWidth() / 2 - ((int) (Options.clockFontSize * 4)/2),
                  (int)(200-Options.bannerFontSize),
                  (int) (Options.clockFontSize * 5),
                  (int) (Options.clockFontSize));

		setBackground(new Color(0, 0, 0, 0));
		setOpaque(false);

		timeLabel = new JLabel("00:00pm");
		timeLabel.setBounds(1, 0, getWidth(),getHeight());
		timeLabel.setFont (timeLabel.getFont ().deriveFont (Options.clockFontSize));//change font size
		timeLabel.setBackground(new Color(0,0,0,0));
        timeLabel.setForeground(Options.clockColor);
		timeLabel.setOpaque(false);
		add(timeLabel);
	
		new Thread(new Runnable() {			
			@Override
			public void run() {
				while(true){
					try{
						timeLabel.setText(Options.clockStyle.format(now));
						now = new Date();
						repaint();
						Thread.sleep(1000);
					}catch(Exception e){}
				}
			}
		}).start();
	}//..

	@Override
	protected void paintComponent(Graphics g) {		
		super.paintComponent(g);
		g.clearRect(0, 0, getWidth(), getHeight());
	}//..		
	
}// end ClockPanel
