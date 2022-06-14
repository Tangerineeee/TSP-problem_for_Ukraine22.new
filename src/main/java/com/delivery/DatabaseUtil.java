package com.delivery;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DatabaseUtil {
    private static DataSource dataSource;
    private static final String JNDI_LOOKUP_SERVICE = "java:comp/env/jdbc/DeliveryService";

    private DatabaseUtil() {
        throw new IllegalStateException("Utility class");
    }

    static {
        try {
            Context context = new InitialContext();
            dataSource = ((DataSource) context.lookup(JNDI_LOOKUP_SERVICE));
        } catch (NamingException e) {
            System.err.println(e.getMessage());
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
