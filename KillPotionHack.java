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
@SearchTags({"kill potion", "KillerPotion", "killer potion", "KillingPotion",
	"killing potion", "InstantDeathPotion", "instant death potion"})

public final class KillPotionHack extends Hack
{
	private final EnumSetting<PotionType> potionType =
		new EnumSetting<>("Potion type", "The type of potion to generate.",
			PotionType.values(), PotionType.SPLASH);
	private final EnumSetting<MobType> target =
		new EnumSetting<>("Target type", "Type of target entity.",
			MobType.values(), MobType.NU);
	private final CheckboxSetting custom_name = new CheckboxSetting("CustomName",
		"If enabled,the potion will have a custom name and may be banned on some server.", true);
	public KillPotionHack()
	{
		super("KillPotion");
		
		setCategory(Category.ITEMS);
		addSetting(target);
		addSetting(potionType);
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
		ItemStack stack = potionType.getSelected().createPotionStack(custom_name.isChecked(),target);
		
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
		private enum MobType
	{
		NU("Non-undead"),
		
		U("Undead"),
		
		B("Both");
		private final String name;
		
		private MobType(String name)
		{
			this.name = name;
		}
		
		@Override
		public String toString()
		{
			return name;
		}
	}
	private enum PotionType
	{
		NORMAL("Normal", "Potion", Items.POTION),
		
		SPLASH("Splash", "Splash Potion", Items.SPLASH_POTION),
		
		LINGERING("Lingering", "Lingering Potion", Items.LINGERING_POTION),
		
		// does not work 
		// ARROW("Arrow", "Arrow", Items.TIPPED_ARROW);
		
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
		
		public ItemStack createPotionStack(boolean custom,final EnumSetting<MobType> target)
		{
			ItemStack stack = new ItemStack(item);
			
			NbtCompound effect;
			NbtList effects = new NbtList();
			if(target.getSelected() == MobType.NU || target.getSelected() == MobType.B){
			effect = new NbtCompound();
			effect.putInt("Amplifier", 125);
			//effect.putInt("Duration", 0);
			effect.putInt("Id", 6);
			effects.add(effect);
			}
			if(target.getSelected() == MobType.U || target.getSelected() == MobType.B){
			effect = new NbtCompound();
			effect.putInt("Amplifier", 125);
			//effect.putInt("Duration", 0);
			effect.putInt("Id", 7);
			effects.add(effect);
			}
			NbtCompound nbt = new NbtCompound();
			nbt.put("CustomPotionEffects", effects);
			stack.setNbt(nbt);
			if(custom){
			String name =
				"\u00a7f" + itemName + " of \u00a74\u00a7lINSTANT DEATH";
			stack.setCustomName(Text.literal(name));
			}
			return stack;
		}
	}
}
