package example.springrest.rest;

import com.sun.istack.NotNull;
import example.springrest.model.Role;
import example.springrest.model.User;

import javax.persistence.Id;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDTO {

    @Id
    private Long id = -1L;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private int age;

    private List<String> roles;

    public UserDTO(Long id, String email, String password, String firstName, String lastName, int age, List<String> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public static UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getEmail(), "",
                user.getFirstName(),
                user.getLastName(),
                user.getAge(),
                user.getRoles().stream().map(role -> role.getRole().replace("ROLE_", "")).collect(Collectors.toList()));
    }

    public static User fromDTO(UserDTO userDTO, Set<Role> roles) {
        return new User(userDTO.getEmail(), userDTO.getPassword(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getAge(), roles);
    }
}
