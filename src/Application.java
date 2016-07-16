import javax.swing.JFrame;

public class Application {
	public static final int WINDOW_WIDTH = 600;
	public static final int WINDOW_HEIGHT = 600;
	public static final int ROWS = 25;
	public static final int COLUMNS = 25;
	public static int CELL_WIDTH = WINDOW_WIDTH / COLUMNS;
	public static int CELL_HEIGHT = WINDOW_HEIGHT / ROWS;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame ("Fourmis");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		MainPanel panel = new MainPanel();
		frame.add(panel);
		
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
//		frame.pack();
		frame.setVisible(true);
	}

}
