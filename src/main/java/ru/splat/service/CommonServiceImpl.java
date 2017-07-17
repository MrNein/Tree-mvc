package ru.splat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ru.splat.model.Node;
import ru.splat.model.json.MoveSessionWrapper;
import ru.splat.model.json.NodeToJSON;
import ru.splat.model.json.RefreshNodeList;
import ru.splat.temp.MoveNode;
import ru.splat.temp.NodeUpdateWrapper;
import ru.splat.temp.TypeOpertator;



/**
 * Created by Vadim on 13.07.2017.
 */
public class CommonServiceImpl implements CommonService
{
    private static final long ZERO = 0L;

    private static final long IDERROR = -1L;

    @Autowired
    private NodeService nodeService;

    @Autowired
    private RefreshService refreshService;

    @Autowired
    private SessionService sessionService;


    @Override
    public List<Node> getChildNodes(final long id)
    {
        return nodeService.getChildNodes(id);
    }


    @Override
    public Node getRoot()
    {
        return nodeService.getRoot();
    }


    @Override
    public String getSessionId()
    {
        return sessionService.nextSessionId();
    }


    @Override
    public long addNode(final NodeToJSON nodeToJSON)
    {
        if(sessionService.isSessionLive(nodeToJSON.getSessionId()))
        {
            final Node node = nodeToJSON.getNode();
            final boolean idFree = refreshService.putIfAbsent(node.getId(),
                    new Node(ZERO, node.getName(), node.getId()),
                    TypeOpertator.ADD);

            if (idFree)
            {
                long newId = nodeService.addNode(node);
                refreshService.updateId(node.getId(), newId);
                return newId;
            }
        }
        return IDERROR;
    }


    @Override
    public boolean deleteNodes(final NodeToJSON nodeToJSON)
    {
        if(sessionService.isSessionLive(nodeToJSON.getSessionId()))
        {
            final Node node = nodeToJSON.getNode();
            final boolean idFree = refreshService.putIfAbsent(node.getId(),
                    node,
                    TypeOpertator.DELETE);

            if (idFree) return nodeService.deleteNodes(node);
        }
        return false;
    }


    @Override
    public boolean renameNode(final NodeToJSON nodeToJSON)
    {
        if(sessionService.isSessionLive(nodeToJSON.getSessionId()))
        {
            final Node node = nodeToJSON.getNode();
            final boolean idFree = refreshService.putIfAbsent(node.getId(),
                    node,
                    TypeOpertator.RENAME);

            if (idFree) return nodeService.renameNode(node);
        }
        return false;
    }


    @Override
    public boolean moveNode(final MoveSessionWrapper moveSessionWrapper)
    {
        if (sessionService.isSessionLive(moveSessionWrapper.getSessionId()))
        {
            final MoveNode moveNode = moveSessionWrapper.getMoveNode();
            final Node node = new Node(moveNode.getId(),
                    nodeService.getNode(moveNode.getId()).getName(),
                    moveNode.getParentId());
            final boolean idFree = refreshService.putIfAbsent(node.getId(), node, TypeOpertator.MOVE);


            if (idFree )
            {
                final boolean parentIdFree = refreshService.putIfAbsent(node.getParentId(), node, TypeOpertator.ADD);
                if (parentIdFree)
                {
                    return nodeService.moveNode(moveNode.getId(), moveNode.getParentId());
                }
                else
                {
                    refreshService.removeId(node.getId());
                }
            }
        }
        return false;
    }


    @Override
    public List<NodeUpdateWrapper> returnUpdatedNodes(final RefreshNodeList nodes)
    {
        sessionService.isSessionLive(nodes.getSessionId());
        return refreshService.returnUpdateNodes(nodes.getList());
    }
}
