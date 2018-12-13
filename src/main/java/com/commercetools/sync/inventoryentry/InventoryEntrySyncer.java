package com.commercetools.sync.inventoryentry;

import static com.commercetools.sync.inventories.utils.InventoryReferenceReplacementUtils.replaceInventoriesReferenceIdsWithKeys;
import static com.commercetools.sync.utils.SphereClientUtils.CTP_TARGET_CLIENT;

import com.commercetools.sync.Syncer;
import com.commercetools.sync.inventories.InventorySync;
import com.commercetools.sync.inventories.InventorySyncOptions;
import com.commercetools.sync.inventories.InventorySyncOptionsBuilder;
import com.commercetools.sync.inventories.helpers.InventorySyncStatistics;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.inventory.InventoryEntryDraft;
import io.sphere.sdk.inventory.expansion.InventoryEntryExpansionModel;
import io.sphere.sdk.inventory.queries.InventoryEntryQuery;
import java.util.List;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InventoryEntrySyncer
    extends Syncer<
        InventoryEntry,
        InventoryEntryDraft,
        InventorySyncStatistics,
        InventorySyncOptions,
        InventoryEntryQuery,
        InventorySync> {

  private static final Logger LOGGER = LoggerFactory.getLogger(InventoryEntrySyncer.class);

  /** Instantiates a {@link Syncer} instance. */
  public InventoryEntrySyncer() {
    super(
        new InventorySync(
            InventorySyncOptionsBuilder.of(CTP_TARGET_CLIENT)
                .errorCallback(LOGGER::error)
                .warningCallback(LOGGER::warn)
                .build()),
        buildQuery());
  }

  /**
   * TODO: Should be added to the commercetools-sync library.
   *
   * @return an {@link InventoryEntryQuery} instance.
   */
  private static InventoryEntryQuery buildQuery() {
    return InventoryEntryQuery.of()
        .withExpansionPaths(InventoryEntryExpansionModel::supplyChannel)
        .plusExpansionPaths(ExpansionPath.of("custom.type"));
  }

  @Nonnull
  @Override
  protected List<InventoryEntryDraft> getDraftsFromPage(@Nonnull final List<InventoryEntry> page) {
    return replaceInventoriesReferenceIdsWithKeys(page);
  }
}
