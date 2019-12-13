import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

// A button shaped like a zamboni. Click on it to start the game
public class ZamboniButton
{
    private int x;
    private int y;

    private boolean isHighlighted;

    private Path2D snowTank;
    private Color snowTankColor;

    private Path2D body;
    private Color bodyColor;

    private Path2D backPart;
    private Color backPartColor;

    private Wheel frontWheel;
    private Wheel backWheel;

    public ZamboniButton(int inputX, int inputY)
    {
        this.x = inputX;
        this.y = inputY;

        this.isHighlighted = false;

        this.makeSnowTank();
        this.makeBody();
        this.makeWheels();
        this.makeBackPart();
    }

    // ======================================
    //
    //             Helper Functions
    //           for the Constructor
    //
    // ======================================
    public void makeSnowTank()
    {
        snowTank = new Path2D.Double();
        snowTank.moveTo(x,y);
        snowTank.lineTo(x - 120, y - 12);
        snowTank.lineTo(x - 120, y - 36);
        snowTank.lineTo(x, y - 48);
        snowTank.lineTo(x,y);

        this.snowTankColor = new Color(255, 235, 240, 128);
    }
    private void makeBody()
    {
        body = new Path2D.Double();
        body.moveTo(x,y);
        body.lineTo(x - 120, y - 12);
        body.lineTo(x - 120, y + 30);
        body.lineTo(x - 114, y + 30);

        // Front wheel
        body.curveTo(x - 114, y + 20.0655, x - 105.93347, y + 12, x - 96, y + 12);
        body.curveTo(x - 86.0655, y + 12, x - 78, y + 20.0655, x - 78, y + 30);
        body.lineTo(x - 18, y + 30);

        // Back Wheel
        body.curveTo(x - 18, y + 20.0655, x - 9.93447, y + 12, x, y + 12);
        body.lineTo(x + 42, y + 12);

        // Seat
        body.lineTo(x + 54, y);
        body.lineTo(x + 54, y - 18);
        body.lineTo(x + 42, y - 18);
        body.lineTo(x + 42, y);
        body.lineTo(x + 18, y);

        // Dashboard
        body.lineTo(x + 18, y - 24);
        body.lineTo(x + 24, y - 24);
        body.lineTo(x + 24, y - 48);
        body.lineTo(x, y - 48);
        body.lineTo(x, y);

        this.bodyColor = new Color(0, 20, 150, 128);
    }

    public void makeWheels()
    {
        this.frontWheel = new Wheel(x - 96, y + 30, 17, 12);
        this.backWheel = new Wheel(x, y + 30, 17, 12);
    }
    public void makeBackPart()
    {
        backPart = new Path2D.Double();
        backPart.moveTo(x + 12, y + 47);
        backPart.lineTo(x + 30, y + 30);
        backPart.lineTo(x + 54, y + 30);
        backPart.lineTo(x + 64, y + 40);
        backPart.lineTo(x + 64, y + 44);
        backPart.lineTo(x + 76, y + 44);
        backPart.lineTo(x + 76, y + 47);
        backPart.lineTo(x + 12, y + 47);
        this.backPartColor = new Color(15, 20, 15, 128);
    }






    public void render(Graphics2D g2d)
    {
        g2d.setColor(this.snowTankColor);
        g2d.fill(this.snowTank);

        g2d.setColor(this.bodyColor);
        g2d.fill(this.body);

        g2d.setColor(this.backPartColor);
        g2d.fill(this.backPart);

        this.frontWheel.render(g2d);
        this.backWheel.render(g2d);
    }




    // =======================================
    //
    //           Mouse Interaction
    //
    // =======================================

    // Return true if the given point is inside the zamboni
    public boolean isInside(double x, double y)
    {
        Point2D p = new Point2D.Double(x, y);
        return this.snowTank.contains(p) || this.body.contains(p) || this.frontWheel.contains(p) ||
                this.backWheel.contains(p) || this.backPart.contains(p);
    }

    public void setIsHighlighted(boolean input)
    {
        if(input)
        {
            this.bodyColor = new Color(this.bodyColor.getRed(), this.bodyColor.getGreen(),
                    this.bodyColor.getBlue(), 255);
            this.snowTankColor = new Color(this.snowTankColor.getRed(), this.snowTankColor.getGreen(),
                    this.snowTankColor.getBlue(), 255);
            this.backPartColor = new Color(this.backPartColor.getRed(), this.backPartColor.getGreen(),
                    this.backPartColor.getBlue(), 255);
        }
        else
        {
            this.bodyColor = new Color(this.bodyColor.getRed(), this.bodyColor.getGreen(),
                    this.bodyColor.getBlue(), 128);
            this.snowTankColor = new Color(this.snowTankColor.getRed(), this.snowTankColor.getGreen(),
                    this.snowTankColor.getBlue(), 128);
            this.backPartColor = new Color(this.backPartColor.getRed(), this.backPartColor.getGreen(),
                    this.backPartColor.getBlue(), 128);
        }
        this.frontWheel.setIsHighlighted(input);
        this.backWheel.setIsHighlighted(input);
        this.isHighlighted = input;
    }
}
