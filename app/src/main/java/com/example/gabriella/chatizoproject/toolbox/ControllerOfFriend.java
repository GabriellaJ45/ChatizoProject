package com.example.gabriella.chatizoproject.toolbox;

import com.example.gabriella.chatizoproject.info.InfoOfFriend;
/**
 * Created by Joyce Amore on 4/30/2017.
 */

public class ControllerOfFriend {
    private static InfoOfFriend[] friendsInfo = null;
    private static InfoOfFriend[] unapprovedFriendsInfo = null;
    private static String activeFriend;

    public static void setFriendsInfo(InfoOfFriend[] friendInfo)
    {
        ControllerOfFriend.friendsInfo = friendInfo;
    }



    public static InfoOfFriend checkFriend(String username, String userKey)
    {
        InfoOfFriend result = null;
        if (friendsInfo != null)
        {
            for (int i = 0; i < friendsInfo.length; i++)
            {
                if ( friendsInfo[i].userName.equals(username) &&
                        friendsInfo[i].userKey.equals(userKey)
                        )
                {
                    result = friendsInfo[i];
                    break;
                }
            }
        }
        return result;
    }

    public static void setActiveFriend(String friendName){
        activeFriend = friendName;
    }

    public static String getActiveFriend()
    {
        return activeFriend;
    }



    public static InfoOfFriend getFriendInfo(String username)
    {
        InfoOfFriend result = null;
        if (friendsInfo != null)
        {
            for (int i = 0; i < friendsInfo.length; i++)
            {
                if ( friendsInfo[i].userName.equals(username) )
                {
                    result = friendsInfo[i];
                    break;
                }
            }
        }
        return result;
    }



    public static void setUnapprovedFriendsInfo(InfoOfFriend[] unapprovedFriends) {
        unapprovedFriendsInfo = unapprovedFriends;
    }



    public static InfoOfFriend[] getFriendsInfo() {
        return friendsInfo;
    }



    public static InfoOfFriend[] getUnapprovedFriendsInfo() {
        return unapprovedFriendsInfo;
    }

}
