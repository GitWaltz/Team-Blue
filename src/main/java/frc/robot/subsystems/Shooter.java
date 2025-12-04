package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.ShooterConstants;

public class Shooter extends SubsystemBase {
  private static final int Shooter_Top_ID = 15;
  private static final int Shooter_Bottom_ID = 16;
  private static final String CAN_BUS = "canivore"; // or "rio"
    private final VelocityVoltage velocityRequest = new VelocityVoltage(0);
// set target position to 100 rotations
  private final TalonFX m_talonFX_Top = new TalonFX(Shooter_Top_ID, CAN_BUS); // central shooter motor
  private final TalonFX m_talonFX2_Bottom = new TalonFX(Shooter_Bottom_ID, CAN_BUS); // adjusted for ratio 


  // Variables (ALL IN METERS)
  private static final double g = 9.81; // gravity 
  private static final double x = 10; // is input from camera, set for testing now 
  private static final double a = 1.09; // angle of shooter (62.5 in radians)
  private static final double h = 2; // height difference between shooter and target, 


  public Shooter() {

    var talonFXConfigs = new TalonFXConfiguration();
    // (just look up on websites for) 
    var slot0Configs = talonFXConfigs.Slot0; 
    slot0Configs.kP = 4.8; // A position error of 2.5 rotations results in 12 V output
    slot0Configs.kI = 0; // no output for integrated error
    slot0Configs.kD = 0.1; // A velocity error of 1 rps results in 0.1 V output
    m_talonFX_Top.getConfigurator().apply(talonFXConfigs);
    // added
  }

  public static double CalcVelocity(double x, double h, double A){
    double cosA = Math.cos(A);
    double tanA = Math.tan(A);

    if (x * tanA - h <= 0){
      return Double.NaN; // invalid [Not a Number]
    }

    double numerator = g * x * x;
    double denominator = 2 * cosA * cosA * (x * tanA - h);
    return Math.sqrt(numerator / denominator);
  }
  public static double MotorVolocity(double x, double h, double a){
    double exitVelocity = Shooter.CalcVelocity(x, h, a);
    exitVelocity *= 386.08858; // convert m/s to in/s
    double wheelDiameter = 3; // in inches
    return exitVelocity / (Math.PI * wheelDiameter) * 2.5 * 60; // convert in/s to RPM
  }


  



  public void Shoot(){
    double RPM = MotorVolocity(x, h, a);
    m_talonFX_Top.setControl(velocityRequest.withVelocity(RPM));
    m_talonFX2_Bottom.setControl(velocityRequest.withVelocity(RPM* (6/5))); // adjusted for ratio
  }

  

  public void stop() {
    m_talonFX_Top.stopMotor();
    m_talonFX2_Bottom.stopMotor();
  }

  // restart position, if motor is already at 100 rot, then it won't rotate to 100 again, so we need to reset position to 0
  public void zero() {
    m_talonFX_Top.setPosition(0);
    m_talonFX2_Bottom.setPosition(0);
  }
}

