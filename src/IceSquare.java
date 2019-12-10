import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

public class IceSquare
{
    private Point topLeft;
    private int size;
    private Color iceColor; // The RBG color of this square
    private Rectangle2D rect;
    private Point center;

    private boolean hasBeenZambonied;

    // The RGB color with alpha
    private Color renderColor;

    public IceSquare(double x, double y, int inputSize, Color inputIceColor)
    {
        this.topLeft = new Point(x,y);
        this.size = inputSize;
        this.iceColor = inputIceColor;
        this.renderColor = new Color(iceColor.getRed()/255.0f, iceColor.getGreen()/255.0f, iceColor.getBlue()/255.0f, 0.5f);
        this.rect = new Rectangle2D.Double(topLeft.x, topLeft.y, size, size);
        this.center = new Point(x + size/2, y + size/2);
        this.hasBeenZambonied = false;
    }

    public void render(Graphics2D g2d)
    {
        g2d.setColor(this.renderColor);
        g2d.fill(this.rect);
    }




    // ==================================
    //
    //             Getters
    //
    // ==================================
    public Point getTopLeft()
    {
        return this.topLeft;
    }
    public Point getBottomRight()
    {
        return new Point(this.topLeft.x + this.size, this.topLeft.y + this.size);
    }
    public Point getCenter()
    {
        return this.center;
    }
    public int getTop() // Return the lowest y-value
    {
        return (int)this.topLeft.y;
    }
    public int getBottom() // Return the highest y-value
    {
        return (int)this.topLeft.y + this.size;
    }
    public int getLeft() // Return the lowest x-value
    {
        return (int)this.topLeft.x;
    }
    public int getRight() // Return the highest x-value
    {
        return (int)this.topLeft.x + this.size;
    }

    // ==================================
    //
    //             Setters
    //
    // ==================================
    public void setColor(Color input)
    {
        this.iceColor = input;
    }

    // Set the alpha to 1
    // Return true if this has already been zambonied
    // False if we are zamboniing it for the first time
    public boolean getZambonied()
    {
        this.renderColor = new Color(iceColor.getRed()/255.0f, iceColor.getGreen()/255.0f, iceColor.getBlue()/255.0f, 1f);
        if(!this.hasBeenZambonied)
        {
            this.hasBeenZambonied = true;
            return false;
        }
        return true;
    }

    public boolean isOnRink(int width, int height, RinkBorder border)
    {
        int radius = height / 8;
        double x = this.getCenter().x;
        double y = this.getCenter().y;

        // If the point is in the middle rectangle of the rink
        if(y > radius && y < height - radius)
        {
            return this.getLeft() > border.getThickness() && this.getRight() < width - border.getThickness();
        }
        // If the point is near the top
        else if(y > border.getThickness() && y <= radius)
        {
            // Top left
            if(x < radius)
            {
                return border.getTopLeftWall().getCenter().distanceToOtherPoint(this.getLeft(), this.getTop()) < radius;
            }
            // Top right
            else if(x > width - radius)
            {
                return border.getTopRightWall().getCenter().distanceToOtherPoint(this.getRight(), this.getTop()) < radius;
            }
            // Top middle
            else
            {
                return true;
            }
        }
        // If the point is near the bottom
        else
        {
            // Bottom left
            if(x < radius)
            {
                return border.getBottomLeftWall().getCenter().distanceToOtherPoint(this.getLeft(), this.getBottom()) < radius;
            }
            // Bottom right
            else if(x > width - radius)
            {
                return border.getBottomRightWall().getCenter().distanceToOtherPoint(this.getRight(), this.getBottom()) < radius;
            }
            // Bottom middle
            else
            {
                return this.getBottom() < height - border.getThickness();
            }
        }
    }
}
