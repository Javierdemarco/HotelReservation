package src.model;

import java.util.regex.Pattern;

public class Customer {

	private final String firstName;
	private final String lastName;
	private final String email;
	private final String emailRegex = "^(.+)@(.+).com";
	private final Pattern pattern = Pattern.compile(emailRegex);

	public Customer(String firstName, String lastName, String email) throws IllegalArgumentException {
		if (!pattern.matcher(email).matches()) {
			throw new IllegalArgumentException("Invalid Email format (name@domail.com)");
		}
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
	}

	/**
	 * @return the emailRegex
	 */
	public String getEmailRegex() {
		return emailRegex;
	}

	/**
	 * @param emailRegex the emailRegex to set
	 */
	public void setEmailRegex(String emailRegex) {
	}

	/**
	 * @return the pattern
	 */
	public Pattern getPattern() {
		return pattern;
	}

	/**
	 * @param pattern the pattern to set
	 */
	public void setPattern(Pattern pattern) {

	}

	@Override
	public String toString() {
		return "Customer: " + firstName + " " + lastName + " email: " + email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

}
