import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Arrow extends GameObject {
    Image texture;

    float x;
    float y;
    float speed;

    Direction dir;

    int height;
    int width;

    public Arrow(float speed, Direction dir, float x, float y) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.speed = speed;

        if (dir == Direction.UP || dir == Direction.DOWN) {
            height = 40;
            width = 20;
        } else {
            height = 20;
            width = 40;
        }

        loadTexture();
    }

    private void loadTexture() {
        try {
            switch (dir) {
                case UP -> texture = ImageIO.read(getClass().getResource("resources/Arrow_up.png"));
                case DOWN -> texture = ImageIO.read(getClass().getResource("resources/Arrow_down.png"));
                case LEFT -> texture = ImageIO.read(getClass().getResource("resources/Arrow_left.png"));
                case RIGHT -> texture = ImageIO.read(getClass().getResource("resources/Arrow_right.png"));
            }
        } catch (IOException e) {

        }
    }

    @Override
    void Update(Graphics2D graphics) {
        graphics.setColor(Color.red);
        graphics.drawImage(texture, (int) x - (int) width / 2, (int) y - (int) height / 2, (int) width, (int) height, null);
        move();

        //DEBUG
        //graphics.setColor(Color.green);
        //graphics.drawRect((int) x - (int) width / 2, (int) y - (int) height / 2, (int) width, (int) height);
    }

    private void move() {
        switch (dir) {
            case UP -> y -= 10 * speed * Time.deltaTime;
            case DOWN -> y += 10 * speed * Time.deltaTime;
            case LEFT -> x -= 10 * speed * Time.deltaTime;
            case RIGHT -> x += 10 * speed * Time.deltaTime;
        }
    }

    /*** GETTERS ***/
    public float getX() {
        return  x - width / 2;
    }

    public float getY() {
        return y - height / 2;
    }

    public float getLength() {
        if(height > width)
            return height;
        else
            return width;
    }

}


