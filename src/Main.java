import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    static String website = "connectionStringHere";
    static String username = "usernameHere";
    static String password = "passwordHere";


    public static void main(String[] args) throws SQLException {
        try (Connection conn = DriverManager.getConnection(website,username,password))
        {
            viewDB(conn);
//            Movie m = submitMovie();
//            addMovie(conn, m);
        }
    }

    public static void viewDB(Connection conn) throws SQLException {
        String sql = "select * from movies";
        var stmt = conn.prepareStatement(sql);
        var rs = stmt.executeQuery();
        float total = 0.00f;
        while(rs.next()){
            System.out.println(rs.getString("title") +
                    " " + rs.getInt("year") +
                    " " + "£" + rs.getFloat("price"));
            float price = rs.getFloat("price");
            total = price + total;
        }
        System.out.print("Total = £");
        System.out.printf("%.2f",total);
    }

    private static void addMovie(Connection conn, Movie m) throws SQLException{
        String sql = "insert into movies (title,year,price) values (?,?,?)";
        var stmt = conn.prepareStatement(sql);
        stmt.setObject(1,m.movieTitle);
        stmt.setObject(2, m.movieYear);
        stmt.setObject(3, m.moviePrice);
        int result = stmt.executeUpdate();
        if(result>0){
            System.out.println("Update Successful!");
        }
        else {
            System.out.println("Update failed!");
        }
    }

    private static Movie submitMovie(){
        Scanner reader = new Scanner(System.in);
        Movie movie = new Movie();
        System.out.print("Enter movie title: ");
        String movietitle = reader.nextLine();
        movie.movieTitle = movietitle;
        return movie;

    }


}
