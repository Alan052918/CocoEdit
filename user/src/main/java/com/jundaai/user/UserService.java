package com.jundaai.user;

import com.jundaai.user.exception.UserEmailConflictException;
import com.jundaai.user.exception.UserNameConflictException;
import com.jundaai.user.exception.UserPasswordCollidedException;
import com.jundaai.user.exception.WrongPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        log.info("Get all users.");
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        log.info("Get user by id: {}", userId);
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Transactional
    public User createUser(UserCreationRequest userCreationRequest) {
        log.info("Create new user: {}", userCreationRequest);

        String requestedName = userCreationRequest.name();
        boolean nameConflicted = userRepository.existsByName(requestedName);
        if (nameConflicted) {
            throw new UserNameConflictException(requestedName);
        }

        String requestedEmail = userCreationRequest.email();
        boolean emailConflicted = userRepository.existsByEmail(requestedEmail);
        if (emailConflicted) {
            throw new UserEmailConflictException(requestedEmail);
        }

        String requestedPassword = userCreationRequest.password();
        boolean passwordCollided = userRepository.existsByPassword(requestedPassword);
        if (passwordCollided) {
            throw new UserPasswordCollidedException(requestedPassword);
        }

        ZonedDateTime requestedDateTime = ZonedDateTime.now();
        User user = User.builder()
                .name(requestedName)
                .email(requestedEmail)
                .password(requestedPassword)
                .dateTimeCreated(requestedDateTime)
                .dateTimeUpdated(requestedDateTime)
                .build();

        return userRepository.save(user);
    }

    @Transactional
    public User updateUserById(Long userId, String newName, String newEmail) {
        log.info("Update user by id: {}, new name: {}, new email: {}", userId, newName, newEmail);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        boolean isUpdated = false;

        if (newName != null && newName.length() > 0 && !Objects.equals(newName, user.getName())) {
            boolean nameConflicted = userRepository.existsByName(newName);
            if (nameConflicted) {
                throw new UserNameConflictException(newName);
            }
            user.setName(newName);
            isUpdated = true;
        }

        if (newEmail != null && newEmail.length() > 0 && !Objects.equals(newName, user.getEmail())) {
            boolean emailConflicted = userRepository.existsByEmail(newEmail);
            if (emailConflicted) {
                throw new UserEmailConflictException(newEmail);
            }
            user.setEmail(newEmail);
            isUpdated = true;
        }

        if (isUpdated) {
            user.setDateTimeUpdated(ZonedDateTime.now());
        }

        return userRepository.save(user);
    }

    @Transactional
    public User resetPasswordByUserId(Long userId, String oldPassword, String newPassword) {
        log.info("Reset password by user id: {}, old password: {}, new password: {}", userId, oldPassword, newPassword);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (!Objects.equals(oldPassword, user.getPassword())) {
            throw new WrongPasswordException();
        }

        boolean passwordCollided = userRepository.existsByPassword(newPassword);
        if (passwordCollided) {
            throw new UserPasswordCollidedException(newPassword);
        }

        user.setPassword(newPassword);
        user.setDateTimeUpdated(ZonedDateTime.now());
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUserById(Long userId) {
        log.info("Delete user by id: {}", userId);
        boolean existsById = userRepository.existsById(userId);
        if (!existsById) {
            throw new UserNotFoundException(userId);
        }
        userRepository.deleteById(userId);
    }

}
