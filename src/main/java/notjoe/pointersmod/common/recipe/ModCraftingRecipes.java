package notjoe.pointersmod.common.recipe;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import notjoe.pointersmod.common.Config;
import notjoe.pointersmod.common.block.ModBlocks;
import notjoe.pointersmod.common.item.ModItems;

public class ModCraftingRecipes {
    public static void addRecipes() {
        if (Config.addCraftingRecipes) {
            GameRegistry.addRecipe(
                new ShapedOreRecipe(new ItemStack(ModItems.component, 1, 0), " Q ", "QEQ", " Q ",
                    'Q', "gemQuartz", 'E', "pearlEnderEye"));

            if (Config.enablePointerInventory) {
                GameRegistry.addRecipe(
                    new ShapelessOreRecipe(new ItemStack(ModItems.pointer_inv, 1),
                        new ItemStack(ModItems.component, 1, 0), "chestEnder", "blockHopper"));
            }

            if (Config.enablePointerRedstone) {
                GameRegistry.addRecipe(
                    new ShapelessOreRecipe(new ItemStack(ModItems.pointer_redstone, 1),
                        new ItemStack(ModItems.component, 1, 0), "blockRedstone",
                        new ItemStack(Blocks.LEVER, 1, 0)));

                GameRegistry.addRecipe(
                    new ShapedOreRecipe(new ItemStack(ModBlocks.receiver_redstone, 1), " R ", " P ",
                        "SSS", 'R', "dustRedstone", 'P', "stickWood", 'S', "slabStone"));
            }

            if (Config.enablePointerTeleport) {
                GameRegistry.addRecipe(
                    new ShapelessOreRecipe(new ItemStack(ModItems.pointer_teleport, 1),
                        new ItemStack(ModItems.component, 1, 0),
                        new ItemStack(Items.CHORUS_FRUIT, 1), "enderpearl"));
            }

            if (Config.enablePointerWorld) {
                GameRegistry.addRecipe(
                    new ShapelessOreRecipe(new ItemStack(ModItems.pointer_world, 1),
                        new ItemStack(ModItems.component, 1, 0), new ItemStack(Blocks.STONE_BUTTON),
                        new ItemStack(Blocks.LEVER)));
            }

            if(Config.enablePointerFluid) {
                GameRegistry.addRecipe(
                    new ShapelessOreRecipe(new ItemStack(ModItems.pointer_fluid, 1),
                        new ItemStack(ModItems.component, 1, 0), new ItemStack(Items.BUCKET),
                        new ItemStack(Items.ENDER_PEARL))
                );
            }
        }
    }
}
