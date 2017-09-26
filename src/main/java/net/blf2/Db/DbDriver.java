/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.blf2.Db;
import com.hp.hpl.jena.db.DBConnection;
import com.hp.hpl.jena.db.IDBConnection;

import java.sql.SQLException;

/**
 * Created by blf2 on 17-6-28.
 */
public class DbDriver {
    public static IDBConnection idbConnection;
    public static String initDb(String dbName,String userName,String userPswd) {
        String url = "jdbc:mysql://localhost:3306/"+dbName+"?user="+userName+
            "&password="+userPswd+ "&useUnicode=true&characterEncoding=UTF-8";
        idbConnection = new DBConnection(url,null,null,Consts.dbType);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            if(idbConnection.getConnection() == null)
                return "获取数据库连接失败";
        } catch (ClassNotFoundException e) {
            return "导入驱动包失败,未找到趣动包";
        } catch (SQLException e) {
            return "数据库连接错误";
        }
        return "连接数据库成功";
    }
    public static String closeConnection(){
        try {
            idbConnection.close();
        } catch (SQLException e) {
           return "关闭数据库连接出错";
        }
        return "数据库连接关闭完成";
    }
}
