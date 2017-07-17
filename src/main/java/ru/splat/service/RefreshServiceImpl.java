package ru.splat.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import ru.splat.model.Node;
import ru.splat.temp.NodeUpdateWrapper;
import ru.splat.temp.NodeWrapper;
import ru.splat.temp.TypeOpertator;


/**
 * Created by Vadim on 13.07.2017.
 */
@EnableScheduling
public class RefreshServiceImpl implements RefreshService
{
    private static final int TIMESPACE = 10000;

    private final int TIMETABLE = 10;

    private ConcurrentHashMap<Long, NodeWrapper> upd;


    public RefreshServiceImpl()
    {
        upd = new ConcurrentHashMap<>();
    }


    @Override
    public boolean putIfAbsent(final long id, final Node node, final TypeOpertator type)
    {
        final LocalDateTime localDateTime = LocalDateTime.now();
        final NodeWrapper nodeWrapped = upd.putIfAbsent(id, new NodeWrapper(new NodeUpdateWrapper(node, type, localDateTime.toString()), localDateTime));

        if(nodeWrapped == null)
        {
            return true;
        }
        return false;
    }


    @Override
    @Scheduled(fixedRate = TIMESPACE)
    public void cleaner()
    {
        final LocalDateTime  now = LocalDateTime.now();

        upd.entrySet().removeIf(entry -> entry.getValue().getLocalDateTime().isBefore(now.minusSeconds(TIMETABLE)));
    }


    @Override
    public List<NodeUpdateWrapper> returnUpdateNodes(final List<Long> nodes)
    {
        return nodes.stream().filter(id -> upd.keySet().contains(id)).map(nodeWrapped-> upd.get(nodeWrapped).getNode()).collect(Collectors.toList());
    }


    @Override
    public void updateId(final long id, final long newId)
    {
        upd.get(id).getNode().getNode().setId(newId);
    }


    @Override
    public void removeId(long id)
    {
        upd.remove(id);
    }
}
