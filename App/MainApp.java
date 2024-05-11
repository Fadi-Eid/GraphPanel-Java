package App;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class MainApp extends JFrame{
	
	public MainApp(GraphPanel canvas) {
		setLayout(new BorderLayout());
		add(canvas, BorderLayout.CENTER);
	}
	
	public static int noisy_sin(int t) {
        return (int)(Math.sin(2 * Math.PI * (1 / 800.0) * t) * generateRandom(60, 120) + 130.0) + generateRandom(-8, 16);
    }

    public static int generateRandom(int min, int max) {
        Random random = new Random();
        // Generate a random integer between min and max
        int noise = random.nextInt(max - min + 1) + min;
        return noise;
    }
	
		
	public static void main(String[] args) {
		GraphPanel canvas = new GraphPanel();
		MainApp frame = new MainApp(canvas);
		frame.setSize(700, 300);
		frame.setTitle("PC Probe");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		int i = 0;
		for(i=0; i<25000; i++) {   
			int y = noisy_sin(i);
			canvas.addPoint(y);
			canvas.updateGraph();
			try {
				Thread.sleep(1, 0);
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}
