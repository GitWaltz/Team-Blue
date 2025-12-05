package frc.robot.subsystems;


import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;


import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;


import com.ctre.phoenix6.controls.PositionVoltage;


public class ColorSorter extends SubsystemBase{


private final String wantedColor = "Purple";


  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
  private final ColorMatch m_colorMatcher = new ColorMatch();


  private final TalonFX m_sorterMotor = new TalonFX(7, "canivore");


  public final PositionVoltage m_request = new PositionVoltage(0);


  /*
  private final Color kBlueTarget = new Color(0.143, 0.427, 0.429);
  private final Color kGreenTarget = new Color(0.197, 0.561, 0.240);
  private final Color kRedTarget = new Color(0.561, 0.232, 0.114);
  private final Color kYellowTarget = new Color(0.361, 0.524, 0.113);
  */
  private final Color kPurpleTarget = new Color(0,0,0);
  private final Color kGreenTarget = new Color(0,0,0);
public ColorSorter(){


  var talonFXConfigs = new TalonFXConfiguration();
//(just look up on websites for)
  var slot0Configs = talonFXConfigs.Slot0;
  slot0Configs.kP = 3; // A position error of 2.5 rotations results in 12 V output
  slot0Configs.kI = 0; // no output for integrated error
  slot0Configs.kD = 0; // A velocity error of 1 rps results in 0.1 V output
  m_sorterMotor.getConfigurator().apply(talonFXConfigs);
  m_colorMatcher.addColorMatch(kPurpleTarget);
  m_colorMatcher.addColorMatch(kGreenTarget);




}


public void sortColor(double rotations){
  m_sorterMotor.setControl(m_request.withVelocity(rotations));
}
public void stopMotor(){
  m_sorterMotor.stopMotor();
}
public void zero(){
  m_sorterMotor.setPosition(0);
}




/* @Override
  public void robotInit() {
    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);    
  }*/


  @Override
  public void periodic() {
    Color detectedColor = m_colorSensor.getColor();
    String colorString;
    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);
/*
    if (match.color == kBlueTarget) {
      colorString = "Blue";
    } else if (match.color == kRedTarget) {
      colorString = "Red";
    } else if (match.color == kGreenTarget) {
      colorString = "Green";
    } else if (match.color == kYellowTarget) {
      colorString = "Yellow";
    } else {
      colorString = "Unknown";
    }
*/
if(match.color == kPurpleTarget){
    colorString = "Purple";
}
else if (match.color == kGreenTarget){
    colorString = "Green";
}
else{
    colorString = "Unknown";
}


    /**
     * Open Smart Dashboard or Shuffleboard to see the color detected by the
     * sensor.
     */
   
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("Confidence", match.confidence);
    SmartDashboard.putString("Detected Color", colorString);
   


    if (match.confidence > 0.8){
        if (colorString == wantedColor){
            sortColor(0.5);
        }
        else {
            sortColor(-0.5);
        }
    }
  }
}
