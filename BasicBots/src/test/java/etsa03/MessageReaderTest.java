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

public class MessageReaderTest {

	private MessageReader reader;
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
		reader = null;
	}
	
	@Test
	public void testLeadership() {
		String s = "offensive";
		String message = "leadership;" + s;
		reader = new MessageReader(message);
		String check = reader.getLeadership();
		assertTrue("Check that sent string is identical to received string", s.compareTo(check) == 0); 
	}
	
	@Test
	public void testTeamMode() {
		String s = "offensive";
		String message = "teamMode;" + s;
		reader = new MessageReader(message);
		String check = reader.getTeamMode();
		assertTrue("Check that sent string is identical to received string", s.compareTo(check) == 0); 
	}
	
	@Test
	public void testMyPos() {
		double x = 25.2;
		double y = 0.0;
		String message = "myPos;" + x + ";" + y;
		reader = new MessageReader(message);
		Point2D.Double check = reader.getMyPos();
		assertTrue("Check that sent x is identical to received x", x == check.getX()); 
		assertTrue("Check that sent y is identical to received y", y == check.getY()); 
	}
	
	@Test
	public void testOneFriendPos() {
		String name = "fakeFriend";
		double x = 25.2;
		double y = 0.0;
		String message = "friendPos;" + name + ";" + x + ";" + y;
		reader = new MessageReader(message);
		String[] data = reader.getFriendPos();
		String[] check = data[0].split(";");
		assertTrue("Check that sent name is identical to received name", name.compareTo(check[0]) == 0); 
		assertTrue("Check that sent x is identical to received x", x == Double.parseDouble(check[1])); 
		assertTrue("Check that sent y is identical to received y", y == Double.parseDouble(check[2])); 
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
		reader = new MessageReader(message);
		String[] data = reader.getFriendPos();
		String[] check1 = data[0].split(";");
		String[] check2 = data[1].split(";");
		assertTrue("Check that the first sent name is identical to received name", name1.compareTo(check1[0]) == 0); 
		assertTrue("Check that the first sent x is identical to received x", x1 == Double.parseDouble(check1[1])); 
		assertTrue("Check that the first sent y is identical to received y", y1 == Double.parseDouble(check1[2])); 
		assertTrue("Check that the second sent name is identical to received name", name2.compareTo(check2[0]) == 0); 
		assertTrue("Check that the second sent x is identical to received x", x2 == Double.parseDouble(check2[1])); 
		assertTrue("Check that the second sent y is identical to received y", y2 == Double.parseDouble(check2[2])); 
	}
	
	@Test
	public void testOneEnemyPos() {
		String name = "fakeEnemy";
		double x = 25.2;
		double y = 0.0;
		String message = "enemyPos;" + name + ";" + x + ";" + y;
		reader = new MessageReader(message);
		String[] data = reader.getEnemyPos();
		String[] check = data[0].split(";");
		assertTrue("Check that sent name is identical to received name", name.compareTo(check[0]) == 0); 
		assertTrue("Check that sent x is identical to received x", x == Double.parseDouble(check[1])); 
		assertTrue("Check that sent y is identical to received y", y == Double.parseDouble(check[2])); 
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
		reader = new MessageReader(message);
		String[] data = reader.getEnemyPos();
		String[] check1 = data[0].split(";");
		String[] check2 = data[1].split(";");
		assertTrue("Check that the first sent name is identical to received name", name1.compareTo(check1[0]) == 0); 
		assertTrue("Check that the first sent x is identical to received x", x1 == Double.parseDouble(check1[1])); 
		assertTrue("Check that the first sent y is identical to received y", y1 == Double.parseDouble(check1[2])); 
		assertTrue("Check that the second sent name is identical to received name", name2.compareTo(check2[0]) == 0); 
		assertTrue("Check that the second sent x is identical to received x", x2 == Double.parseDouble(check2[1])); 
		assertTrue("Check that the second sent y is identical to received y", y2 == Double.parseDouble(check2[2])); 
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
		reader = new MessageReader(message);
		String[] data = reader.getEnemyDetails();
		String[] check = data[0].split(";");
		assertTrue("Check that sent name is identical to received name", name.compareTo(check[0]) == 0); 
		assertTrue("Check that sent x is identical to received x", x == Double.parseDouble(check[1])); 
		assertTrue("Check that sent y is identical to received y", y == Double.parseDouble(check[2])); 
		assertTrue("Check that sent velocity is identical to received velocity", velocity == Double.parseDouble(check[3])); 
		assertTrue("Check that sent energy is identical to received energy", energy == Double.parseDouble(check[4])); 
		assertTrue("Check that sent heading is identical to received heading", heading == Double.parseDouble(check[5])); 
		assertTrue("Check that sent gunHeading is identical to received gunHeading", gunHeading == Double.parseDouble(check[6])); 
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
		String message = "enemyDetails;" + name1 + ";" + x1 + ";" + y1 + ";" + velocity1 + ";" + energy1 + ";" + heading1 + ";" + gunHeading1 + "\n" + "enemyDetails;" + name2 + ";" + x2 + ";" + y2 + ";" + velocity2 + ";" + energy2 + ";" + heading2 + ";" + gunHeading2;
		reader = new MessageReader(message);
		String[] data = reader.getEnemyDetails();
		String[] check1 = data[0].split(";");
		String[] check2 = data[1].split(";");
		assertTrue("Check that sent name is identical to received name", name1.compareTo(check1[0]) == 0); 
		assertTrue("Check that sent x is identical to received x", x1 == Double.parseDouble(check1[1])); 
		assertTrue("Check that sent y is identical to received y", y1 == Double.parseDouble(check1[2])); 
		assertTrue("Check that sent velocity is identical to received velocity", velocity1 == Double.parseDouble(check1[3])); 
		assertTrue("Check that sent energy is identical to received energy", energy1 == Double.parseDouble(check1[4])); 
		assertTrue("Check that sent heading is identical to received heading", heading1 == Double.parseDouble(check1[5])); 
		assertTrue("Check that sent gunHeading is identical to received gunHeading", gunHeading1 == Double.parseDouble(check1[6]));
		assertTrue("Check that sent name is identical to received name", name2.compareTo(check2[0]) == 0); 
		assertTrue("Check that sent x is identical to received x", x2 == Double.parseDouble(check2[1])); 
		assertTrue("Check that sent y is identical to received y", y2 == Double.parseDouble(check2[2])); 
		assertTrue("Check that sent velocity is identical to received velocity", velocity2 == Double.parseDouble(check2[3])); 
		assertTrue("Check that sent energy is identical to received energy", energy2 == Double.parseDouble(check2[4])); 
		assertTrue("Check that sent heading is identical to received heading", heading2 == Double.parseDouble(check2[5])); 
		assertTrue("Check that sent gunHeading is identical to received gunHeading", gunHeading2 == Double.parseDouble(check2[6]));
	}
	
	@Test
	public void testTargetEnemy() {
		String name = "fakeEnemy";
		String message = "targetEnemy;" + name;
		reader = new MessageReader(message);
		String check = reader.getTargetEnemy();
		assertTrue("Check that sent string is identical to received string", name.compareTo(check) == 0); 
	}
	
	@Test
	public void testOneBulletDetails() {
		double x = 25.2;
		double y = 0.0;
		double absBearing = 90.0;
		double bulletPower = 0.8;
		String message = "bulletDetails;" + x + ";" + y + ";" + absBearing + ";" + bulletPower;
		reader = new MessageReader(message);
		String[] data = reader.getBulletDetails();
		String[] check = data[0].split(";");
		assertTrue("Check that sent x is identical to received x", x == Double.parseDouble(check[0])); 
		assertTrue("Check that sent y is identical to received y", y == Double.parseDouble(check[1])); 
		assertTrue("Check that sent absBearing is identical to received absBearing", absBearing == Double.parseDouble(check[2])); 
		assertTrue("Check that sent bulletPower is identical to received bulletPower", bulletPower == Double.parseDouble(check[3]));
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
		reader = new MessageReader(message);
		String[] data = reader.getEnemyDetails();
		String[] check1 = data[0].split(";");
		String[] check2 = data[1].split(";");
		assertTrue("Check that sent x is identical to received x", x1 == Double.parseDouble(check1[0])); 
		assertTrue("Check that sent y is identical to received y", y1 == Double.parseDouble(check1[1])); 
		assertTrue("Check that sent velocity is identical to received velocity", absBearing1 == Double.parseDouble(check1[2])); 
		assertTrue("Check that sent energy is identical to received energy", bulletPower1 == Double.parseDouble(check1[3]));
		assertTrue("Check that sent x is identical to received x", x2 == Double.parseDouble(check2[0])); 
		assertTrue("Check that sent y is identical to received y", y2 == Double.parseDouble(check2[1])); 
		assertTrue("Check that sent velocity is identical to received velocity", absBearing2 == Double.parseDouble(check2[2])); 
		assertTrue("Check that sent energy is identical to received energy", bulletPower2 == Double.parseDouble(check2[3]));
	}
	
	@Test
	public void testTargetPos() {
		double x = 25.2;
		double y = 0.0;
		String message = "targetPos;" + x + ";" + y;
		reader = new MessageReader(message);
		Point2D.Double check = reader.getTargetPos();
		assertTrue("Check that sent x is identical to received x", x == check.getX()); 
		assertTrue("Check that sent y is identical to received y", y == check.getY());
	}
	
	@Test
	public void testMoveTo() {
		double x = 25.2;
		double y = 0.0;
		String message = "moveTo;" + x + ";" + y;
		reader = new MessageReader(message);
		Point2D.Double check = reader.getMoveTo();
		assertTrue("Check that sent x is identical to received x", x == check.getX()); 
		assertTrue("Check that sent y is identical to received y", y == check.getY()); 
	}
}