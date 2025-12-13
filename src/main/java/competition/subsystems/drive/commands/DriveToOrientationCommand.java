package competition.subsystems.drive.commands;

import javax.inject.Inject;

import competition.subsystems.pose.PoseSubsystem;
import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;

import static java.lang.Math.abs;

public class DriveToOrientationCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;
    double goal;
    double currentPostion;
    double previousPos;


    @Inject
    public DriveToOrientationCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose) {
        this.drive = driveSubsystem;
        this.pose = pose;
    }

    public void setTargetHeading(double heading) {
        goal = heading; // heading = direction you want face in degrees
        // This method will be called by the test, and will give you a goal heading.
        if (goal > 180) {
            goal = goal - 360; //get the position between 0 to 180
        }
        // You'll need to remember this target position and use it in your calculations.
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        // Here you'll need to figure out a technique that:
        // - Gets the robot to turn to the target orientation
        // - Gets the robot stop (or at least be moving really really slowly) at the target position
        //pose.getCurrentHeading(); //use current and goal/target & where you are facing rn
        //always turn to left,  but turn to right at some point
        currentPostion = pose.getCurrentHeading().getDegrees();
        double speed = pose.getCurrentHeading().getDegrees() - previousPos;
        double error = goal - pose.getCurrentHeading().getDegrees();
        double power = error - speed;
        // higher distance error higher power,more power less speed ||| power = error - speed
        drive.tankDrive(-power, power);
        previousPos = pose.getCurrentHeading().getDegrees();
    }


    @Override
    public boolean isFinished() {
        double error = pose.getCurrentHeading().getDegrees() - goal;
        double speed = pose.getCurrentHeading().getDegrees() - previousPos;

        return Math.abs(error) < 0.05 && Math.abs(speed) < 0.05; //simple version of both of the ones at the bottom


       // boolean closeEnough = Math.abs(error) < 0.05; simple version of the one at the bottom
       // boolean slowEnough = Math.abs(speed) < 0.05;  simple version of the one at the bottom

       // if (Math.abs(error) < 0.05) {
       //     closeEnough = true;
       // }

       // if (Math.abs(speed) < 0.05) {
       //     slowEnough = true;
       // }
        // return closeEnough && slowEnough;


    }
}

