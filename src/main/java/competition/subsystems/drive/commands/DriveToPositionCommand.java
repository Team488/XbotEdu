package competition.subsystems.drive.commands;

import javax.inject.Inject;

import xbot.common.command.BaseCommand;
import xbot.common.math.PIDManager;
import xbot.common.math.PIDManager.PIDManagerFactory;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;

public class DriveToPositionCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;
    PIDManager pid;
    double targetPos;

    @Inject
    public DriveToPositionCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose, PIDManagerFactory pidManagerFactory){
        this.drive = driveSubsystem;
        this.pose = pose;
        this.pid = pidManagerFactory.create("DriveToPoint");

        pid.setEnableErrorThreshold(true); // Turn on distance checking
        pid.setErrorThreshold(0.1);
        pid.setEnableDerivativeThreshold(true); // Turn on speed checking
        pid.setDerivativeThreshold(0.1);

        // manually adjust these values to adjust the action
        pid.setP(1);
        pid.setD(4);
    }

    public void setTargetPosition(double position) {
        // This method will be called by the test, and will give you a goal distance.
        // You'll need to remember this target position and use it in your calculations.
        System.out.println("Target Position: " + String.valueOf(position));
        targetPos = position;
    }

    @Override
    public void initialize() {
        // If you have some one-time setup, do it here.
        pid.reset();
    }

    @Override
    public void execute() {
        double currentPosition = pose.getPosition();
        double power = pid.calculate(targetPos, currentPosition);
        drive.tankDrive(power, power);
    }

    @Override
    public boolean isFinished() {
        return pid.isOnTarget();
    }

    /* Below are old custom logic I had wrote. Above are the newer stuff written with
    SeriouslyCommonLib.

    @Override
    public void execute() {
        

        // So basically make an algorithm function to figure out: total # of loops needed, and when to stop adding velocity, and terminate when # of loops reached.

        // Here you'll need to figure out a technique that:
        // - Gets the robot to move to the target position
        // - Hint: use pose.getPosition() to find out where you are
        // - Gets the robot stop (or at least be moving really really slowly) at the
        // target position

        // How you do this is up to you. If you get stuck, ask a mentor or student for
        // some hints!

        // Positioning variables
        double currentPosition = pose.getPosition();
        double distanceUntilGoal = targetPos - currentPosition;

        // Calculate velocity
        double velocity = currentPosition - lastPos;

        // Debugging
        System.out.println("Current Position: " + String.valueOf(currentPosition));
        System.out.println("Dist. til goal: " + Double.valueOf(distanceUntilGoal));

        System.out.println("Finished: " + finished);

        double power = (distanceUntilGoal * 0.5) - (velocity * 3);

        drive.tankDrive(power, power);
        lastPos = currentPosition;
    }

    @Override
    public boolean isFinished() {
        // Modify this to return true once you have met your goal,
        // and you're moving fairly slowly (ideally stopped)

        double currentPosition = pose.getPosition();
        double velocity = currentPosition - lastPos;

        if (currentPosition <= targetPos + error_threshold & currentPosition >= targetPos - error_threshold & velocity >= -error_threshold & velocity <= error_threshold) {
            return true;
        }
        return false;
    } */

}
