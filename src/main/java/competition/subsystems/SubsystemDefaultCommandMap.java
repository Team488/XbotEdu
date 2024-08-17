package competition.subsystems;

import javax.inject.Inject;
import javax.inject.Singleton;

import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.SwerveDriveSubsystem;
import competition.subsystems.drive.commands.DebugSwerveCommand;
import competition.subsystems.drive.commands.TankDriveWithJoysticksCommand;

@Singleton
public class SubsystemDefaultCommandMap {
    // For setting the default commands on subsystems

    @Inject
    public SubsystemDefaultCommandMap() {}

    @Inject
    public void setupDriveSubsystem(DriveSubsystem driveSubsystem, TankDriveWithJoysticksCommand command) {
        driveSubsystem.setDefaultCommand(command);
    }

    @Inject
    public void setupSwerveDriveSubsystem(SwerveDriveSubsystem swerveDriveSubsystem, DebugSwerveCommand command) {
        swerveDriveSubsystem.setDefaultCommand(command);
    }
}
