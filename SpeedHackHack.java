/*
 * Copyright (c) 2014-2023 Wurst-Imperium and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.hacks;

import net.minecraft.util.math.Vec3d;
import net.wurstclient.Category;
import net.wurstclient.SearchTags;
import net.wurstclient.events.UpdateListener;
import net.wurstclient.hack.Hack;

import net.wurstclient.settings.CheckboxSetting;
import net.wurstclient.settings.SliderSetting;
import net.wurstclient.settings.SliderSetting.ValueDisplay;
@SearchTags({"speed hack"})
public final class SpeedHackHack extends Hack implements UpdateListener
{
	private final SliderSetting speed = new SliderSetting("Speed",
		"How fast you expect.", 1, 0, 32, 0.01, ValueDisplay.DECIMAL);
	private final CheckboxSetting wsnake = new CheckboxSetting("Work when snaking",
		"", true);
	private final CheckboxSetting bypass = new CheckboxSetting("Bypass NoCheat+",
		"Limit speed to highest value that works on NoCheat+ version.", false);
	public SpeedHackHack()
	{
		super("SpeedHack");
		setCategory(Category.MOVEMENT);
		addSetting(speed);
		addSetting(wsnake);
		addSetting(bypass);
	}
	
	@Override
	public void onEnable()
	{
		EVENTS.add(UpdateListener.class, this);
	}
	
	@Override
	public void onDisable()
	{
		EVENTS.remove(UpdateListener.class, this);
	}
	
	@Override
	public void onUpdate()
	{
		// return if sneaking or not walking
		if(MC.player.isSneaking() && !wsnake.isChecked() || 
		MC.player.forwardSpeed == 0 && MC.player.sidewaysSpeed == 0){
			return;
		}
		
		// activate sprint if walking forward
		/*if(MC.player.forwardSpeed > 0 && !MC.player.horizontalCollision)
			MC.player.setSprinting(true);
		
		// activate mini jump if on ground
		if(!MC.player.isOnGround())
			return;
		*/
		double speedvalue=speed.getValue();
		Vec3d v = MC.player.getVelocity();
		MC.player.setVelocity(0 , v.y, 0);
		MC.player.updateVelocity((float)speedvalue,
			new Vec3d(MC.player.sidewaysSpeed, 0, MC.player.forwardSpeed));
		v = MC.player.getVelocity();
		double currentSpeed = Math.sqrt(Math.pow(v.x, 2) + Math.pow(v.z, 2));
		// limit speed to highest value that works on NoCheat+ version
		// 3.13.0-BETA-sMD5NET-b878
		// UPDATE: Patched in NoCheat+ version 3.13.2-SNAPSHOT-sMD5NET-b888
		double maxSpeed = 0.66F;
		if(!bypass.isChecked())maxSpeed*=speedvalue;
		if(currentSpeed > maxSpeed)
			MC.player.setVelocity(v.x / currentSpeed * maxSpeed, v.y,
				v.z / currentSpeed * maxSpeed);
	}
}
