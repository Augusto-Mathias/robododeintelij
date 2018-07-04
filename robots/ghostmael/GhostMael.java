package ghostmael;

import robocode.*;

import java.awt.geom.*;
import java.awt.Color;

public class GhostMael extends AdvancedRobot {


    Point2D.Double debugGoTo = null; //DEBUG

    /**
     * Type of bullet used for predictive targeting
     */
    final static private double PREDICTION_BULLET_POWER = Rules.MAX_BULLET_POWER/2;

    private enum RobotPart
    {
        BODY, RADAR, GUN
    }

    private Evasion evasion = null;

    private Target target = null;

    private Point2D.Double robotLocation = null;

    private double targetEnergy = 0;

    /*
     * When true it triggers a custom event
     */
    private boolean targetEnergyDrop = false;
    private long targetEnergyDropTime = 0;

    private long bulletHitTime = -1;

    public void run()
    {
        setAllColors(Color.BLACK);
        setBulletColor(Color.GREEN);
        setScanColor(Color.GREEN);

        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        setAdjustRadarForRobotTurn(true);

        addCustomEvent(new TargetEnergyDrop());

        evasion = new Evasion(getBattleFieldWidth(), getBattleFieldHeight());

        while (true)
        {
            turnRadarRight(Rules.MAX_TURN_RATE);
        }
    }

    public void onScannedRobot(ScannedRobotEvent e)
    {
        /*
         * Zero will never be greater than the target's energy
         */
        if (targetEnergy > e.getEnergy())
            targetEnergyDrop = true;

        targetEnergy = e.getEnergy();

        robotLocation = new Point2D.Double(getX(), getY());

        target = new Target(
                e.getDistance(),
                e.getBearingRadians(),
                e.getHeadingRadians(),
                e.getVelocity()
        );

        target.calculateLocation(robotLocation, getHeadingRadians());

        target.calculatePredictedLocation(
                getBattleFieldWidth(),
                getBattleFieldHeight(),
                PREDICTION_BULLET_POWER
        );

        setTurnRobotPartTowards(RobotPart.GUN, target.getPredictedLocation());

        if (getGunHeat() == 0)
        {
            ChooseBullets bullet = new ChooseBullets(robotLocation, target);
            setFire(bullet.chooseBullet(PREDICTION_BULLET_POWER));
            scan();
        }

        setTurnRobotPartTowards(RobotPart.RADAR, target.getLocation());
    }

    public void onBulletHit(BulletHitEvent e)
    {
        bulletHitTime = getTime();
    }

    public void onCustomEvent(CustomEvent e)
    {
        String eventName = e.getCondition().getName();

        switch(eventName)
        {
            case "targetEnergyDrop":
                /*
                 * Check if the target's energy drop didin't
                 * coincide with a hit of one of our bullets
                 */
                if (bulletHitTime != (targetEnergyDropTime-1))
                    setGoTo(evasion.evade(robotLocation, target.getLocation()));
                break;
            default:
                break;
        }
    }

    private void setTurnRobotPartTowards(RobotPart part, Point2D.Double location)
    {
        double bearing = MyUtils.getRelativeBearing(robotLocation, location);

        switch (part)
        {
            case BODY:
                setTurnRightRadians(
                        MyUtils.normalRelativeAngle(bearing-getHeadingRadians())
                );
                break;
            case RADAR:
                setTurnRadarRightRadians(
                        MyUtils.normalRelativeAngle(bearing-getRadarHeadingRadians())
                );
                break;
            case GUN:
                setTurnGunRightRadians(
                        MyUtils.normalRelativeAngle(bearing-getGunHeadingRadians())
                );
                break;
            default:
                break;
        }
    }

    private void setGoTo(Point2D.Double location)
    {
        setTurnRobotPartTowards(RobotPart.BODY, location);

        setAhead(robotLocation.distance(location));
    }

    private class TargetEnergyDrop extends Condition
    {
        TargetEnergyDrop()
        {
            this.setName("targetEnergyDrop");
            this.setPriority(90);
        }

        public boolean test()
        {
            if (targetEnergyDrop)
            {
                targetEnergyDropTime = getTime();
                targetEnergyDrop = false;

                return true;
            }
            return false;
        }
    }
}