import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class SocialNetworkTest {

	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	
	}

	@Test 
	public void canJoinSocialNetwork() {
		SocialNetwork sn = new SocialNetwork();
		Account me = sn.join("Hakan");
		assertEquals("Hakan", me.getUserName());
	}
	
	@Test 
	public void canListSingleMemberOfSocialNetwork() {
		SocialNetwork sn = new SocialNetwork();
		sn.join("Hakan");
		Collection<String> members = sn.listMembers();
		assertEquals(1, members.size());
		assertTrue(members.contains("Hakan"));
	}
	
	@Test 
	public void twoPeopleCanJoinSocialNetwork() {
		SocialNetwork sn = new SocialNetwork();
		sn.join("Hakan");
		sn.join("Cecile");
		Collection<String> members = sn.listMembers();
		assertEquals(2, members.size());
		assertTrue(members.contains("Hakan"));
		assertTrue(members.contains("Cecile"));
	}
	
	@Test 
	public void aMemberCanSendAFriendRequestToAnother() {
		SocialNetwork sn = new SocialNetwork();
		Account me = sn.join("Hakan");
		Account her = sn.join("Cecile");
		sn.sendFriendRequestTo("Cecile", me);
		assertTrue(her.whoWantsToBeFriends().contains("Hakan"));
	}
	
	@Test 
	public void aMemberCanAcceptAFriendRequestFromAnother() {
		SocialNetwork sn = new SocialNetwork();
		Account me = sn.join("Hakan");
		Account her = sn.join("Cecile");
		sn.sendFriendRequestTo("Cecile", me);
		sn.acceptFriendshipFrom("Hakan", her);
		assertTrue(me.hasFriend("Cecile"));
		assertTrue(her.hasFriend("Hakan"));
	}
	
    @Test
    public void acceptAllPendingRequests(){
    	SocialNetwork sn = new SocialNetwork();
        Account me = sn.join("Hakan");
        Account her = sn.join("Cecile");
        Account anshima = sn.join("Anshima");
        Account archana = sn.join("Archana");
        sn.sendFriendRequestTo("Hakan", her);
        sn.sendFriendRequestTo("Hakan", anshima);
        sn.sendFriendRequestTo("Hakan", archana);
        sn.acceptAllFriendRequestsTo(me);
        assertTrue(me.hasFriend(her.getUserName()));
        assertTrue(me.hasFriend(anshima.getUserName()));
        assertTrue(me.hasFriend(archana.getUserName()));
        assertTrue(her.hasFriend(me.getUserName()));
        assertTrue(archana.hasFriend(me.getUserName()));
        assertTrue(anshima.hasFriend(me.getUserName()));       
    }
    
    @Test
    public void rejectFriendRequest() {
    	SocialNetwork sn = new SocialNetwork();
        Account me = sn.join("Hakan");
        Account her = sn.join("Cecile");
        sn.sendFriendRequestTo("Hakan", her);
        assertTrue(me.whoWantsToBeFriends().contains(her.getUserName()));
        sn.rejectFriendRequestFrom(her.getUserName(), me);   
        assertFalse(me.whoWantsToBeFriends().contains(her.getUserName()));
    }
    
    @Test
    public void rejectAllPendingRequests(){
    	SocialNetwork sn = new SocialNetwork();
        Account me = sn.join("Hakan");
        Account her = sn.join("Cecile");
        Account anshima = sn.join("Anshima");
        Account archana = sn.join("Archana");
        sn.sendFriendRequestTo("Hakan", her);
        sn.sendFriendRequestTo("Hakan", anshima);
        sn.sendFriendRequestTo("Hakan", archana);
        sn.rejectAllFriendRequestsTo(me);
        assertFalse(me.whoWantsToBeFriends().contains(her.getUserName()));
        assertFalse(me.whoWantsToBeFriends().contains(anshima.getUserName()));
        assertFalse(me.whoWantsToBeFriends().contains(archana.getUserName()));       
    }
    
    
	
}
