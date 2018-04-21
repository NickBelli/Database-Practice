/**
 * The Person class is a test class to store Person Info
 * in a Customer Database. 
 * @author Fred Astaire
 *
 */
public class Person {

	/**
	 * Data Field: First Name
	 */
	private String firstName;
	
	/**
	 * Data Field: Last Name
	 */
	private String lastName;
	
	/**
	 * Data Field: Age
	 */
	private int age;
	
	/**
	 * Data Field: Social Security Number (Fake)
	 */
	private long ssn;
	
	/**
	 * Data Field: Credit Card Number (Fake)
	 */
	private long creditCard;
	
	/**
	 * Default Constructor
	 */
	public Person () {
		
	}
	
	/**
	 * Construct a Person with a first name, last name, age, 
	 * social security number (fake) and a credit card number (fake)
	 * @param firstName
	 * @param lastName
	 * @param age
	 * @param ssn
	 * @param creditCard
	 */
	public Person(String firstName, String lastName, int age, long ssn, long creditCard){
		setFirstName(firstName);
		setLastName(lastName);
		setAge(age);
		setSsn(ssn);
		setCreditCard(creditCard);
	}

	/**
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName set a new First Name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName set a new Last Name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age set a new Age
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return ssn
	 */
	public long getSsn() {
		return ssn;
	}

	/**
	 * @param ssn set a new Social Security Number
	 */
	public void setSsn(long ssn) {
		this.ssn = ssn;
	}

	/**
	 * @return creditCard
	 */
	public long getCreditCard() {
		return creditCard;
	}

	/**
	 * @param creditCard set a new Credit Card Number
	 */
	public void setCreditCard(long creditCard) {
		this.creditCard = creditCard;
	}
	
	/**
	 * @return message returns a formatted String of all Person Information
	 */
	@Override
	public String toString(){
		String message = this.getFirstName() + "\t" + this.getLastName() + "\t" + this.getAge() 
			+ "\t" + this.getSsn() + "\t" + this.getCreditCard();
		return message;
	}
}
