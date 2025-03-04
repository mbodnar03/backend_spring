package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.user.api.*;
import pl.wsb.fitnesstracker.user.api.UserDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<UserShortDto> getAllUsersShort() {
        return userRepository.findAll().stream()
                .map(user -> new UserShortDto(user.getId(), user.getEmail()))
                .toList();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(Long id, UserDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setBirthdate(dto.birthdate());
        user.setEmail(dto.email());

        return userRepository.save(user);
    }

    @Override
    public List<UserShortDto> searchByEmailFragment(String fragment) {
        return userRepository.findByEmailContainingIgnoreCase(fragment).stream()
                .map(user -> new UserShortDto(user.getId(), user.getEmail()))
                .toList();
    }

    @Override
    public List<UserShortDto> findUsersOlderThan(int age) {
        LocalDate cutoffDate = LocalDate.now().minusYears(age);
        return userRepository.findUsersBornBefore(cutoffDate).stream()
                .map(user -> new UserShortDto(user.getId(), user.getEmail()))
                .toList();
    }

    @Override
    public User partiallyUpdateUser(Long id, UserDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (dto.firstName() != null) {
            user.setFirstName(dto.firstName());
        }
        if (dto.lastName() != null) {
            user.setLastName(dto.lastName());
        }
        if (dto.birthdate() != null) {
            user.setBirthdate(dto.birthdate());
        }
        if (dto.email() != null) {
            user.setEmail(dto.email());
        }

        return userRepository.save(user);
    }
}