// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.Shooter;;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  private final Shooter shooter = new Shooter();
    private final PS4Controller controller = new PS4Controller(0);

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {
        // Bind Square button → move shooter to 100 rotations
        new JoystickButton(controller, PS4Controller.Button.kSquare.value)

          
            .onTrue(new InstantCommand(() -> {shooter.zero();shooter.moveToPosition(100);}, shooter));

        // (Optional) Circle button → stop shooter
        new JoystickButton(controller, PS4Controller.Button.kCircle.value)
            .onTrue(new InstantCommand(() -> shooter.stop(), shooter));
    }
}
