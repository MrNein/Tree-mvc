package ru.splat.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.splat.dao.NodeDAO;
import ru.splat.model.Node;

import java.util.List;

/**
 * Created by Vadim on 06.07.2017.
 */
public class NodeServiceImpl implements NodeService {
    @Autowired
    NodeDAO nodeDAO;


    @Override
    public List<Node> getChildNodes(final long id)
    {
        return nodeDAO.getChildNodes(id);
    }


    @Override
    public Node getRoot()
    {
        return nodeDAO.getRoot();
    }


    @Override
    public long addNode(final Node node)
    {
        return nodeDAO.addNode(node);
    }


    @Override
    public boolean deleteNodes(final int id)
    {
        return nodeDAO.deleteNodes(id);
    }


    @Override
    public boolean renameNode(final Node node)
    {
        return nodeDAO.renameNode(node);
    }

    @Override
    public boolean moveNode(long id, long parentId)
    {
        return nodeDAO.moveNode(id, parentId);
    }
}
