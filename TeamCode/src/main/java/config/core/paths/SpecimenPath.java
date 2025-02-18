package config.core.paths;

import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;

public class SpecimenPath {

    public static Pose startPose = new Pose(8, 60, Math.toRadians(0));
    public static Pose scorePreload = new Pose(39, 72, Math.toRadians(0));
    public static Pose controlPointSample1 =new Pose(33,50);
    public static Pose grabSample1 = new Pose(45, 28, Math.toRadians(90));
    public static Pose giveSample1 = new Pose(23, 23, Math.toRadians(0));
    public static Pose grabSample2 = new Pose(45, 19, Math.toRadians(90));
    public static Pose giveSample2 = new Pose(4, 31, Math.toRadians(0));
    public static Pose getSpecimen = new Pose(23, 23, Math.toRadians(0));
    public static Pose controlPointSpecimen1 = new Pose(33, 31);
    public static Pose scoreSpecimen1= new Pose(39, 64, Math.toRadians(0));
    public static Pose controlPointSpecimen2 = new Pose(42, 26);




    public static PathChain preload() {
        return new PathBuilder()
                .addPath(new BezierLine(new Point(startPose), new Point(scorePreload)))
                .setLinearHeadingInterpolation(startPose.getHeading(), scorePreload.getHeading())
                .setZeroPowerAccelerationMultiplier(2)
                .build();
    }
    public static PathChain grabSample1() {
        return new PathBuilder()
                .addPath(new BezierCurve(new Point(scorePreload), new Point(controlPointSample1),new Point(grabSample1)))
                .setLinearHeadingInterpolation(scorePreload.getHeading(), grabSample1.getHeading())
                .setZeroPowerAccelerationMultiplier(2)
                .build();
    }
    public static PathChain giveSample1() {
        return new PathBuilder()
                .addPath(new BezierLine(new Point(grabSample1), new Point(giveSample1)))
                .setLinearHeadingInterpolation(grabSample1.getHeading(), giveSample1.getHeading())
                .setZeroPowerAccelerationMultiplier(2)
                .build();
    }
    public static PathChain grabSample2() {
        return new PathBuilder()
                .addPath(new BezierLine(new Point(giveSample1), new Point(grabSample2)))
                .setLinearHeadingInterpolation(giveSample1.getHeading(), grabSample2.getHeading())
                .setZeroPowerAccelerationMultiplier(2)
                .build();
    }
    public static PathChain giveSample2() {
        return new PathBuilder()
                .addPath(new BezierLine(new Point(grabSample2), new Point(giveSample2)))
                .setLinearHeadingInterpolation(grabSample2.getHeading(), giveSample2.getHeading())
                .setZeroPowerAccelerationMultiplier(2)
                .build();
    }
    public static PathChain getSpecimen1() {
        return new PathBuilder()
                .addPath(new BezierCurve(new Point(giveSample2), new Point(controlPointSpecimen1),new Point(getSpecimen)))
                .setLinearHeadingInterpolation(giveSample2.getHeading(), getSpecimen.getHeading())
                .setZeroPowerAccelerationMultiplier(2)
                .build();
    }
    public static PathChain scoreSpecimen1() {
        return new PathBuilder()
                .addPath(new BezierLine(new Point(getSpecimen), new Point(scoreSpecimen1)))
                .setLinearHeadingInterpolation(giveSample2.getHeading(), getSpecimen.getHeading())
                .setZeroPowerAccelerationMultiplier(2)
                .build();
    }








}
