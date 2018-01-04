import java.sql.*;
import java.util.*;

public class User {

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
            System.out.println(e);
        } finally {
            User.closeConnect();
        }

        return user;
    }
    public int addUser(String phone, String name, String password, String address, String longitude, String latitude){

        int success = 0;
        try{
            User.openConnect();
            String sql = "Insert into user(name, phone, address, password, longitude, latitude) values (name, phone, address, password, longitude, latitude)";
            User.stmt.executeUpdate(sql);
            success = 1;
            System.out.println("them thanh cong!");
        } catch(Exception e){
            success = 0;
            System.out.println("them that bai!");
        } finally {
            User.closeConnect();
        }
        return success;
    }

    public int deleteUser(String phone){
        int success = 0;
        try{
            User.openConnect();
            String sql = "DELETE user where phone =" + phone;
            User.stmt.executeDelete(sql);
            success = 1;
            System.out.println("them thanh cong!");
        } catch(Exception e){
            success = 0;
            System.out.println("them that bai!");
        }
        return success;
    }

    public static User changePassword(String phone, String password, String newPass) {
        String success;

        try {

            User.openConnect();

            String sql = "UPDATE user WHERE phone = " + phone + " AND password = " + password;

            Integer rs = User.stmt.executeUpdate(sql);

            if (rs > 0) {
                success = "true";
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            User.closeConnect();
        }

        return user;
    }

    String toJson(Object obj) {
        com.google.gson.Gson gson = new com.google.gson.Gson();
        return gson.toJson(obj);
    }

}
