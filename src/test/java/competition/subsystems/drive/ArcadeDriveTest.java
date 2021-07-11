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

        left.setLeftStick(new XYPair(0, 1));
        command.execute();
        this.assertDrive(1, 1);

        left.setLeftStick(new XYPair(1, 0));
        command.execute();
        this.assertDrive(1, -1);

        left.setLeftStick(new XYPair(0.8, 0.8));
        command.execute();
        this.assertTurningRight();
    }
}