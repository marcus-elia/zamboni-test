import java.awt.*;

public class GameManager
{
    private GameMode currentMode;

    private ZamboniButton playButton;

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
    private int timeElapsed; // In seconds
    private int prevTime;

    private boolean needToRedraw; // Do we need to redraw everything this frame?

    // How far from the zamboni needs to be redrawn each frame
    private double renderRadius;

    public GameManager(int width, int height)
    {
        this.currentMode = GameMode.Intro;

        this.numFrames = 0;

        this.windowWidth = width;
        this.windowHeight = height;

        this.playButton = new ZamboniButton(windowWidth/2, windowHeight/2);

        this.border = new RinkBorder(this, this.windowWidth, this.windowHeight, 4);

        this.zamboni = new Zamboni(this, this.border, 4 + 10, windowHeight/2,
                36, 20, 3*Math.PI/2, .6, .1, .008,
                Math.PI/20, new Color(20, 40, 100));


        this.squareSize = 4;
        this.surface = new IceSurface(this.squareSize, this.windowWidth, this.windowHeight);

        this.numSquaresOnRink = this.countSquaresOnRink();
        this.numSquaresLeft = this.numSquaresOnRink + 0;

        this.renderRadius = Math.sqrt(zamboni.getXWidth()*zamboni.getXWidth()/4 + zamboni.getYWidth()*zamboni.getYWidth()/4)
                               + this.squareSize;

        this.distanceTraveled = 0;
        this.percent = 100 - this.numSquaresLeft*100.0 / this.numSquaresOnRink;
        this.timeElapsed = 0;

        this.needToRedraw = false;

    }

    public void tick()
    {
        if(this.currentMode == GameMode.InGame)
        {
            this.border.tick();
            this.zamboni.tick();
            this.numSquaresLeft -= this.surface.updateSquares(this.zamboni.getHitbox(), this.getTopLeftRenderCorner(),
                    this.getRenderRectXSize(), this.getRenderRectYSize());

            this.distanceTraveled += this.zamboni.getCurSpeed();
            this.percent = 100 - this.numSquaresLeft*100.0 / this.numSquaresOnRink;
            this.timeElapsed += (int)System.currentTimeMillis()/1000 - this.prevTime;
            this.prevTime = (int)System.currentTimeMillis()/1000;

            if(this.getPercentZambonied().equals("100.00"))
            {
                this.endGame();
            }
        }

    }

    public void render(Graphics2D g2d)
    {
        if(this.currentMode == GameMode.Intro)
        {
            g2d.setColor(Color.gray);
            g2d.fillRect(0,0, windowWidth, windowHeight + 60);
            this.playButton.render(g2d);
        }
        else if(this.currentMode == GameMode.InGame)
        {
            g2d.setColor(Color.gray);
            g2d.fillRect((int)this.getTopLeftRenderCorner().x, (int)this.getTopLeftRenderCorner().y,
                    this.getRenderRectXSize(), this.getRenderRectYSize());

            g2d.fillRect(0, windowHeight, windowWidth, 60);
            if(this.numFrames == 0 || this.needToRedraw)
            {
                g2d.setColor(Color.gray);
                g2d.fillRect(0,0, this.windowWidth, this.windowHeight);
                this.surface.renderEverything(g2d);
                this.needToRedraw = false;
            }
            this.surface.render(g2d, this.getTopLeftRenderCorner(), this.getRenderRectXSize(), this.getRenderRectYSize());
            this.border.render(g2d);
            this.zamboni.render(g2d);

            this.drawStrings(g2d);

            this.numFrames++;
        }
        else if(this.currentMode == GameMode.Paused)
        {
            g2d.setColor(Color.gray);
            g2d.fillRect(windowWidth/2 - 50, windowHeight/2 - 60, 40, 120);
            g2d.fillRect(windowWidth/2 + 10, windowHeight/2 - 60, 40, 120);
        }
        else if(this.currentMode == GameMode.End)
        {
            g2d.setColor(Color.gray);
            g2d.fillRect(0,0, windowWidth, windowHeight + 60);
            this.printGrade(g2d);
        }

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
        this.drawDistance(g2d);
        this.drawTime(g2d);
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
        g2d.drawString("Percent", this.windowWidth/2 - pixelLength/2, this.windowHeight + 54);
    }

    public String getPercentZambonied()
    {
        return String.format("%.2f", 100 - this.numSquaresLeft*100.0 / this.numSquaresOnRink);
    }

    public void drawDistance(Graphics2D g2d)
    {
        // The actual distance
        g2d.setFont(new Font("Courier", Font.PLAIN, 24));
        g2d.setColor(Color.BLACK);
        int pixelLength = g2d.getFontMetrics().stringWidth(this.getDistanceTraveled());
        g2d.drawString(this.getDistanceTraveled(), 3*this.windowWidth/4 - pixelLength/2, this.windowHeight + 32);

        // The word "Distance"
        g2d.setFont(new Font("Courier", Font.PLAIN, 16));
        g2d.setColor(Color.BLACK);
        pixelLength = g2d.getFontMetrics().stringWidth("Distance");
        g2d.drawString("Distance", 3*this.windowWidth/4 - pixelLength/2, this.windowHeight + 54);
    }

    public String getDistanceTraveled()
    {
        return String.format("%.2f", this.distanceTraveled*.4/5280) + "mi";
    }

    public void drawTime(Graphics2D g2d)
    {
        // The actual time
        g2d.setFont(new Font("Courier", Font.PLAIN, 24));
        g2d.setColor(Color.BLACK);
        int pixelLength = g2d.getFontMetrics().stringWidth(this.getTime());
        g2d.drawString(this.getTime(), this.windowWidth/4 - pixelLength/2, this.windowHeight + 32);

        // The word "Distance"
        g2d.setFont(new Font("Courier", Font.PLAIN, 16));
        g2d.setColor(Color.BLACK);
        pixelLength = g2d.getFontMetrics().stringWidth("Time");
        g2d.drawString("Time", this.windowWidth/4 - pixelLength/2, this.windowHeight + 54);
    }

    public String getTime()
    {
        int minutes = this.timeElapsed/60;
        int seconds = this.timeElapsed % 60;
        if(seconds < 10)
        {
            return minutes + ":0" + seconds;
        }
        return minutes + ":" + seconds;
    }

    public String getGrade()
    {
        if(this.timeElapsed < 5*60 && this.distanceTraveled < 0.80)
        {
            return "A+";
        }
        else if(this.timeElapsed < 5*60 && this.distanceTraveled < 0.85)
        {
            return "A";
        }
        else if(this.distanceTraveled < 0.80 && this.timeElapsed < 5*60 + 10)
        {
            return "A";
        }
        else if(this.timeElapsed < 5*60 + 10 && this.distanceTraveled < 0.85)
        {
            return "A-";
        }
        else if(this.timeElapsed < 5*60 + 10 && this.distanceTraveled < 0.90)
        {
            return "B+";
        }
        else if(this.distanceTraveled < 0.85 && this.timeElapsed < 5*60 + 15)
        {
            return "B+";
        }
        else if(this.distanceTraveled < 0.90 && this.timeElapsed < 5*60 + 15)
        {
            return "B";
        }
        else if(this.distanceTraveled < 0.95 && this.timeElapsed < 5*60 + 20)
        {
            return "B-";
        }
        else if(this.distanceTraveled < 1 && this.timeElapsed < 5*60 + 30)
        {
            return "C";
        }
        else if(this.distanceTraveled < 1.1 && this.timeElapsed < 5*60 + 40)
        {
            return "D";
        }
        else
        {
            return "F";
        }
    }

    public void printGrade(Graphics2D g2d)
    {
        // The actual grade
        g2d.setFont(new Font("Courier", Font.PLAIN, 24));
        g2d.setColor(Color.BLACK);
        int pixelLength = g2d.getFontMetrics().stringWidth(this.getGrade());
        g2d.drawString(this.getGrade(), this.windowWidth/2 - pixelLength/2, this.windowHeight/2);

        // The word "Grade"
        g2d.setFont(new Font("Courier", Font.PLAIN, 16));
        g2d.setColor(Color.BLACK);
        pixelLength = g2d.getFontMetrics().stringWidth("Grade");
        g2d.drawString("Grade", this.windowWidth/2 - pixelLength/2, this.windowHeight/2 - 32);
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
    public GameMode getCurrentMode()
    {
        return this.currentMode;
    }
    public ZamboniButton getPlayButton()
    {
        return this.playButton;
    }

    public double getRenderRadius()
    {
        return this.renderRadius;
    }
    public Point getRenderCenter()
    {
        return this.zamboni.getHitbox().getCenter();
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


    public void startGame()
    {
        this.currentMode = GameMode.InGame;
        this.prevTime = (int)System.currentTimeMillis()/1000;
    }
    public void pause()
    {
        this.currentMode = GameMode.Paused;
    }
    public void unpause()
    {
        this.currentMode = GameMode.InGame;
        this.prevTime = (int)System.currentTimeMillis()/1000;
        this.needToRedraw = true;
    }
    public void endGame()
    {
        this.currentMode = GameMode.End;
    }
}
