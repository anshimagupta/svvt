import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class AccountTest {
	
	Account me, her, another;
	
	@Before
	public void setUp() throws Exception {
		me = new Account("Hakan");
		her = new Account("Serra");
		another = new Account("Cecile");
	}

	@Test
	public void canBefriendAnother() {
		me.befriend(her);
		assertTrue(me.whoWantsToBeFriends().contains(her.getUserName()));
	}
	
	@Test
	public void noFriendRequests() {
		assertEquals(0, me.whoWantsToBeFriends().size());
	}
	
	@Test
	public void testMultipleFriendRequests() {
		me.befriend(her);
		me.befriend(another);
		assertEquals(2, me.whoWantsToBeFriends().size());
		assertTrue(me.whoWantsToBeFriends().contains(another.getUserName()));
		assertTrue(me.whoWantsToBeFriends().contains(her.getUserName()));
	}
	
	@Test
	public void doubleFriendRequestsAreOk() {
		me.befriend(her);
		me.befriend(her);
		assertEquals(1, me.whoWantsToBeFriends().size());
	}
	
	@Test
	public void afterAcceptingFriendRequestWhoWantsToBeFriendsUpdated() {
		me.befriend(her);
		her.accepted(me);
		assertFalse(me.whoWantsToBeFriends().contains(her.getUserName()));
	}
	
	@Test
	public void everybodyAreFriends() {
		me.befriend(her);
		me.befriend(another);
		her.befriend(another);
		her.accepted(me);
		her.accepted(another);
		another.accepted(her);
		another.accepted(me);
		assertTrue(me.hasFriend(her.getUserName()));
		assertTrue(me.hasFriend(another.getUserName()));
		assertTrue(her.hasFriend(me.getUserName()));
		assertTrue(her.hasFriend(another.getUserName()));
		assertTrue(another.hasFriend(her.getUserName()));
		assertTrue(her.hasFriend(me.getUserName()));
	}
	
	@Test
	public void cannotBefriendAnExistingFriend() {
		me.befriend(her);
		her.accepted(me);
		assertTrue(her.hasFriend(me.getUserName()));
		me.befriend(her);
		assertFalse(me.whoWantsToBeFriends().contains(her.getUserName()));
		assertFalse(her.whoWantsToBeFriends().contains(me.getUserName()));
	}
	
	@Test
	public void afterSendingFriendRequestWhoAllHaveNotAccepted() {
		her.befriend(me);
		assertTrue(me.whoDidIAskToBefriend().contains(her.getUserName()));
		her.accepted(me);
		assertEquals(0, her.whoDidIAskToBefriend().size());
	}
	
	@Test
	public void rejectFriendRequest() {
		me.befriend(her);
		assertTrue(me.whoWantsToBeFriends().contains(her.getUserName()));
		her.rejected(me);
		assertFalse(me.whoWantsToBeFriends().contains(her.getUserName()));	
	}

}
