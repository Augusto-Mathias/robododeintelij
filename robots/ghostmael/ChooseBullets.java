package ghostmael;


import robocode.Rules;

import java.awt.geom.Point2D;

class ChooseBullets
{
    Point2D.Double myLocation 			  = null;
    Point2D.Double targetLocation 		  = null;
    Point2D.Double targetPredictedLocation = null;

    /**
     *
     * This class is an auxiliary static class
     * that encapsulates all the method necessary
     * for choosing the correct <code>bulletPower</code>
     *
     */
    ChooseBullets
    (
            Point2D.Double myLocation,
            Target target
    )
    {
        this.myLocation 				= myLocation;
        this.targetLocation 			= target.getLocation();
        this.targetPredictedLocation = target.getPredictedLocation();
    }


    public double chooseBullet
    (
            double predictionBulletPower
    )
    {
        double bulletPower = predictionBulletPower;

        double targetDistance = myLocation.distance(targetLocation);

        double targetPredictedDistance = myLocation.distance(targetPredictedLocation);

        if (targetDistance < 400)
        {
            double bulletVelocity = 20-(3*predictionBulletPower);

            double impactTime = (targetDistance/bulletVelocity);

            double altBulletVelocity = (targetPredictedDistance/impactTime);

            double altBulletPower = (20-altBulletVelocity)/3;

            bulletPower = Math.min(Rules.MAX_BULLET_POWER,
                    Math.max(Rules.MIN_BULLET_POWER, altBulletPower)
            );
        }
        else if (targetDistance < 100)
        {
            bulletPower = Rules.MAX_BULLET_POWER;
        }

        return bulletPower;
    }
}
