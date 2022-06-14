package com.delivery;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

@WebListener
public class JdbcDriverShutdown implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            var driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        AbandonedConnectionCleanupThread.checkedShutdown();
    }

}
