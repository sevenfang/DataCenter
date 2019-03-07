import com.alibaba.druid.pool.DruidDataSource;

import java.sql.*;
import java.util.Properties;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/27.
 */
public class PrestoTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DruidDataSource dataSource = new DruidDataSource();
        Properties properties = new Properties();
        properties.setProperty("druid.driverClassName","com.facebook.presto.jdbc.PrestoDriver");
        properties.setProperty("druid.url","jdbc:presto://120.27.227.137:9099/hive/dw");
        properties.setProperty("druid.username","root");
        properties.setProperty("druid.password","");
        dataSource.configFromPropety(properties);
        dataSource.init();
//        Class.forName("com.facebook.presto.jdbc.PrestoDriver");
//        Connection connection = DriverManager.getConnection("jdbc:presto://120.27.227.137:9099/hive/dw","root",null);  ;
        Connection connection = dataSource.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("show tables");
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
        rs.close();
        connection.close();

    }

}
