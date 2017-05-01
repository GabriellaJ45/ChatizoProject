package com.example.gabriella.chatizoproject.toolbox;

import com.example.gabriella.chatizoproject.info.InfoOfFriend;
import com.example.gabriella.chatizoproject.info.InfoOfMessage;
import com.example.gabriella.chatizoproject.info.InfoStatus;
import com.example.gabriella.chatizoproject.interfacer.Updater;


import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import android.util.Log;


/**
 * Created by Joyce Amore on 4/30/2017.
 */

public class HandlerXML extends DefaultHandler{

    private String userKey = new String();
    private Updater updater;

    public HandlerXML(Updater updater) {
        super();
        this.updater = updater;
    }

    private Vector<InfoOfFriend> mFriends = new Vector<InfoOfFriend>();
    private Vector<InfoOfFriend> mOnlineFriends = new Vector<InfoOfFriend>();
    private Vector<InfoOfFriend> mUnapprovedFriends = new Vector<InfoOfFriend>();

    private Vector<InfoOfMessage> mUnreadMessages = new Vector<InfoOfMessage>();


    public void endDocument() throws SAXException
    {
        InfoOfFriend[] friends = new InfoOfFriend[mFriends.size() + mOnlineFriends.size()];
        InfoOfMessage[] messages = new InfoOfMessage[mUnreadMessages.size()];

        int onlineFriendCount = mOnlineFriends.size();
        for (int i = 0; i < onlineFriendCount; i++)
        {
            friends[i] = mOnlineFriends.get(i);
        }


        int offlineFriendCount = mFriends.size();
        for (int i = 0; i < offlineFriendCount; i++)
        {
            friends[i + onlineFriendCount] = mFriends.get(i);
        }

        int unApprovedFriendCount = mUnapprovedFriends.size();
        InfoOfFriend[] unApprovedFriends = new InfoOfFriend[unApprovedFriendCount];

        for (int i = 0; i < unApprovedFriends.length; i++) {
            unApprovedFriends[i] = mUnapprovedFriends.get(i);
        }

        int unreadMessagecount = mUnreadMessages.size();
        //Log.i("MessageLOG", "mUnreadMessages="+unreadMessagecount );
        for (int i = 0; i < unreadMessagecount; i++)
        {
            messages[i] = mUnreadMessages.get(i);
            Log.i("MessageLOG", "i="+i );
        }

        this.updater.updateData(messages, friends, unApprovedFriends, userKey);
        super.endDocument();
    }

    public void startElement(String uri, String localName, String name,
                             Attributes attributes) throws SAXException
    {
        if (localName == "friend")
        {
            InfoOfFriend friend = new InfoOfFriend();
            friend.userName = attributes.getValue(InfoOfFriend.USERNAME);
            String status = attributes.getValue(InfoOfFriend.STATUS);
            friend.ip = attributes.getValue(InfoOfFriend.IP);
            friend.port = attributes.getValue(InfoOfFriend.PORT);
            friend.userKey = attributes.getValue(InfoOfFriend.USER_KEY);
            //friend.expire = attributes.getValue("expire");

            if (status != null && status.equals("online"))
            {
                friend.status = InfoStatus.ONLINE;
                mOnlineFriends.add(friend);
            }
            else if (status.equals("unApproved"))
            {
                friend.status = InfoStatus.UNAPPROVED;
                mUnapprovedFriends.add(friend);
            }
            else
            {
                friend.status = InfoStatus.OFFLINE;
                mFriends.add(friend);
            }
        }
        else if (localName == "user") {
            this.userKey = attributes.getValue(InfoOfFriend.USER_KEY);
        }
        else if (localName == "message") {
            InfoOfMessage message = new InfoOfMessage();
            message.userid = attributes.getValue(InfoOfMessage.USERID);
            message.sendt = attributes.getValue(InfoOfMessage.SENDT);
            message.messagetext = attributes.getValue(InfoOfMessage.MESSAGETEXT);
            Log.i("MessageLOG", message.userid + message.sendt + message.messagetext);
            mUnreadMessages.add(message);
        }
        super.startElement(uri, localName, name, attributes);
    }

    @Override
    public void startDocument() throws SAXException {
        this.mFriends.clear();
        this.mOnlineFriends.clear();
        this.mUnreadMessages.clear();
        super.startDocument();
    }
}
