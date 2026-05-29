package li.cil.tis3d.common.item;

import li.cil.tis3d.util.TooltipUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;
import java.util.List;

public class ModBlockItem extends BlockItem {
    public ModBlockItem(final Block block, final Properties properties) {
        super(block, properties);
    }

    public ModBlockItem(final Block block) {
        this(block, createProperties());
    }

    // --------------------------------------------------------------------- //

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        TooltipUtils.tryAddDescription(stack, tooltipComponents);
    }

    // --------------------------------------------------------------------- //

    protected static Properties createProperties() {
        return new Properties();
    }
}
