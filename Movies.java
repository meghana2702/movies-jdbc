import java.util.*;
import java.io.*;
import java.sql.*;

public class Movies{
	
	public static void main(String args[]){
		
		try{
			
			//Register the Driver Class
			Class.forName("com.mysql.jdbc.Driver");
			
			
			//Establishing the Connection
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviesdb","root","Megha@2702");
			
			if(conn != null){
				System.out.println("Connection Established Successfully.....................!!!");
			}
			else{
				System.out.println("Connection Establishment Failed...Please try again....");
				throw new Exception();
			}
			
			
			//Create a Statement object
			Statement stmt = conn.createStatement();
			
			
			//Execute Queries
			Scanner sc = new Scanner(System.in);
			
			
			//Creation of a Movies Table
			StringBuilder q1 = new StringBuilder("IF NOT EXISTS (CREATE TABLE movies ");
				q1.append("(movie varchar(255) not NULL, ");
				q1.append(" actor VARCHAR(255), ");			   
				q1.append(" actress VARCHAR(255), "); 
				q1.append(" release_year DATE, ");
				q1.append(" director VARCHAR(255), ");			   
				q1.append(" PRIMARY KEY ( movie )))");
			
			int s = stmt.executeUpdate(q1.toString());
			
			if(s == 1){
				System.out.println("Table has been created Succesfully.....................!!!");
			}
			else {	
				System.out.println("Table has not been created...Please try again....");
				throw new Exception();
			}
			
			
			//Insert Movies into the table
			System.out.println("How many movies do you want to insert into the database?");
			int n = sc.nextInt();
			System.out.println("Enter details for all movies: (Movie Name, Name of Actor, Name of Actress, Release year, Director Name)");
			
			for(int i=0;i<n;i++){
				String name = sc.nextLine();
				String actor = sc.nextLine();
				String actress = sc.nextLine();
				String year = sc.nextLine();
				String director = sc.nextLine();
				
				String q2 = "insert into movies values('" +name+ "', '" +actor+ "', '" +actress+ "', '" +year+ "', '" +director+ "');";
				int x = stmt.executeUpdate(q2);
				
				if(x == 0){ 
					throw new Exception(); 
				}
			}
			
			
			//View Movies from the Table
			while(true){				
				System.out.print("Enter '1' to display all movies from table\nEnter '2' to give actor's name as input to select movies from table\nEnter '0' to exit\n");
				int choice = sc.nextInt();
			
				//Select all movies from the table
				if(choice == 1){
					String q3 = "select * from movies;";
					ResultSet rs = stmt.executeQuery(q3);
					while(rs.next()){
						System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getDate(4)+"\t"+rs.getString(5));
					}
				} 
				//Select only those movies which have actor match the given actor name
				else if(choice == 2){
					String actr = sc.nextLine();
					String q4 = "select * from movies where actor = '" +actr+ "';";
					ResultSet rs = stmt.executeQuery(q4);
					if(rs.next()){
						while(rs.next()){
							System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getDate(4)+"\t"+rs.getString(5));
						}
					}
					else{ 
						System.out.println("There are no movies with the mentioned actor."); 
					}
				}
				//Terminate the Program
				else if(choice == 0){
					break;
					//conn.close();
					//System.exit(0);
				}
				else{
					System.out.println("Please entar a valid option number..");
				}
			}
			
			
			//Close Connection
			conn.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}
