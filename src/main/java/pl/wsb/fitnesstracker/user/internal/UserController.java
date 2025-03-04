package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.UserShortDto;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/short")
    public List<UserShortDto> listUsers() {
        return userService.getAllUsersShort();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userMapper.toDto(userService.getUserById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody UserDto userDto) {
        var user = userMapper.toEntity(userDto);
        var savedUser = userService.createUser(user);
        return userMapper.toDto(savedUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(
            @PathVariable Long id,
            @RequestBody pl.wsb.fitnesstracker.user.api.UserDto userDto
    ) {
        var updated = userService.updateUser(id, userDto);
        return userMapper.toDto(updated);
    }


    @GetMapping("/search")
    public List<UserShortDto> searchByEmail(@RequestParam String email) {
        return userService.searchByEmailFragment(email);
    }

    @GetMapping("/older")
    public List<UserShortDto> findOlderThan(@RequestParam int age) {
        return userService.findUsersOlderThan(age);
    }

    @PatchMapping("/{id}")
    public UserDto partiallyUpdateUser(
            @PathVariable Long id,
            @RequestBody pl.wsb.fitnesstracker.user.api.UserDto updates
    ) {
        var updated = userService.partiallyUpdateUser(id, updates);
        return userMapper.toDto(updated);
    }
}