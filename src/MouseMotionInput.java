import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMotionInput implements MouseMotionListener
{
    private GameManager manager;

    public MouseMotionInput(GameManager inputManager)
    {
        this.manager = inputManager;
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {

    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();
        if(this.manager.getCurrentMode() == GameMode.Intro)
        {
            if(this.manager.getPlayButton().isInside(x, y))
            {
                this.manager.getPlayButton().setIsHighlighted(true);
            }
            else
            {
                this.manager.getPlayButton().setIsHighlighted(false);
            }
        }
    }
}
