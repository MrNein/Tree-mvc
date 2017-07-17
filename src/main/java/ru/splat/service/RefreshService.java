package ru.splat.service;

import java.util.List;

import ru.splat.model.Node;
import ru.splat.temp.NodeUpdateWrapper;
import ru.splat.temp.TypeOpertator;


/**
 * Created by Vadim on 13.07.2017.
 */
public interface RefreshService
{
    boolean putIfAbsent(final long id, final Node node, final TypeOpertator type);


    void cleaner();


    List<NodeUpdateWrapper> returnUpdateNodes(final List<Long> nodes);


    void updateId(final long id, final long newId);


    void removeId(final long id);
}
