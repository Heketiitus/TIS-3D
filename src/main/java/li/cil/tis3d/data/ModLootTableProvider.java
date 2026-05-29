package li.cil.tis3d.data;

import com.google.common.collect.Sets;
import li.cil.tis3d.api.API;
import li.cil.tis3d.common.block.Blocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(final PackOutput packOutput, final CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, Collections.emptySet(), Collections.emptyList(), registries);
    }

    @Override
    protected void validate(WritableRegistry<LootTable> writableRegistry, ValidationContext validationContext, ProblemReporter.Collector problemreporter$collector) {
        final Set<ResourceLocation> modLootTableIds =
            BuiltInLootTables
                .all()
                .stream()
                .map(ResourceKey::location)
                .filter(lootTable -> Objects.equals(lootTable.getNamespace(), API.MOD_ID))
                .collect(Collectors.toSet());

        for (final ResourceLocation id : Sets.difference(modLootTableIds, writableRegistry.keySet()))
            validationContext.reportProblem("Missing mod loot table: " + id);

        /*
        writableRegistry.forEach((table) ->
            table.validate(
                validationContext
                    .setParams(table.getParamSet())
                    .enterElement(
                        "{" + location + "}",
                        new LootDataId<>(LootDataType.TABLE, location)
                    )
            )
        );
         */
    }

    @Override
    public List<LootTableProvider.SubProviderEntry> getTables() {
        return Collections.singletonList(new SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK));
    }

    public static final class ModBlockLootTables extends BlockLootSubProvider {
        public ModBlockLootTables(HolderLookup.Provider registries) {
            super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags(), registries);
        }

        @Override
        protected void generate() {
            dropSelf(Blocks.CASING.get());
            dropSelf(Blocks.CONTROLLER.get());
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return StreamSupport.stream(super.getKnownBlocks().spliterator(), false)
                .filter(block -> {
                    final ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(block);
                    return Objects.equals(blockId.getNamespace(), API.MOD_ID);
                })
                .collect(Collectors.toSet());
        }
    }
}
