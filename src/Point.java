/*
The Point class acts like a C++ struct: it simply stores an x and
y coordinate publicly.
 */
public class Point
{
    public double x;
    public double y;

    public Point(double inputX, double inputY)
    {
        this.x = inputX;
        this.y = inputY;
    }

    // Return the Euclidean distance to another set of coordinates
    public double distanceToOtherPoint(double x2, double y2)
    {
        return Math.sqrt((x - x2)*(x - x2) + (y - y2)*(y - y2));
    }
    public double distanceToOtherPoint(Point otherPoint)
    {
        return this.distanceToOtherPoint(otherPoint.x, otherPoint.y);
    }

    // Return the angle (between 0 and 2pi) from this point to the other point
    public double angleToPoint(Point otherPoint)
    {
        double tanInv = Math.atan2(otherPoint.y - y, otherPoint.x - x);

        // Since atan has a restricted range, get the things in quandrants II and III
        if(otherPoint.x - x < 0)
        {
            tanInv += Math.PI;
        }

        // Ensure the output is between 0 and 2pi
        if(tanInv >= 2*Math.PI)
        {
            return tanInv - 2*Math.PI;
        }
        if(tanInv < 0)
        {
            return tanInv + 2*Math.PI;
        }
        return tanInv;
    }
}
