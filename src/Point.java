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
}
