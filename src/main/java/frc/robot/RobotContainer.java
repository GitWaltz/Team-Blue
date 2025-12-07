package frc.robot;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import frc.robot.subsystems.Shooter;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class RobotContainer {

    private final Shooter shooter = new Shooter();
    private final CommandPS4Controller controller = new CommandPS4Controller(0);

    // Swerve drivetrain
    private final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {

        // SWERVE DEFAULT COMMAND
        drivetrain.setDefaultCommand(
            drivetrain.applyRequest(() ->
                drivetrain.drive(
                    -controller.getLeftY(),
                    -controller.getLeftX(),
                    -controller.getRightX(),
                    true,
                    true
                )
            )
        );

        // SHOOTER BINDING
        controller.R2().onTrue(new InstantCommand(() -> shooter.Shoot(), shooter));
    }
}
