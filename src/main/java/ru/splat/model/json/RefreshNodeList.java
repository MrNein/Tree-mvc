package ru.splat.model.json;

import java.util.List;


/**
 * Created by Vadim on 13.07.2017.
 */
public class RefreshNodeList
{
    private String sessionId;

    private List<Long> list;


    public String getSessionId()
    {
        return sessionId;
    }


    public void setSessionId(String sessionId)
    {
        this.sessionId = sessionId;
    }


    public List<Long> getList()
    {
        return list;
    }


    public void setList(List<Long> list)
    {
        this.list = list;
    }
}
