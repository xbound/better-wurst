/*
 * Copyright (c) 2014-2023 Wurst-Imperium and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.hacks;

//import net.minecraft.util.hit.BlockHitResult;
//import net.minecraft.util.hit.HitResult;
import net.wurstclient.Category;
import net.wurstclient.events.LeftClickListener;
import net.wurstclient.hack.Hack;
import net.wurstclient.settings.SliderSetting;
import net.wurstclient.settings.SliderSetting.ValueDisplay;
import net.wurstclient.settings.BlockSetting;
import net.wurstclient.settings.EnumSetting;
import java.math.BigDecimal;
public final class GhastSimulatorHack extends Hack implements LeftClickListener
{
	private final EnumSetting<BallType> ball =
		new EnumSetting<>("Type", "The type of fireball to generate.",
			BallType.values(), BallType.GHAST);
	private final SliderSetting power = new SliderSetting("Power",
		"Power of ghast fireball.", 2, 0, 127, 1, ValueDisplay.INTEGER);
	private final SliderSetting speed1 = new SliderSetting("Speed",
		"How fast.", 0.08, 0.05, 8, 0.05, ValueDisplay.DECIMAL);
	private final BlockSetting sblock = new BlockSetting("Sand block", "minecraft:sand" , true);
	
	public GhastSimulatorHack()
	{
		super("GhastSimulator");
		
		setCategory(Category.OTHER);
		addSetting(ball);
		addSetting(power);
		addSetting(speed1);
		addSetting(sblock);
	}

	@Override
	public void onEnable()
	{
		EVENTS.add(LeftClickListener.class, this);
	}
	
	@Override
	public void onDisable()
	{
		EVENTS.remove(LeftClickListener.class, this);
	}
	private enum BallType{
		GHAST("Ghast","minecraft:fireball"),
		BLAZE("Blaze","minecraft:small_fireball"),
		DRAGON("Dragon","minecraft:dragon_fireball"),
		WITHER("Wither(black)","minecraft:wither_skull"),
		//not work
		//WITHER2("Wither(blue)","minecraft:wither_skull_dangerous"),
		TNT("TNT","minecraft:tnt"),
		SAND("Sand","minecraft:falling_block");
		private String name;
		private String ename;
		private BallType(String name, String eName){
			this.name=name;
			this.ename=eName;
		}
		@Override
		public String toString()
		{
			return name;
		}
		public String getname()
		{
			return ename;
		}
	}
	@Override
	public void onLeftClick(LeftClickEvent event)
	{
		double speed=speed1.getValue(),yaw,pitch,x,y,z;
		yaw=(MC.player.getYaw()+90)*Math.PI/180;
		pitch=MC.player.getPitch()*Math.PI/180;
		x=Math.cos(yaw)*Math.cos(pitch);
		y=-Math.sin(pitch);
		z=Math.sin(yaw)*Math.cos(pitch);
		String xs=(new BigDecimal(x)).toPlainString();
		String ys=(new BigDecimal(y+1)).toPlainString();
		String zs=(new BigDecimal(z)).toPlainString();
		//if(!MC.options.useKey.isPressed())
		//	return;
		if(ball.getSelected() == BallType.GHAST)
			MC.getNetworkHandler().sendChatCommand("summon minecraft:fireball ~" + xs + " ~" + ys + " ~" + zs + " {ExplosionPower:" + power.getValueI() + ",power:[" + speed*x + "d,"+ speed*y + "d," + speed*z + "d]}");
			else if(ball.getSelected() == BallType.SAND)MC.getNetworkHandler().sendChatCommand("summon minecraft:falling_block ~" + xs + " ~" + ys + " ~" + zs + " {Motion:[" + speed*x + "d,"+ speed*y + "d," + speed*z + "d],BlockState:{Name:\"" + sblock.getBlockName() + "\"}}");
			else if(ball.getSelected() == BallType.TNT)MC.getNetworkHandler().sendChatCommand("summon minecraft:tnt ~" + xs + " ~" + ys + " ~" + zs + " {Fuse:80,Motion:[" + speed*x + "d,"+ speed*y + "d," + speed*z + "d]}");
			else MC.getNetworkHandler().sendChatCommand("summon " + ball.getSelected().getname() + " ~" + xs + " ~" + ys + " ~" + zs + " {power:[" + speed*x + "d,"+ speed*y + "d," + speed*z + "d]}");
	}
}
