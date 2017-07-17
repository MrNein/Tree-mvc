package ru.splat.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.transaction.annotation.Transactional;
import ru.splat.model.Node;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

/**
 * Created by Vadim on 06.07.2017.
 */
public class NodeDAOPostgres implements NodeDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    final RowMapper<Node> rm = (rs, rowNum) ->
    {
        Node node = new Node();
        node.setId(rs.getLong("id"));
        node.setName(rs.getString("name"));
        node.setParentId(rs.getLong("parent_id"));
        return node;
    };


    @Override
    public List<Node> getChildNodes(final long id)
    {
        final String sql = "SELECT * FROM node WHERE parent_id = ?";

        return jdbcTemplate.query(sql, rm, Collections.singleton(id).toArray());
    }


    @Override
    public Node getRoot()
    {
        final String sql = "SELECT * FROM node WHERE parent_id = 0";

        return jdbcTemplate.query(sql, rm).get(0);
    }


    @Override
    public long addNode(Node node)
    {
        final String sql = "INSERT INTO node (name, parent_id) VALUES (?, ?) ";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, node.getName());
            statement.setLong(2, node.getId());
            return statement;
        }, keyHolder);

        return (long) keyHolder.getKeys().get("id");
    }

    @Override
    @Transactional
    public boolean deleteNodes(final Node node)
    {
        final String delete = "UPDATE node SET parent_id = -1 WHERE id = ? ";
        final String insert = "INSERT INTO delete_nodes (id) VALUES (?) ";
        if (jdbcTemplate.update(delete, node.getId()) < 1) return false;
        return (jdbcTemplate.update(insert, node.getId()) > 0);
    }

    @Override
    public boolean renameNode(final Node node)
    {
        final String sql = "UPDATE node SET name = ? WHERE id = ? ";
        return (jdbcTemplate.update(sql, node.getName(), node.getId()) > 0);
    }


    @Override
    public boolean moveNode(long id, long parentId)
    {
        final String sql = "UPDATE node SET parent_id = ? WHERE id = ? ";
        return (jdbcTemplate.update(sql, parentId, id) > 0);
    }


    @Override
    public Node getNode(final long id) {
        final String sql = "SELECT * FROM node WHERE id = ?";

        return jdbcTemplate.query(sql, rm, Collections.singleton(id).toArray()).get(0);
    }
}
