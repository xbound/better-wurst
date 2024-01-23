/*
 * Copyright (c) 2014-2023 Wurst-Imperium and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.hacks;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;
import net.minecraft.text.Text;
import net.wurstclient.Category;
import net.wurstclient.SearchTags;
import net.wurstclient.hack.Hack;
import net.wurstclient.settings.EnumSetting;
import net.wurstclient.util.ChatUtils;

import net.wurstclient.settings.CheckboxSetting;
import net.wurstclient.settings.SliderSetting;
import net.wurstclient.settings.SliderSetting.ValueDisplay;
@SearchTags({"troll potion", "TrollingPotion", "trolling potion"})
public final class TrollPotionHack extends Hack
{
	private final EnumSetting<PotionType> potionType =
		new EnumSetting<>("Potion type", "The type of potion to generate.",
			PotionType.values(), PotionType.SPLASH);
	private final CheckboxSetting custom_name = new CheckboxSetting("CustomName",
		"If enabled,the potion will have a custom name and may be banned on some server.", true);
	private final CheckboxSetting buff = new CheckboxSetting("Buff",
		"", false);
	private final CheckboxSetting debuff = new CheckboxSetting("Debuff",
		"", true);
	private final SliderSetting level = new SliderSetting("Level",
		"Level of effects.", 2, 0, 127, 1, ValueDisplay.INTEGER);
	public TrollPotionHack()
	{
		super("TrollPotion");
		setCategory(Category.ITEMS);
		addSetting(level);
		addSetting(potionType);
		addSetting(debuff);
		addSetting(buff);
		addSetting(custom_name);
	}
	
	@Override
	public void onEnable()
	{
		// check gamemode
		if(!MC.player.getAbilities().creativeMode)
		{
			ChatUtils.error("Creative mode only.");
			setEnabled(false);
			return;
		}
		
		// generate potion
		ItemStack stack = potionType.getSelected().createPotionStack(level.getValueI(),custom_name.isChecked(),debuff.isChecked(),buff.isChecked());
		
		// give potion
		if(placeStackInHotbar(stack))
			ChatUtils.message("Potion created.");
		else
			ChatUtils.error("Please clear a slot in your hotbar.");
		
		setEnabled(false);
	}
	
	private boolean placeStackInHotbar(ItemStack stack)
	{
		for(int i = 0; i < 9; i++)
		{
			if(!MC.player.getInventory().getStack(i).isEmpty())
				continue;
			
			MC.player.networkHandler.sendPacket(
				new CreativeInventoryActionC2SPacket(36 + i, stack));
			return true;
		}
		
		return false;
	}
	
	private enum PotionType
	{
		NORMAL("Normal", "Potion", Items.POTION),
		
		SPLASH("Splash", "Splash Potion", Items.SPLASH_POTION),
		
		LINGERING("Lingering", "Lingering Potion", Items.LINGERING_POTION),
		
		ARROW("Arrow", "Arrow", Items.TIPPED_ARROW);
		
		private final String name;
		private final String itemName;
		private final Item item;
		
		private PotionType(String name, String itemName, Item item)
		{
			this.name = name;
			this.itemName = itemName;
			this.item = item;
		}
		
		@Override
		public String toString()
		{
			return name;
		}
		
		public ItemStack createPotionStack(int lvl,boolean custom,boolean debuff,boolean buff)
		{
			ItemStack stack = new ItemStack(item);
			NbtCompound effect;
			NbtList effects = new NbtList();
			for(int i = 1; i <= 23; i++)
			{
				if(i==6||i==7)continue;
				if(i==2||i==4||i==9||i==15||i==17||i==18||i==19||i==20){
					if(!debuff)continue;				
				}else {
					if(!buff)continue;	
				}
				effect = new NbtCompound();
				effect.putInt("Amplifier", lvl);
				effect.putInt("Duration", Integer.MAX_VALUE);
				effect.putInt("Id", i);
				effects.add(effect);
			}
			
			NbtCompound nbt = new NbtCompound();
			nbt.put("CustomPotionEffects", effects);
			stack.setNbt(nbt);
			if(custom){
			String name = "\u00a7f" + itemName + " of Trolling";
			stack.setCustomName(Text.literal(name));
			}
			return stack;
		}
	}
}
