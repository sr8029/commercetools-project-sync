package com.commercetools.project.sync.service;

import com.commercetools.project.sync.model.LastSyncCustomObject;
import io.sphere.sdk.customobjects.CustomObject;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface CustomObjectService {
  @Nonnull
  CompletionStage<ZonedDateTime> getCurrentCtpTimestamp(
      @Nonnull final String runnerName, @Nonnull final String methodName);

  @Nonnull
  CompletionStage<Optional<CustomObject<LastSyncCustomObject>>> getLastSyncCustomObject(
      @Nonnull final String sourceProjectKey,
      @Nonnull final String syncModuleName,
      @Nonnull final String runnerName);

  @Nonnull
  CompletionStage<CustomObject<LastSyncCustomObject>> createLastSyncCustomObject(
      @Nonnull final String sourceProjectKey,
      @Nonnull final String syncModuleName,
      @Nonnull final String runnerName,
      @Nonnull final LastSyncCustomObject lastSyncCustomObject);

  @Nonnull
  CustomObjectService attachRunnerName(@Nullable final String runnerName);

  @Nonnull
  CustomObjectService attachMethodName(@Nullable final String methodName);
}
