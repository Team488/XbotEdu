package competition.subsystems.drive;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import javax.inject.Inject;
import javax.inject.Singleton;

import competition.electrical_contract.ElectricalContract;
import edu.wpi.first.math.geometry.Translation2d;
import xbot.common.advantage.AKitLogger;
import xbot.common.advantage.DataFrameRefreshable;
import xbot.common.controls.actuators.XCANSparkMax;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.controls.actuators.XCANTalon.XCANTalonFactory;
import xbot.common.injection.electrical_contract.CANTalonInfo;
import xbot.common.injection.electrical_contract.DeviceInfo;
import xbot.common.math.PIDManager;
import xbot.common.math.XYPair;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;
import xbot.common.subsystems.drive.BaseDriveSubsystem;

@Singleton
public class DriveSubsystem extends BaseDriveSubsystem implements DataFrameRefreshable {

    public final XCANSparkMax frontLeft;
    public final XCANSparkMax frontRight;
    boolean precisionMode=false;
    DoubleProperty dp;
    @Inject
    public DriveSubsystem(XCANSparkMax.XCANSparkMaxFactory sparkMaxFactory, ElectricalContract electricalContract, PropertyFactory pf) {
        log.info("Creating DriveSubsystem");
        // instantiate speed controllers and sensors here, save them as class members

        this.frontLeft = sparkMaxFactory
                .create(new DeviceInfo("FrontLeft", 1, false), this.getPrefix(), "FrontLeft");
        this.frontRight = sparkMaxFactory
                .create(new DeviceInfo("FrontRight", 2, false), this.getPrefix(), "FrontRight");
        frontLeft.setCheckForSuspiciousSensorValues(false);
        frontRight.setCheckForSuspiciousSensorValues(false);

        pf.setPrefix(this);
        dp = pf.createPersistentProperty("DriveSubsystem", 1.5);
    }
   public void tankDrive(double leftPower, double rightPower) {
       if (this.precisionMode){
           this.frontLeft.set(leftPower*.5);
           this.frontRight.set(rightPower*.5);
       } else{
           this.frontLeft.set(leftPower);
           this.frontRight.set(rightPower);
       }
    }

    public boolean getPrecison(){
        return precisionMode;
    }
    public void setPrecision(boolean activate){
        precisionMode=activate;
    }
    @Override
   public PIDManager getPositionalPid() {
        // TODO: Auto-generated method stub
        return null;
    }

    @Override
    public PIDManager getRotateToHeadingPid() {
        // TODO: Auto-generated method stub
        return null;
    }

    @Override
    public PIDManager getRotateDecayPid() {
        // TODO: Auto-generated method stub
        return null;
    }

    @Override
    public void move(XYPair translate, double rotate) {
       throw new RuntimeException("Not yet implemented");
    }

    @Override
    public double getLeftTotalDistance() {
        // TODO: Auto-generated method stub
        return 0;
    }

    @Override
    public double getRightTotalDistance() {
        // TODO: Auto-generated method stub
        return 0;
    }

    @Override
    public double getTransverseDistance() {
        // TODO: Auto-generated method stub
        return 0;
    }

    public void periodic() {
        aKitLog.setLogLevel(AKitLogger.LogLevel.DEBUG);
    }

    public void refreshDataFrame() {
        frontLeft.refreshDataFrame();
        frontRight.refreshDataFrame();
    }
}
