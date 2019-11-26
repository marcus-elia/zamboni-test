import java.awt.*;
import java.util.ArrayList;

public class RinkBorder
{
    private GameManager manager;
    private int width;
    private int height;

    private double thickness;

    private ArrayList<Wall> walls;

    private StraightWall leftWall;
    private StraightWall topWall;
    private StraightWall rightWall;
    private StraightWall bottomWall;

    public RinkBorder(GameManager inputManager, int inputWidth, int inputHeight, double inputThickness)
    {
        this.manager = inputManager;
        this.width = inputWidth;
        this.height = inputHeight;
        this.thickness = inputThickness;

        this.initializeWalls();
    }

    // ==========================================
    //
    //            Helper Functions
    //           for the Constructor
    //
    // ==========================================
    public void initializeWalls()
    {
        this.leftWall = new StraightWall(new Point(this.thickness/2, this.height/2),
                this.thickness, 3*this.height/4);
        this.rightWall = new StraightWall(new Point(this.width - this.thickness/2, this.height/2),
                this.thickness, 3*this.height/4);
        this.topWall = new StraightWall(new Point(this.width/2, this.thickness/2),
                this.width - this.height/4, this.thickness);
        this.bottomWall = new StraightWall(new Point(this.width/2, this.height - this.thickness/2),
                this.width - this.height/4, this.thickness);

        this.walls = new ArrayList<Wall>();
        this.walls.add(leftWall);
        this.walls.add(rightWall);
        this.walls.add(topWall);
        this.walls.add(bottomWall);
    }

    public void tick()
    {

    }

    public void render(Graphics2D g2d)
    {
        for(Wall w : this.walls)
        {
            w.render(g2d);
        }
    }

}
