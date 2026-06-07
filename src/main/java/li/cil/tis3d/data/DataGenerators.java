package li.cil.tis3d.data;

import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class DataGenerators {

    public static void gatherData(final GatherDataEvent event) {
        final var generator = event.getGenerator();
        final var output = generator.getPackOutput();
        final var lookupProvider = event.getLookupProvider();
        final var existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeServer(), new ModLootTableProvider(output, event.getLookupProvider()));
        final var blockTagProvider = new ModBlockTagsProvider(output, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagProvider);
        generator.addProvider(event.includeServer(), new ModItemTagsProvider(output, lookupProvider, blockTagProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModRecipesProvider(output, lookupProvider));

        generator.addProvider(event.includeClient(), new ModBlockStateProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModItemModelProvider(output, existingFileHelper));
    }
}
