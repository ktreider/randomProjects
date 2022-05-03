package snake;

import java.util.Random;

public class Food {

	//position of food
    private final int posX;
    private final int posY;

    //where position
    public Food() {
        posX = generatePos(Graphics.WIDTH);
        posY = generatePos(Graphics.HEIGHT);
    }

    //generate a random position
    private int generatePos(int size) {
        Random random = new Random();
        return random.nextInt(size / Graphics.TICK_SIZE) * Graphics.TICK_SIZE;
    }

    //get the positions
    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
