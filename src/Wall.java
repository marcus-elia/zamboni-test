public abstract class Wall
{
    // The points are swept out clockwise from the 0 angle
    protected Point p1;
    protected Point p2;
    protected Point p3;
    protected Point p4;

    protected Point center;

    public Wall()
    {

    }

    public abstract boolean isInWall(Point p);
}
