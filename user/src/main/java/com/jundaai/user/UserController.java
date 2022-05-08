package com.jundaai.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/users")
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserModelAssembler userModelAssembler;

    @Autowired
    public UserController(UserService userService, UserModelAssembler userModelAssembler) {
        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<User>> getAllUsers() {
        log.info("Request to get all users.");
        List<User> users = userService.getAllUsers();
        return userModelAssembler.toCollectionModel(users);
    }

    @GetMapping(path = "{userId}")
    public EntityModel<User> getUserById(@PathVariable(name = "userId") Long userId) {
        log.info("Request to get user by id: {}", userId);
        User user = userService.getUserById(userId);
        return userModelAssembler.toModel(user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<User> createUser(@RequestBody UserCreationRequest userCreationRequest) {
        log.info("Request to create new user: {}", userCreationRequest);
        User user = userService.createUser(userCreationRequest);
        return userModelAssembler.toModel(user);
    }

    @PostMapping(path = "{userId}/update")
    public EntityModel<User> updateUserById(@PathVariable(name = "userId") Long userId,
                                            @RequestParam(name = "name", required = false) String newName,
                                            @RequestParam(name = "email", required = false) String newEmail) {
        log.info("Request to update user by id: {}, new name: {}, new email: {}", userId, newName, newEmail);
        User user = userService.updateUserById(userId, newName, newEmail);
        return userModelAssembler.toModel(user);
    }

    @PostMapping(path = "{userId}/password/reset")
    public EntityModel<User> resetPasswordByUserId(@PathVariable(name = "userId") Long userId,
                                                   @RequestParam(name = "oldPassword") String oldPassword,
                                                   @RequestParam(name = "newPassword") String newPassword) {
        log.info("Request to reset password by user id: {}, old password: {}, new password: {}", userId, oldPassword, newPassword);
        User user = userService.resetPasswordByUserId(userId, oldPassword, newPassword);
        return userModelAssembler.toModel(user);
    }

    @DeleteMapping(path = "{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteUserById(@PathVariable(name = "userId") Long userId) {
        log.info("Request to delete user by id: {}", userId);
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

}
