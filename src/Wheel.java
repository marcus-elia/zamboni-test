import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

// A wheel to be drawn on a ZamboniButton
public class Wheel
{
    private int x;
    private int y;
    private int outerRadius;
    private int innerRadius;

    private boolean isHighlighted;

    private Ellipse2D tire;
    private Color tireColor;

    private Ellipse2D innerWheel;
    private Color innerWheelColor;

    public Wheel(int inputX, int inputY, int inputOuterRadius, int inputInnerRadius)
    {
        this.x = inputX;
        this.y = inputY;
        this.outerRadius = inputOuterRadius;
        this.innerRadius = inputInnerRadius;

        this.isHighlighted = false;

        this.tire = new Ellipse2D.Double(x - outerRadius, y - outerRadius, 2*outerRadius, 2*outerRadius);
        this.tireColor = new Color(0,0,0,128);
        this.innerWheel = new Ellipse2D.Double(x - innerRadius, y - innerRadius, 2*innerRadius, 2*innerRadius);
        this.innerWheelColor = new Color(255, 255, 200, 128);
    }

    public void render(Graphics2D g2d)
    {
        g2d.setColor(this.tireColor);
        g2d.fill(this.tire);

        g2d.setColor(this.innerWheelColor);
        g2d.fill(this.innerWheel);
    }


    // Return true if the point is inside the wheel
    public boolean contains(Point2D p)
    {
        return this.tire.contains(p);
    }

    public void setIsHighlighted(boolean input)
    {
        if(input)
        {
            this.tireColor = new Color(this.tireColor.getRed(), this.tireColor.getGreen(),
                    this.tireColor.getBlue(), 255);
            this.innerWheelColor = new Color(this.innerWheelColor.getRed(), this.innerWheelColor.getGreen(),
                    this.innerWheelColor.getBlue(), 255);
        }
        else
        {
            this.tireColor = new Color(this.tireColor.getRed(), this.tireColor.getGreen(),
                    this.tireColor.getBlue(), 128);
            this.innerWheelColor = new Color(this.innerWheelColor.getRed(), this.innerWheelColor.getGreen(),
                    this.innerWheelColor.getBlue(), 128);
        }
        this.isHighlighted = input;
    }
}
