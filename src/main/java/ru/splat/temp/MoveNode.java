package ru.splat.temp;


/**
 * Created by Vadim on 13.07.2017.
 */
public class MoveNode
{
    private Long id;

    private Long parentId;

    private Long oldParentId;


    public Long getId()
    {
        return id;
    }


    public Long getParentId()
    {
        return parentId;
    }


    public Long getOldParentId()
    {
        return oldParentId;
    }


    public void setId(final Long id)
    {
        this.id = id;
    }


    public void setParentId(final Long parentId)
    {
        this.parentId = parentId;
    }


    public void setOldParentId(final Long oldParentId)
    {
        this.oldParentId = oldParentId;
    }
}
