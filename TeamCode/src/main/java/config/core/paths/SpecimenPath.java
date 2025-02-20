package config.core.paths;

import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;

public class SpecimenPath {

    public static Pose startPose = new Pose(8, 63, Math.toRadians(0));
    public static Pose scorePreload = new Pose(34, 75, Math.toRadians(0));
    public static Pose controlPointSample1 =new Pose(23,45);
    public static Pose grabSample1 = new Pose(42, 37, Math.toRadians(90));
    public static Pose giveSample1 = new Pose(28, 23, Math.toRadians(0));
    public static Pose grabSample2 = new Pose(42, 27, Math.toRadians(90));
    public static Pose giveSample2 = new Pose(28, 23, Math.toRadians(0));
    public static Pose getSpecimen = new Pose(9, 34, Math.toRadians(180));
    public static Pose controlPointSpecimen1 = new Pose(37, 31);
    public static Pose scoreSpecimen1= new Pose(34, 73, Math.toRadians(0));
    public static Pose scoreSpecimen2= new Pose(34, 71, Math.toRadians(0));
    public static Pose scoreSpecimen3= new Pose(34, 69, Math.toRadians(0));
    public static Pose finalPose = new Pose (8, 34, Math.toRadians(90));
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
                .setZeroPowerAccelerationMultiplier(1)
                .build();
    }
    public static PathChain scoreSpecimen1() {
        return new PathBuilder()
                .addPath(new BezierLine(new Point(getSpecimen), new Point(scoreSpecimen1)))
                .setLinearHeadingInterpolation(getSpecimen.getHeading(), scoreSpecimen1.getHeading())
                .setZeroPowerAccelerationMultiplier(1)
                .build();
    }
    public static PathChain getSpecimen2() {
        return new PathBuilder()
                .addPath(new BezierCurve(new Point(scoreSpecimen1), new Point(controlPointSpecimen1),new Point(getSpecimen)))
                .setLinearHeadingInterpolation(scoreSpecimen1.getHeading(), getSpecimen.getHeading())
                .setZeroPowerAccelerationMultiplier(1)
                .build();
    }
    public static PathChain scoreSpecimen2() {
        return new PathBuilder()
                .addPath(new BezierLine(new Point(getSpecimen), new Point(scoreSpecimen2)))
                .setLinearHeadingInterpolation(getSpecimen.getHeading(), scoreSpecimen2.getHeading())
                .setZeroPowerAccelerationMultiplier(1)
                .build();
    }
    public static PathChain getSpecimen3() {
        return new PathBuilder()
                .addPath(new BezierCurve(new Point(scoreSpecimen2), new Point(controlPointSpecimen1),new Point(getSpecimen)))
                .setLinearHeadingInterpolation(scoreSpecimen2.getHeading(), getSpecimen.getHeading())
                .setZeroPowerAccelerationMultiplier(1)
                .build();
    }
    public static PathChain scoreSpecimen3() {
        return new PathBuilder()
                .addPath(new BezierLine(new Point(getSpecimen), new Point(scoreSpecimen3)))
                .setLinearHeadingInterpolation(getSpecimen.getHeading(), scoreSpecimen3.getHeading())
                .setZeroPowerAccelerationMultiplier(1)
                .build();
    }

    public static PathChain finalPath() {
        return new PathBuilder()
                .addPath(new BezierLine(new Point(scoreSpecimen3), new Point(finalPose)))
                .setLinearHeadingInterpolation(scoreSpecimen3.getHeading(), finalPose.getHeading())
                .setZeroPowerAccelerationMultiplier(2)
                .build();
    }










}
