package fatec2018;
import robocode.*;
import robocode.ScannedRobotEvent;
import robocode.HitByBulletEvent;
import java.awt.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;

//import java.awt.Color;



/**
 * ThePunisher - a robot by (your name here)
 */
public class ThePunisher extends RateControlRobot
{
	/**
	 * run: Justiceiro's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here

          //setBulletColor(Color.writh);

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		// setColors(Color.red,Color.blue,Color.green); // body,gun,radar
    setBodyColor(Color.black);
          setGunColor(Color.black);
          setRadarColor(Color.red);
          setScanColor(Color.red);
          //setBulletColor(Color.writh);
		// Robot main loop
		while(true) {
			// Replace the next 4 lines with any behavior you would like
			ahead(100);
			turnGunRight(360);
			back(100);
			turnGunRight(360);
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {

		String nomeRobo = e.getName();// Captura o nome dos robos escaneados
		if( nomeRobo.equals("fatec2018.ThePunisher*")){
			scan(); // Escanear novamente
		}
		if( nomeRobo.equals("fatec2018.DrStranger*")){
			scan(); // Escanear novamente
		}
			if( nomeRobo.equals("fatec2018.GhostRider*")){
			scan(); // Escanear novamente
		}
		// Replace the next line with any behavior you would like
		double max = 100;

          //Faz um controle da energia que é gasta no que diz
          //respeito à potência do tiro
      if(e.getEnergy() < max){
         max = e.getEnergy();
         miraCanhao(e.getBearing(), max, getEnergy());
      }else if(e.getEnergy() >= max){
         max = e.getEnergy();
         miraCanhao(e.getBearing(), max, getEnergy());
      }else if(getOthers() == 1){
         max = e.getEnergy();
         miraCanhao(e.getBearing(), max, getEnergy());
      }
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		double giroDoRadar = normalRelativeAngleDegrees(e.getBearing() + getHeading() - getRadarHeading());
          setTurnRadarRight(giroDoRadar);
          setTurnLeft(-3);
          setTurnRate(3);
          setVelocityRate(-1 * getVelocityRate());

	}

	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		back(20);
	}

public void miraCanhao(double PosIni, double energiaIni, double minhaEnergia) {
       double Distancia = PosIni;
           double Coordenadas = getHeading() + PosIni - getGunHeading();
           double PontoQuarenta = (energiaIni / 4) + .1;
           if (!(Coordenadas > -180 && Coordenadas <= 180)) {
              while (Coordenadas <= -180) {
                     Coordenadas += 360;
                  }
                  while (Coordenadas > 180) {
                     Coordenadas -= 360;
                  }
           }
           turnGunRight(Coordenadas);

           if (Distancia > 200 || minhaEnergia < 15 || energiaIni > minhaEnergia){
          fire(1);
       } else if (Distancia > 50 ) {
          fire(2);
       } else {
          fire(PontoQuarenta);
       }
   }

//Dança da vitória
   public void onWin(WinEvent e) {

turnRight(72000);
turnLeft(72000);
   }
}
