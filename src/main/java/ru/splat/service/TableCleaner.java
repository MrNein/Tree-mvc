package ru.splat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import ru.splat.dao.NodeDAOCleaner;


/**
 * Created by Vadim on 08.07.2017.
 */
@EnableScheduling
public class TableCleaner
{
    private static final int TIMESPACE = 120000;

    
    @Autowired
    NodeDAOCleaner nodeDAOCleaner;


    @Scheduled(fixedRate = TIMESPACE)
    public void Clear()
    {
        nodeDAOCleaner.clearNodes();
    }
}
