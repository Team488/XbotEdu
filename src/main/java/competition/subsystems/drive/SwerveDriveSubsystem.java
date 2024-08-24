package competition.subsystems.drive;

import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import xbot.common.advantage.DataFrameRefreshable;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANSparkMax;
import xbot.common.injection.electrical_contract.DeviceInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

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

        swerveDrivePoseEstimator = new SwerveDrivePoseEstimator(
                kinematics,
                Rotation2d.fromDegrees(0),
                getSwerveModulePositions(),
                new Pose2d());
    }

    public void move(double xVelocity, double yVelocity, double radiansPerSecond) {
        // This standard WPI library converts velocity/rotation goals
        // into individual wheel angles and speeds.
        var swerveModuleStates = kinematics.toSwerveModuleStates(
                new ChassisSpeeds(xVelocity, yVelocity, radiansPerSecond)
        );
        aKitLog.record("DesiredSwerveStates", swerveModuleStates);

        // Note for mentors - all the code below this line in this method
        // should be deleted before releasing to students, as it is the partial implementation
        // of the swerve drive algorithm that students will be implementing.

        // Create another set of swerveModuleStates, optimizedSwerveModuleStates,
        // by calling SwerveModuleState.optimize(SwerveModuleState desiredState, Rotation2d currentAngle)
        var optimizedSwerveModuleStates = new SwerveModuleState[4];
        var modulePositions = getSwerveModulePositions();

        for (int i = 0; i < 4; i++) {
            optimizedSwerveModuleStates[i] = SwerveModuleState.optimize(swerveModuleStates[i], modulePositions[i].angle);
        }

        // Set the drive and steer values for each wheel
        frontLeftDrive.set(optimizedSwerveModuleStates[0].speedMetersPerSecond / maxVelocity);
        frontRightDrive.set(optimizedSwerveModuleStates[1].speedMetersPerSecond / maxVelocity);
        rearLeftDrive.set(optimizedSwerveModuleStates[2].speedMetersPerSecond / maxVelocity);
        rearRightDrive.set(optimizedSwerveModuleStates[3].speedMetersPerSecond / maxVelocity);

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

    public SwerveModulePosition[] getSwerveModulePositions() {
        return new SwerveModulePosition[]{
                new SwerveModulePosition(frontLeftDrive.getPosition(), Rotation2d.fromRadians(frontLeftSteering.getPosition())),
                new SwerveModulePosition(frontRightDrive.getPosition(), Rotation2d.fromRadians(frontRightSteering.getPosition())),
                new SwerveModulePosition(rearLeftDrive.getPosition(), Rotation2d.fromRadians(rearLeftSteering.getPosition())),
                new SwerveModulePosition(rearRightDrive.getPosition(), Rotation2d.fromRadians(rearRightSteering.getPosition()))
        };
    }

    public SwerveModuleState buildSwerveModuleStateFromMotors(XCANSparkMax driveMotor, XCANSparkMax steeringMotor) {
        return new SwerveModuleState(
                driveMotor.getVelocity(),
                Rotation2d.fromRadians(steeringMotor.getPosition())
        );
    }

    Rotation2d swerveDriveHeading = Rotation2d.fromDegrees(0);
    Pose2d swerveDrivePose = new Pose2d();

    public Pose2d getSwervePose() {
        return swerveDrivePose;
    }

    @Override
    public void periodic() {
        swerveDrivePose = swerveDrivePoseEstimator.update(
                swerveDriveHeading,
                getSwerveModulePositions()
        );

        var speeds = kinematics.toChassisSpeeds(getSwerveModuleStates());
        swerveDriveHeading = swerveDrivePose.getRotation().plus(Rotation2d.fromRadians(speeds.omegaRadiansPerSecond * 0.02));

        aKitLog.record("CurrentPose", swerveDrivePose);
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
