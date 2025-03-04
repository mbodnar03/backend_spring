package pl.wsb.fitnesstracker.user.api;

import java.util.List;

/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface UserService {

    User createUser(User user);
    List<UserShortDto> getAllUsersShort();
    User getUserById(Long id);
    void deleteUserById(Long id);
    User updateUser(Long id, UserDto dto);
    List<UserShortDto> searchByEmailFragment(String fragment);
    List<UserShortDto> findUsersOlderThan(int age);
    User partiallyUpdateUser(Long id, UserDto dto);
}
