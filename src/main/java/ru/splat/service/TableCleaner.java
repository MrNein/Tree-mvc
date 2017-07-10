package ru.splat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.splat.dao.NodeDAOCleaner;


/**
 * Created by Vadim on 08.07.2017.
 */
@EnableScheduling
@Service
public class TableCleaner {

    @Autowired
    NodeDAOCleaner nodeDAOCleaner;


    @Scheduled(fixedRate = 120000)
    public void Clear(){
        nodeDAOCleaner.clearNodes();
    }
}
