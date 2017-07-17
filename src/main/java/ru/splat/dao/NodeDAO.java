package ru.splat.dao;

import ru.splat.model.Node;

import java.util.List;

/**
 * Created by Vadim on 06.07.2017.
 */
public interface NodeDAO
{
    List<Node> getChildNodes(final long id);


    Node getRoot();


    long addNode(final Node node);


    boolean deleteNodes(final Node node);


    boolean renameNode(final Node node);


    boolean moveNode(long id, long parentId);


    Node getNode(final long id);
}
