package frc.robot.subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.TimedRobot;

public class swerve extends TimedRobot{
    private final TalonFX Krakenee = new TalonFX(2,"canivore");
    private final PositionVoltage postCtrl = new PositionVoltage(0);
    @Override
    public void teleopPeriodic() {
        Krakenee.setControl(postCtrl.withPosition(0.5));
    }
}
