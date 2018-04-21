import java.sql.*;
import java.util.ArrayList;

/**
 * Creates the Connect Class to allow connections to be passed through methods.
 * Utilizes DriverManager Configuration due to low volume and application needs.
 * @author Fred Astaire
 *
 */
class Connect {
	public Connection createConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection(
					"jdbc:mysql://localhost/customers?verifyServerCertificate=false&useSSL=true", "scott", "tiger");
			return connect;
		} catch (Exception e) {
			return null;
		}
	}
}

/**
 * Main Application Class to Create a new database with tables, 
 * and then insert records, select records, create new objects from 
 * database records, and delete records. Also allows a full printing
 * of the table. 
 * @author Fred Astaire
 *
 */
public class DatabaseMain {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {

		Connect c = new Connect();
		Connection con = c.createConnection();

		// Create a Statement
		Statement statement = con.createStatement();

		// Create a table in a new database which can be commented out later.
		statement.executeUpdate("drop database if exists customers");
		statement.executeUpdate("create database customers");
		statement.executeUpdate("use customers");
		statement.executeUpdate("drop table if exists Person");
		statement.executeUpdate(
				"create table Person(id MEDIUMINT NOT NULL AUTO_INCREMENT, firstName varchar(50) NOT NULL,"
						+ "lastName varchar(50) NOT NULL, " + " age char(3) NOT NULL," + " ssn char(9) NOT NULL,"
						+ " creditCard char(16) NOT NULL," + " PRIMARY KEY (id))");

		statement.executeUpdate("insert into Person (firstName, lastName, age, ssn, creditCard)"
				+ "values ('Bryan', 'Adams', '69', '123456778', '1111555566667777')");
		statement.executeUpdate("insert into Person (firstName, lastName, age, ssn, creditCard)"
				+ "values ('Wanda', 'Lost', '89', '987654321', '1111667788990011')");
		statement.executeUpdate("insert into Person (firstName, lastName, age, ssn, creditCard)"
				+ "values ('Rick', 'Roll', '42', '888228888', '1234567812345678')");
		statement.executeUpdate("insert into Person (firstName, lastName, age, ssn, creditCard)"
				+ "values ('Leroy', 'Jenkins', '28', '555119999', '1000000000000001')");
		statement.executeUpdate("insert into Person (firstName, lastName, age, ssn, creditCard)"
				+ "values ('Family', 'Guy', '100', '119191199', '2525252525252525')");

		// Create multiple records to be inserted using Person Constructor Override Method
		Person matt = new Person("Matt", "Davis", 32, 222334444L, 1234123412341234L);
		Person james = new Person("James", "Brown", 68, 111229999L, 9999888877776666L);
		Person angela = new Person("Angela", "Dean", 28, 123456789L, 1111555544446666L);
		Person jimmy = new Person("James", "Dean", 78, 987654321L, 3333222255557777L);

		// Insert Person Objects into the Person Table
		insertPerson(matt, con);
		insertPerson(james, con);
		insertPerson(angela, con);
		insertPerson(jimmy, con);

		// Print query end delimiter
		System.out.println("\n========================TASK 3===================================\n");

		// Execute a statement
		ResultSet resultSet = statement.executeQuery("select * from Person");

		// Iterate through the statement and print results
		// Note: Order will be by Auto-Incrementing ID due to Primary Key

		while (resultSet.next()) {
			System.out.println(resultSet.getString(2) + "\t" + resultSet.getString(3) + "\t" + resultSet.getInt(4)
					+ "\t" + resultSet.getLong(5) + "\t" + resultSet.getLong(6));
		}

		// Print query end delimiter
		System.out.println("\n========================TASK 5===================================\n");

		// Search for all Persons with the name "Rick"
		resultSet = statement.executeQuery("select * from Person where firstName = 'Rick'");

		// Iterate through the statement and print results
		// Note: Order will be by Auto-Incrementing ID due to Primary Key

		while (resultSet.next()) {
			System.out.println(resultSet.getString(2) + "\t" + resultSet.getString(3) + "\t" + resultSet.getInt(4)
					+ "\t" + resultSet.getLong(5) + "\t" + resultSet.getLong(6));
		}

		// Print query end delimiter
		System.out.println("\n========================TASK 6===================================\n");

		selectPerson("James", con);

		// Print query end delimiter
		System.out.println("\n========================TASK 7===================================\n");

		for (Person person : findAllPeople(con)) {
			System.out.println(person.toString());
		}

		// Print query end delimiter
		System.out.println("\n========================TASK 8===================================\n");

		// Search for the person and return if they do not exist in the database
		System.out.println(deletePerson("John", "Smith", con));

		// Search for the person and delete if they exist in the database
		System.out.println(deletePerson("James", "Dean", con));

		// Show new revised database table
		for (Person person : findAllPeople(con)) {
			System.out.println(person.toString());
		}

		// Close the connections
		resultSet.close();
		statement.close();
		con.close();
	}

	/**
	 * Inserts a person into the first empty row with a unique identity key.
	 * 
	 * @param person The Person object being called
	 * @param con The database connection being passed from the Main
	 * @throws SQLException
	 */
	public static void insertPerson(Person person, Connection con) throws SQLException {

		Statement stmt = null;
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			ResultSet mySet = stmt.executeQuery("SELECT * from Person");

			mySet.moveToInsertRow();
			mySet.updateString("firstName", person.getFirstName());
			mySet.updateString("lastName", person.getLastName());
			mySet.updateInt("age", person.getAge());
			mySet.updateLong("ssn", person.getSsn());
			mySet.updateLong("creditCard", person.getCreditCard());

			mySet.insertRow();
			mySet.beforeFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
	}

	/**
	 * Selects a row from the Person table in the database and creates
	 * a usable Person object in the application structure. It currently prints
	 * the toString method of the Person Object to confirm the Object is ready for use.
	 * 
	 * @param firstName is the data we wish to select from the database
	 * @param con The database connection being passed from the Main
	 * @throws SQLException
	 */
	public static void selectPerson(String firstName, Connection con) throws SQLException {

		Statement stmt = null;
		try {
			stmt = con.createStatement();
			ResultSet mySet = stmt.executeQuery("SELECT * from Person where firstName = '" + firstName + "';");
			while (mySet.next()) {
				Person person = new Person(mySet.getString(2), mySet.getString(3), mySet.getInt(4), mySet.getLong(5),
						mySet.getLong(6));
				System.out.println(person.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
	}

	/**
	 * Creates a usable Array List of Person Objects from the Person table in the 
	 * database. 
	 * 
	 * @param con The database connection being passed from the Main
	 * @return customerList the list of all Person Objects in the person table of the customer
	 * database. 
	 * @throws SQLException
	 */
	public static ArrayList<Person> findAllPeople(Connection con) throws SQLException {

		ArrayList<Person> customerList = new ArrayList<>();

		Statement stmt = null;

		try {
			stmt = con.createStatement();

			ResultSet mySet = stmt.executeQuery("SELECT * from Person");
			while (mySet.next()) {
				Person person = new Person(mySet.getString(2), mySet.getString(3), mySet.getInt(4), mySet.getLong(5),
						mySet.getLong(6));
				customerList.add(person);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
		return customerList;
	}

	/**
	 * Searches for a person on the person table of the database, and if the person
	 * exists, it returns confirmation that the person exists and deletes the record from the database.
	 * If it does not exist, it confirms that the person does not exist and ends the method.
	 * 
	 * @param firstName the First Name of the person to search for
	 * @param lastName the Last Name of the person to search for
	 * @param con The database connection being passed from the Main
	 * @return message returns the confirmation whether or not a person was found to be deleted.
	 * @throws SQLException
	 */
	public static String deletePerson(String firstName, String lastName, Connection con) throws SQLException {

		String message = "";
		Statement stmt = null;

		try {
			stmt = con.createStatement();

			ResultSet mySet = stmt.executeQuery(
					"SELECT * from Person where firstName = '" + firstName + "' and lastName = '" + lastName + "';");

			if (mySet.next()) {
				message = ("\n" + firstName + " " + lastName + " was found in the database and will be deleted.\n");
				stmt.executeUpdate(
						"DELETE from Person where firstName = '" + firstName + "' and lastName = '" + lastName + "';");
			} else {
				message = ("\n" + firstName + " " + lastName + " was not found in the database.\n");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}

		return message;
	}
}
