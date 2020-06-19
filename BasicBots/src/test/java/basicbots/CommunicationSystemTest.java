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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CommunicationSystemTest {

	private MessageWriter writer;
	private MessageReader reader;
	
	@Before
	public void setUp() {
		writer = new MessageWriter();
	}
	
	@After
	public void tearDown() {
		writer = null;
		reader = null;
	}
	
	@Test
	public void testLeadership() {
		String s = "followMe";
		writer.addLeadership(s);
		reader = new MessageReader(writer.composeMessage());
		String check = reader.getLeadership();
		assertTrue("Check that sent string is identical to recieved string", s.compareTo(check) == 0); 
	}
	
	@Test
	public void testTeamMode() {
		String s = "offensive";
		writer.addTeamMode(s);
		reader = new MessageReader(writer.composeMessage());
		String check = reader.getTeamMode();
		assertTrue("Check that sent string is identical to recieved string", s.compareTo(check) == 0); 
	}
	
	@Test
	public void testMyPos() {
		double x = 25.2;
		double y = 0.0;
		writer.addMyPos(x, y);
		reader = new MessageReader(writer.composeMessage());
		String[] check = reader.getMyPos();
		assertTrue("Check that sent x is identical to recieved x", x == Double.parseDouble(check[0])); 
		assertTrue("Check that sent y is identical to recieved y", y == Double.parseDouble(check[1])); 
	}
	
	@Test
	public void testFriendPos() {
		String name = "fakeFriend";
		double x = 25.2;
		double y = 0.0;
		writer.addFriendPos(name, x, y);
		reader = new MessageReader(writer.composeMessage());
		String[] check = reader.getFriendPos();
		assertTrue("Check that sent name is identical to recieved name", s.compareTo(check[0]) == 0); 
		assertTrue("Check that sent x is identical to recieved x", x == Double.parseDouble(check[1])); 
		assertTrue("Check that sent y is identical to recieved y", y == Double.parseDouble(check[2])); 
	}
	
	@Test
	public void testEnemyPos() {
		String name = "fakeEnemy";
		double x = 25.2;
		double y = 0.0;
		writer.addEnemyPos(name, x, y);
		reader = new MessageReader(writer.composeMessage());
		String[] check = reader.getEnemyPos();
		assertTrue("Check that sent name is identical to recieved name", s.compareTo(check[0]) == 0); 
		assertTrue("Check that sent x is identical to recieved x", x == Double.parseDouble(check[1])); 
		assertTrue("Check that sent y is identical to recieved y", y == Double.parseDouble(check[2])); 
	}
	
	@Test
	public void testEnemyDetails() {
		String name = "fakeEnemy";
		double x = 25.2;
		double y = 0.0;
		double velocity = 30.0;
		double energy = 120.0;
		double heading = 12.3;
		double gunHeading = null;
		writer.addEnemyPos(name, x, y);
		reader = new MessageReader(writer.composeMessage());
		String[] check = reader.getEnemyDetails();
		assertTrue("Check that sent name is identical to recieved name", s.compareTo(check[0]) == 0); 
		assertTrue("Check that sent x is identical to recieved x", x == Double.parseDouble(check[1])); 
		assertTrue("Check that sent y is identical to recieved y", y == Double.parseDouble(check[2])); 
		assertTrue("Check that sent velocity is identical to recieved velocity", velocity == Double.parseDouble(check[3])); 
		assertTrue("Check that sent energy is identical to recieved energy", energy == Double.parseDouble(check[4])); 
		assertTrue("Check that sent heading is identical to recieved heading", heading == Double.parseDouble(check[5])); 
		assertTrue("Check that sent gunHeading is identical to recieved gunHeading", gunHeading == Double.parseDouble(check[gunHeading])); 
	}
	
	@Test
	public void testTargetEnemy() {
		String name = "fakeEnemy";
		writer.addTargetEnemy(name);
		reader = new MessageReader(writer.composeMessage());
		String check = reader.getTargetEnemy();
		assertTrue("Check that sent string is identical to recieved string", name.compareTo(check) == 0); 
	}
	
	@Test
	public void testTargetPos() {
		double x = 25.2;
		double y = 0.0;
		writer.addTargetPos(x, y);
		reader = new MessageReader(writer.composeMessage());
		String[] check = reader.getTargetPos();
		assertTrue("Check that sent x is identical to recieved x", x == Double.parseDouble(check[0])); 
		assertTrue("Check that sent y is identical to recieved y", y == Double.parseDouble(check[1])); 
	}
	
	@Test
	public void testMoveTo() {
		double x = 25.2;
		double y = 0.0;
		writer.addMoveTo(x, y);
		reader = new MessageReader(writer.composeMessage());
		String[] check = reader.getMoveTo();
		assertTrue("Check that sent x is identical to recieved x", x == Double.parseDouble(check[0])); 
		assertTrue("Check that sent y is identical to recieved y", y == Double.parseDouble(check[1])); 
	}
}
