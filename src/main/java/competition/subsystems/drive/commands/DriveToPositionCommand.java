package competition.subsystems.drive.commands;

import javax.inject.Inject;

import xbot.common.math.PIDManager;
import xbot.common.math.PIDManager.PIDManagerFactory;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;

public class DriveToPositionCommand extends BaseCommand {
    DriveSubsystem drive;
    PoseSubsystem pose;
    PIDManager pid;

    double target;

    @Inject
    public DriveToPositionCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose, PIDManagerFactory pidManagerFactory) {
        this.drive = driveSubsystem;
        this.pose = pose;
        this.pid = pidManagerFactory.create("DriveToPoint");

        pid.setEnableErrorThreshold(true);
        pid.setErrorThreshold(0.02);
        pid.setEnableDerivativeThreshold(true);
        pid.setDerivativeThreshold(0.05);

        pid.setP(0.3);
        pid.setD(11);
    }

    public void setTargetPosition(double position) {
        target = position;
    }

    @Override
    public void initialize() {
        pid.reset();
    }

    @Override
    public void execute() {
        double current = pose.getPosition();
        double power = pid.calculate(target, current);
        drive.tankDrive(power, power);
    }

    @Override
    public boolean isFinished() {
        if (pid.isOnTarget()) {
            drive.tankDrive(0, 0);
            return true;
        }
        return false;
    }

}
