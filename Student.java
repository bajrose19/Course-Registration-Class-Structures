public class Student extends User implements StudentInterface {
	
	private ArrayList<Course> registeredCourses;
	
	
	public Student(String firstName, String lastName, String username, String password) {
		
		super(firstName, lastName, username, password);
        this.registeredCourses = new ArrayList<>();
		
	}
	
	public ArrayList<Course> viewRegisteredCourses() {
		return registeredCourses;

	}
	
	public ArrayList<Course> viewAvaliableCourses(ArrayList<Course> courses) {
		 ArrayList<Course> available = new ArrayList<>();

	        for (Course c : courses) {
	            if (!c.isFull()) {
	                available.add(c);
	            }
	        }

	        return available; 
		
	}
	
	@Override
	public boolean courseRegistration(Course courses) {
		if (!courses.isFull() && !registeredCourses.contains(courses)) {
			if (courses.addStudent(this)) {
				registeredCourses.add(courses);
				return true;
	            }
	        }

	        return false;
	    }
	
	@Override
	public boolean courseWithdrawal(Course courses) {
		 String fullName = getFirstName() + " " + getLastName();

	        if (registeredCourses.contains(courses)) {
	            if (courses.removeStudent(fullName)) {
	                registeredCourses.remove(courses);
	                return true;
	            }
	        }

	        return false;
		
	}
	
	public ArrayList<Course> viewAllCourses(ArrayList<Course> courses) {
		ArrayList<Course> available = new ArrayList<>();

        for (Course c : courses) {
            if (!c.isFull()) {
                available.add(c);
            }
        }

        return available;
		
	}
	 @Override
	    public String toString() {
	        return getFirstName() + " " + getLastName() +
	               " (" + getUsername() + ")";
	    }
		
}
	


