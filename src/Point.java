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

    // =====================================
    //
    //          Geometry Functions
    //
    // ====================================

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

    // Returns the point whose coordinates are p rotated around this by theta radians
    public Point rotateAroundThis(double px, double py, double theta)
    {
        // Translate the point to the origin
        double shiftedX = px - this.x;
        double shiftedY = py - this.y;

        // Rotate around the origin
        double newX = shiftedX*Math.cos(theta) - shiftedY*Math.sin(theta);
        double newY = shiftedX*Math.sin(theta) + shiftedY*Math.cos(theta);

        // Translate it back
        return new Point(newX + this.x, newY + this.y);
    }
    public Point rotateAroundThis(Point p, double theta)
    {
        return this.rotateAroundThis(p.x, p.y, theta);
    }
}
