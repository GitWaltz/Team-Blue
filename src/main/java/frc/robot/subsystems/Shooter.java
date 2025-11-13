package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.ShooterConstants;

public class Shooter extends SubsystemBase{
    private static TalonFX spinnyMotor = new TalonFX(ShooterConstants.spinnyMotorID, ShooterConstants.canbus);
  private static TalonFX tiltyMotor = new TalonFX(ShooterConstants.tiltyMotorID, ShooterConstants.canbus);
  private static double targetDist = 0;
  private static double tiltyTarget = 0;
  private static double spinnyTarget = 0;

  
}
