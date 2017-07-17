package ru.splat.model.json;

import ru.splat.temp.MoveNode;


/**
 * Created by Vadim on 15.07.2017.
 */
public class MoveSessionWrapper
{
    private String sessionId;

    private MoveNode moveNode;


    public String getSessionId()
    {
        return sessionId;
    }


    public void setSessionId(String sessionId)
    {
        this.sessionId = sessionId;
    }


    public MoveNode getMoveNode()
    {
        return moveNode;
    }


    public void setMoveNode(MoveNode moveNode)
    {
        this.moveNode = moveNode;
    }
}
