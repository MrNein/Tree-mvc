package ru.splat.temp;

import ru.splat.model.Node;


/**
 * Created by Vadim on 13.07.2017.
 */
public class NodeUpdateWrapper
{
    private Node node;

    private TypeOpertator type;

    private String token;


    public NodeUpdateWrapper(final Node node, final TypeOpertator type, final String token)
    {
        this.node = node;
        this.type = type;
        this.token = token;
    }


    public Node getNode()
    {
        return node;
    }


    public TypeOpertator getType()
    {
        return type;
    }


    public String getToken()
    {
        return token;
    }
}
