package lab9;

import java.text.CollationKey;
import java.text.Collator;
import java.util.Locale;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class lab9 {

	public static void main(String[] args) {
		UsersGroup firstGroup=new UsersGroup("1");
		firstGroup.addUser(new User("username1","password1","a","b",21));
		firstGroup.addUser(new User("username3","password3","b","c",23));
		firstGroup.addUser(new User("username2","password2","c","a",21));
		System.out.println("Natural order: ");
		System.out.println(firstGroup);
		
		System.out.println("Sort By Last Name: ");
		firstGroup.sortByLastName();
		System.out.println(firstGroup);
		
		System.out.println("Sort By First Name: ");
		firstGroup.sortByFirstName();
		System.out.println(firstGroup);
		
		System.out.println("Sort By Username Password: ");
		firstGroup.sortByUP();
		System.out.println(firstGroup);
		
	}

}

class Person{
	String fName;
	String lName;
	int age;
	private static final Collator collator=Collator.getInstance(Locale.getDefault());
	private final CollationKey sortLastNameKey;
	private final CollationKey sortFirstNameKey;
	
	Person(String f,String l,int a){
		fName=f;
		lName=l;
		age=a;
		sortLastNameKey=collator.getCollationKey(lName.toUpperCase()+fName.toUpperCase());
		sortFirstNameKey=collator.getCollationKey(fName.toUpperCase()+lName.toUpperCase());
	}
	
	public void setFirstName(String s) {fName=s;}
	public void setLastName(String s) {lName=s;}
	public void setAge(int i) {age=i;}
	
	public CollationKey getFirstNameKey() {return sortFirstNameKey;}
	public CollationKey getLastNameKey() {return sortLastNameKey;}
	public int getAge() {return age;}
	
	public int compareTo(Object o) {
		Person per=(Person)o;
		if(this.age<per.getAge()) {return -1;}
		else if(this.age==per.getAge()) {return 0;}
		else {return 1;}
	}
	@Override
	public String toString() {return ""+fName+" "+lName+" "+age;}
	
}

class LastNameComparator implements Comparator{
	public int compare(Object p1, Object p2) {
		return (((Person) p1).getLastNameKey().compareTo(((Person) p2).getLastNameKey()));
	}

}

class FirstNameComparator implements Comparator{
	public int compare(Object p1, Object p2) {
		return (((Person) p1).getFirstNameKey().compareTo(((Person) p2).getFirstNameKey()));
	}
}

class User extends Person implements Comparable{
	String uName;
	String pass;
	
	User(String u,String p, String fn, String ln, int i){super(fn, ln, i);uName=u;pass=p;}
	
	public String getUserName() {return uName;}
	public String getPassword() {return pass;}
	
	@Override
	public boolean equals(Object o) {
		User u=(User)o;
		String sU1=this.getUserName()+this.getPassword()+this.getAge();
		String sU2=u.getUserName()+u.getPassword()+u.getAge();
		if(sU1==sU2) {return true;}
		else {return false;}
	}
	@Override
	public String toString() {return ""+uName+" "+fName+" "+lName+" "+age;}
	
	@Override
	public int compareTo(Object o) {
		User u=(User)o;
		String sU1=this.getUserName()+this.getPassword()+this.getAge();
		String sU2=u.getUserName()+u.getPassword()+u.getAge();
		int res=sU1.compareTo(sU2);
		return res;
	}
	
}

class UsersGroup{
	String gName;
	List<User> lu=new ArrayList<>();
	
	UsersGroup(String gn){gName="";}
	
	public void addUser(User u) {lu.add(u);}
	
	public void sortByLastName() {
		LastNameComparator lastName = new LastNameComparator();
		Collections.sort(this.lu,lastName);
	}
	
	public void sortByFirstName() {
		FirstNameComparator firstName = new FirstNameComparator();
		Collections.sort(this.lu,firstName);
	}
	
	public void sortByUP() {
		Comparator<User> userComparator = new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return	u1.compareTo(u2);
            }
		};
		Collections.sort(this.lu, userComparator);
	}
	
	@Override
	public String toString() {
		String sl="";
		for (Object o : lu){sl += o + "\n";}
		return sl;
	}
	
}

