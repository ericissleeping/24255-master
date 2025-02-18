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
    public static Pose grabSample1 = new Pose(45, 28, Math.toRadians(90));
    public static Pose giveSample1 = new Pose(23, 23, Math.toRadians(0));
    public static Pose grabSample2 = new Pose(45, 19, Math.toRadians(90));
    public static Pose giveSample2 = new Pose(4, 31, Math.toRadians(0));
    public static Pose getSpecimen1 = new Pose(23, 23, Math.toRadians(0));
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







}
