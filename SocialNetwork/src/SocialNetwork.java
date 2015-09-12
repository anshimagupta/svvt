import java.util.HashSet;
import java.util.Collection;
import java.util.Set;

public class SocialNetwork {

	private Collection<Account> accounts = new HashSet<Account>();
	private boolean setAutoAccept = false;

	// join SN with a new user name
	public Account join(String userName) {
		if(userName!=null && userName!=""){
            if(findAccountForUserName(userName)!=null){
                try {
                    throw new UserExistsException();
                } catch (UserExistsException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return null;
            }
            else{
                Account newAccount = new Account(userName);
                accounts.add(newAccount);
                return newAccount;
            }
            
        }
        else{
            return null;
        }
	}

	// find a member by user name
	private Account findAccountForUserName(String userName) {
		// find account with user name userName
		// not accessible to outside because that would give a user full access
		// to another member's account
		for (Account each : accounts) {
			if (each.getUserName().equals(userName))
				return each;
		}
		return null;
	}

	// list user names of all members
	public Collection<String> listMembers() {
		Collection<String> members = new HashSet<String>();
		for (Account each : accounts) {
			members.add(each.getUserName());
		}
		return members;
	}

	// send a friend request to user with userName from my account
	public void sendFriendRequestTo(String userName, Account me) {
		Account accountWithUserName = findAccountForUserName(userName);
		accountWithUserName.befriend(me);
		
		if(setAutoAccept == true) {
			accountWithUserName.accepted(me);
		}
	}

	// accept a friend request from another user with userName from my account
	public void acceptFriendshipFrom(String userName, Account me) {
		Account accountWithUserName = findAccountForUserName(userName);
		accountWithUserName.accepted(me);
	}

	// A2: accept all friend requests that are pending a response
	public void acceptAllFriendRequestsTo(Account me) {
		Set<String> pendingReqests = me.whoWantsToBeFriends();
		Set<String> clearingPendingRequest = new HashSet<String>(pendingReqests);
		for (String userName : clearingPendingRequest) {
			acceptFriendshipFrom(userName, me);
		}
	}
	

    // A3: reject friend requests that are pending a response
    public void rejectFriendRequestFrom(String userName, Account toAccount){
        // TODO Auto-generated method stub
            findAccountForUserName(userName).rejected(toAccount);
        }
    
    // A4: Reject all friend requests sent to toAccount
    public void rejectAllFriendRequestsTo(Account toAccount) {
    	Set<String> pendingReqests = toAccount.whoWantsToBeFriends();
		Set<String> rejectingPendingRequest = new HashSet<String>(pendingReqests);
		for (String userName : rejectingPendingRequest) {
			rejectFriendRequestFrom(userName, toAccount);
		}
    }
    
    // A5: AutoAccept all requests
    public void autoAcceptFriendRequestsTo(Account toAccount) {
    	setAutoAccept = true;
    }
    
    // A6: Unfriend an existing friend
    public void sendUnfriendRequestTo(String userName, Account me) {
    	Account accountWithUserName = findAccountForUserName(userName);
    	accountWithUserName.unfriend(me);
    }
    
    // A7: Leave the social network
    public void leave(Account me) {
    	for(Account account : accounts){
            if(account!=me){
                if(account.hasFriend(me.getUserName())){
                    account.unfriend(me);
                }
                if(account.whoDidIAskToBefriend().contains(me.getUserName())){
                    account.whoDidIAskToBefriend().remove(me.getUserName());
                }
                if(account.whoWantsToBeFriends().contains(me.getUserName())){
                    account.whoWantsToBeFriends().remove(me.getUserName());
                }
            }
                
        }
        accounts.remove(me);
    }
    
}
