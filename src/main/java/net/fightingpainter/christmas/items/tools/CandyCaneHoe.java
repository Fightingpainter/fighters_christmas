package net.fightingpainter.christmas.items.tools;

import net.minecraft.item.*;

public class CandyCaneHoe extends HoeItem{

    public CandyCaneHoe(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

}
