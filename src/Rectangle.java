public class Rectangle
{
    // These determine the rectangle
    private Point center;
    private double xWidth;
    private double yWidth;
    private double angle;

    // These are calculated
    private Point corner1;
    private Point corner2;
    private Point corner3;
    private Point corner4;


    public Rectangle(Point inputCenter, double inputXWidth, double inputYWidth, double inputAngle)
    {
        this.center = inputCenter;
        this.xWidth = inputXWidth;
        this.yWidth = inputYWidth;
        this.angle = inputAngle;
        this.calculateCorners();
    }

    // Getters
    public Point getCenter()
    {
        return this.center;
    }
    public double getXWidth()
    {
        return this.xWidth;
    }
    public double getYWidth()
    {
        return this.yWidth;
    }
    public double getAngle()
    {
        return this.angle;
    }
    public Point getCorner1()
    {
        return this.corner1;
    }
    public Point getCorner2()
    {
        return this.corner2;
    }
    public Point getCorner3()
    {
        return this.corner3;
    }
    public Point getCorner4()
    {
        return this.corner4;
    }

    // Setters
    public void setCenter(Point inputCenter)
    {
        this.center = inputCenter;
    }
    public void setXWidth(double inputXWidth)
    {
        this.xWidth = inputXWidth;
    }
    public void setYWidth(double inputYWidth)
    {
        this.yWidth = inputYWidth;
    }
    public void setAngle(double inputAngle)
    {
        // Make sure the angle is between 0 and 2pi
        if(inputAngle >= 2*Math.PI)
        {
            this.angle = inputAngle - 2*Math.PI;
        }
        else if(inputAngle < 0)
        {
            this.angle = inputAngle + 2*Math.PI;
        }
        else
        {
            this.angle = inputAngle;
        }
    }

    // =================================
    //
    //       Functions for Finding
    //          the Corners
    //
    // ================================


    // Set the corners based on the other variables of the rectangle
    public void calculateCorner1()
    {
        this.corner1 = this.center.rotateAroundThis(center.x + xWidth/2, center.y + yWidth/2, this.angle);
    }
    public void calculateCorner2()
    {
        this.corner2 = this.center.rotateAroundThis(center.x - xWidth/2, center.y + yWidth/2, this.angle);
    }
    public void calculateCorner3()
    {
        this.corner3 = this.center.rotateAroundThis(center.x - xWidth/2, center.y - yWidth/2, this.angle);
    }
    public void calculateCorner4()
    {
        this.corner4 = this.center.rotateAroundThis(center.x + xWidth/2, center.y - yWidth/2, this.angle);
    }

    // Update all 4 of the corners based on the center and angle of the rectangle
    public void calculateCorners()
    {
        this.calculateCorner1();
        this.calculateCorner2();
        this.calculateCorner3();
        this.calculateCorner4();
    }

    // =======================================================
    //
    //             Functions for Moving
    //                 the Rectangle
    //
    // =======================================================
    public void moveHorizontally(double amount)
    {
        this.center.x += amount;
        this.corner1.x += amount;
        this.corner2.x += amount;
        this.corner3.x += amount;
        this.corner4.x += amount;
    }
    public void moveVertically(double amount)
    {
        this.center.y += amount;
        this.corner1.y += amount;
        this.corner2.y += amount;
        this.corner3.y += amount;
        this.corner4.y += amount;
    }
    public void rotate(double amount)
    {
        this.setAngle(this.angle + amount);
        this.calculateCorners();
    }


}
