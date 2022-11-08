import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputHandler extends KeyAdapter {
    public static InputHandler instance;

    public boolean isMovingForward;
    public boolean isMovingBackward;
    public boolean isMovingRight;
    public boolean isMovingLeft;

    public boolean isShooting;

    public boolean isPause;

    public InputHandler() {
        instance = this;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        /*** MOVEMENT ***/
        if (e.getKeyCode() == KeyEvent.VK_D)
            isMovingRight = true;
        if (e.getKeyCode() == KeyEvent.VK_A)
            isMovingLeft = true;
        if (e.getKeyCode() == KeyEvent.VK_W)
            isMovingForward = true;
        if (e.getKeyCode() == KeyEvent.VK_S)
            isMovingBackward = true;

        /*** SHOOTING ***/
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
             isShooting = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        /*** MOVEMENT ***/
        if (e.getKeyCode() == KeyEvent.VK_D)
            isMovingRight = false;
        if (e.getKeyCode() == KeyEvent.VK_A)
            isMovingLeft = false;
        if (e.getKeyCode() == KeyEvent.VK_W)
            isMovingForward = false;
        if (e.getKeyCode() == KeyEvent.VK_S)
            isMovingBackward = false;

        /*** SHOOTING ***/
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            isShooting = false;
    }
}
