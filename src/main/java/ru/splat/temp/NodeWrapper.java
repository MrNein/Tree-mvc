package ru.splat.temp;

import java.time.LocalDateTime;


/**
 * Created by Vadim on 13.07.2017.
 */
public class NodeWrapper
{
    private NodeUpdateWrapper node;

    private LocalDateTime localDateTime;


    public NodeWrapper(final NodeUpdateWrapper node, final LocalDateTime localDateTime)
    {
        this.node = node;
        this.localDateTime = localDateTime;
    }


    public NodeUpdateWrapper getNode()
    {
        return node;
    }


    public LocalDateTime getLocalDateTime()
    {
        return localDateTime;
    }
}
