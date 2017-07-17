package ru.splat.service;


/**
 * Created by Vadim on 14.07.2017.
 */
public interface SessionService
{
    String nextSessionId();


    boolean isSessionLive(final String sessionId);
}
