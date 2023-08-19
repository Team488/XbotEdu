package competition.subsystems.drive.commands;

import javax.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;
import xbot.common.math.PID;
import xbot.common.math.PIDManager;
import xbot.common.subsystems.drive.control_logic.HeadingModule;

public class DriveToOrientationCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;
    PIDManager pid;

    HeadingModule headingModule;

    boolean finished = false;
    double error_threshold = 0.1;
    double currentYaw;
    double target_rotation;
    double lastYaw = 0;
    double velocity = 0;;
    double targetHeading;

    /*@Inject
    public DriveToOrientationCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose) {
        this.drive = driveSubsystem;
        this.pose = pose;
    } */

    @Inject
    public DriveToOrientationCommand(DriveSubsystem driveSubsystem, HeadingModule.HeadingModuleFactory headingModuleFactory, PIDManager.PIDManagerFactory pidManagerFactory) {
        this.drive = driveSubsystem;
        pid = pidManagerFactory.create("Rotate");
        headingModule = headingModuleFactory.create(pid);
    }


    /*public double compareCurrentAndGoal(double current, double goal) {
        current %= 180;
        goal %= 180;

        double distance;
        double magnitude1;
        double magnitude2;
        
        if (current > goal) {
            magnitude1 = current - goal;
            magnitude2 = current - (goal + 360);
        } else {
            magnitude1 = current - goal;
            magnitude2 = current - (goal - 360);
        }

        if (Math.abs(magnitude1) > Math.abs(magnitude2)){
            distance = magnitude2;
        } else {
            distance = magnitude1;
        }

        return -distance;
    }*/

    public void setTargetHeading(double heading) {
        // This method will be called by the test, and will give you a goal heading.
        // You'll need to remember this target position and use it in your calculations.
        targetHeading = heading;
    }

    @Override
    public void initialize() {
        /* // If you have some one-time setup, do it here.
        currentYaw = pose.getCurrentHeading().getDegrees();
        target_rotation = targetHeading;

        if (target_rotation > 180) {
            target_rotation -= 360;
            System.out.println("target_rotation" + target_rotation);
        } else if (target_rotation < -180) {
            target_rotation += 360;
        }*/
    }

    @Override
    public void execute() {
        double power = headingModule.calculateHeadingPower(targetHeading);
        drive.tankDrive(-power, power);
    }

    /*@Override
    public void execute() {
        // Here you'll need to figure out a technique that:
        // - Gets the robot to turn to the target orientation
        // - Gets the robot stop (or at least be moving really really slowly) at the
        // target position

        // How you do this is up to you. If you get stuck, ask a mentor or student for
        // some hints!
        double rotationUntilGoal = compareCurrentAndGoal(currentYaw, target_rotation);
        double power = (rotationUntilGoal * 3) - (velocity * 1);
        System.out.println("POWER: " + power);
        System.out.println("RTG: " + rotationUntilGoal);
        System.out.println("C-YAW: " + currentYaw);
        System.out.println("TARG: " + target_rotation);

        drive.tankDrive(0, power);
        lastYaw = currentYaw;
    }

    @Override
    public boolean isFinished() {
        // Modify this to return true once you have met your goal,
        // and you're moving fairly slowly (ideally stopped)
        currentYaw = pose.getCurrentHeading().getDegrees();
        velocity = currentYaw - lastYaw;
       // rotated += velocity;
        System.out.println("velocity: " + velocity);

        if (currentYaw >= target_rotation - error_threshold & currentYaw <= target_rotation + error_threshold & velocity >= -error_threshold & velocity <= error_threshold) {
            return true;
        }
        return false;
    }*/
}
