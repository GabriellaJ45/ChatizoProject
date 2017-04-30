package com.example.gabriella.chatizoproject.toolbox;

import com.example.gabriella.chatizoproject.info.InfoOfMessage;

/**
 * Created by Joyce Amore on 4/30/2017.
 */

public class MessageController {

    private static InfoOfMessage[] messagesInfo = null;

    public static void setMessagesInfo(InfoOfMessage[] messageInfo)
    {
        MessageController.messagesInfo = messageInfo;
    }



    public static InfoOfMessage checkMessage(String username)
    {
        InfoOfMessage result = null;
        if (messagesInfo != null)
        {
            for (int i = 0; i < messagesInfo.length;)
            {

                result = messagesInfo[i];
                break;

            }
        }
        return result;
    }





    public static InfoOfMessage getMessageInfo(String username)
    {
        InfoOfMessage result = null;
        if (messagesInfo != null)
        {
            for (int i = 0; i < messagesInfo.length;)
            {
                result = messagesInfo[i];
                break;

            }
        }
        return result;
    }






    public static InfoOfMessage[] getMessagesInfo() {
        return messagesInfo;
    }


}
