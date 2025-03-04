package pl.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;

/**
 *
 * maly dto zawierajacy tylko id i email
 *
 * @param id
 * @param email
 */
public record UserShortDto(
        @Nullable Long id,
        String email
) {}