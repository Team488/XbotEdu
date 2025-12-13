package competition.subsystems.drive.commands;

import javax.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

public class DriveToPositionCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;
    double targetPosition;
    DoubleProperty pProperty;
    DoubleProperty dProperty;
    double previousPosition = 0;

    //DriveSubsystem driveSubsystem

    @Inject
    public DriveToPositionCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose, PropertyFactory propertyFactory) {
        propertyFactory.setPrefix(this);
        this.drive = driveSubsystem;
        this.pose = pose;

        pProperty = propertyFactory.createPersistentProperty("p", 1);
        dProperty = propertyFactory.createPersistentProperty("d", 1);
    }

    public void setTargetPosition(double position) {
        // This method will be called by the test, and will give you a goal distance.
        // You'll need to remember this target position and use it in your calculations.
        this.targetPosition = position;
    }

    @Override
    public void initialize() {
        // If you have some one-time setup, do it here.
    }

    @Override
    public void execute() {
        //starts at 0, stops at 300. Slowly goes up by 1 (momentum) << we wanna remove it, so we can stop exactly on 300
        // Here you'll need to figure out a technique that:
        // - Gets the robot to move to the target position
        // - Gets the robot stop (or at least be moving really really slowly) at the target position
        //y=mx+b , power = p*(error) + d or - d * speed
        //d = deriative, speed = "velocity" how fast your going, error = distance from goal  p, = proportion

        //stop when you reach target
    //cordinates or target/end position in the brackets

        double error = this.targetPosition - pose.getPosition();
//faster speed less power, further away more power,
        // if (error < .001) {
        //   drive.tankDrive(0, 0);
        //  }else if(error < 2) {
        //    drive.tankDrive(0.005, 0.005);

        // } else {
        //      drive.tankDrive(0.10, 0.10);
        double speed = pose.getPosition() - previousPosition;
        double power = pProperty.get() * error - dProperty.get() * speed;

        drive.tankDrive(power, power);
        previousPosition = pose.getPosition(); //previous to current position



    }

    @Override
    public boolean isFinished() {

        //speed, current position,
        return true;

        // Modify this to return true once you have met your goal,
        // and you're moving fairly slowly (ideally stopped)
    }

}
