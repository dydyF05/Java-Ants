import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ControlPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JLabel stepLabel = new JLabel();
	private static JLabel foodLeft = new JLabel();
	private static JLabel foodInNest = new JLabel();
	private static JLabel nestPhers = new JLabel();
	private static JLabel foodPhers = new JLabel();

	ControlPanel(){
		setLayout( new BorderLayout() );
		this.setBackground(Color.LIGHT_GRAY);
		foodLeft.setForeground(Color.RED);
		this.addBtns();
	}
	private static double getRoundedDouble(double num){
		num = 100*num;
		num =  Math.round(num);
		num = (double) num;
		num = num/100;
		return num;
	}
	public static void udpateSteps(){
		stepLabel.setText("step: "+Application.pasSimulation);
		foodLeft.setText(" - foodLeft: "+Application.totalFoodInEnvironment);
		if(Application.nestCell != null)
			foodInNest.setText(" - foodInNest: "+Application.nestCell.foodLeft);
		foodPhers.setText(" - pherF: "+getRoundedDouble(Application.totalFoodPheromons));
		nestPhers.setText(" - pherN: "+getRoundedDouble(Application.totalNestPheromons));
	}
	private void addBtns(){
		JPanel blockPanel = new JPanel();
		blockPanel.setBackground(Color.LIGHT_GRAY);
		blockPanel.setLayout(new BoxLayout(blockPanel, BoxLayout.X_AXIS));
		blockPanel.setBorder(BorderFactory.createTitledBorder("Controleurs"));		
		
		JButton play = new JButton("Play");
		play.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {				
				if(Application.nestCell != null && !Application.foodSet.isEmpty()){
					Application.paused = false;
					Application.moveOneStep();
				}
				else
					System.out.println("L'appli a besoin d'au moins une source de nourriture et un nid !");
			}
		});
		JButton pause = new JButton("Pause");
		pause.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Application.paused = true;
			}
		});
		JButton step = new JButton("Step");
		step.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				udpateSteps();
//				System.out.println("click sur step !");
				if(Application.nestCell != null && !Application.foodSet.isEmpty()){
//					Si on l'application est lancée pour la première fois
					Application.paused = true;
					Application.moveOneStep();
				}
				else
					System.out.println("L'appli a besoin d'au moins une source de nourriture et un nid !");
			}
		});
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(play);
		bg.add(pause);
		bg.add(step);
		
		blockPanel.add(play);
		blockPanel.add(pause);
		blockPanel.add(step);
		blockPanel.add(stepLabel);
		blockPanel.add(foodLeft);
		blockPanel.add(foodInNest);
		blockPanel.add(foodPhers);
		blockPanel.add(nestPhers);
		
		blockPanel.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		
		add(blockPanel);
	}
}
