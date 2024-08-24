package competition.subsystems.drive.commands;

import competition.subsystems.drive.SwerveDriveSubsystem;
import xbot.common.command.BaseCommand;

import javax.inject.Inject;

public class DebugSwerveCommand extends BaseCommand {

    SwerveDriveSubsystem swerve;

    @Inject
    public DebugSwerveCommand(SwerveDriveSubsystem swerve) {
        this.swerve = swerve;
        this.addRequirements(swerve);
    }

    @Override
    public void initialize() {
        // This is where you'll want to set up the swerve drive to move in a square pattern
    }

    @Override
    public void execute() {
        swerve.move(0, 0, 1);
    }

    @Override
    public boolean isFinished() {
        // This is where you'll want to return true when the swerve drive has completed the square pattern
        return false;
    }
}
