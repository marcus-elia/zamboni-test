import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener
{
    private GameManager manager;

    public MouseInput(GameManager inputManager)
    {
        this.manager = inputManager;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();

        if(manager.getCurrentMode() == GameMode.Intro)
        {
            if(manager.getPlayButton().isInside(x,y))
            {
                manager.startGame();
            }
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
