import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.image.BufferedImage;

public class GraphicsSystem extends JPanel {
    //For Buffering
    private GraphicsConfiguration graphicsConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    private BufferedImage imageBuffer;
    private Graphics2D graphics;

    public GraphicsSystem(InputHandler keyInput) {
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());

        imageBuffer = graphicsConfig.createCompatibleImage(this.getWidth(), this.getHeight());
        graphics = (Graphics2D) imageBuffer.getGraphics();
        setLayout(null);

        addKeyListener(keyInput);
        setFocusable(true);

    }

    public void clear() {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
    }

    public void draw(List<GameObject> gameObjects) {
        clear();

        /*** Draw background Image: ***/
        BufferedImage tmpBack = World.instance.getBack();
        graphics.drawImage(tmpBack, 0, 0, (int)World.instance.getWorldMaxWidth(), (int) World.instance.getWorldMaxHeight(), null);

        /*** Draw Game Objects: ***/
        for (GameObject d : gameObjects) {
            d.Update(graphics);
        }

        /*** Draw Text Objects: ***/
        for (GameObject g : UIManager.instance.getTextObjectList()){
            g.Update(graphics);
       }

        redraw();
    }

    public void redraw() {
        this.getGraphics().drawImage(imageBuffer, 0,0, this);
    }
}

