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
        this.updateAngle();
        this.setVelocityComponents();
        this.move();
        this.updateHitbox();
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
            this.curWheelAngle = Math.min(0, this.curWheelAngle + this.deltaTheta);
        }
        else if(this.curWheelAngle > 0)
        {
            this.curWheelAngle = Math.max(0, this.curWheelAngle - this.deltaTheta);
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
        // do not turn if stationary
        if(this.curSpeed == 0)
        {
            return;
        }
        if(this.steeringRight && this.steeringLeft)
        {
            return;
        }
        else if(this.steeringRight)
        {
            this.setAngle(this.angle + this.deltaTheta);
        }
        else if(this.steeringLeft)
        {
            this.setAngle(this.angle - this.deltaTheta);
        }
    }

    public void move()
    {
        this.x += this.vx;
        this.y += this.vy;
    }

    // Update the hitbox when things have moved
    public void updateHitbox()
    {
        this.hitbox.setCenter(new Point(x,y));
        this.hitbox.setAngle(this.angle);
        this.hitbox.calculateCorners();
    }
}
