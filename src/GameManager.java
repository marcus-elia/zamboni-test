import java.awt.*;

public class GameManager
{
    private int windowWidth;
    private int windowHeight;
    private RinkBorder border;
    private Zamboni zamboni;

    public GameManager(int width, int height)
    {
        this.windowWidth = width;
        this.windowHeight = height;

        this.border = new RinkBorder(this, this.windowWidth, this.windowHeight, 4);

        this.zamboni = new Zamboni(this, this.border, 4 + 20, windowHeight/2,
                60, 40, 3*Math.PI/2, 3, .2, .05,
                Math.PI/6, Color.white);
    }

    public void tick()
    {
        this.border.tick();
        this.zamboni.tick();
    }

    public void render(Graphics2D g2d)
    {
        this.border.render(g2d);
        this.zamboni.render(g2d);
    }


    // ===================================
    //
    //              Getters
    //
    // ===================================
    public Zamboni getZamboni()
    {
        return this.zamboni;
    }
    public double getCornerRadius()
    {
        return this.windowHeight/8;
    }
}
