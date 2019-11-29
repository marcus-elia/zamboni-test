import java.awt.*;
import java.awt.geom.Path2D;

public class CurvedWall extends Wall
{
    private double innerRadius;
    private double outerRadius;
    private double startAngle;
    private double endAngle;

    // Used for making the bezier curve approximate a circle
    private Point innerBezier1;
    private Point innerBezier2;
    private Point outerBezier1;
    private Point outerBezier2;



    public CurvedWall(Point inputCenter, double inputOuterRadius, double thickness, double inputStartAngle, double inputEndAngle)
    {
        this.center = inputCenter;

        this.startAngle = inputStartAngle;
        this.endAngle = inputEndAngle;


        this.outerRadius = inputOuterRadius;
        this.innerRadius = outerRadius - thickness;


        // In order to get the Bezier points, we need to know the sign of
        // the sine and cosine of angles swept out by this curve.
        double middleAngle = (startAngle + endAngle) / 2;
        double signOfCos = Math.abs(Math.cos(middleAngle)) / Math.cos(middleAngle);
        double signOfSin = Math.abs(Math.sin(middleAngle)) / Math.sin(middleAngle);
        double c = 0.551915024494; // from spencermortensen.com

        this.innerBezier1 = new Point(center.x + innerRadius*c*signOfCos, center.y + innerRadius*signOfSin);
        this.innerBezier2 = new Point(center.x + innerRadius*signOfCos, center.y + innerRadius*c*signOfSin);

        this.outerBezier1 = new Point(center.x + outerRadius*c*signOfCos, center.y + outerRadius*signOfSin);
        this.outerBezier2 = new Point(center.x + outerRadius*signOfCos, center.y + outerRadius*c*signOfSin);

        this.p1 = new Point(center.x + innerRadius*Math.cos(startAngle), center.y + innerRadius*Math.sin(startAngle));
        this.p2 = new Point(center.x + outerRadius*Math.cos(startAngle), center.y + outerRadius*Math.sin(startAngle));
        this.p3 = new Point(center.x + outerRadius*Math.cos(endAngle), center.y + outerRadius*Math.sin(endAngle));
        this.p4 = new Point(center.x + innerRadius*Math.cos(endAngle), center.y + innerRadius*Math.sin(endAngle));

    }

    public void tick()
    {

    }

    public void render(Graphics2D g2d)
    {
        g2d.setColor(Color.black);
        Path2D path = new Path2D.Double();
        path.moveTo(p1.x, p1.y);
        path.lineTo(p2.x, p2.y);
        path.curveTo(outerBezier1.x, outerBezier1.y, outerBezier2.x, outerBezier2.y, p3.x, p3.y);
        path.lineTo(p4.x, p4.y);
        path.curveTo(innerBezier2.x, innerBezier2.y, innerBezier1.x, innerBezier1.y, p1.x, p1.y);
        path.closePath();
        g2d.fill(path);
    }


    public boolean isInWall(Point p)
    {
        double angleFromCenter = this.center.angleToPoint(p);
        if(angleFromCenter >= this.startAngle && angleFromCenter <= this.endAngle)
        {
            double distanceFromCenter = this.center.distanceToOtherPoint(p);
            return distanceFromCenter >= this.innerRadius && distanceFromCenter <= this.outerRadius;
        }
        return false;
    }

    // ================================
    //
    //            Getters
    //
    // ================================
    public Point getCenter()
    {
        return this.center;
    }
    public double getInnerRadius()
    {
        return this.innerRadius;
    }
    public double getOuterRadius()
    {
        return this.outerRadius;
    }
}
