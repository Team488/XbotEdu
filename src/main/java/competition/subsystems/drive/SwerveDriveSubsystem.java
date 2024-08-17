package competition.subsystems.drive;

import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import xbot.common.advantage.DataFrameRefreshable;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANSparkMax;
import xbot.common.injection.electrical_contract.DeviceInfo;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.nio.channels.IllegalBlockingModeException;

@Singleton
public class SwerveDriveSubsystem extends BaseSubsystem implements DataFrameRefreshable {

    public XCANSparkMax frontLeftDrive;
    public XCANSparkMax frontRightDrive;
    public XCANSparkMax rearLeftDrive;
    public XCANSparkMax rearRightDrive;

    public XCANSparkMax frontLeftSteering;
    public XCANSparkMax frontRightSteering;
    public XCANSparkMax rearLeftSteering;
    public XCANSparkMax rearRightSteering;

    SwerveDriveKinematics kinematics;
    public static double maxVelocity = 3;
    SwerveDrivePoseEstimator swerveDrivePoseEstimator;

    @Inject
    public SwerveDriveSubsystem(XCANSparkMax.XCANSparkMaxFactory sparkMaxFactory) {
        frontLeftDrive = sparkMaxFactory.create(new DeviceInfo("FrontLeftDrive", 11, false), this.getPrefix(), "FrontLeftDrive");
        frontRightDrive = sparkMaxFactory.create(new DeviceInfo("FrontRightDrive", 22, false), this.getPrefix(), "FrontRightDrive");
        rearLeftDrive = sparkMaxFactory.create(new DeviceInfo("RearLeftDrive", 3, false), this.getPrefix(), "RearLeftDrive");
        rearRightDrive = sparkMaxFactory.create(new DeviceInfo("RearRightDrive", 4, false), this.getPrefix(), "RearRightDrive");

        frontLeftSteering = sparkMaxFactory.create(new DeviceInfo("FrontLeftSteering", 5, false), this.getPrefix(), "FrontLeftSteering");
        frontRightSteering = sparkMaxFactory.create(new DeviceInfo("FrontRightSteering", 6, false), this.getPrefix(), "FrontRightSteering");
        rearLeftSteering = sparkMaxFactory.create(new DeviceInfo("RearLeftSteering", 7, false), this.getPrefix(), "RearLeftSteering");
        rearRightSteering = sparkMaxFactory.create(new DeviceInfo("RearRightSteering", 8, false), this.getPrefix(), "RearRightSteering");

        kinematics = new SwerveDriveKinematics(
                new Translation2d(0.5, 0.5),
                new Translation2d(0.5, -0.5),
                new Translation2d(-0.5, 0.5),
                new Translation2d(-0.5, -0.5)
        );

        swerveDrivePoseEstimator = new SwerveDrivePoseEstimator()
    }

    public void move(double xVelocity, double yVelocity, double rotationPower) {
        // Calculate the drive and steer values for each wheel
        var swerveModuleStates = kinematics.toSwerveModuleStates(
                new ChassisSpeeds(xVelocity, yVelocity, rotationPower)
        );

        aKitLog.record("DesiredSwerveStates", swerveModuleStates);

        // Set the drive and steer values for each wheel
        frontLeftDrive.set(swerveModuleStates[0].speedMetersPerSecond / maxVelocity);
        frontRightDrive.set(swerveModuleStates[1].speedMetersPerSecond / maxVelocity);
        rearLeftDrive.set(swerveModuleStates[2].speedMetersPerSecond / maxVelocity);
        rearRightDrive.set(swerveModuleStates[3].speedMetersPerSecond / maxVelocity);

        //frontLeftSteering.set(0.25);
        //frontRightSteering.set(0.25);
        //rearLeftSteering.set(0.25);
        //rearRightSteering.set(0.25);
    }

    public SwerveModuleState[] getSwerveModuleStates() {
        return new SwerveModuleState[]{
                buildSwerveModuleStateFromMotors(frontLeftDrive, frontLeftSteering),
                buildSwerveModuleStateFromMotors(frontRightDrive, frontRightSteering),
                buildSwerveModuleStateFromMotors(rearLeftDrive, rearLeftSteering),
                buildSwerveModuleStateFromMotors(rearRightDrive, rearRightSteering)
        };
    }

    public SwerveModuleState buildSwerveModuleStateFromMotors(XCANSparkMax driveMotor, XCANSparkMax steeringMotor) {
        return new SwerveModuleState(
                driveMotor.getVelocity(),
                Rotation2d.fromRadians(steeringMotor.getPosition())
        );
    }

    @Override
    public void periodic() {

    }

    @Override
    public void refreshDataFrame() {
        frontLeftDrive.refreshDataFrame();
        frontRightDrive.refreshDataFrame();
        rearLeftDrive.refreshDataFrame();
        rearRightDrive.refreshDataFrame();

        frontLeftSteering.refreshDataFrame();
        frontRightSteering.refreshDataFrame();
        rearLeftSteering.refreshDataFrame();
        rearRightSteering.refreshDataFrame();

        aKitLog.record("CurrentSwerveState", getSwerveModuleStates());
    }
}
