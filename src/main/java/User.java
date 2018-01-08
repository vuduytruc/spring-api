import java.sql.*;
import java.util.*;
import java.io.Serializable;

public class User implements Serializable {

    Integer id;
    String phone;
    String password = "";
    String name = null;
    String address = null;
    Double longitude = null;
    Double latitude = null;

    static Connection conn = null;
    static Statement stmt = null;

    public static void openConnect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_01", "root", "123");
            stmt = conn.createStatement();
        } catch (Exception e) {
            //
        }
    }

    public static void closeConnect() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            //
        }
    }

    User(String phone, String password, String name, String address) {
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.address = address;
    }
    User(Integer id, String phone, String name, String address) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.address = address;
    }
    User(Integer id, String phone, String name, String address, Double longitude, Double latitude) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public static List<User> getAll() {
        List<User> users = new ArrayList<User>();

        try {
            User.openConnect();

            String sql = "SELECT * FROM user";

            ResultSet rs = User.stmt.executeQuery(sql);

            while (rs.next()) {
                Integer rs_id = rs.getInt("id");
                String rs_phone = rs.getString("phone");
                String rs_name = rs.getString("name");
                String rs_address = rs.getString("address");
                Double rs_longitude = rs.getDouble("longitude");
                Double rs_latitude = rs.getDouble("latitude");
                users.add(new User(rs_id, rs_phone, rs_name, rs_address, rs_longitude, rs_latitude));
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            User.closeConnect();
        }

        return users;
    }

    public static User findByPhone(String phone) {
        User user = null;

        try {
            User.openConnect();

            String sql = "SELECT * FROM user WHERE phone = " + phone;

            ResultSet rs = User.stmt.executeQuery(sql);

            if (rs.next()) {
                Integer rs_id = rs.getInt("id");
                String rs_phone = rs.getString("phone");
                String rs_name = rs.getString("name");
                String rs_address = rs.getString("address");
                Double rs_longitude = rs.getDouble("longitude");
                Double rs_latitude = rs.getDouble("latitude");
                user = new User(rs_id, rs_phone, rs_name, rs_address, rs_longitude, rs_latitude);
            } else {
                //TODO handle can't find
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            User.closeConnect();
        }

        return user;
    }

    public static User login(String phone, String password) {
        User user = null;

        try {
            User.openConnect();

            String sql = "SELECT * FROM user WHERE phone = " + phone + " AND password = " + password;
            ResultSet rs = User.stmt.executeQuery(sql);

            if (rs.next()) {
                Integer rs_id = rs.getInt("id");
                String rs_phone = rs.getString("phone");
                String rs_name = rs.getString("name");
                String rs_address = rs.getString("address");
                Double rs_longitude = rs.getDouble("longitude");
                Double rs_latitude = rs.getDouble("latitude");
                user = new User(rs_id, rs_phone, rs_name, rs_address, rs_longitude, rs_latitude);
            } else {
                //TODO handle can't find
            }

        } catch (Exception e) {
            //
        } finally {
            User.closeConnect();
        }

        return user;
    }

    public static Boolean changePassword(String phone, String oldpassword, String newPassword) {
        User user = User.login(phone, oldpassword);
        if (user == null) {
            return false;
        } else {
            Integer rs = 0;

            try {

                User.openConnect();

                String sql = "UPDATE user SET password=" + newPassword + " + WHERE phone=" + phone;

                rs = User.stmt.executeUpdate(sql);

            } catch (Exception e) {
                System.out.println(e);
            } finally {
                User.closeConnect();
            }

            return rs == 1 ? true : false;
        }
    }

    String toJson(Object obj) {
        com.google.gson.Gson gson = new com.google.gson.Gson();
        return gson.toJson(obj);
    }

}
