package Person;

public class PersonService {
    private static PersonDAO personDAO = new PersonDAO();
    public Person login(String username, String password) {
        Person person = personDAO.read(username);
        if (person != null && person.getPassword().equals(password)) {
            return person;
        }
        return null;
    }
}
