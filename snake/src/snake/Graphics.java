package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Graphics extends JPanel implements ActionListener {

    static final int WIDTH = 900;	//screen width
    static final int HEIGHT = 900; //screen height
    static final int TICK_SIZE = 35; //size of snake and food
    static final int BOARD_SIZE = (WIDTH * HEIGHT) / (TICK_SIZE * TICK_SIZE); //size of screen relative to choices above

    final Font font = new Font("Roboto", Font.ITALIC, 30);

    int[] snakePosX = new int[BOARD_SIZE];
    int[] snakePosY = new int[BOARD_SIZE];
    int snakeLength; //store length of snake

    Food food;
    int foodEaten; //whether food was consumed

    char direction = 'R'; //direction the snake is moving (R:right, L:left, U:up, D:down)
    boolean isMoving = false;
    final Timer timer = new Timer(150, this);

    public Graphics() {
    	//create dimensions of board
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        
        //background color
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        
        //add listener for user movements of snake
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //if key pressed, find which key events 
            	if (isMoving) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_LEFT: //left movement
                            if (direction != 'R') {
                                direction = 'L';
                            }
                            break;
                        case KeyEvent.VK_RIGHT: //right movement
                            if (direction != 'L') {
                                direction = 'R';
                            }
                            break;
                        case KeyEvent.VK_UP: //up movement
                            if (direction != 'D') {
                                direction = 'U';
                            }
                            break;
                        case KeyEvent.VK_DOWN: //down movement
                            if (direction != 'U') {
                                direction = 'D';
                            }
                            break;
                    }
                } else {
                    start();
                }
            }
        });

        start();
    }

    //starting settings
    protected void start() {
        snakePosX = new int[BOARD_SIZE];
        snakePosY = new int[BOARD_SIZE];
        snakeLength = 5; //starting snake length
        foodEaten = 0; //counter for food eaten
        direction = 'R'; //starting direction
        isMoving = true; //start moving 
        spawnFood(); //randomly spawn some food
        timer.start(); //start timer
    }

    
    //draw graphics
    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);

        if (isMoving) {
            g.setColor(Color.RED);
            g.fillOval(food.getPosX(), food.getPosY(), TICK_SIZE, TICK_SIZE);

            g.setColor(Color.DARK_GRAY);
            for (int i = 0; i < snakeLength; i++) {
                g.fillRect(snakePosX[i], snakePosY[i], TICK_SIZE, TICK_SIZE);
            }
        } else {
            String scoreText = String.format("END -- Score: %d -- Press any key to play again!", foodEaten);
            g.setColor(Color.WHITE);
            g.setFont(font); 
            g.drawString(scoreText, (WIDTH - getFontMetrics(g.getFont()).stringWidth(scoreText)) / 2, HEIGHT / 2);
        }
    }

    //movement of snake
    protected void move() {
    	//snake position
        for (int i = snakeLength; i > 0; i--) {
            snakePosX[i] = snakePosX[i-1];
            snakePosY[i] = snakePosY[i-1];
        }
        
        //snake direction
        switch (direction) {
            case 'U' -> snakePosY[0] -= TICK_SIZE;
            case 'D' -> snakePosY[0] += TICK_SIZE;
            case 'L' -> snakePosX[0] -= TICK_SIZE;
            case 'R' -> snakePosX[0] += TICK_SIZE;
        }
    }

    //spawn new food
    protected void spawnFood() {
        food = new Food();
    }

    //if snake crosses food, eat the food
    protected void eatFood() {
        if ((snakePosX[0] == food.getPosX()) && (snakePosY[0] == food.getPosY())) {
            snakeLength++;
            foodEaten++;
            spawnFood(); //spawn new food
        }
    }

    //collision
    protected void collisionTest() {
        for (int i = snakeLength; i > 0; i--) {
            if ((snakePosX[0] == snakePosX[i]) && (snakePosY[0] == snakePosY[i])) {
                isMoving = false;
                break;
            }
        }

        if (snakePosX[0] < 0 || snakePosX[0] > WIDTH - TICK_SIZE || snakePosY[0] < 0 || snakePosY[0] > HEIGHT - TICK_SIZE) {
            isMoving = false;
        }

        //stop timer if collision
        if (!isMoving) {
            timer.stop();
        }
    }

    
    //if snake is moving, call movement, testing for collision, and eating food aspects
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isMoving) {
            move();
            collisionTest();
            eatFood();
        }

        repaint(); //repaint graphics
    }
}
