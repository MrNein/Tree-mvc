package ru.splat.model.json;

import ru.splat.model.Node;


/**
 * Created by Vadim on 15.07.2017.
 */
public class NodeToJSON
{
    private String sessionId;

    private Node node;


    public String getSessionId()
    {
        return sessionId;
    }


    public void setSessionId(String sessionId)
    {
        this.sessionId = sessionId;
    }


    public Node getNode()
    {
        return node;
    }


    public void setNode(Node node)
    {
        this.node = node;
    }
}
