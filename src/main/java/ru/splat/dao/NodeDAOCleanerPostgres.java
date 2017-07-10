package ru.splat.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.splat.model.Node;

import java.util.List;

/**
 * Created by Vadim on 09.07.2017.
 */
public class NodeDAOCleanerPostgres implements NodeDAOCleaner {

    @Autowired
    JdbcTemplate jdbcTemplate;

    final RowMapper<Node> rm = (rs, rowNum) ->
    {
        Node node = new Node();
        node.setId(rs.getLong("id"));
        return node;
    };


    @Override
    public void clearNodes() {

        final String sql = "SELECT * FROM delete_nodes";

        List<Node> nodes = jdbcTemplate.query(sql, rm);

        final String sqlDel = "DELETE FROM delete_nodes WHERE id = ?";
        final String sqlRemove = "WITH RECURSIVE remove(id, parent_id) AS \n" +
                "(SELECT id, parent_id FROM node \n" +
                "WHERE id = ? \n" +
                "UNION ALL \n" +
                "SELECT n.id, n.parent_id FROM node n, remove r \n" +
                "WHERE r.id = n.parent_id )\n" +
                "DELETE FROM node \n" +
                "WHERE id IN(SELECT id FROM remove);";
        for(Node node: nodes){
            jdbcTemplate.update(sqlRemove, node.getId());
            jdbcTemplate.update(sqlDel, node.getId());
        }
    }
}
