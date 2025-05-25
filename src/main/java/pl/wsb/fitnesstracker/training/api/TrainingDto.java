package pl.wsb.fitnesstracker.training.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import pl.wsb.fitnesstracker.training.internal.ActivityType;

import java.util.Date;

public record TrainingDto(@Nullable Long id, Long userId,
                          @JsonFormat(pattern = "yyyy-MM-dd") Date startTime,
                          @JsonFormat(pattern = "yyyy-MM-dd") Date endTime,
                          ActivityType activityType, double distance, double averageSpeed
) {}