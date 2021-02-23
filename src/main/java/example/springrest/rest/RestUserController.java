package example.springrest.rest;

import example.springrest.model.Role;
import example.springrest.model.User;
import example.springrest.service.RoleService;
import example.springrest.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class RestUserController {

    private final UserService userService;
    private final RoleService roleService;

    public RestUserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/api/users")
    public List<UserDTO> getAllUsers() {
        return userService.listUsers().stream().map(UserDTO::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/api/users/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return UserDTO.toDTO(userService.getUserById(id));
    }

    @PostMapping("/api/users")
    public UserDTO add(@RequestBody UserDTO newUserDTO) {
        Set<Role> roleSet = roleService.listRole().stream().filter(role -> newUserDTO.getRoles()
                .contains(role.getRole().replace("ROLE_", "")))
                .collect(Collectors.toSet());
        User newUser = UserDTO.fromDTO(newUserDTO, roleSet);
        userService.addUser(newUser);

        return UserDTO.toDTO(userService.getUserByLogin(newUser.getEmail()));
    }

    @PutMapping("/api/users/{id}")
    public UserDTO editUser(@RequestBody UserDTO editUserDTO, @PathVariable Long id) {
        Set<Role> roles = roleService.listRole().stream().filter(role -> editUserDTO.getRoles()
                .contains(role.getRole().replace("ROLE_", "")))
                .collect(Collectors.toSet());
        User user = UserDTO.fromDTO(editUserDTO, roles);
        userService.updateUser(id, user);

        return UserDTO.toDTO(userService.getUserById(id));
    }

    @DeleteMapping("/api/users/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}
