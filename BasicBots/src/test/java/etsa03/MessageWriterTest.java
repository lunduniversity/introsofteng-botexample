/**	
Copyright (c) 2020 Teodor Ahlinder
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

package etsa03;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.geom.Point2D;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MessageWriterTest {

	private MessageWriter writer;
	
	@Before
	public void setUp() {
		writer = new MessageWriter();
	}
	
	@After
	public void tearDown() {
		writer = null;
	}
	
	@Test
	public void testLeadership() {
		String s = "offensive";
		String message = "leadership;" + s;
		writer.addLeadership(s);
		String check = writer.composeMessage();
		assertTrue("Check that sent string is identical to received string", message.compareTo(check) == 0); 
	}
	
	@Test
	public void testTeamMode() {
		String s = "offensive";
		String message = "teamMode;" + s;
		writer.addTeamMode(s);
		String check = writer.composeMessage();
		assertTrue("Check that sent string is identical to received string", message.compareTo(check) == 0); 
	}
	
	@Test
	public void testMyPos() {
		double x = 25.2;
		double y = 0.0;
		String message = "myPos;" + x + ";" + y;
		writer.addMyPos(x, y);
		String check = writer.composeMessage();
		assertTrue("Check that sent string is identical to received string", message.compareTo(check) == 0); 
	}
	
	@Test
	public void testOneFriendPos() {
		String name = "fakeFriend";
		double x = 25.2;
		double y = 0.0;
		String message = "friendPos;" + name + ";" + x + ";" + y;
		writer.addFriendPos(name, x, y);
		String check = writer.composeMessage();
		assertTrue("Check that sent string is identical to received string", message.compareTo(check) == 0); 
	}
	
	@Test
	public void testTwoFriendPos() {
		String name1 = "fakeFriend1";
		double x1 = 25.2;
		double y1 = 0.0;
		String name2 = "fakeFriend2";
		double x2 = 0.0;
		double y2 = 41.6;
		String message = "friendPos;" + name1 + ";" + x1 + ";" + y1 + "\n" + "friendPos;" + name2 + ";" + x2 + ";" + y2;
		writer.addFriendPos(name1, x1, y1);
		writer.addFriendPos(name2, x2, y2);
		String check = writer.composeMessage();
		assertTrue("Check that sent string is identical to received string", message.compareTo(check) == 0); 
	}
	
	@Test
	public void testOneEnemyPos() {
		String name = "fakeEnemy";
		double x = 25.2;
		double y = 0.0;
		String message = "enemyPos;" + name + ";" + x + ";" + y;
		writer.addEnemyPos(name, x, y);
		String check = writer.composeMessage();
		assertTrue("Check that sent string is identical to received string", message.compareTo(check) == 0); 
	}
	
	@Test
	public void testTwoEnemyPos() {
		String name1 = "fakeEnemy1";
		double x1 = 25.2;
		double y1 = 0.0;
		String name2 = "fakeEnemy2";
		double x2 = 0.0;
		double y2 = 41.6;
		String message = "enemyPos;" + name1 + ";" + x1 + ";" + y1 + "\n" + "enemyPos;" + name2 + ";" + x2 + ";" + y2;
		writer.addEnemyPos(name1, x1, y1);
		writer.addEnemyPos(name2, x2, y2);
		String check = writer.composeMessage();
		assertTrue("Check that sent string is identical to received string", message.compareTo(check) == 0); 
	}
	
	@Test
	public void testOneEnemyDetails() {
		String name = "fakeEnemy";
		double x = 25.2;
		double y = 0.0;
		double velocity = 30.0;
		double energy = 120.0;
		double heading = 12.3;
		double gunHeading = -1;
		String message = "enemyDetails;" + name + ";" + x + ";" + y  + ";" + velocity + ";" + energy + ";" + heading + ";" + gunHeading;
		writer.addEnemyDetails(name, x, y, velocity, energy, heading, gunHeading);
		String check = writer.composeMessage();
		assertTrue("Check that sent string is identical to received string", message.compareTo(check) == 0); 
	}
	
	@Test
	public void testTwoEnemyDetails() {
		String name1 = "fakeEnemy1";
		double x1 = 25.2;
		double y1 = 0.0;
		double velocity1 = 30.0;
		double energy1 = 120.0;
		double heading1 = 12.3;
		double gunHeading1 = -1;
		String name2 = "fakeEnemy2";
		double x2 = 0.0;
		double y2 = 41.6;
		double velocity2 = -16.0;
		double energy2 = 23.0;
		double heading2 = 156.2;
		double gunHeading2 = -1;
		String message = "enemyDetails;" + name1 + ";" + x1 + ";" + y1  + ";" + velocity1 + ";" + energy1 + ";" + heading1 + ";" + gunHeading1 + "\n" + "enemyDetails;" + name2 + ";" + x2 + ";" + y2  + ";" + velocity2 + ";" + energy2 + ";" + heading2 + ";" + gunHeading2;
		writer.addEnemyDetails(name1, x1, y1, velocity1, energy1, heading1, gunHeading1);
		writer.addEnemyDetails(name2, x2, y2, velocity2, energy2, heading2, gunHeading2);
		String check = writer.composeMessage();
		assertTrue("Check that sent string is identical to received string", message.compareTo(check) == 0); 
	}
	
	@Test
	public void testTargetEnemy() {
		String name = "fakeEnemy";
		String message = "targetEnemy;" + name;
		writer.addTargetEnemy(name);
		String check = writer.composeMessage();
		assertTrue("Check that sent string is identical to received string", message.compareTo(check) == 0); 
	}
	
	@Test
	public void testOneBulletDetails() {
		double x = 25.2;
		double y = 0.0;
		double absBearing = 90.0;
		double bulletPower = 0.8;
		String message = "bulletDetails;" + x + ";" + y + ";" + absBearing + ";" + bulletPower;
		writer.addBulletDetails(x, y, absBearing, bulletPower);
		String check = writer.composeMessage();
		assertTrue("Check that sent string is identical to received string", message.compareTo(check) == 0); 
	}
	
	@Test
	public void testTwoBulletDetails() {
		double x1 = 25.2;
		double y1 = 0.0;
		double absBearing1 = 90.0;
		double bulletPower1 = 0.8;
		double x2 = 14.0;
		double y2 = 80.5;
		double absBearing2 = 12.0;
		double bulletPower2 = 0.2;
		String message = "bulletDetails;" + x1 + ";" + y1 + ";" + absBearing1 + ";" + bulletPower1 + "\n" + "bulletDetails;" + x2 + ";" + y2 + ";" + absBearing2 + ";" + bulletPower2;
		writer.addBulletDetails(x1, y1, absBearing1, bulletPower1);
		writer.addBulletDetails(x2, y2, absBearing2, bulletPower2);
		String check = writer.composeMessage();
		assertTrue("Check that sent string is identical to received string", message.compareTo(check) == 0); 
	}
	
	@Test
	public void testTargetPos() {
		double x = 25.2;
		double y = 0.0;
		String message = "targetPos;" + x + ";" + y;
		writer.addTargetPos(x, y);
		String check = writer.composeMessage();
		assertTrue("Check that sent string is identical to received string", message.compareTo(check) == 0); 
	}
	
	@Test
	public void testMoveTo() {
		double x = 25.2;
		double y = 0.0;
		String message = "moveTo;" + x + ";" + y;
		writer.addMoveTo(x, y);
		String check = writer.composeMessage();
		assertTrue("Check that sent string is identical to received string", message.compareTo(check) == 0); 
	}
}