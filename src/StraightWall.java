import java.awt.*;
import java.awt.geom.Rectangle2D;

public class StraightWall extends Wall
{
    public StraightWall(Point center, double width, double height)
    {
        this.center = center;
        this.p1 = new Point(center.x + width/2, center.y + height/2);
        this.p2 = new Point(center.x - width/2, center.y + height/2);
        this.p3 = new Point(center.x - width/2, center.y - height/2);
        this.p4 = new Point(center.x + width/2, center.y - height/2);
    }

    public void tick()
    {

    }

    public void render(Graphics2D g2d)
    {
        g2d.setColor(Color.black);
        Rectangle2D rect = new Rectangle2D.Double(p3.x, p3.y, p4.x-p3.x, p2.y-p3.y);
        g2d.fill(rect);
    }

    public boolean isInWall(Point p)
    {
        return p.x > this.p2.x && p.x < this.p1.x && p.y > this.p3.y && p.y < this.p1.y;
    }
}
