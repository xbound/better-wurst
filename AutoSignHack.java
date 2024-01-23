/*
 * Copyright (c) 2014-2023 Wurst-Imperium and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.hacks;

import net.wurstclient.Category;
import net.wurstclient.SearchTags;
import net.wurstclient.hack.DontSaveState;
import net.wurstclient.hack.Hack;

import net.wurstclient.settings.SliderSetting;
import net.wurstclient.settings.SliderSetting.ValueDisplay;
@SearchTags({"auto sign"})
@DontSaveState
public final class AutoSignHack extends Hack
{
	private String[] signText;
	private final SliderSetting rep = new SliderSetting("Repeat",
		"Times for repeating the text.\nIf this value is changed,this hack should be reenableed.", 1, 1, 1024*1024, 1, ValueDisplay.INTEGER);
	public AutoSignHack()
	{
		super("AutoSign");
		setCategory(Category.BLOCKS);
		addSetting(rep);
	}
	
	@Override
	public void onDisable()
	{
		this.signText = null;
	}
	
	public String[] getSignText()
	{
		return this.signText;
	}
	
	public void setSignText(String[] signText)
	{
		int i1;
		int reptime=rep.getValueI();
		String[] set = new String[signText.length];
		if(isEnabled() && this.signText == null){
		for(int i=0;i<signText.length;++i){
			set[i]=signText[i];
			for(i1=1;i1*2<=reptime;i1*=2){
				set[i]=set[i]+set[i];
				//System.out.println(set[i]);
			}
			for(i1=reptime-i1;i1>0;--i1){
				set[i]=set[i]+signText[i];
				//System.out.println(set[i]);
			}
		}
			this.signText = set;
		}
	}
}
