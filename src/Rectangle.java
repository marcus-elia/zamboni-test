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

    // =========================================
    //
    //          Functions for Finding
    //              the Corners
    //
    // ========================================


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

    // Return the max value of four doubles
    public double max4(double d1, double d2, double d3, double d4)
    {
        double maxSoFar = d1;
        if(d2 > d1)
        {
            maxSoFar = d2;
        }
        if(d3 > maxSoFar)
        {
            maxSoFar = d3;
        }
        return Math.max(maxSoFar, d4);
    }
    public double min4(double d1, double d2, double d3, double d4)
    {
        return -max4(-d1, -d2, -d3, -d4);
    }

    public double getLeftest()
    {
        return min4(corner1.x, corner2.x, corner3.x, corner4.x);
    }
    public double getRightest()
    {
        return max4(corner1.x, corner2.x, corner3.x, corner4.x);
    }
    public double getHighest()
    {
        return min4(corner1.y, corner2.y, corner3.y, corner4.y);
    }
    public double getLowest()
    {
        return max4(corner1.y, corner2.y, corner3.y, corner4.y);
    }

    // =======================================================
    //
    //                Functions for Moving
    //                    the Rectangle
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



    // ===============================================
    //
    //                Functions for
    //         Determining Square Intersection
    //
    // ===============================================
    // Returns true if the point p is inside this rectangle
    public boolean isInside(Point p)
    {
        Point rotatedP = this.center.rotateAroundThis(p, -this.angle);
        return rotatedP.x > center.x - xWidth/2 && rotatedP.x < center.x + xWidth/2 &&
                rotatedP.y > center.y - yWidth/2 && rotatedP.y < center.y - yWidth/2;
    }

    public boolean isInside(IceSquare isq)
    {
        return isInside(isq.getTopLeft()) || isInside(isq.getBottomRight());
    }

}
