package ghostmael;


import java.awt.geom.Point2D;

class Target
{
    private double distance  = 0;
    private double bearing   = 0;
    private double heading   = 0;
    private double velocity  = 0;

    private Point2D.Double location = null;
    private Point2D.Double predictedLocation = null;

    /**
     *
     * This class represents a <b>target</b>.
     *
     * It contains all of the methods needed for targeting.
     *
     */
    Target
    (
            double distance,
            double bearing,
            double heading,
            double velocity
    )
    {
        this.distance = distance;
        this.bearing  = bearing;
        this.heading  = heading;
        this.velocity = velocity;
    }

    /**
     * Calculates the target's location in the battlefield.
     * You can get it using the method <code>{@link #getLocation}</code>.
     *
     * @param robotLocation - <i>our robot's</i> location in the battlefield
     * @param robotHeading - <i>our robot's</i> heading in <b>radians</b>
     */
    public void calculateLocation
    (
            Point2D.Double robotLocation,
            double robotHeading
    )
    {
        /*
         * The target's bearing relative to the battlefield's center
         */
        double targetAbsBearing = robotHeading+bearing;

        double transX = robotLocation.getX()
                + (Math.sin(targetAbsBearing)*distance);

        double transY = robotLocation.getY()
                + (Math.cos(targetAbsBearing)*distance);

        this.location = new Point2D.Double(transX, transY);
    }

    /**
     * Calculates a prediction of the target's location
     * when a bullet with <code>bulletPower</code> arrives
     * at the target's current location
     *
     * @param battleFieldWidth - the battlefield's width
     * @param battleFieldHeight - the battlefield's height
     * @param bulletPower - the bullet power used for the prediction
     * @return a <code>Point2D.Double</code> with the coordinates of the target's predicted location
     */
    Point2D.Double calculatePredictedLocation
    (
            double battleFieldWidth,
            double battleFieldHeight,
            double bulletPower
    )
    {
        double bulletVelocity = 20-3*(bulletPower);

        double impactTime = distance/bulletVelocity;
        double travDist   = velocity*impactTime;

        double transX = Math.sin(heading)*travDist;
        double transY = Math.cos(heading)*travDist;

        predictedLocation = new Point2D.Double(
                Math.min(battleFieldWidth, Math.max(0, location.getX()+transX)),
                Math.min(battleFieldHeight, Math.max(0, location.getY()+transY))
        );

        return predictedLocation;
    }

    /**
     * Returns the target's <b>current</b> location
     *
     * @return a <code>Point2D.Double</code> with the coordinates of the target's <b>current</b> location
     */
    Point2D.Double getLocation()
    {
        return location;
    }

    /**
     * Returns the target's <b>predicted</b> location
     *
     * @return the target's <b>predicted</b> location
     */
    Point2D.Double getPredictedLocation()
    {
        return predictedLocation;
    }
}