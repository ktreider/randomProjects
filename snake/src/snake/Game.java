package snake;

import javax.swing.*;

public class Game extends JFrame {

    public Game() {
        this.add(new Graphics()); //add graphics
        
        this.setTitle("Snake Game"); //title the game
        
        this.pack();
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit
        
        this.setResizable(false);	//one size
        
        this.setVisible(true);	
        this.setLocationRelativeTo(null);
    }

}