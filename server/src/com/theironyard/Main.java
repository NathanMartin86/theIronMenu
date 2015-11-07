package com.theironyard;
import jodd.json.JsonSerializer;
import spark.Spark;
import java.sql.*;
import java.util.ArrayList;

public class Main {
    public static void createTable(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS menu (id IDENTITY, name VARCHAR, type VARCHAR, breakfast BOOLEAN, lunch BOOLEAN, dinner BOOLEAN, price DECIMAL, vegetarian BOOLEAN, glutenFree BOOLEAN, priceRange int)");
    }

    public static void insertMenuItem(Connection conn, int id, String name, String type, boolean breakfast, boolean lunch, boolean dinner, double price, boolean vegetarian, boolean glutenFree, int priceRange) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO menu VALUES (NULL,?,?,?,?,?,?,?,?,?)");
        stmt.setString(1, name);
        stmt.setString(2, type);
        stmt.setBoolean(3, breakfast);
        stmt.setBoolean(4, lunch);
        stmt.setBoolean(5, dinner);
        stmt.setDouble(6, price);
        stmt.setBoolean(7, vegetarian);
        stmt.setBoolean(8, glutenFree);
        stmt.setInt(9, priceRange);
        stmt.execute();
    }

    public static MenuItem selectMenuItem(Connection conn, int id) throws SQLException {
        MenuItem item = null;
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM menu WHERE id = ?");
        stmt.setInt(1, id);
        ResultSet results = stmt.executeQuery();
        if (results.next()) {
            item = new MenuItem();
            item.id = results.getInt("id");
            item.name = results.getString("name");
            item.type = results.getString("type");
            item.breakfast = results.getBoolean("breakfast");
            item.lunch = results.getBoolean("lunch");
            item.dinner = results.getBoolean("dinner");
            item.price = results.getDouble("price");
            item.vegetarian = results.getBoolean("vegetarian");
            item.glutenFree = results.getBoolean("glutenFree");
            item.priceRange = results.getInt("priceRange");
        }
        return item;
    }

    public static ArrayList<MenuItem> selectMenu(Connection conn) throws SQLException {
        ArrayList<MenuItem> items = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("Select * FROM menu");
        ResultSet results = stmt.executeQuery();
        while (results.next()) {
            MenuItem item = new MenuItem();
            item.id = results.getInt("id");
            item.name = results.getString("name");
            item.type = results.getString("type");
            item.breakfast = results.getBoolean("breakfast");
            item.lunch = results.getBoolean("lunch");
            item.dinner = results.getBoolean("dinner");
            item.price = results.getDouble("price");
            item.vegetarian = results.getBoolean("vegetarian");
            item.glutenFree = results.getBoolean("glutenFree");
            item.priceRange = results.getInt("priceRange");
            items.add(item);
        }
        return items;
    }

    public static ArrayList<MenuItem> typeFilter(Connection conn, String type) throws SQLException {
        ArrayList<MenuItem> types = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM menu WHERE type = ?");
        stmt.setString(1, type);
        ResultSet results = stmt.executeQuery();
        while (results.next()) {
            MenuItem item = new MenuItem();
            item.id = results.getInt("id");
            item.name = results.getString("name");
            item.type = results.getString("type"); // filter finished
            item.breakfast = results.getBoolean("breakfast"); // needs boolean filter
            item.lunch = results.getBoolean("lunch");// needs boolean filter
            item.dinner = results.getBoolean("dinner");// needs boolean filter
            item.price = results.getDouble("price");
            item.vegetarian = results.getBoolean("vegetarian");// needs boolean filter
            item.glutenFree = results.getBoolean("glutenFree");// needs boolean filter
            item.priceRange = results.getInt("priceRange"); // needs int filter....?
            types.add(item);
        }
        return types;
    }

    static void editItem(Connection conn, int id, String name, String type, boolean breakfast, boolean lunch, boolean dinner, double price, boolean vegetarian, boolean glutenFree, int priceRange) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE menu SET name =?, type=?, breakfast = ?,lunch = ?, dinner = ? price =?, vegetarian = ? gultenFree =?, priceRange = ? WHERE id =? ");
        stmt.setInt(1,id);
        stmt.setString(2, name);
        stmt.setString(3, type);
        stmt.setBoolean(4, breakfast);
        stmt.setBoolean(5, lunch);
        stmt.setBoolean(6, dinner);
        stmt.setDouble(7, price);
        stmt.setBoolean(8, vegetarian);
        stmt.setBoolean(9, glutenFree);
        stmt.setInt(10, priceRange);
        stmt.execute();
    }

    static void deleteItem(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM menu WHERE id =?");
        stmt.setInt(1, id);
        stmt.execute();
    }

    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        createTable(conn);

        Spark.externalStaticFileLocation("client");
        Spark.init();

        if (selectMenu(conn).size() == 0) {
            Main.insertMenuItem(conn, 1, "Steak", "entree", true, true, true, 25.00, false, false, 2);
            Main.insertMenuItem(conn, 2, "Salad", "app", false, true, true, 10.00, true, true, 1);
            Main.insertMenuItem(conn, 3, "Beer", "drink", true, true, true, 7.00, true, false, 1);
            Main.insertMenuItem(conn, 4, "BLT", "entree", false, true, true, 12.50, false, false, 1);
            Main.insertMenuItem(conn, 5, "Full Staxx Pancakes", "entree",true,true,true,100.00, true,true,3);
            Main.insertMenuItem(conn, 6, "Soup", "app",false,true,true,12.00, true, true,1);
            Main.insertMenuItem(conn, 7, "Nachos", "app",false,true,true,20.00, true,false,2);
            Main.insertMenuItem(conn, 8, "Chicken Wings", "entree",false,true,true, 15.00, true,true,3);
            Main.insertMenuItem(conn, 9, "Cheesecake", "dessert",true,true,true,10.00, true,false,1);
            Main.insertMenuItem(conn, 10, "Tofu Stir Fry", "entree",false,true,true,22.00, true,true,2);
            Main.insertMenuItem(conn, 11, "Eggs Benedict", "entree",true,false,false,13.50, true,false,2);
            Main.insertMenuItem(conn, 12, "Chocolate Lava Cake", "dessert",false,true,true,9.00, true,true,3);
            Main.insertMenuItem(conn, 13, "Fruit Cup", "app",false,true,true,4.00, true,true,1);
            Main.insertMenuItem(conn, 5, "Breakfast Burrito", "entree",true,false,false,12.00, true,true,2);
            Main.insertMenuItem(conn, 5, "Mimosa", "drink",true,false,false,100.00, true,true,3);



        }
        //creating routes for Ajax
        Spark.get(
                "/menu",
                ((request, response) -> {
                    String type = request.queryParams("type");
                    ArrayList<MenuItem> items;

                    if (type == null) {
                        items = selectMenu(conn);
                    } else {
                        items = typeFilter(conn, type);
                    }
                    JsonSerializer serializer = new JsonSerializer();
                    String json = serializer.serialize(items);
                    return json;
                })
        );

        Spark.post(
                "/add-item",
                ((request, response) -> {
                    int id = Integer.valueOf(request.queryParams("id"));
                    String name = request.queryParams("name");
                    String type = request.queryParams("type");
                    Boolean isBreakfast = Boolean.valueOf(request.queryParams("breakfast"));
                    boolean isLunch = Boolean.valueOf(request.queryParams("lunch"));
                    boolean isDinner = Boolean.valueOf(request.queryParams("dinner"));
                    double price = Double.valueOf(request.queryParams("price"));
                    boolean isVegetarian = Boolean.valueOf(request.queryParams("vegetarian"));
                    boolean isGlutenFree = Boolean.valueOf(request.queryParams("glutenFree"));
                    int priceRange = Integer.valueOf(request.queryParams("priceRange"));
                    if (name == null || type == null) {
                        Spark.halt(403);
                    }
                    insertMenuItem(conn, id, name, type, isBreakfast, isLunch, isDinner, price, isVegetarian, isGlutenFree, priceRange);
                    return "";
                })
        );

        Spark.post(
                "/edit-item",
                ((request, response) -> {
                    int id = Integer.valueOf(request.queryParams("id"));
                    String name = request.queryParams("name");
                    String type = request.queryParams("type");
                    Boolean isBreakfast = Boolean.valueOf(request.queryParams("breakfast"));
                    boolean isLunch = Boolean.valueOf(request.queryParams("lunch"));
                    boolean isDinner = Boolean.valueOf(request.queryParams("dinner"));
                    double price = Double.valueOf(request.queryParams("price"));
                    boolean isVegetarian = Boolean.valueOf(request.queryParams("vegetarian"));
                    boolean isGlutenFree = Boolean.valueOf(request.queryParams("glutenFree"));
                    int priceRange = Integer.valueOf(request.queryParams("priceRange"));
                    try {
                        int idNum = Integer.valueOf(id);
                        editItem(conn, idNum, name, type, isBreakfast, isLunch, isDinner, price, isVegetarian, isGlutenFree, priceRange);
                    } catch (Exception e) {
                    }

                    return "";
                })
        );

        Spark.post(
                "/delete-item",
                ((request, response) -> {
                    String id = request.queryParams("id");
                    try {
                        int idNum = Integer.valueOf(id);
                        deleteItem(conn, idNum);
                    } catch (Exception e) {

                    }
                    return "";
                })
        );

    }
}
