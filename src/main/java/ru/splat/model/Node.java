package ru.splat.model;


/**
 * Created by Vadim on 06.07.2017.
 */
public class Node
{

    private Long id;


    private Long parentId;


    private String name;


    public Node(){}


    public Node(Long id, String name, Long parentId)
    {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }


    public Long getId()
    {
        return id;
    }


    public void setId(final Long id)
    {
        this.id = id;
    }


    public Long getParentId()
    {
        return parentId;
    }


    public void setParentId(final Long parentId)
    {
        this.parentId = parentId;
    }


    public String getName()
    {
        return name;
    }


    public void setName(final String name)
    {
        this.name = name;
    }


    @Override
    public String toString()
    {
        return "Node{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                '}';
    }
}