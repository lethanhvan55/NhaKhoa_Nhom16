//package connectDB;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class ConnectDB {
//
//    private static Connection con = null;
//
//    private static final String URL =
//            "jdbc:sqlserver://localhost:1433;"
//            + "databaseName=QuanLyQuanCaPhe;"
//            + "encrypt=false;"
//            + "trustServerCertificate=true";
//
//    private static final String USER = "sa";
//    private static final String PASSWORD = "280405";
//
//    public static Connection getConnection() {
//        try {
//            if (con == null || con.isClosed()) {
//                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//                con = DriverManager.getConnection(URL, USER, PASSWORD);
//                System.out.println("[DB] Kết nối SQL Server thành công.");
//            }
//        } catch (ClassNotFoundException e) {
//            System.err.println("[DB] Không tìm thấy JDBC Driver: " + e.getMessage());
//        } catch (SQLException e) {
//            System.err.println("[DB] Lỗi kết nối: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        return con;
//    }
//
//    public static void disconnect() {
//        try {
//            if (con != null && !con.isClosed()) {
//                con.close();
//                System.out.println("[DB] Đã đóng kết nối SQL Server.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}

package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {

    public static Connection con = null;
    public static ConnectDB instance = new ConnectDB();

    public static ConnectDB getInstance() {
        return instance;
    }

    public static Connection getConnection() {
        try {
            if (con == null || con.isClosed()) {
                getInstance().connect();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public void connect() throws SQLException {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=QL_NK;encrypt=false";
        String user = "sa";
        String password = "123";

        if (con == null || con.isClosed()) {
            con = DriverManager.getConnection(url, user, password);
            System.out.println("[DB] Kết nối SQL Server thành công.");
        }
    }

    public void disconnect() {
        if (con != null) {
            try {
                if (!con.isClosed()) {
                    con.close();
                    System.out.println("[DB] Đã đóng kết nối SQL Server.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}