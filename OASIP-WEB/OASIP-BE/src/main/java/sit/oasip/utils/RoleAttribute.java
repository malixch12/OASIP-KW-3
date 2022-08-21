package sit.oasip.utils;


public class RoleAttribute  {
    String role;
    public String roleChoice(String newValue) {

        switch (newValue) {
            case "Student":
               role = Role.Student.name();
                break;
            case "Admin":
                role= Role.Admin.name();
                break;
            case "Lecturer":
                role= Role.Lecturer.name();
                break;
        } return role;
    }
}
