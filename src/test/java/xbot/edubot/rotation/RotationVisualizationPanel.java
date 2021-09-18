package xbot.edubot.rotation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import xbot.common.math.XYPair;
import xbot.edubot.rotation.BaseOrientationEngineTest.RotationEnvironmentState;

public class RotationVisualizationPanel extends JPanel {
    private static final long serialVersionUID = 2785468671430694763L;
    private int centerX;
    private int centerY;
    private XYPair center;
    private double baseMagnitude;

    private int preferredWidth = 200;
    private int minimumWidth = 200;

    public RotationVisualizationPanel() {

    }

    public RotationVisualizationPanel(int preferredWidth, int minimumWidth) {
        this.preferredWidth = preferredWidth;
        this.minimumWidth = minimumWidth;
    }

    RotationEnvironmentState envState = new RotationEnvironmentState();

    public void updateState(RotationEnvironmentState envState) {
        this.envState = envState;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;

        centerX = this.getWidth() / 2;
        centerY = this.getHeight() / 2;
        center = new XYPair(centerX, centerY);
        baseMagnitude = Math.min(centerX, centerY) * 0.8;

        graphics.setStroke(new BasicStroke(5));
        graphics.setColor(Color.MAGENTA);
        drawVector(graphics, XYPair.fromPolar(-envState.targetOrientation, baseMagnitude));

        graphics.setColor(getColorForEnvState());
        XYPair currentOrientationVector = XYPair.fromPolar(-envState.currentOrientation, baseMagnitude);
        drawVector(graphics, currentOrientationVector);

        graphics.setColor(Color.CYAN);
        drawVector(graphics, XYPair.fromPolar(-envState.currentOrientation - 90,
                envState.currentRotationalPower * 0.5 * baseMagnitude), currentOrientationVector);

        graphics.setColor(Color.GRAY);
        drawVector(graphics,
                XYPair.fromPolar(-envState.currentOrientation - 90, envState.currentVelocity / 30 * baseMagnitude),
                currentOrientationVector.scale(0.5));

        graphics.setColor(Color.BLACK);
        graphics.setStroke(new BasicStroke(5));
        graphics.drawString("Heading:" + String.format("%.1f", envState.currentOrientation) + "°", centerX+25, centerY+50);
        graphics.drawString("Speed:" + String.format("%.1f", envState.currentVelocity) + "°/t", centerX+25, centerY+50+g.getFontMetrics().getHeight());
    }

    private Color getColorForEnvState() {
        if(envState.isAtOrientationTarget && envState.isAtVelocityTarget && envState.isCommandFinished) {
            // At goal and command finished
            return Color.GREEN;
        }
        else if (envState.isAtOrientationTarget && envState.isAtVelocityTarget && !envState.isCommandFinished) {
            // At goal but command hasn't finished
            return Color.BLUE;
        }
        else if ((!envState.isAtOrientationTarget || !envState.isAtVelocityTarget) && envState.isCommandFinished) {
            // Not at goal but command reported completion
            // TODO: Warn that this will cause everything to fail
            return Color.RED;
        }
        else if (envState.isAtOrientationTarget || envState.isAtVelocityTarget) {
            // Partially meeting goal
            return Color.YELLOW;
        }
        else {
            // Not meeting goal and not finished
            return Color.BLACK;
        }
    }
    
    protected void drawVector(Graphics2D graphics, XYPair vector) {
        drawVector(graphics, vector, XYPair.ZERO);
    }

    protected void drawVector(Graphics2D graphics, XYPair vector, XYPair originPosition) {
        XYPair centeredOrigin = originPosition.clone().add(center);
        XYPair positionedVector = vector.clone().add(centeredOrigin);
        graphics.drawLine((int) centeredOrigin.x, (int) centeredOrigin.y, (int) positionedVector.x,
                (int) positionedVector.y);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(preferredWidth, 0);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(minimumWidth, 0);
    }
}