import java.awt.*;
import java.awt.geom.Path2D;

public class CurvedWall extends Wall
{
    private double innerRadius;
    private double outerRadius;
    private double startAngle;
    private double endAngle;

    // On the opposite side of the curve from the center,
    // used as the Bezier point
    private Point outerAntiCenter;
    private Point innerAntiCenter;



    public CurvedWall(Point inputCenter, double inputOuterRadius, double thickness, double inputStartAngle, double inputEndAngle)
    {
        this.center = inputCenter;

        this.startAngle = inputStartAngle;
        this.endAngle = inputEndAngle;


        this.outerRadius = inputOuterRadius;
        this.innerRadius = outerRadius - thickness;

        this.outerAntiCenter = new Point(center.x + outerRadius*Math.cos(startAngle), center.y + outerRadius*Math.sin(endAngle));
        this.innerAntiCenter = new Point(center.x + innerRadius*Math.cos(startAngle), center.y + innerRadius*Math.sin(endAngle));

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
        path.curveTo(outerAntiCenter.x, outerAntiCenter.y, outerAntiCenter.x, outerAntiCenter.y, p3.x, p3.y);
        path.lineTo(p4.x, p4.y);
        path.curveTo(innerAntiCenter.x, innerAntiCenter.y, innerAntiCenter.x, innerAntiCenter.y, p1.x, p1.y);
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
}
