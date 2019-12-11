import java.awt.*;

public class GameManager
{
    private int numFrames;

    private int windowWidth;
    private int windowHeight;
    private RinkBorder border;
    private Zamboni zamboni;

    private int squareSize;
    private IceSurface surface;
    // how many squares are actually on the rink
    private int numSquaresOnRink;
    // how many squares are left to zamboni
    private int numSquaresLeft;

    private double distanceTraveled; // How far the player has moved in total
    private double percent; // How much of the rink has been zambonied

    // How far from the zamboni needs to be redrawn each frame
    private double renderRadius;

    public GameManager(int width, int height)
    {
        this.numFrames = 0;

        this.windowWidth = width;
        this.windowHeight = height;

        this.border = new RinkBorder(this, this.windowWidth, this.windowHeight, 4);

        this.zamboni = new Zamboni(this, this.border, 4 + 10, windowHeight/2,
                36, 20, 3*Math.PI/2, .6, .1, .008,
                Math.PI/16, new Color(20, 40, 100));


        this.squareSize = 4;
        this.surface = new IceSurface(this.squareSize, this.windowWidth, this.windowHeight);

        this.numSquaresOnRink = this.countSquaresOnRink();
        this.numSquaresLeft = this.numSquaresOnRink + 0;

        this.renderRadius = Math.sqrt(zamboni.getXWidth()*zamboni.getXWidth()/4 + zamboni.getYWidth()*zamboni.getYWidth()/4)
                               + this.squareSize;

        this.distanceTraveled = 0;
        this.percent = 100 - this.numSquaresLeft*100.0 / this.numSquaresOnRink;
    }

    public void tick()
    {
        this.border.tick();
        this.zamboni.tick();
        this.numSquaresLeft -= this.surface.updateSquares(this.zamboni.getHitbox(), this.getTopLeftRenderCorner(),
                this.getRenderRectXSize(), this.getRenderRectYSize());

        this.distanceTraveled += this.zamboni.getCurSpeed();
        this.percent = 100 - this.numSquaresLeft*100.0 / this.numSquaresOnRink;
    }

    public void render(Graphics2D g2d)
    {
        if(this.numFrames == 0)
        {
            g2d.setColor(Color.gray);
            g2d.fillRect(0,0, this.windowWidth, this.windowHeight);
            this.surface.renderEverything(g2d);
        }
        this.surface.render(g2d, this.getTopLeftRenderCorner(), this.getRenderRectXSize(), this.getRenderRectYSize());
        this.border.render(g2d);
        this.zamboni.render(g2d);

        this.drawStrings(g2d);

        this.numFrames++;
    }


    // ===================================
    //
    //          Helper Functions
    //           For Rendering
    //
    // ===================================
    public void drawStrings(Graphics2D g2d)
    {
        this.drawPercent(g2d);
        //this.drawDistance(g2d);
    }

    public void drawPercent(Graphics2D g2d)
    {
        // The actual percentage
        g2d.setFont(new Font("Courier", Font.PLAIN, 24));
        g2d.setColor(Color.BLACK);
        int pixelLength = g2d.getFontMetrics().stringWidth(this.getPercentZambonied()); // the number of pixels the string is long
        g2d.drawString(this.getPercentZambonied(), this.windowWidth/2 - pixelLength/2, this.windowHeight + 32);

        // The word Percent
        g2d.setFont(new Font("Courier", Font.PLAIN, 16));
        g2d.setColor(Color.BLACK);
        pixelLength = g2d.getFontMetrics().stringWidth("Percent"); // the number of pixels the string is long
        g2d.drawString("Percent", this.windowWidth/2 - pixelLength/2, this.windowHeight + 48);
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

    public double getRenderRadius()
    {
        return this.renderRadius;
    }
    public Point getRenderCenter()
    {
        return this.zamboni.getHitbox().getCenter();
    }

    public String getPercentZambonied()
    {
        return String.format("%.2f", 100 - this.numSquaresLeft*100.0 / this.numSquaresOnRink) + "%";
    }


    // =======================================
    //
    //             Helper Functions
    //            for the Constructor
    //
    // =======================================
    public int countSquaresOnRink()
    {
        int count = 0;
        for(int i = 0; i < this.surface.getNumCols(); i++)
        {
            for(int j = 0; j < this.surface.getNumRows(); j++)
            {
                if(surface.getIceSquare(i,j).isOnRink(windowWidth, windowHeight, border))
                {
                    count++;
                }
                else  // if not on rink, set the zambonied boolean to false so we never count this square
                {
                    surface.getIceSquare(i,j).getZambonied();
                }
            }
        }
        return count;
    }

    // =======================================
    //
    //            Functions for
    //               Rendering
    //
    // =======================================

    // Returns the closest point to (x,y) whose coordinates are both
    // multiples of modulus and less than or equal to the corresponding
    // coordinate
    public Point getUpLeftMultiple(double x, double y, int modulus)
    {
        return new Point(((int)x / modulus) * modulus, ((int)y / modulus) * modulus);
    }
    public Point getDownRightMultiple(double x, double y, int modulus)
    {
        return new Point(((int)Math.ceil(x) / modulus) * modulus, ((int)Math.ceil(y) / modulus) * modulus);
    }

    public Point getTopLeftRenderCorner()
    {
        return this.getUpLeftMultiple(this.zamboni.getHitbox().getCenter().x - this.renderRadius,
                this.zamboni.getHitbox().getCenter().y - this.renderRadius, this.squareSize);
    }
    public Point getBottomRightRenderCorner()
    {
        return this.getDownRightMultiple(this.zamboni.getHitbox().getCenter().x + this.renderRadius,
                this.zamboni.getHitbox().getCenter().y + this.renderRadius, this.squareSize);
    }

    public int getRenderRectXSize()
    {
        return (int)this.getBottomRightRenderCorner().x - (int)this.getTopLeftRenderCorner().x;
    }
    public int getRenderRectYSize()
    {
        return (int)this.getBottomRightRenderCorner().y - (int)this.getTopLeftRenderCorner().y;
    }

    // For debugging, print out the locations of the squares that have not been zambonied yet
    public void printUnzamboniedSquares()
    {
        for(int i = 0; i < this.surface.getNumCols(); i++)
        {
            for (int j = 0; j < this.surface.getNumRows(); j++)
            {
                if (!surface.getIceSquare(i, j).getHasBeenZambonied())
                {
                    System.out.println(Integer.toString((int) surface.getIceSquare(i, j).getCenter().x) + ", " +
                            Integer.toString((int) surface.getIceSquare(i, j).getCenter().y));
                }
            }
        }
    }
}
