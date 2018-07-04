package ghostmael;


import java.awt.geom.Point2D;

class MyUtils
{
    /**
     * Reduces the amount of characters one has to type
     * we in order to use {@link robocode.util.Utils#normalRelativeAngle}
     *
     * @param angle - the angle to normalize in radians
     * @return the normalized angle that will be in the range of [-PI,PI[
     */
    public static double normalRelativeAngle
    (
            double angle
    )
    {
        return robocode.util.Utils.normalRelativeAngle(angle);
    }

    /**
     * Reduces the amount of characters one has to type
     * we in order to use {@link robocode.util.Utils#normalAbsoluteAngle}
     *
     * @param angle - the angle to normalize in radians
     * @return the normalized angle that will be in the range of [0,2*PI[
     */
    public static double normalAbsoluteAngle
    (
            double angle
    )
    {
        return robocode.util.Utils.normalAbsoluteAngle(angle);
    }

    /**
     * Calculates the relative bearing between two points
     *
     * @param referenceLocation - reference location as a <code>Point2D.Double</code>
     * @param relativeLocation - relative location as a <code>Point2D.Double</code>
     * @return the bearing of <code>l2</code> relative to <code>l1</code>
     */
    public static double getRelativeBearing
    (
            Point2D.Double referenceLocation,
            Point2D.Double relativeLocation
    )
    {
        double x = relativeLocation.getX()-referenceLocation.getX();
        double y = relativeLocation.getY()-referenceLocation.getY();

        return MyUtils.normalAbsoluteAngle(Math.atan2(x, y));
    }

    /**
     * Calculates the nearest equidistant point between
     * two other points.
     *
     * @param point1 - first point as a <code>Point2D.Double</code>
     * @param point2 - second point as a <code>Point2D.Double</code>
     * @return the nearest equidistant point between <code>point1</code>
     * and <code>point2</code> as a <code>Point2D.Double</code>
     */
    public static Point2D.Double getMiddlePoint
    (
            Point2D.Double point1,
            Point2D.Double point2
    )
    {
        return new Point2D.Double(
                (point1.getX()+point2.getX())/2, (point1.getY()+point2.getY())/2
        );
    }
}