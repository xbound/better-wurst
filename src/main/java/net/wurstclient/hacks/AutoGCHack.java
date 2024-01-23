/*
 * Copyright (c) 2014-2023 Wurst-Imperium and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.hacks;

import net.minecraft.client.network.ClientPlayerEntity;
import net.wurstclient.Category;
import net.wurstclient.SearchTags;
import net.wurstclient.hack.Hack;

import net.wurstclient.settings.SliderSetting;
import net.wurstclient.settings.SliderSetting.ValueDisplay;
final class gc_thread implements Runnable
{
	
	int sleepms;
	public gc_thread(int ms){
		sleepms=ms;
	}
	@Override
	public void run()
	{
		while(sleepms > 0){
		try {
		Thread.sleep(sleepms);
		} catch(Exception e){}
		System.gc();
		//System.out.println("SystemGC");
		}
	}
	
	public void stopgc(){
		sleepms=0;
	}
}
@SearchTags({"auto gc memory clean"})
public final class AutoGCHack extends Hack
{
	private final SliderSetting p = new SliderSetting("Period",
		"In seconds.\nIf this value is changed,this hack should be reenableed.",60, 1, 600, 5, ValueDisplay.INTEGER);
		gc_thread gt;
	public AutoGCHack()
	{
		super("AutoGC");
		addSetting(p);
		setCategory(Category.OTHER);
	}
	
	@Override
	public void onEnable()
	{
		gt = new gc_thread(p.getValueI()*1000);
		(new Thread(gt)).start();
	}
	
	@Override
	public void onDisable()
	{
		gt.stopgc();
		gt = null;
	}
}
