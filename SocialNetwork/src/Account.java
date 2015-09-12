import java.util.HashSet;
import java.util.Set;

public class Account {

	// the unique user name of account owner
	private String userName;

	// list of members who awaiting an acceptance response from this account's
	// owner
	private Set<String> pendingResponses = new HashSet<String>();

	// list of members who are friends of this account's owner
	private Set<String> friends = new HashSet<String>();

	// list of members who have not yet accepted this account's owner request
	private Set<String> pendingRequests = new HashSet<String>();

	private boolean setAutoAcceptToTrue = false;

	public Account(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	// return list of member who had sent a friend request to this account's
	// owners
	// and still waiting for a response
	public Set<String> whoWantsToBeFriends() {
		return pendingResponses;
	}

	// an incoming friend request to this account's owner from member with user
	// name userName
	public void befriend(Account fromAccount) {
		if (!friends.contains(fromAccount.getUserName())) {
			pendingResponses.add(fromAccount.getUserName());
			fromAccount.addPendingRequest(this.getUserName());
		}

		if (setAutoAcceptToTrue == true) {
			friends.add(fromAccount.getUserName());
			fromAccount.friends.add(this.getUserName());
			this.pendingResponses.remove(fromAccount.getUserName());
			fromAccount.deletePendingRequest(this.getUserName());
		}
	}

	private void addPendingRequest(String userName) {
		// TODO Auto-generated method stub
		pendingRequests.add(userName);
	}

	private void deletePendingRequest(String userName) {
		// TODO Auto-generated method stub
		pendingRequests.remove(userName);
	}

	// check if account owner has member with user name userName as a friend
	public boolean hasFriend(String userName) {
		return friends.contains(userName);
	}

	// send an acceptance to a member still awaiting a friend-request response
	// from this account owner
	public void accepted(Account toAccount) {
		friends.add(toAccount.getUserName());
		toAccount.friends.add(this.getUserName());
		toAccount.pendingResponses.remove(this.getUserName());
		pendingRequests.remove(toAccount.getUserName());
	}

	// A1: List pending friend requests sent by this Account Holder to others
	public Set<String> whoDidIAskToBefriend() {
		return pendingRequests;
	}

	// A3: Send a rejection to a member still awaiting a friend-request response
	// from this account owner
	public void rejected(Account fromAccount) {
		fromAccount.pendingResponses.remove(this.getUserName());
		deletePendingRequest(fromAccount.getUserName());
	}

	// A5: Auto Accept all friend requests sent to the account
	public void autoAccept() {
		setAutoAcceptToTrue = true;
	}

	// A6: Unfriend an existing friend
	public void unfriend(Account toAccount) {
		friends.remove(toAccount.getUserName());
		toAccount.friends.remove(this.getUserName());
	}
	
	public Set<String> getFriends() {
		return friends;
	}

}
