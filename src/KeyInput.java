import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter
{
    private GameManager manager;

    public KeyInput(GameManager manager)
    {
        this.manager = manager;
    }

    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W)
        {
            manager.getZamboni().setGasPedal(true);
        }
        else if (key == KeyEvent.VK_S)
        {
            manager.getZamboni().setBrakePedal(true);
        }
        else if (key == KeyEvent.VK_D)
        {
            manager.getZamboni().setSteeringRight(true);
        }
        else if (key == KeyEvent.VK_A)
        {
            manager.getZamboni().setSteeringLeft(true);
        }
    }

    public void keyReleased(KeyEvent e)
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W)
        {
            manager.getZamboni().setGasPedal(false);
        }
        else if (key == KeyEvent.VK_S)
        {
            manager.getZamboni().setBrakePedal(false);
        }
        else if (key == KeyEvent.VK_D)
        {
            manager.getZamboni().setSteeringRight(false);
        }
        else if (key == KeyEvent.VK_A)
        {
            manager.getZamboni().setSteeringLeft(false);
        }
    }
}

