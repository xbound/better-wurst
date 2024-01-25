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
import net.wurstclient.hack.Hack;

import net.wurstclient.clickgui.screens.CmdInputScreen;
import net.wurstclient.command.CmdProcessor;
import net.wurstclient.settings.CheckboxSetting;
@SearchTags({"command", "cmd", "chat"})
public final class CommandHack extends Hack
{
	private final CheckboxSetting nc = new CheckboxSetting("Disable in chat",
		"Disable commands in chat started by \".\" .", false);
	public CommandHack()
	{
		super("Command");
		setCategory(Category.CHAT);
		addSetting(nc);
	}
	@Override
	public void onEnable()
	{
		CmdInputScreen cis = new CmdInputScreen(MC.currentScreen);
		MC.setScreen(cis);
		setEnabled(false);
	}
	public boolean nochat(){
		return nc.isChecked();
	}
	// See net.wurstclient.command.CmdProcessor
}
