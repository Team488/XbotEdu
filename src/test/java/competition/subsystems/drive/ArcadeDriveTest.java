package competition.subsystems.drive;

import org.junit.Test;

import xbot.common.command.BaseCommand;
import xbot.common.math.XYPair;
import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.commands.ArcadeDriveWithJoysticksCommand;
import edu.wpi.first.wpilibj.MockXboxControllerAdapter;

public class ArcadeDriveTest extends BaseDriveTest {

    @Test
    public void test() {
        OperatorInterface oi = this.injector.getInstance(OperatorInterface.class);

        BaseCommand command = injector.getInstance(ArcadeDriveWithJoysticksCommand.class);

        MockXboxControllerAdapter left = (MockXboxControllerAdapter) oi.gamepad;

        command.initialize();

        command.execute();
        this.assertDrive(0, 0);

        // 0 "Rotation", 1 "Forward" : robot should move forward in a straight line at full power.
        left.setLeftStick(new XYPair(0, 1));
        command.execute();
        // Since the robot is using a tank drive chassis, moving forward means both the left and right wheels should
        // be moving forward at full power.
        this.assertDrive(1, 1);

        // 1 "Rotation", 0 "Forward" : robot should turn left with maximum power, since postive rotation == left rotation.
        left.setLeftStick(new XYPair(1, 0));
        command.execute();
        // Tank drive chassis, so to turn left at maximum power, the left side drives backwards and the right side drives forwards (both at full power)
        this.assertDrive(-1, 1);

        // 0.8 "Rotation", 0.9 "Forward" : robot does some combination of moving forward and turning left.
        // Depending on student implementation, a whole range of values are acceptable, so we just check for
        // "Is the robot mostly turning left" and "Is the robot mostly moving forward"
        left.setLeftStick(new XYPair(0.8, 0.8));
        command.execute();
        this.assertTurningLeft();
        this.assertGoingForward();
    }
}