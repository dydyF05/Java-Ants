import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class ConfigurationPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Dimension inputDimension = new Dimension(20, 10);
	public static enum Elem{OBSTACLE, NEST, FOOD, CLEAR;}
	public static Elem el = Elem.FOOD;
	
	ConfigurationPanel(){
		setLayout( new BorderLayout() );
		this.setBackground(Color.LIGHT_GRAY);

		this.addCheckbboxes();
	}
//	Ajoute les choix de configuration d'environement
	private void addCheckbboxes(){
		JPanel blockPanel = new JPanel();
		blockPanel.setLayout(new BoxLayout(blockPanel, BoxLayout.X_AXIS));
		blockPanel.setBorder(BorderFactory.createTitledBorder("Choisis quoi placer"));		
		
		JRadioButton obstacle = new JRadioButton("Obstacle");
		obstacle.setFocusable(false);
		obstacle.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				ConfigurationPanel.el = ConfigurationPanel.Elem.OBSTACLE;
			}
		});
		JRadioButton nest = new JRadioButton("Nid");
		nest.setFocusable(false);
		nest.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				ConfigurationPanel.el = ConfigurationPanel.Elem.NEST;
			}
		});
		JRadioButton food = new JRadioButton("Bouffe");
		food.setFocusable(false);
		food.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				ConfigurationPanel.el = ConfigurationPanel.Elem.FOOD;
			}
		});
		JRadioButton clear = new JRadioButton("Clear");
		clear.setFocusable(false);
		clear.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				ConfigurationPanel.el = ConfigurationPanel.Elem.CLEAR;
			}
		});
		
		
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(obstacle);
		bg.add(nest);
		bg.add(food);
		bg.add(clear);
		food.setSelected(true);
		
		blockPanel.add(obstacle);
		blockPanel.add(nest);
		blockPanel.add(food);
		blockPanel.add(clear);
		
		blockPanel.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		
		add(blockPanel);
		
	}
	
	
}
