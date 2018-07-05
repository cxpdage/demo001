package cn.activiti.bean;

public class Person implements java.io.Serializable{

	private static final long serialVersionUID = -1387204093875501449L;
	
	private int id;
	private String name;
	
	public Person() {
		super();
	}
	public Person(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
