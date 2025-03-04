package pl.wsb.fitnesstracker.user.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query searching users by email address. It matches by exact match.
     *
     * @param email email of the user to search
     * @return {@link Optional} containing found user or {@link Optional#empty()} if none matched
     */
    default Optional<User> findByEmail(String email) {
        return findAll().stream()
                .filter(user -> Objects.equals(user.getEmail(), email))
                .findFirst();
    }

    /**
     *
     * wyszukuje uzytkownika po email ignorujac wielkosc liter
     *
     * @param fragment
     * @return
     */
    List<User> findByEmailContainingIgnoreCase(String fragment);

    /**
     *
     * wyszukuje uzytkownikow urodzonch przed podana data
     *
     * @param beforeDate
     * @return
     */
    @Query("SELECT u FROM User u WHERE u.birthdate < :beforeDate")
    List<User> findUsersBornBefore(@Param("beforeDate") LocalDate beforeDate);

}
