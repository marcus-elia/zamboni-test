import java.awt.*;

public class GameManager
{
    private int windowWidth;
    private int windowHeight;
    private RinkBorder border;
    private Zamboni zamboni;
    private IceSurface surface;

    public GameManager(int width, int height)
    {
        this.windowWidth = width;
        this.windowHeight = height;

        this.border = new RinkBorder(this, this.windowWidth, this.windowHeight, 4);

        this.zamboni = new Zamboni(this, this.border, 4 + 20, windowHeight/2,
                60, 40, 3*Math.PI/2, 1, .1, .005,
                Math.PI/8, new Color(20, 40, 100));

        this.surface = new IceSurface(4, this.windowWidth, this.windowHeight);
    }

    public void tick()
    {
        this.border.tick();
        this.zamboni.tick();
        this.surface.updateSquares(this.zamboni.getHitbox());
    }

    public void render(Graphics2D g2d)
    {
        this.surface.render(g2d);
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
    public double getWindowHeight()
    {
        return this.windowHeight;
    }
    public double getWindowWidth()
    {
        return this.windowWidth;
    }
}
