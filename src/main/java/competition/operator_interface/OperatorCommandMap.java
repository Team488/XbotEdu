package competition.operator_interface;

import javax.inject.Inject;
import javax.inject.Singleton;

import competition.subsystems.drive.commands.ArcadeDriveWithJoysticksCommand;
import competition.subsystems.drive.commands.TogglePrecisionDriveCommand;
import xbot.common.subsystems.pose.commands.SetRobotHeadingCommand;

/**
 * Maps operator interface buttons to commands
 */
@Singleton
public class OperatorCommandMap {
    
    @Inject
    public OperatorCommandMap() {}

    // Example for setting up a command to fire when a button is pressed:
    @Inject
    public void setupMyCommands(
            OperatorInterface operatorInterface,
            SetRobotHeadingCommand resetHeading,
            TogglePrecisionDriveCommand togglePrecision,
            ArcadeDriveWithJoysticksCommand ArcadeDrive)
    {
        resetHeading.setHeadingToApply(90);
        operatorInterface.gamepad.getifAvailable(1).whileTrue(resetHeading);
        operatorInterface.gamepad.getifAvailable(2).onTrue(togglePrecision);
        operatorInterface.gamepad.getifAvailable(3).onTrue(ArcadeDrive);
    }

}
