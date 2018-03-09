package Main;
import robocode.*;
/**
 * Created by Oliver on 09/03/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

/**
 * This class is an example of what the robot Java file typically looks like
 * The values in each method are determined by the genes of the robot
 */
class OliverBathurstEA extends Robot {
    public void run(){
        while(true) {
            turnGunRight(Double.POSITIVE_INFINITY);
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        turnGunRight(88.52620056792767);
        turnLeft(2.412960851011736);
        ahead(153.61489546221063);
    }

    public void onHitByBullet(HitByBulletEvent e){
        turnGunRight(261.9726502353522);
        turnRadarLeft(15.510032852584942);
    }

    public void onHitWall(HitWallEvent e) {
        turnGunRight(88.70092579836357);
    }

    public void onHitRobot(HitRobotEvent e) {
        turnRight(77.6986745451936);
    }

    public void fireAtEnemy(Object e, Double d) {
        if(e instanceof ScannedRobotEvent){
            if(!((ScannedRobotEvent) e).getName().contains(getName())){
                fire(d);
            }
        }else if(e instanceof HitByBulletEvent){
            if(!((HitByBulletEvent) e).getName().contains(getName())){
                fire(d);
            }
        }else if(e instanceof HitRobotEvent){
            if(!((HitRobotEvent) e).getName().contains(getName())){
                fire(d);
            }
        }else{
            fire(d);
        }
    }
}