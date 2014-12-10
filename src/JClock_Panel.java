

import java.awt.*;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class JClock_Panel extends JPanel {

	static JLabel timeLabel;	
	Date now = new Date();
	
	Point p;
	
	JClock_Panel() {
		setLayout(null);
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

		setBounds(gd.getDisplayMode().getWidth() / 2 - ((int) (Options.fontSize * 4)/2), 200, (int) (Options.fontSize * 4), (int) (Options.fontSize));
		setBackground(new Color(0, 0, 0, 0));
		setOpaque(false);

		timeLabel = new JLabel("00:00pm");
		timeLabel.setBounds(1, 0, getWidth(),getHeight());
		timeLabel.setFont (timeLabel.getFont ().deriveFont (Options.fontSize));//change font size
		timeLabel.setBackground(new Color(0,0,0,0));
		timeLabel.setOpaque(false);
		add(timeLabel);
	
		new Thread(new Runnable() {			
			@Override
			public void run() {
				while(true){
					try{
						timeLabel.setText(Options.clockStyle.format(now));
                        timeLabel.setForeground(Options.color);
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
