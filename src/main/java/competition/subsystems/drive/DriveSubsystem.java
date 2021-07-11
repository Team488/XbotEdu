package competition.subsystems.drive;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import edu.wpi.first.wpilibj.MockDistanceSensor;
import competition.electrical_contract.ElectricalContract;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.injection.electrical_contract.CANTalonInfo;

@Singleton
public class DriveSubsystem extends BaseSubsystem {

    public XCANTalon frontLeft;
    public XCANTalon frontRight;
    public XCANTalon rearLeft;
    public XCANTalon rearRight;

    @Inject
    public DriveSubsystem(CommonLibFactory factory, ElectricalContract electricalContract) {
        // instantiate speed controllers and sensors here, save them as class members
        frontLeft = factory.createCANTalon(new CANTalonInfo(1));
        frontRight = factory.createCANTalon(new CANTalonInfo(2));
        rearLeft = factory.createCANTalon(new CANTalonInfo(3));
        rearRight = factory.createCANTalon(new CANTalonInfo(4));
    }

    public void tankDrive(double leftPower, double rightPower) {
        // You'll need to take these power values and assign them to all of the motors.
        // As
        // an example, here is some code that has the frontLeft motor to spin according
        // to
        // the value of leftPower:
        frontLeft.simpleSet(leftPower);
    }
}
