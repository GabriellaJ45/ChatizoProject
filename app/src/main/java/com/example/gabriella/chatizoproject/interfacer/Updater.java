package com.example.gabriella.chatizoproject.interfacer;
import com.example.gabriella.chatizoproject.info.InfoOfFriend;
import com.example.gabriella.chatizoproject.info.InfoOfMessage;
/**
 * Created by Joyce Amore on 4/30/2017.
 */

public interface Updater {
    public void updateData(InfoOfMessage[] messages, InfoOfFriend[] friends, InfoOfFriend[] unApprovedFriends, String userKey);

}
