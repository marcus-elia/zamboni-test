import java.awt.*;

public class GameManager
{
    private int windowWidth;
    private int windowHeight;
    private RinkBorder border;

    public GameManager(int width, int height)
    {
        this.windowWidth = width;
        this.windowHeight = height;

        this.border = new RinkBorder(this, this.windowWidth, this.windowHeight, 4);
    }

    public void tick()
    {
        this.border.tick();
    }

    public void render(Graphics2D g2d)
    {
        this.border.render(g2d);
    }
}
