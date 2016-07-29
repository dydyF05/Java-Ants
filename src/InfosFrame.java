import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
        JPanel speedPanel = new JPanel();
        JPanel submitPanel = new JPanel();
                
        JLabel labelAnts = new JLabel("Nombre de fourmis : ");
        JLabel labelPheromones = new JLabel("Taux d'évaporation : ");
        JLabel labelFood = new JLabel("Quantité de bouffe par tas : ");
        JLabel labelSpeed = new JLabel("Vitesse de l'appli : ");
               
        JFormattedTextField nbAnts = new JFormattedTextField();
        JFormattedTextField nbPheromones = new JFormattedTextField();
        JFormattedTextField nbFood = new JFormattedTextField();
        JFormattedTextField speed = new JFormattedTextField();
        
        JButton submitButton = new JButton();
        submitButton.setText("Valider");
        submitButton.addActionListener(new ButtonListener(container,nbAnts, nbPheromones, nbFood, speed));
        
        JButton saveButton = new JButton();
        saveButton.setText("Sauvegarder");
        saveButton.addActionListener(new SaveListener(container,nbAnts, nbPheromones, nbFood, speed));
        
        JButton loadButton = new JButton();
        loadButton.setText("Charger");
        loadButton.addActionListener(new LoadListener());
        
        nbAnts.setPreferredSize(new Dimension(75,20));
        nbPheromones.setPreferredSize(new Dimension(75,20));
        nbFood.setPreferredSize(new Dimension(75,20));
        speed.setPreferredSize(new Dimension(75,20));
                        
        
        antsPanel.add(labelAnts);
        antsPanel.add(nbAnts);
        pheromonePanel.add(labelPheromones);
        pheromonePanel.add(nbPheromones);
        foodPanel.add(labelFood);
        foodPanel.add(nbFood);
        speedPanel.add(labelSpeed);
        speedPanel.add(speed);
        submitPanel.add(submitButton);
        submitPanel.add(saveButton);
        submitPanel.add(loadButton);
        
        container.setLayout(null);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        
        container.add(antsPanel);
        container.add(pheromonePanel);
        container.add(foodPanel);
        container.add(speedPanel);
        container.add(submitPanel);
        
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
    public JFormattedTextField speed;
    
    ButtonListener(JPanel p, JFormattedTextField a, JFormattedTextField b, JFormattedTextField c, JFormattedTextField d){
        
        this.panel = p;
        this.nbAnts = a;
        this.nbPheromone = b;
        this.nbFood = c;
        this.speed = d;
    }
    public boolean isPourcentage(String s){
    	int num = Integer.parseInt(s); 
//    	Le taux d'�vaporation doit etre entre 1 et 90%
    	if(num < 1 || num > 90)
    		return false;
    	return true;
    }
    public boolean isSpeedInBounds(String s){
    	int speed = Integer.parseInt(s);
    	if(speed < 10 || speed > 200)
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
            CellPanel.rateEvaporation = evap;
        }else{
            /** valeur par défaut d'évaporation dans CellPanel **/
        }
        
        if(isInteger(this.nbFood.getText())){
        	Application.maxFoodPerCell = Integer.parseInt(this.nbFood.getText());
        }else{
            /** valeur par défaut d'évaporation dans Application.maxFoodPerCell **/
        }
        
        if(isInteger(this.speed.getText()) && isSpeedInBounds(this.speed.getText())){
            Application.attente = (double) Integer.parseInt(this.speed.getText());
        }else{
        	/** valeur par défaut d'évaporation dans Application.attente **/
        }
        
        Application.createAntFrame();
    }

  }
    
    class SaveListener implements ActionListener {

        public JPanel panel;
        public JFormattedTextField nbAnts;
        public JFormattedTextField nbPheromone;
        public JFormattedTextField nbFood;
        public JFormattedTextField speed;

        SaveListener(JPanel p, JFormattedTextField a, JFormattedTextField b, JFormattedTextField c, JFormattedTextField d){

            this.panel = p;
            this.nbAnts = a;
            this.nbPheromone = b;
            this.nbFood = c;
            this.speed = d;
        }

        public void actionPerformed(ActionEvent e) {
//        	C'est plus facile en C, objectiveC, C# ET Fortram!!! OMG !!! impossible d'ecrire à la racine du projet T_T
//            String rootPath = new File("").getAbsolutePath()+"\\save.xml";
//            Path save_path = Paths.get(rootPath);
//            System.out.println(save_path);
//            Charset charset = Charset.forName("US-ASCII");
//            String s = "chibar\r\n";
//            try (BufferedWriter writer = Files.newBufferedWriter(save_path, charset, StandardOpenOption.APPEND)) {
//                writer.write(s, 0, s.length());
//            } catch (IOException x) {
//                System.err.format("IOException: %s%n", x);
//            }
        	saveAsXml.init();
        }

    }
    class LoadListener implements ActionListener {

        public JPanel panel;
        public JFormattedTextField nbAnts;
        public JFormattedTextField nbPheromone;
        public JFormattedTextField nbFood;
        public JFormattedTextField speed;

        LoadListener(){
        	
        }

        public void actionPerformed(ActionEvent e) {
        	loadXml.init();
        }

    }
}