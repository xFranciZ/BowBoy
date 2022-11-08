import java.util.Random;

public class GameManager {
    public static GameManager instance;
    Random r;

    public GameManager() {
        instance = this;
        r = new Random();
    }

   public int score = 0;
    public void addScore(int amount) {
        score += amount;
        UIManager.instance.setScore(score);
    }

    public void run() {
        spawnWave();
    }

    private void spawnWave() {
        if (readyForNextWave()) {
            World.instance.spawnEnemies(r.nextInt(6 - 1 + 1) + 1);
        }
    }

    float currentWaitTime;
    float spawnDelay = 2;

    private boolean readyForNextWave() {
        if (World.instance.getEnemyCount() < 1) {
            if (currentWaitTime > 0) {
                currentWaitTime -= Time.deltaTime;

            } else {
                currentWaitTime = spawnDelay;
                return true;
            }
        }
        return false;
    }

    public void restartGame() {
        World.instance.cleanUpWorld();
        UIManager.instance.hideRestartText();
        UIManager.instance.hideGameOverText();
        World.instance.createPlayer();
        score = 0;
        UIManager.instance.setScore(score);
    }
}
