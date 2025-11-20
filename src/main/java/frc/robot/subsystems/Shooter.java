package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.ShooterConstants;

public Shooter() {

  var talonFXConfigs = new TalonFXConfiguration();
//(just look up on websites for) 
  var slot0Configs = talonFXConfigs.Slot0; 
slot0Configs.kP = 4.8; // A position error of 2.5 rotations results in 12 V output
slot0Configs.kI = 0; // no output for integrated error
slot0Configs.kD = 0.1; // A velocity error of 1 rps results in 0.1 V output
m_talonFX.getConfigurator().apply(talonFXConfigs);
//addded
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

