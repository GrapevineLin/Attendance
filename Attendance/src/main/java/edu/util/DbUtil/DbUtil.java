package edu.util.DbUtil;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.taglibs.standard.tag.common.sql.DataSourceUtil;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtil {

    //私有化构造方法
    //为什么要私有化构造函数  -->目的是为了不让别人可以new这个对象
    private DbUtil() {
    }

    private static DruidDataSource druid = null;
    static Properties properties = new Properties();

    //利用静态代码块加载驱动
    static {
        //利用静态代码块来读取C3P0的配置文件
        // 静态块 执行的优先级比较高  静态代码块只执行一次
        //通过类加载器获得流
        InputStream is = DataSourceUtil.class.getClassLoader().getResourceAsStream("Druid.properties");
        try {
            properties.load(is);
            druid = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            System.out.println("读取properties文件异常！！");
            e.printStackTrace();
        }
    }

    /*
     * 获取连接
     * */
    public static Connection getConn() {
        Connection con = null;
        try {
            con = druid.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //返回连接给调用者
        return con;

    }

    /*
     * 关闭连接*/
    public static void close(Connection con) {
/*        //关闭顺序==>rs<pstm<con
        //关闭预编译对象
        if (pstm != null) {
            try {
                pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
        //关闭连接
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }


}