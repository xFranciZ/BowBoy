import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class UIManager {
    public static UIManager instance;

    private Image titleImage;

    private final TextObject startText;
    private final TextObject restartText;
    private final TextObject pauseText;
    private final TextObject healthText;
    private final TextObject scoreText;
    private final TextObject gameOverText;

    private final ArrayList<GameObject> textObjectList;
    TitleScreen titleScreen;

    public UIManager() {
        instance = this;

        titleScreen = new TitleScreen();

        startText = new TextObject("press SPACE to start", (int) World.instance.getWorldMaxWidth() / 2, (int) World.instance.getWorldMaxHeight() - 300, 30, Color.WHITE, true);
        pauseText = new TextObject("", (int) World.instance.getWorldMaxWidth() / 2, 200, 100, Color.WHITE, true);
        restartText = new TextObject("", (int) World.instance.getWorldMaxWidth() / 2, (int) World.instance.getWorldMaxHeight() / 2 + 50, 30, Color.WHITE, true);
        healthText = new TextObject("Health: ", 20, (int) World.instance.getWorldMaxHeight() - 20, 25, Color.WHITE, false);
        scoreText = new TextObject("Score: 0", 20, 30, 25, Color.WHITE, false);
        gameOverText = new TextObject("", (int) World.instance.getWorldMaxWidth() / 2, (int) World.instance.getWorldMaxHeight() / 2, 100, Color.WHITE, true);

        textObjectList = new ArrayList<>();
        textObjectList.add(startText);
        textObjectList.add(pauseText);
        textObjectList.add(restartText);
        textObjectList.add(healthText);
        textObjectList.add(scoreText);
        textObjectList.add(gameOverText);
        textObjectList.add(titleScreen);
    }


    /*** SETTERS ***/
    public void showPauseText() {
        pauseText.text = "Pause";
    }

    public void hidePauseText() {
        pauseText.text = "";
    }

    public void hideTitleScreen() {
        textObjectList.remove(titleScreen);
    }

    public void showstartText() {
        startText.text = "press SPACE to start";
    }

    public void hidestartText() {
        startText.text = "";
    }

    public void showRestartText() {
        restartText.text = "press SPACE to restart";
    }

    public void hideRestartText() {
        restartText.text = "";
    }

    public void setHealth(int currentHealth) {
        healthText.text = "Health: " + currentHealth;
    }

    public void setScore(int currentScore) {
        scoreText.text = "Score: " + currentScore;
    }

    public void showGameOverText() {
        gameOverText.text = "GAME OVER";
    }

    public void hideGameOverText() {
        gameOverText.text = "";
    }

    public ArrayList<GameObject> getTextObjectList() {
        return textObjectList;
    }

    /*** ANIMATION ***/
    private final float delay = 1;
    private float currentShowTime = delay;
    private float currentHideTime = delay;

    public void doStartTextAnimation() {
        if (currentShowTime > 0) {
            currentShowTime -= Time.deltaTime;
            showstartText();
        } else {
            if (currentHideTime > 0) {
                currentHideTime -= Time.deltaTime;
                hidestartText();
            } else {
                currentShowTime = delay;
                currentHideTime = delay;
            }

        }
    }

    private final float reDelay = 1;
    private float recurrentShowTime = reDelay;
    private float recurrentHideTime = reDelay;

    public void doReStartTextAnimation() {
        if (recurrentShowTime > 0) {
            recurrentShowTime -= Time.deltaTime;
            showRestartText();
        } else {
            if (recurrentHideTime > 0) {
                recurrentHideTime -= Time.deltaTime;
                hideRestartText();
            } else {
                recurrentShowTime = delay;
                recurrentHideTime = delay;
            }
        }
    }

    private class TitleScreen extends GameObject {

        private Image titleScreen;

        public TitleScreen() {
            loadTitleImage();
        }

        private void loadTitleImage() {
            try {
                titleImage = ImageIO.read(getClass().getResource("resources/Title.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        void Update(Graphics2D graphics) {
            graphics.drawImage(titleImage, (int) World.instance.getWorldMaxWidth() / 2 - 350, (int) World.instance.getWorldMaxHeight() / 6, 700, 300, null);
        }
    }
}