package ru.splat.service;

import java.util.List;

import ru.splat.model.Node;
import ru.splat.model.json.MoveSessionWrapper;
import ru.splat.model.json.NodeToJSON;
import ru.splat.model.json.RefreshNodeList;
import ru.splat.temp.NodeUpdateWrapper;


/**
 * Created by Vadim on 13.07.2017.
 */
public interface CommonService
{
    List<Node> getChildNodes(final long id);


    String getSessionId();


    Node getRoot();


    long addNode(final NodeToJSON node);


    boolean deleteNodes(final NodeToJSON node);


    boolean renameNode(final NodeToJSON node);


    boolean moveNode(final MoveSessionWrapper moveSessionWrapper);


    List<NodeUpdateWrapper> returnUpdatedNodes(final RefreshNodeList nodes);
}
