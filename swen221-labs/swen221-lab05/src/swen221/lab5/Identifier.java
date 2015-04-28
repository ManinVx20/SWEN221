package swen221.lab5;

/**
 * Identifies a row in the data. </p> You must modify this class!
 * 
 * @author ncameron
 *
 */
public class Identifier implements Comparable<Identifier> {

	private String name;

	private String dept;

	public Identifier(String name, String dept) {

		this.name = name;
		this.dept = dept;
	}

	public String getName() {

		return name;
	}

	public String getDept() {

		return dept;
	}

	// HINT: you need to implement an appropriate equals() and hashCode()
	// method!!
	@Override
	public int compareTo(Identifier o) {

		if (this.name.equals(o.getName()) && this.dept.equals(o.getDept())) {
			return 0;
		} else if (this.dept.equals(o.getDept())) {
			return this.name.compareTo(o.getName());
		} else {
			return (this.dept.compareTo(o.getDept()));
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Identifier && this.hashCode() == o.hashCode()) {
			return true;
		}
		return false;
	}
	
	/*
	 * This hashcode is generated from the lowercase hascode of the name plus the hascode of the department. It's consitent
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		return (this.name.toLowerCase().hashCode()+this.dept.hashCode());
	}
}
