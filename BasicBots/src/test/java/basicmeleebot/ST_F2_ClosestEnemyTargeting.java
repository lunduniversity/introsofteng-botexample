/**	
Copyright (c) 2018 David Phung

Building on work by Philip Johnson and Keone Hiraide, University of Hawaii.
https://ics613s13.wordpress.com/

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

import static org.junit.Assert.assertTrue;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.RoundEndedEvent;
import robocode.control.events.RoundStartedEvent;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IBulletSnapshot;
import robocode.control.snapshot.IRobotSnapshot;
import robocode.control.snapshot.RobotState;
import robocode.control.testing.RobotTestBed;

/**
 * Test class for feature 2 - Closest enemy targeting of BasicMeleeBot.
 *
 * @author David Phung
 *
 */
@RunWith(JUnit4.class)
public class ST_F2_ClosestEnemyTargeting extends RobotTestBed {
	
	// constants used to configure this system test case
	// ETSA02 Lab 3: We recommend that you design a deterministic test case with two SittingDuck robots.
	private String ROBOT_UNDER_TEST = "se.lth.cs.etsa02.basicmeleebot.BasicMeleeBot*";
	private String ENEMY_ROBOTS = "sample.SpinBot,sample.SpinBot";
	private int NBR_ROUNDS = 1; //the battle will be deterministic and we will set initial positions so one round is enough.
	private boolean PRINT_DEBUG = false;
	private double correctHits = 0;
	private double totalHits;
	private double precisionReq = 0.5; //precision requirement
	private double correctHitRatio = 0;
	
	/**
	 * The names of the robots that want battling is specified.
	 * 
	 * @return The names of the robots we want battling.
	 */
	@Override
	public String getRobotNames() {
		return ROBOT_UNDER_TEST + "," + ENEMY_ROBOTS;
	}

	/**
	 * Pick the amount of rounds that we want our robots to battle for.
	 *
	 * @return Amount of rounds we want to battle for.
	 */
	@Override
	public int getNumRounds() {
		return NBR_ROUNDS;
	}

	/**
	 * Returns a comma or space separated list like: x1,y1,heading1,
	 * x2,y2,heading2, which are the coordinates and heading of robot #1 and #2.
	 * So "(0,0,180), (50,80,270)" means that robot #1 has position (0,0) and
	 * heading 180, and robot #2 has position (50,80) and heading 270.
	 * 
	 * Override this method to explicitly specify the initial positions for your
	 * test cases.
	 * 
	 * Defaults to null, which means that the initial positions are determined
	 * randomly. Since battles are deterministic by default, the initial
	 * positions are randomly chosen but will always be the same each time you
	 * run the test case.
	 * 
	 * @return The list of initial positions.
	 */
	@Override
	public String getInitialPositions() {
		return null;
	}

	/**
	 * Returns true if the battle should be deterministic and thus robots will
	 * always start in the same position each time.
	 * 
	 * Override to return false to support random initialization.
	 * 
	 * @return True if the battle will be deterministic.
	 */
	@Override
	public boolean isDeterministic() {
		return true;
	}

	/**
	 * Specifies how many errors you expect this battle to generate. Defaults to
	 * 0. Override this method to change the number of expected errors.
	 * 
	 * @return The expected number of errors.
	 */
	@Override
	protected int getExpectedErrors() {
		return 0;
	}

	/**
	 * Invoked before the test battle begins. Default behavior is to do nothing.
	 * Override this method in your test case to add behavior before the battle
	 * starts.
	 */
	@Override
	protected void runSetup() {
	}

	/**
	 * Invoked after the test battle ends. Default behavior is to do nothing.
	 * Override this method in your test case to add behavior after the battle
	 * ends.
	 */
	@Override
	protected void runTeardown() {
	}
	
	/**
	 * Called after the battle. Provided here to show that you could use this
	 * method as part of your testing.
	 * 
	 * @param event
	 *            Holds information about the battle has been completed.
	 */
	@Override
	public void onBattleCompleted(BattleCompletedEvent event) {
		assertTrue("BMB does not correctly hit the closest target according to the target level of " + precisionReq*100 + 
				"% but rather hit the closest enemy only " + correctHitRatio + "% of the time.", correctHitRatio > precisionReq*100);
	}
	
	/**
	 * Called before each round. Provided here to show that you could use this
	 * method as part of your testing.
	 * 
	 * @param event
	 *            The RoundStartedEvent.
	 */
	@Override
	public void onRoundStarted(RoundStartedEvent event) {
	}
	
	/**
	 * Called after each round. Provided here to show that you could use this
	 * method as part of your testing.
	 * 
	 * @param event
	 *            The RoundEndedEvent.
	 */
	@Override
	public void onRoundEnded(RoundEndedEvent event) {
		correctHitRatio = Math.round(((double) correctHits / (double) totalHits) * 100);
	}
	
	/**
	 * Called after each turn. Provided here to show that you could use this
	 * method as part of your testing.
	 * 
	 * @param event
	 *            The TurnEndedEvent.
	 */
	@Override
	public void onTurnEnded(TurnEndedEvent event) {
		IRobotSnapshot bmb = event.getTurnSnapshot().getRobots()[0];
		double xBMB = bmb.getX();
		double yBMB = bmb.getY();
		double minDist = Integer.MAX_VALUE;
		int minIndex = -1;
		
		//Loop to find closest enemy every turn
		for(int i = 1; i < event.getTurnSnapshot().getRobots().length; i++) {
			IRobotSnapshot s = event.getTurnSnapshot().getRobots()[i];
			double sx = s.getX();
			double sy = s.getY();
			double currentDist = Math.hypot(sx - xBMB, sy - yBMB);
			
			if(currentDist < minDist) { //if we find enemy that is closer than current minDist, update index and distance
				minIndex = i;
				minDist = currentDist;
			}
		}

		IBulletSnapshot[] bulletList = event.getTurnSnapshot().getBullets();
		
		//Check if any bullet hit the closest enemy
		for(IBulletSnapshot bullet : bulletList) {
			if (bullet.getOwnerIndex() == 0) {//check if our bullet
				if (bullet.getState().getValue() == 2) { //state 2, check if bullet hit enemy
					
					if(PRINT_DEBUG) {
					System.out.println("VictimIndex: " + bullet.getVictimIndex() + " ClosestEnemyIndex: " + minIndex); //print index of the enemy hit and index closest enemy
					}
					
					assertTrue("Bullet power is not 1, but instead " + bullet.getPower() + ".", bullet.getPower() == 1);
					
					if(bullet.getVictimIndex() == minIndex) {
						correctHits++;
					}
					
					totalHits++;
				}
			}	
		}
	}
	
}