package competition.subsystems.drive.commands;

import javax.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;

public class TurnLeft90DegreesCommand extends BaseCommand {

    DriveSubsystem drive;

    PoseSubsystem pose;

    double p = 10;

    double d = 10;

    double speed = 0;

    double previousPosition;

    double goal;

    double currentRotation;

    @Inject
    public TurnLeft90DegreesCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose) {
        this.drive = driveSubsystem;
        this.pose = pose;
    }

    @Override
    public void initialize() {
        currentRotation = pose.getCurrentHeading().getDegrees();
        goal = currentRotation + 90;
        previousPosition = currentRotation;
        currentRotation = -currentRotation;
        System.out.println("MY GOAL:" + goal);
        System.out.println("MY starting pos:" + currentRotation);
    }

    @Override
    public void execute() {
        currentRotation = pose.getCurrentHeading().getDegrees();
        double error = goal - currentRotation;
        speed = currentRotation - previousPosition;
        if (error >= 180) {
            error = error - 360;
        }
        double power = p * error - d * speed;
        drive.tankDrive(-power,power);
        previousPosition = currentRotation;
        }
    }
