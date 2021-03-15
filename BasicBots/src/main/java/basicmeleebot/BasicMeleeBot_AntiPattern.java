/**	
Copyright (c) 2018-2019 David Phung

Building on work by Mathew A. Nelson and Robocode contributors.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package basicmeleebot;

import robocode.TeamRobot;
import robocode.MessageEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;
import etsa03.RobotColors;

import java.awt.geom.Point2D;

/**
 * @author David Phung
 * 
 * Example robot for ETSA03. A melee bot implemented following the Blob anti-pattern.
 * https://sourcemaking.com/antipatterns/the-blob
 */
public class BasicMeleeBot_AntiPattern extends TeamRobot {
	
	// These three variables are used to track enemies. An enemy will have its name and position stored
	// at the same index in the two arrays enemyNames and enemyPositions. The number of enemies is stored in enemyCount.
	private Point2D.Double[] enemyPositions;
	private String[] enemyNames;
	private int enemyCount;

	/**
	 * The main loop controlling the robot behavior.
	 */
	@Override
	public void run() {
		// Initiate the arrays. They should have the same length.
		enemyNames = new String[10];
		enemyPositions = new Point2D.Double[enemyNames.length];
		
		// Usually when the robot turns, the gun and radar turn along with it. 
		// This makes computations more difficult so we make them turn independently from each other.
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		
		// This loop is run once every turn (Robocode's turn).
		while (true) {
			// We don't need any advanced behavior from the radar so we just let it spin around all the time.
			setTurnRadarRight(Double.POSITIVE_INFINITY);
			
			// This variable is used by both movement and targeting code so make sure to copy it when refactoring in Lab 2.
			Point2D.Double robotPosition = new Point2D.Double(getX(), getY());
			
			// ####### MOVEMENT ####### 
			// Each enemy is assigned a "gravity" force that pushes our robot away. 
			// The sum of all these forces determines the direction of our robot.
			// Don't worry too much about the details of the computation steps.

			// The sum of all forces is represented as a vector whose x and y components are xForce and yForce.
			// For the refactoring task in Lab 2, note that these two variables shall be replaced by
			// two corresponding attributes in the class MovementSystem.
			double xForce = 0;
			double yForce = 0;
			// Loop through all enemies and compute their forces.
			for (int i = 0; i < enemyCount; i++) {
				Point2D.Double enemyPosition = enemyPositions[i];
				double absBearing=Utils.normalAbsoluteAngle(calcAngle(robotPosition, enemyPosition));
				double distance = enemyPosition.distance(robotPosition);
				xForce -= Math.sin(absBearing) / (distance * distance);
				yForce -= Math.cos(absBearing) / (distance * distance);
			}
			
			// Set the movement in a smart way so that the robot would never turn more than 90 degrees.
			double angle = Math.atan2(xForce, yForce);
			if (xForce == 0 && yForce == 0) {
				// If no force, do nothing.
			} else if (Math.abs(Utils.normalRelativeAngle(angle - Utils.normalRelativeAngle(getHeadingRadians()))) < Math.PI / 2) {
				setTurnRightRadians(Utils.normalRelativeAngle(angle - Utils.normalRelativeAngle(getHeadingRadians())));
				setAhead(Double.POSITIVE_INFINITY);
			} else {
				setTurnRightRadians(Utils.normalRelativeAngle(angle + Math.PI - Utils.normalRelativeAngle(getHeadingRadians())));
				setAhead(Double.NEGATIVE_INFINITY);
			}
			
			// ####### AIMING AND SHOOTING #######
			// Find the closest enemy and store its position.
			double smallestDistanceSq = Double.POSITIVE_INFINITY;
			Point2D.Double pointToTurnTo = null;
			for (int i = 0; i < enemyCount; i++) {
				Point2D.Double enemyPosition = enemyPositions[i];
				double d = enemyPosition.distanceSq(robotPosition);
				if (d < smallestDistanceSq) {
					smallestDistanceSq = d;
					pointToTurnTo = enemyPosition;
				}
			}
			// Compute how much our robot's gun has to turn.
			double angleToTurn = 0;
			if (pointToTurnTo != null) {
				double dx = pointToTurnTo.x - robotPosition.x;
				double dy = pointToTurnTo.y - robotPosition.y;
				angleToTurn = Math.toDegrees(Math.atan2(dx, dy)) - getGunHeading();
			}

			// Fire when the gun has finished turning.
			if (getGunTurnRemaining() == 0) {
				setFire(1);
			}
			// Turn the gun.
			setTurnGunRight(Utils.normalRelativeAngleDegrees(angleToTurn));
			
			// Execute the commands that we have set. Don't move this when refactoring in Lab 2.
			execute();
		}
	}

	/**
	 * Describes the action taken when a robot has been scanned.
	 * 
	 * @param event The ScannedRobotEvent provided by Robocode.
	 */
	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		// Compute the position of a scanned robot.
		double absBearing = e.getBearing() + getHeading();
		double x = getX() + e.getDistance() * Math.sin(Math.toRadians(absBearing));
		double y = getY() + e.getDistance() * Math.cos(Math.toRadians(absBearing));
		Point2D.Double enemyPosition = new Point2D.Double(x, y);
		
		// If the scanned robot is already stored in the arrays, update its position. Otherwise add it as a new element.
		int index = findEnemyByName(e.getName());
		if (index >= 0) {
			enemyPositions[index] = enemyPosition;
		} else {
			enemyNames[enemyCount] = e.getName();
			enemyPositions[enemyCount] = enemyPosition;
			enemyCount++;
		}
	}
	
	/**
	 * Describes the action taken when a robot has been destroyed.
	 * 
	 * @param event The RobotDeathEvent provided by Robocode.
	 */
	@Override
	public void onRobotDeath(RobotDeathEvent e) {
		// Remove the destroyed robot from the arrays.
		int index = findEnemyByName(e.getName());
		if (index >= 0) {
			for (int i = index + 1; i < enemyCount; i++) {
				enemyNames[i - 1] = enemyNames[i];
				enemyPositions[i-1] = enemyPositions[i];
			}
			enemyCount--;
		}
	}
	
	/**
	 * onMessageReceived: What to do when our leader sends a message
	 */
	@Override
	public void onMessageReceived(MessageEvent e) {
		// Set our colors
		if (e.getMessage() instanceof RobotColors) {
			RobotColors c = (RobotColors) e.getMessage();
			setBodyColor(c.bodyColor);
			setGunColor(c.gunColor);
			setRadarColor(c.radarColor);
			setScanColor(c.scanColor);
			setBulletColor(c.bulletColor);
		}
	}
	
	/**
	 * Find the index of an enemy robot given its name. The index can then be used to access the robot's position in the position array.
	 * 
	 * @param name The name of the robot.
	 * @return The index of the robot in the internal array, -1 if not found.
	 */
	private int findEnemyByName(String name) {
		for (int i = 0; i < enemyCount; i++) {
			if (enemyNames[i].equals(name)) return i;
		}
		return -1;
	}
	
	/**
	 * Calculate the angle between two points in two dimensions.
	 * 
	 * @param p1 The first point.
	 * @param p2 The second point.
	 * @return The angle between the two points.
	 */
	private static double calcAngle(Point2D.Double p1, Point2D.Double p2) {
		double dx = p2.x - p1.x;
		double dy = p2.y - p1.y;
		return Math.atan2(dx, dy);
	}
}