package ru.splat.service;

import java.util.List;

import ru.splat.model.Node;


/**
 * Created by Vadim on 06.07.2017.
 */
public interface NodeService
{
    List<Node> getChildNodes(final long id);


    Node getRoot();


    long addNode(final Node node);


    boolean deleteNodes(final Node node);


    boolean renameNode(final Node node);


    boolean moveNode(final long id, final long parentId);


    Node getNode(final long id);
}
