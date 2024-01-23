/*
 * Copyright (c) 2014-2023 Wurst-Imperium and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.commands;

import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.wurstclient.command.CmdError;
import net.wurstclient.command.CmdException;
import net.wurstclient.command.CmdSyntaxError;
import net.wurstclient.command.Command;
import net.wurstclient.util.ChatUtils;

public final class GSLaunchCmd extends Command
{
	public GSLaunchCmd()
	{
		super("gslaunch", "launch the GhastSimulator.", ".gslaunch");
	}
	
	@Override
	public void call(String[] args) throws CmdException
	{
		
		if(args.length > 1)
			throw new CmdSyntaxError();
		WURST.getHax().ghastSimulatorHack.launch();
	}
		
	@Override
	public String getPrimaryAction()
	{
		return "Launch the GhastSimulator.";
	}
	
	@Override
	public void doPrimaryAction()
	{
		WURST.getCmdProcessor().process("gslaunch");
	}
}
