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

    private Ellipse2D tire;
    private Ellipse2D innerWheel;

    public Wheel(int inputX, int inputY, int inputOuterRadius, int inputInnerRadius)
    {
        this.x = inputX;
        this.y = inputY;
        this.outerRadius = inputOuterRadius;
        this.innerRadius = inputInnerRadius;

        this.tire = new Ellipse2D.Double(x - outerRadius, y - outerRadius, 2*outerRadius, 2*outerRadius);
        this.innerWheel = new Ellipse2D.Double(x - innerRadius, y - innerRadius, 2*innerRadius, 2*innerRadius);
    }

    public void render(Graphics2D g2d)
    {
        g2d.setColor(Color.black);
        g2d.fill(this.tire);

        g2d.setColor(Color.white);
        g2d.fill(this.innerWheel);
    }


    // Return true if the point is inside the wheel
    public boolean contains(double x, double y)
    {
        Point2D p = new Point2D.Double(x,y);
        return this.tire.contains(p);
    }
}
