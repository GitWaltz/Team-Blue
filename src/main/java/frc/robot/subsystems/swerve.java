// src/main/java/frc/robot/subsystems/KrakenDriveSubsystem.java
package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class swerve extends SubsystemBase {
  // TalonFX inside the Kraken X60
  private final TalonFX driveMotor;

  // Control requests
  private final DutyCycleOut openLoopRequest = new DutyCycleOut(0.0);
  private final VelocityVoltage velocityRequest = new VelocityVoltage(0.0);

  // --- Constants: adjust for your swerve module ---
  // Example: 4 in (0.1016 m) wheel
  private static final double WHEEL_DIAMETER_METERS = 0.1016;
  private static final double WHEEL_CIRCUMFERENCE_METERS = Math.PI * WHEEL_DIAMETER_METERS;

  // Example gear ratio for SDS MK4i L2 (change to your module)
  private static final double DRIVE_GEAR_RATIO = 6.75; 
  // Motor sensor rotations per wheel rotation
  private static final double SENSOR_ROTATIONS_PER_WHEEL_ROTATION = DRIVE_GEAR_RATIO;

  public swerve(int canId, String canBus) {
    driveMotor = new TalonFX(canId, canBus); // canBus = "rio" or your CANivore name

    TalonFXConfiguration config = new TalonFXConfiguration();

    // Neutral mode
    config.MotorOutput.NeutralMode = NeutralModeValue.Brake;

    // Current limits (tune for your robot)
    config.CurrentLimits.SupplyCurrentLimitEnable = true;
    config.CurrentLimits.SupplyCurrentLimit = 40;    // amps
    config.CurrentLimits.SupplyCurrentLowerLimit = 60; // amps before limiting
    config.CurrentLimits.SupplyCurrentLowerTime = 0.1; // seconds

    // Apply config
    driveMotor.getConfigurator().apply(config);
  }

  /** Simple open-loop percent output [-1, 1] */
  public void setPercentOutput(double percent) {
    // clamp just in case
    percent = Math.max(-1.0, Math.min(1.0, percent));
    driveMotor.setControl(openLoopRequest.withOutput(percent));
  }

  /** Closed-loop velocity in meters per second */
  public void setVelocityMps(double metersPerSecond) {
    // Convert wheel linear speed to motor rotations per second
    double wheelRotPerSec = metersPerSecond / WHEEL_CIRCUMFERENCE_METERS;
    double motorRotPerSec = wheelRotPerSec * SENSOR_ROTATIONS_PER_WHEEL_ROTATION;

    // Phoenix 6 TalonFX velocity is in rotations/second
    velocityRequest.Velocity = motorRotPerSec;
    driveMotor.setControl(velocityRequest);
  }

  /** Stop the drive motor */
  public void stop() {
    driveMotor.setControl(openLoopRequest.withOutput(0.0));
  }
}
