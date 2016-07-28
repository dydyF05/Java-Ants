import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfosFrame extends JFrame{
    
    InfosFrame(String name){
        
            InfosPanel infos = new InfosPanel();
            this.setTitle(name);
            this.setSize(300, 200);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setLocationRelativeTo(null);
            this.setContentPane(infos);

            this.setVisible(true);
    }
    
    public class InfosPanel extends JPanel{
    
    InfosPanel(){
		setLayout( new BorderLayout() );
		this.setBackground(Color.LIGHT_GRAY);
		this.addTextFields();
        this.setVisible(true);
	}
//	Ajoute les choix de configuration d'environement
	private void addTextFields(){
                JPanel container = new JPanel();
		JPanel antsPanel = new JPanel();
                JPanel pheromonePanel = new JPanel();
                JPanel foodPanel = new JPanel();
                JPanel submitPanel = new JPanel();
                
                JLabel labelAnts = new JLabel("Nombre de fourmis : ");
                JLabel labelPheromones = new JLabel("Taux d'évaporation : ");
                JLabel labelFood = new JLabel("Quantité de bouffe par tas : ");
               
                JFormattedTextField nbAnts = new JFormattedTextField();
                JFormattedTextField nbPheromones = new JFormattedTextField();
                JFormattedTextField nbFood = new JFormattedTextField();
                
                JButton submitButton = new JButton();
                submitButton.setText("Valider");
                submitButton.addActionListener(new ButtonListener(container,nbAnts, nbPheromones, nbFood));
                
                nbAnts.setPreferredSize(new Dimension(75,20));
                nbPheromones.setPreferredSize(new Dimension(75,20));
                nbFood.setPreferredSize(new Dimension(75,20));
                                
                
                antsPanel.add(labelAnts);
                antsPanel.add(nbAnts);
                pheromonePanel.add(labelPheromones);
                pheromonePanel.add(nbPheromones);
                foodPanel.add(labelFood);
                foodPanel.add(nbFood);
                submitPanel.add(submitButton);
                
                container.setLayout(null);
                container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
                
                container.add(antsPanel);
                container.add(pheromonePanel);
                container.add(foodPanel);
                container.add(submitButton);
                
                this.add(container);

	}

        private JFormattedTextField JFormattedTextField(NumberFormat percentInstance) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    public class ButtonListener implements ActionListener{
    
    public JPanel panel;
    public JFormattedTextField nbAnts;
    public JFormattedTextField nbPheromone;
    public JFormattedTextField nbFood;
    
    ButtonListener(JPanel p, JFormattedTextField a, JFormattedTextField b, JFormattedTextField c){
        
        this.panel = p;
        this.nbAnts = a;
        this.nbPheromone = b;
        this.nbFood = c;
    }
    public boolean isPourcentage(String s){
    	int num = Integer.parseInt(s); 
//    	Le taux d'évaporation doit etre entre 1 et 90%
    	if(num < 1 || num > 90)
    		return false;
    	return true;
    }
    public boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(isInteger(this.nbAnts.getText())){
            Application.antsNumber = Integer.parseInt(this.nbAnts.getText());
        }else{
            /** valeur par défaut dans Application **/
        }
        
        if(isInteger(this.nbPheromone.getText()) && isPourcentage(this.nbPheromone.getText())){
            double evap = Integer.parseInt(this.nbPheromone.getText());
            evap = evap / 100;
            evap = 1 - evap;
            System.out.println(evap);
            CellPanel.rateEvaporation = evap;
        }else{
            /** valeur par défaut d'évaporation dans CellPanel **/
        }
        
        if(isInteger(this.nbFood.getText())){
            /** STOCKER this.nbFood.getText() **/
        	Application.maxFoodPerCell = Integer.parseInt(this.nbFood.getText());
        }else{
            /** METTRE LA VALEUR PAR DEFAUT **/
        }
        
        Application.createAntFrame();
    }

  }
}
