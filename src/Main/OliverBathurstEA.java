package Main;
import robocode.*;
/**
 * Created by Oliver on 09/03/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 * This class is an example of what the robot Java file typically looks like
 * The values in each method are determined by the genes of the robot
 */
public class OliverBathurstEA extends AdvancedRobot{

    public void run(){
        while(true) {
            turnGunRight(Double.POSITIVE_INFINITY);
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        turnRight(-330.596968532769);
    }

    public void onHitByBullet(HitByBulletEvent e){
        ahead(-340.7054933377207);
        turnRadarLeft(-444.74914633856065);
        back(142.52172106161413);
        turnGunRight(-84.72812012130095);
        turnGunLeft(242.57445868205798);
        turnGunRight(-212.24309913356888);
        turnLeft(22.14759310468662);
    }

    public void onHitWall(HitWallEvent e) {
        turnRadarRight(-298.3977548920367);
        turnGunLeft(173.88035616017055);
    }

    public void onHitRobot(HitRobotEvent e) {
        ahead(303.1169395655379);
        turnRight(-154.56739711267375);
        back(207.3349274979289);
        turnRight(-412.760610481228);
        turnRadarLeft(-443.67785539079154);
    }

    public void fireAtEnemy(Object e, Double d) {
        if(e instanceof ScannedRobotEvent){
            String opponentName = ((ScannedRobotEvent) e).getName();
            if(!opponentName.contains("Clone") && !opponentName.contains("OliverBathurstEA")){
                fire(d);
            }
        }else if(e instanceof HitByBulletEvent){
            String opponentName = ((HitByBulletEvent) e).getName();
            if(!opponentName.contains("Clone") && !opponentName.contains("OliverBathurstEA")){
                fire(d);
            }
        }else if(e instanceof HitRobotEvent){
            String opponentName = ((HitRobotEvent) e).getName();
            if(!opponentName.contains("Clone") && !opponentName.contains("OliverBathurstEA")){
                fire(d);
            }
        }else{
            fire(d);
        }
    }
}