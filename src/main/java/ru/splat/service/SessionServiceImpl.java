package ru.splat.service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by Vadim on 13.07.2017.
 */
public class SessionServiceImpl implements SessionService
{
    private final int MAXTIME = 16;

    private final ConcurrentHashMap<String, LocalDateTime> sessions;

    private final SecureRandom random;


    public SessionServiceImpl()
    {
        sessions = new ConcurrentHashMap<>();
        random = new SecureRandom();
    }


    private String getSessionId()
    {
        return new BigInteger(130, random).toString(32);
    }


    @Override
    public String nextSessionId()
    {
        final String sessionId = getSessionId();
        sessions.put(sessionId, LocalDateTime.now());
        return sessionId;
    }


    @Override
    public boolean isSessionLive(final String sessionId)
    {
        if(sessions.get(sessionId).isAfter(LocalDateTime.now().minusSeconds(MAXTIME)))
        {
            sessions.put(sessionId, LocalDateTime.now());
            return true;
        }
        sessions.remove(sessionId);
        return false;
    }
}
