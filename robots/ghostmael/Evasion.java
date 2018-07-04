package ghostmael;

import java.awt.geom.Point2D;

class Evasion {
    final private Point2D.Double[] battleFieldCorners = new Point2D.Double[4];

    /**
     * This class implements an algorithm for evading bullets.
     *
     * @param battleFieldWidth  - the battlefield's width
     * @param battleFieldHeight - the battlefield's height
     * @see #evade
     */
    Evasion(double battleFieldWidth, double battleFieldHeight) {
        battleFieldCorners[0] = new Point2D.Double(0, 0);
        battleFieldCorners[1] = new Point2D.Double(0, battleFieldHeight);
        battleFieldCorners[2] = new Point2D.Double(battleFieldWidth, 0);
        battleFieldCorners[3] = new Point2D.Double(battleFieldWidth, battleFieldHeight);
    }

    /**
     * Calculates the point our robot should go to evade our target's bullets
     * using the following algorithm:
     * </br></br>
     * <p>
     * Four triangles are drawn using as vertices our current location,
     * our target's current location and each of the four vertices of
     * the battlefield. The corner which produces an escape point that
     * moves us the farthest away from the target is the on that's chosen.
     *
     * @param robotLocation  - <i>our robot's</i> location in the battlefield
     * @param targetLocation - <i>the target's</i> location in the battlefield
     * @return the Point2D.Double where we should go to evade enemy bullets
     */
    Point2D.Double evade
    (
            Point2D.Double robotLocation,
            Point2D.Double targetLocation
    ) {
        Point2D.Double bestEvasionPoint = null;
        double bestChangeInBearing = Double.MIN_VALUE;

        for (Point2D.Double corner : battleFieldCorners) {
            Point2D.Double currEvasionPoint = MyUtils.getMiddlePoint(
                    MyUtils.getMiddlePoint(robotLocation, targetLocation), corner
            );

            double currChangeInBearing = Math.abs(
                    MyUtils.getRelativeBearing(robotLocation, targetLocation)
                            - MyUtils.getRelativeBearing(robotLocation, currEvasionPoint)
            );

            if (currChangeInBearing > bestChangeInBearing) {
                bestChangeInBearing = currChangeInBearing;
                bestEvasionPoint = currEvasionPoint;
            }
        }

        return bestEvasionPoint;
    }
}