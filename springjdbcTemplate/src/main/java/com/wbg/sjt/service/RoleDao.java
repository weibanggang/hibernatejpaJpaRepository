package com.wbg.sjt.service;

import com.wbg.sjt.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class RoleDao {
    @Autowired
    DataSource dataSource;
    @Autowired
    private PlatformTransactionManager transactionManager = null;

    @Autowired
    private TransactionTemplate transactionTemplate;
    ;
    @Autowired
    private JdbcOperations jdbcOperations;
    //返回一个对象
   public Role getRole(){
        String sql = "select * from role where id = 1";
        Role role =  jdbcOperations.queryForObject(
                sql,
              /*
              //方式一
               new RowMapper<Role>() {
                    @Override
                    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new Role(rs.getInt(1),rs.getString(2),rs.getString(3));
                    }
                }*/
                //方式二：
                (rs, rowNum) -> new Role(rs.getInt(1),rs.getString(2),rs.getString(3))
        );
        return role;
    }

    public void create() {
        transactionTemplate.execute(status -> {
            //让事务出错
            String sql = "insert into role(role_name,note) values(?,?)";
            String sql2 = "insert into role(role_namess,note) values(?,?)";
            jdbcOperations.update(sql, "sql", "aa");
            jdbcOperations.update(sql2, "sql", "aa");
            return null;
        });
    }


    //0代码实现
    public List<Role> listAll() {
        List<Role> list = new ArrayList<Role>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "select * from role";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            Role role = null;
            while (resultSet.next()) {
                role = new Role(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                );
                list.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
                if(preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    //返回多个个对象
    public List<Map<String, Object>> getToList() {
        List<Map<String, Object>> list = jdbcOperations.queryForList("select * from role");
        return list;
    }
    //返回一个对象
    public Map<String, Object> getToMap() {
        String sql = "select * from role where id = ?";
        Map<String, Object> map = jdbcOperations.queryForMap(sql, 1);
        return map;
    }

    public int insert(Role role) {
        Connection connection = null;
        DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
        dtd.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus ts = transactionManager.getTransaction(dtd);
        String sql = "insert into role(role_name,note) values(?,?)";
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, role.getRoleName());
            preparedStatement.setString(2, role.getNote());
            preparedStatement.executeUpdate();
            transactionManager.commit(ts);
        } catch (SQLException e) {
            transactionManager.rollback(ts);
            System.out.println("原因:" + e.getMessage());
        }
        return 0;
    }
}
