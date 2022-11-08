import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
    public static World instance;

    private BufferedImage back;
    private Player player;

    private List<GameObject> worldObjects;
    private List<Arrow> arrows;
    private List<Enemy> enemies;

    private double worldMaxWidth;
    private double worldMaxHeight;

    Random r;

    /*** CONSTRUCTOR ***/
    public World(InputHandler input) {
        worldObjects = new ArrayList<>();
        arrows = new ArrayList<>();
        enemies = new ArrayList<>();
        instance = this;
        worldMaxWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        worldMaxHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

        r = new Random();

        init(input);
    }

    /*** HANDLE ACTIONS ***/
    public void handleActions() {
        player.shoot();
    }

    /*** INIT ***/
    private void init(InputHandler input) {
        createPlayer();
        loadBack();
        //spawnEnemy(new Enemy(player, 50, 0, 0));
    }

    public void spawnArrow(Arrow arrow) {
        arrows.add(arrow);
        worldObjects.add(arrow);
    }

    public void spawnEnemies(int count) {
        int x = 0, y = 0;

        for (int i = 0; i < count; i++) {
            int spawn = r.nextInt(8);
            switch (spawn) {
                case 0:
                    //UP
                    x = (int) getWorldMaxWidth() / 2;
                    y = 0;
                    break;
                case 1:
                    //Right UP
                    x = (int) getWorldMaxWidth();
                    y = 0;
                    break;
                case 2:
                    //Right
                    x = (int) getWorldMaxWidth();
                    y = (int) getWorldMaxHeight() / 2;
                    break;
                case 3:
                    //Right Down
                    x = (int) getWorldMaxWidth();
                    y = (int) getWorldMaxHeight();
                    break;
                case 4:
                    //Down
                    x = (int) getWorldMaxWidth() / 2;
                    y = (int) getWorldMaxHeight();
                    break;
                case 5:
                    //Left Down
                    x = 0;
                    y = (int) getWorldMaxHeight();
                    break;
                case 6:
                    //Left
                    x = 0;
                    y = (int) getWorldMaxHeight() / 2;
                    break;
                case 7:
                    x = 0;
                    y = 0;
                    //Left Up
                    break;
            }

            int speed = r.nextInt(300 - 50) + 50;

            Enemy enemy = new Enemy(player, speed, x, y);

            enemies.add(enemy);
            worldObjects.add(enemy);
        }


    }

    public void createPlayer() {
        player = new Player(40, 40, Color.WHITE, 50, InputHandler.instance);
        worldObjects.add(player);
    }

    private void loadBack() {
        try {
            back = ImageIO.read(getClass().getResource("resources/world.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cleanUpWorld() {
        arrows.clear();
        enemies.clear();
        worldObjects.clear();
    }

    public BufferedImage getBack() {
        return back;
    }

    public void createEnemy() {

    }

    /*** GAMEOBJECTS ***/
    public List<GameObject> getWorldObjects() {
        return worldObjects;
    }


    public void destroyGameObject(GameObject gameObject) {
        if (gameObject instanceof Enemy) {
            enemies.remove(gameObject);
            GameManager.instance.addScore(10);

        } else if (gameObject instanceof Arrow) {
            arrows.remove(gameObject);
        }

        worldObjects.remove(gameObject);
    }

    /*** COLLISION ***/
    public void checkForCollision() {   // currently checks if enemy is hit and instantly deletes them + plays destroy anim.
        checkArrowCollision();
        checkPlayerCollision();
    }

    private void checkArrowCollision() {
        Arrow arrowToDestroy = null;
        Enemy enemyToDestroy = null;

        for (Arrow a : arrows) {
            for (Enemy e : enemies) {
                double dist = e.getWidth() / 2 + a.getLength() / 2;
                double dx = Math.abs(e.getX() - a.getX());
                double dy = Math.abs(e.getY() - a.getY());

                if (dx < dist && dy < dist) {
                    if (dx * dx + dy * dy < dist * dist) {
                        DestroyAnimation dA = new DestroyAnimation((int) e.getX(), (int) e.getY(), 30);
                        arrowToDestroy = a;
                        enemyToDestroy = e;
                    }
                }
            }
        }

        destroyGameObject(arrowToDestroy);
        destroyGameObject(enemyToDestroy);
    }

    private void checkPlayerCollision() {
        Enemy enemyToDestroy = null;
        for (Enemy e : enemies) {
            double dist = e.getWidth() / 2 + player.getWidth() / 2;
            double dx = Math.abs(e.getX() - player.getX());
            double dy = Math.abs(e.getY() - player.getY());

            if (dx < dist && dy < dist) {
                if (dx * dx + dy * dy < dist * dist) {
                    enemyToDestroy = e;
                    player.takeDamage();
                    DestroyAnimation da = new DestroyAnimation((int) player.getX(), (int) player.getY(), 30);
                }
            }
        }
        destroyGameObject(enemyToDestroy);
    }


    /*** GETTER ***/
    public double getWorldMaxWidth() {
        return worldMaxWidth;
    }

    public double getWorldMaxHeight() {
        return worldMaxHeight;
    }

    public Player getPlayer() {
        return player;
    }

    public int getEnemyCount() {
        return enemies.size();
    }

}