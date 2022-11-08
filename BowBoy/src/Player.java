import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLOutput;

public class Player extends GameObject {
    private Image textureDown;
    private Image textureUp;
    private Image textureLeft;
    private Image textureRight;
    private Image hitTexture;
    private double speed;
    private int health;
    private float width;
    private float height;
    private Color color;
    private InputHandler input;
    private boolean isDead;
    Direction lookDir;

    /*** CONSTRUCTOR ***/
    public Player(float width, float height, Color color, double speed, InputHandler input) {
        this.x = World.instance.getWorldMaxWidth() / 2;
        this.y = World.instance.getWorldMaxHeight() / 2;

        this.speed = speed;
        this.color = color;
        this.width = width;
        this.height = height;
        this.input = input;
        this.health = 3;

        this.lookDir = Direction.UP;

        loadTexture();
    }

    double r = 0;

    /*** Update ***/
    @Override
    public void Update(Graphics2D graphics) {
        updateTexture(graphics);
        UIManager.instance.setHealth(health);
        move();
        //DEBUG
        //graphics.setColor(Color.green);
        //graphics.drawRect((int) x - (int) width / 2, (int) y - (int) height / 2, (int) width, (int) height);
    }

    private void updateTexture(Graphics2D g) {
        switch (lookDir) {
            case UP -> g.drawImage(textureUp,  (int) x - (int) width / 2, (int) y - (int) height / 2, (int) width, (int) height, null);
            case DOWN -> g.drawImage(textureDown,  (int) x - (int) width / 2, (int) y - (int) height / 2, (int) width, (int) height, null);
            case LEFT -> g.drawImage(textureLeft,  (int) x - (int) width / 2, (int) y - (int) height / 2, (int) width, (int) height, null);
            case RIGHT -> g.drawImage(textureRight,  (int) x - (int) width / 2, (int) y - (int) height / 2, (int) width, (int) height, null);
        }
    }


    private void loadTexture() {
        try {
            textureDown = ImageIO.read(getClass().getResource("resources/Char_down.png"));
            textureUp = ImageIO.read(getClass().getResource("resources/Char_up.png"));
            textureLeft = ImageIO.read(getClass().getResource("resources/Char_left.png"));
            textureRight = ImageIO.read(getClass().getResource("resources/Char_right.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*** MOVE ***/
    private void move() {
        if (input.isMovingRight && canMoveRight()) {
            x += 10 * speed * Time.deltaTime;
            lookDir = Direction.RIGHT;
        }
        if (input.isMovingLeft && canMoveLeft()) {
            x -= 10 * speed * Time.deltaTime;
            lookDir = Direction.LEFT;
        }
        if (input.isMovingForward && canMoveUp()) {
            y -= 10 * speed * Time.deltaTime;
            lookDir = Direction.UP;
        }
        if (input.isMovingBackward && canMoveDown()) {
            y += 10 * speed * Time.deltaTime;
            lookDir = Direction.DOWN;
        }
    }

   private boolean canMoveRight() {
        return x < World.instance.getWorldMaxWidth() - width * 0.5;
   }

   private boolean canMoveLeft() {
        return x > 0;
   }

   private boolean canMoveUp() {
        return y > 0;
   }

   private boolean canMoveDown() {
        return y < World.instance.getWorldMaxHeight() - height * 0.5;
    }

    /*** SHOOT ***/
    boolean canShoot;
    float currentShootTime;
    float shootDelay = 0.5f;

    public void shoot() {
        if (input.isShooting && canShoot) {
            World.instance.spawnArrow(new Arrow(100, lookDir, (int) x, (int) y));
            canShoot = false;
        }

        if (!canShoot) {
            if (currentShootTime > 0)
                currentShootTime -= Time.deltaTime;
            else {
                canShoot = true;
                currentShootTime = shootDelay;
            }
        }
    }


    /*** Health ***/
    public void takeDamage() {
        if (isDead()) return;
        health--;
        UIManager.instance.setHealth(health);
        checkForDeath();
    }

    private void checkForDeath() {
        if (health == 0) {
            UIManager.instance.showGameOverText();
            UIManager.instance.doReStartTextAnimation();
            isDead = true;
            World.instance.destroyGameObject(this);
        }
    }


    /*** GETTER ***/
    public double getWidth() {
        return width;
    }

    public float getX() {
        return (float) x - width / 2;
    }

    public float getY() {
        return (float) y - height / 2;
    }

    public boolean isDead() {
        return isDead;
    }

    public double getSpeed() {
        return speed;
    }
}
