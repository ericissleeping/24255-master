package config.core.paths;

import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;

public class SamplePath {

    public static Pose startPose = new Pose(8, 110, Math.toRadians(90));
    public static Pose scorePose = new Pose(17, 124, Math.toRadians(135));
    public static Pose grabSample1 = new Pose(30,119,Math.toRadians(180));

    public static Pose scorePose1 = new Pose(17, 124, Math.toRadians(135));
    public static Pose grabSample2= new Pose(30,128,Math.toRadians(180));
    public static Pose grabSample3= new Pose(31,130,Math.toRadians(-152));
    public static Pose grabSample4= new Pose(62,96,Math.toRadians(100));
    public static Pose detectSample4= new Pose(62,94,Math.toRadians(80));



    public static Pose controlPoint1= new Pose(58,108);


    public static PathChain preload() {
        return new PathBuilder()
                .addPath(new BezierLine(new Point(startPose), new Point(scorePose)))
                .setLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading())
                .setZeroPowerAccelerationMultiplier(2)
                .build();
    }

    public static PathChain grabSample1(){
        return new PathBuilder()
                .addPath(new BezierLine(new Point(scorePose), new Point(grabSample1)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), grabSample1.getHeading())
                .setZeroPowerAccelerationMultiplier(2)
                .build();
    }
    public static PathChain scoreSample1(){
        return new PathBuilder()
                .addPath(new BezierLine(new Point(grabSample1), new Point(scorePose1)))
                .setLinearHeadingInterpolation(grabSample1.getHeading(), scorePose1.getHeading())
                .setZeroPowerAccelerationMultiplier(2)
                .build();
    }

    public static PathChain grabSample2(){
        return new PathBuilder()
                .addPath(new BezierLine(new Point(scorePose1), new Point(grabSample2)))
                .setLinearHeadingInterpolation(scorePose1.getHeading(), grabSample2.getHeading())
                .setZeroPowerAccelerationMultiplier(2)
                .build();
    }
    public static PathChain scoreSample2(){
        return new PathBuilder()
                .addPath(new BezierLine(new Point(grabSample2), new Point(scorePose)))
                .setLinearHeadingInterpolation(grabSample2.getHeading(), scorePose.getHeading())
                .setZeroPowerAccelerationMultiplier(2)
                .build();
    }
    public static PathChain grabSample3(){
        return new PathBuilder()
                .addPath(new BezierLine(new Point(scorePose1), new Point(grabSample3)))
                .setLinearHeadingInterpolation(scorePose1.getHeading(), grabSample3.getHeading())
                .setZeroPowerAccelerationMultiplier(2)
                .build();
    }
    public static PathChain scoreSample3(){
        return new PathBuilder()
                .addPath(new BezierLine(new Point(grabSample3), new Point(scorePose)))
                .setLinearHeadingInterpolation(grabSample3.getHeading(), scorePose.getHeading())
                .setZeroPowerAccelerationMultiplier(2)
                .build();
    }
    public static PathChain grabSample4(){
        return new PathBuilder()
                .addPath(new BezierCurve(new Point(scorePose),new Point(controlPoint1),new Point(grabSample4)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), grabSample4.getHeading())
                .setZeroPowerAccelerationMultiplier(1)
                .build();
    }
    public static PathChain detectSample4(){
        return new PathBuilder()
                .addPath(new BezierLine(new Point(grabSample4),new Point(detectSample4)))
                .setLinearHeadingInterpolation(grabSample4.getHeading(), detectSample4.getHeading())
                .setZeroPowerAccelerationMultiplier(4)
                .build();
    }

    public static PathChain scoreSample4(){
        return new PathBuilder()
                .addPath(new BezierCurve(new Point(detectSample4),new Point(controlPoint1),new Point(scorePose)))
                .setLinearHeadingInterpolation(detectSample4.getHeading(), scorePose.getHeading())
                .setZeroPowerAccelerationMultiplier(2)
                .build();
    }






}
