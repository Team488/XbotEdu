package competition.subsystems.drive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;

import competition.BaseCompetitionTest;
import competition.operator_interface.OperatorInterface;
import competition.subsystems.pose.PoseSubsystem;
import edu.wpi.first.wpilibj.MockXboxControllerAdapter;
import xbot.common.controls.actuators.mock_adapters.MockCANTalon;

public class BaseDriveTest extends BaseCompetitionTest {

    protected DriveSubsystem drive;
    protected PoseSubsystem pose;
    OperatorInterface oi;

    MockXboxControllerAdapter gamepad;

    @Before
    public void setUp() {
        super.setUp();
        drive = this.injector.getInstance(DriveSubsystem.class);
        oi = this.injector.getInstance(OperatorInterface.class);
        pose = this.injector.getInstance(PoseSubsystem.class);
        gamepad = (MockXboxControllerAdapter) oi.gamepad;
    }

    public void assertDrive(double left, double right) {
        assertDrive(left, right, "");
    }

    public void assertDrive(double left, double right, String message) {
        assertEquals(message, left, drive.frontLeft.getMotorOutputPercent(), 0.001);

        assertEquals(message, right, drive.frontRight.getMotorOutputPercent(), 0.001);
    }

    public void assertTurningLeft() {
        // left < right
        assertTrue(drive.frontLeft.getMotorOutputPercent() < drive.frontRight.getMotorOutputPercent());
    }

    public void assertTurningRight() {
        // right < left
        assertTrue(drive.frontLeft.getMotorOutputPercent() > drive.frontRight.getMotorOutputPercent());
    }

    public void assertGoingForward() {
        // right + left > 0
        assertTrue((drive.frontLeft.getMotorOutputPercent() + drive.frontRight.getMotorOutputPercent()) > 0);
    }

    public void assertGoingBackward() {
        // right + left > 0
        assertTrue((drive.frontLeft.getMotorOutputPercent() + drive.frontRight.getMotorOutputPercent()) < 0);
    }

    public void setPosition(double position) {
        ((MockCANTalon)drive.frontRight).setPosition(position / pose.scalingFactorFromTicksToInches);
        ((MockCANTalon)drive.frontLeft).setPosition(position / pose.scalingFactorFromTicksToInches);
    } 

}
