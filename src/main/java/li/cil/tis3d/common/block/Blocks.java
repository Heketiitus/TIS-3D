package li.cil.tis3d.common.block;

import li.cil.tis3d.util.RegistryUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class Blocks {
    private static final DeferredRegister<Block> BLOCKS = RegistryUtils.getDeferred(Registries.BLOCK);

    // --------------------------------------------------------------------- //

    public static final DeferredHolder<Block, CasingBlock> CASING = BLOCKS.register(
        "casing",
        () -> new CasingBlock(Properties.of()
            .mapColor(MapColor.METAL)
            .sound(SoundType.METAL)
            .strength(1.5f, 6f))
    );
    public static final DeferredHolder<Block, ControllerBlock> CONTROLLER = BLOCKS.register(
        "controller",
        () -> new ControllerBlock(Properties.of()
            .mapColor(MapColor.METAL)
            .sound(SoundType.METAL)
            .strength(1.5f, 6f))
    );

    // --------------------------------------------------------------------- //

    public static void initialize(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
