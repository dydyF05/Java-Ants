import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class ControlPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ControlPanel(){
		setLayout( new BorderLayout() );
		this.setBackground(Color.LIGHT_GRAY);

		this.addBtns();
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
		
		blockPanel.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		
		add(blockPanel);
	}
}
