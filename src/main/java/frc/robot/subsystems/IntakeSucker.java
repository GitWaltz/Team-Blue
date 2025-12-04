package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.ShooterConstants;

public class IntakeSucker extends SubsystemBase{
  
  private static final int INTAKE_SUCKER_ID = 13;
  private static final String CAN_BUS = "canivore"; // or "rio"
    public final MotionMagicVoltage m_request = new MotionMagicVoltage(0);
// set target position to 100 rotations

  private final TalonFX m_talonFX = new TalonFX(INTAKE_SUCKER_ID, CAN_BUS); 
public IntakeSucker() {

  var talonFXConfigs = new TalonFXConfiguration();
//(just look up on websites for) 
  var slot0Configs = talonFXConfigs.Slot0; 
slot0Configs.kS = 0.25; // Add 0.25 V output to overcome static friction
slot0Configs.kV = 0.12; // A velocity target of 1 rps results in 0.12 V output
slot0Configs.kA = 0.01; // An acceleration of 1 rps/s requires 0.01 V output
slot0Configs.kP = 4.8; // A position error of 2.5 rotations results in 12 V output
slot0Configs.kI = 0; // no output for integrated error
slot0Configs.kD = 0.1; // A velocity error of 1 rps results in 0.1 V output


  // set Motion Magic settings
var motionMagicConfigs = talonFXConfigs.MotionMagic;
motionMagicConfigs.MotionMagicCruiseVelocity = 80; // Target cruise velocity of 80 rps
motionMagicConfigs.MotionMagicAcceleration = 160; // Target acceleration of 160 rps/s (0.5 seconds)
motionMagicConfigs.MotionMagicJerk = 1600; // Target jerk of 1600 rps/s/s (0.1 seconds)

m_talonFX.getConfigurator().apply(talonFXConfigs);




//final MotionMagicVoltage m_request = new MotionMagicVoltage(0);

}
public void moveToPosition(double rotations) {
  
  m_talonFX.setControl(m_request.withPosition(rotations));
}
public void stop(){
  m_talonFX.stopMotor();
}
// restart position, if motor is already at 100 rot, then it won't rotate to 100 agian, so we need to reset position to 0
public void zero(){
  m_talonFX.setPosition(0);
}
}
