import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Enemy extends GameObject {
    Player player;

    Image textureUp;
    Image textureDown;
    Image textureLeft;
    Image textureRight;

    float speed;
    float x;
    float y;
    float width;
    float height;

    Direction lookDir;

    public Enemy(Player player, float speed, float x, float y) {
        this.player = player;
        this.speed = speed;
        this.x = x;
        this.y = y;
        this.height = 50;
        this.width = 50;
        lookDir = Direction.DOWN;
        loadTexture();
    }

    private void loadTexture() {
        try {
            textureDown = ImageIO.read(getClass().getResource("resources/Enemy_down.png"));
            textureUp = ImageIO.read(getClass().getResource("resources/Enemy_up.png"));
            textureLeft = ImageIO.read(getClass().getResource("resources/Enemy_left.png"));
            textureRight = ImageIO.read(getClass().getResource("resources/Enemy_right.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateTexture(Graphics2D g) {
        switch (lookDir) {
            case UP:
                g.drawImage(textureUp, (int) x - (int) width / 2, (int) y - (int) height / 2, (int) width, (int) height, null);
                break;
            case DOWN:
                g.drawImage(textureDown, (int) x - (int) width / 2, (int) y - (int) height / 2, (int) width, (int) height, null);
                break;
            case LEFT:
                g.drawImage(textureLeft, (int) x - (int) width / 2, (int) y - (int) height / 2, (int) width, (int) height, null);
                break;
            case RIGHT:
                g.drawImage(textureRight, (int) x - (int) width / 2, (int) y - (int) height / 2, (int) width, (int) height, null);
                break;
        }
    }

    @Override
    void Update(Graphics2D graphics) {
        updateTexture(graphics);
        moveToPlayer();
        //DEBUG
        //graphics.setColor(Color.green);
        //graphics.drawRect((int) x - (int) width / 2, (int) y - (int) height / 2, (int) width, (int) height);
    }

    private void moveToPlayer() {
        if (player.isDead()) return;

        if (player.getX() > x) {
            x += speed * Time.deltaTime;
            lookDir = Direction.RIGHT;
        } else {
            x -= speed * Time.deltaTime;
            lookDir = Direction.LEFT;
        }

        if (player.getY() > y) {
            y += speed * Time.deltaTime;
            lookDir = Direction.DOWN;
        } else {
            y -= speed * Time.deltaTime;
            lookDir = Direction.UP;
        }
    }

    /*** GETTERS ***/
    public float getX() {
        return x - width / 2;
    }

    public float getY() {
        return y - height / 2;
    }

    public float getWidth() {
        return 50;
    }
}
