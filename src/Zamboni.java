import java.awt.*;
import java.awt.geom.Path2D;

public class Zamboni
{
    // Location and Size
    private double x;
    private double y;
    private double xWidth;
    private double yWidth;
    private double angle;

    // Hitbox
    private Rectangle hitbox;

    // Speed
    private double curSpeed;
    private double maxSpeed;
    private double vx;
    private double vy;
    private double acceleration;

    // Turning
    private double deltaTheta;
    private double curWheelAngle;
    private double maxWheelAngle;

    // User input
    private boolean gasPedal;
    private boolean brakePedal;
    private boolean steeringRight;
    private boolean steeringLeft;

    private Color color;

    private GameManager manager;
    private RinkBorder border;

    public Zamboni(GameManager manager, RinkBorder border, double inputX, double inputY, double inputXWidth,
                   double inputYWidth, double inputAngle, double inputMaxSpeed,
                   double inputAcceleration, double inputDeltaTheta, double inputMaxWheelAngle, Color inputColor)
    {
        this.manager = manager;
        this.border = border;
        this.x = inputX;
        this.y = inputY;
        this.xWidth = inputXWidth;
        this.yWidth = inputYWidth;
        this.angle = inputAngle;
        this.color = inputColor;

        this.hitbox = new Rectangle(new Point(x,y), xWidth, yWidth, angle);

        this.maxSpeed = inputMaxSpeed;
        this.curSpeed = 0;
        this.vx = 0;
        this.vy = 0;
        this.acceleration = inputAcceleration;

        this.deltaTheta = inputDeltaTheta;
        this.curWheelAngle = 0;
        this.maxWheelAngle = inputMaxWheelAngle;

        this.gasPedal = false;
        this.brakePedal = false;
        this.steeringRight = false;
        this.steeringLeft = false;
    }

    public void tick()
    {
        this.updateSpeed();
        this.turnWheels();
        this.updateAngle();
        this.setVelocityComponents();
        this.move();
        this.updateHitbox();
        this.correctNearBorders();
    }

    public void render(Graphics2D g2d)
    {
        g2d.setColor(this.color);
        Path2D path = new Path2D.Double();
        path.moveTo(this.hitbox.getCorner1().x, this.hitbox.getCorner1().y);
        path.lineTo(this.hitbox.getCorner2().x, this.hitbox.getCorner2().y);
        path.lineTo(this.hitbox.getCorner3().x, this.hitbox.getCorner3().y);
        path.lineTo(this.hitbox.getCorner4().x, this.hitbox.getCorner4().y);
        path.lineTo(this.hitbox.getCorner1().x, this.hitbox.getCorner1().y);
        path.closePath();
        g2d.fill(path);
    }

    // ===================================
    //
    //             Getters
    //
    // ===================================
    public Rectangle getHitbox()
    {
        return this.hitbox;
    }
    public double getXWidth()
    {
        return this.xWidth;
    }
    public double getYWidth()
    {
        return this.yWidth;
    }

    // ===================================
    //
    //              Setters
    //
    // ===================================
    public void setBrakePedal(boolean input)
    {
        this.brakePedal = input;
    }
    public void setGasPedal(boolean input)
    {
        this.gasPedal = input;
    }
    public void setSteeringRight(boolean input)
    {
        this.steeringRight = input;
    }
    public void setSteeringLeft(boolean input)
    {
        this.steeringLeft = input;
    }

    // Update the velocity components to be consistent with the speed and angle
    public void setVelocityComponents()
    {
        this.vx = this.curSpeed*Math.cos(this.angle);
        this.vy = this.curSpeed*Math.sin(this.angle);
    }

    public void setAngle(double inputAngle)
    {
        if(inputAngle < 0)
        {
            this.angle = inputAngle + 2*Math.PI;
        }
        else if(inputAngle >= 2*Math.PI)
        {
            this.angle = inputAngle - 2*Math.PI;
        }
        else
        {
            this.angle = inputAngle;
        }
    }
    public void setX(double inputX)
    {
        this.x = inputX;
    }
    public void setY(double inputY)
    {
        this.y = inputY;
    }

    // ===========================================
    //
    //            Movement Functions
    //
    // ===========================================



    // Increase the speed by the amount input, or set it to maxSpeed, whichever is smaller
    public void increaseSpeed(double amount)
    {
        this.curSpeed = Math.min(this.curSpeed + amount, this.maxSpeed);
    }
    // Decrease the speed by the amount input, or set it to minSpeed, whichever is bigger
    public void decreaseSpeed(double amount)
    {
        this.curSpeed = Math.max(this.curSpeed - amount, 0);
    }
    // Turn the wheels right, but not beyond the limit
    public void turnWheelsRight(double amount)
    {
        this.curWheelAngle = Math.min(this.curWheelAngle + amount, this.maxWheelAngle);
    }
    // Turn the wheels left, but not beyond the limit
    public void turnWheelsLeft(double amount)
    {
        this.curWheelAngle = Math.max(this.curWheelAngle - amount, -this.maxWheelAngle);
    }
    // If the wheels are not straight and no user input is turning,
    // start setting the wheels back to normal
    public void resetWheels()
    {
        if(this.curWheelAngle < 0)
        {
            this.curWheelAngle = Math.min(0, this.curWheelAngle + 5*this.deltaTheta);
        }
        else if(this.curWheelAngle > 0)
        {
            this.curWheelAngle = Math.max(0, this.curWheelAngle - 5*this.deltaTheta);
        }
    }

    // Update the magnitude of the speed based on the gas pedal and brake pedal input
    public void updateSpeed()
    {
        if(this.gasPedal && this.brakePedal)
        {
            // Do not change the speed if both gas and brake are pressed
            return;
        }
        // Holding the gas
        else if(this.gasPedal)
        {
            this.increaseSpeed(this.acceleration);
        }
        // Braking
        else if(this.brakePedal)
        {
            this.decreaseSpeed(this.acceleration / 3);
        }
        // Coasting
        else
        {
            this.decreaseSpeed(this.acceleration / 6);
        }
    }


    // Turn the angle of the wheels based on user input
    public void turnWheels()
    {
        if(this.steeringLeft && this.steeringRight)
        {
            return;
        }
        else if(this.steeringRight)
        {
            this.turnWheelsRight(this.deltaTheta);
        }
        else if(this.steeringLeft)
        {
            this.turnWheelsLeft(this.deltaTheta);
        }
        else // Power steering
        {
            this.resetWheels();
        }
    }

    public void updateAngle()
    {
        this.setAngle(this.angle + this.curSpeed / this.maxSpeed * this.curWheelAngle / 10);
    }

    public void moveHorizontally(double amount)
    {
        this.setX(this.x + amount);
        this.hitbox.moveHorizontally(amount);
    }
    public void moveVertically(double amount)
    {
        this.setY(this.y + amount);
        this.hitbox.moveVertically(amount);
    }

    public void move()
    {
        this.moveHorizontally(vx);
        this.moveVertically(vy);
    }

    // Update the hitbox when things have moved
    public void updateHitbox()
    {
        this.hitbox.setCenter(new Point(x,y));
        this.hitbox.setAngle(this.angle);
        this.hitbox.calculateCorners();
        this.hitbox.calculateBottomCenter();
    }

    public void correctLeft()
    {
        double distanceFromEdge = this.hitbox.getLeftest() - this.border.getThickness();
        if(distanceFromEdge < 0)
        {
            this.moveHorizontally(-distanceFromEdge);
        }
    }

    public void correctTop()
    {
        double distanceFromEdge = this.hitbox.getHighest() - this.border.getThickness();
        if(distanceFromEdge < 0)
        {
            this.moveVertically(-distanceFromEdge);
        }
    }

    public void correctRight()
    {
        double distanceFromEdge = this.hitbox.getRightest() - (this.manager.getWindowWidth() - this.border.getThickness());
        if(distanceFromEdge > 0)
        {
            this.moveHorizontally(-distanceFromEdge);
        }
    }

    public void correctBottom()
    {
        double distanceFromEdge = this.hitbox.getLowest() - (this.manager.getWindowHeight() - this.border.getThickness());
        if(distanceFromEdge > 0)
        {
            this.moveVertically(-distanceFromEdge);
        }
    }

    // A helper function used in each of the four corners
    public void correctCorner(Point center, double radius)
    {
        double distanceFromEdge;

        distanceFromEdge = radius - center.distanceToOtherPoint(this.hitbox.getCorner1());
        if(distanceFromEdge < 0)
        {
            double theta = center.angleToPoint(this.hitbox.getCorner1());
            this.moveHorizontally(distanceFromEdge*Math.cos(theta));
            this.moveVertically(distanceFromEdge*Math.sin(theta));
        }

        distanceFromEdge = radius - center.distanceToOtherPoint(this.hitbox.getCorner2());
        if(distanceFromEdge < 0)
        {
            double theta = center.angleToPoint(this.hitbox.getCorner2());
            this.moveHorizontally(distanceFromEdge*Math.cos(theta));
            this.moveVertically(distanceFromEdge*Math.sin(theta));
        }

        distanceFromEdge = radius - center.distanceToOtherPoint(this.hitbox.getCorner3());
        if(distanceFromEdge < 0)
        {
            double theta = center.angleToPoint(this.hitbox.getCorner3());
            this.moveHorizontally(distanceFromEdge*Math.cos(theta));
            this.moveVertically(distanceFromEdge*Math.sin(theta));
        }

        distanceFromEdge = radius - center.distanceToOtherPoint(this.hitbox.getCorner4());
        if(distanceFromEdge < 0)
        {
            double theta = center.angleToPoint(this.hitbox.getCorner4());
            this.moveHorizontally(distanceFromEdge*Math.cos(theta));
            this.moveVertically(distanceFromEdge*Math.sin(theta));
        }
    }

    public void correctTopLeft()
    {
        Point center = this.border.getTopLeftWall().getCenter();
        double radius = this.border.getTopLeftWall().getInnerRadius();
        this.correctCorner(center, radius);
    }

    public void correctBottomLeft()
    {
        Point center = this.border.getBottomLeftWall().getCenter();
        double radius = this.border.getBottomLeftWall().getInnerRadius();
        this.correctCorner(center, radius);
    }

    public void correctTopRight()
    {
        Point center = this.border.getTopRightWall().getCenter();
        double radius = this.border.getTopRightWall().getInnerRadius();
        this.correctCorner(center, radius);
    }

    public void correctBottomRight()
    {
        Point center = this.border.getBottomRightWall().getCenter();
        double radius = this.border.getBottomRightWall().getInnerRadius();
        this.correctCorner(center, radius);
    }

    public void correctNearBorders()
    {
        if(this.x < this.border.getThickness() + this.xWidth)
        {
            if(this.y < this.manager.getCornerRadius())
            {
                this.correctTopLeft();
            }
            else if(this.y > this.manager.getWindowHeight() - this.manager.getCornerRadius())
            {
                this.correctBottomLeft();
            }
            else
            {
                this.correctLeft();
            }
        }
        else if(this.x > this.manager.getWindowWidth() - this.border.getThickness() - this.xWidth)
        {
            if(this.y < this.manager.getCornerRadius())
            {
                this.correctTopRight();
            }
            else if(this.y > this.manager.getWindowHeight() - this.manager.getCornerRadius())
            {
                this.correctBottomRight();
            }
            else
            {
                this.correctRight();
            }
        }
        else if(this.y < this.border.getThickness() + this.xWidth)
        {
            this.correctTop();
        }
        else if(this.y > this.manager.getWindowHeight() - this.border.getThickness() - this.xWidth)
        {
            this.correctBottom();
        }
    }


    public void turboBoost(double amount)
    {
        this.curSpeed += amount;
        this.setVelocityComponents();
    }
}
