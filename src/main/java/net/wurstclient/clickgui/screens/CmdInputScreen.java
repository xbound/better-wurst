/*
 * Copyright (c) 2014-2023 Wurst-Imperium and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.clickgui.screens;

import org.lwjgl.glfw.GLFW;


import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.wurstclient.util.BlockUtils;
import net.wurstclient.util.RenderUtils;
import net.wurstclient.WurstClient;
public final class CmdInputScreen extends Screen
{
	private final Screen prevScreen;
	
	private TextFieldWidget blockField;
	private ButtonWidget doneButton;
	
	public CmdInputScreen(Screen prevScreen)
	{
		super(Text.literal(""));
		this.prevScreen = prevScreen;
	}
	
	@Override
	public void init()
	{
		int x1 = width / 2 - 100;
		int y1 = 60;
		int y2 = height / 3 * 2;
		
		TextRenderer tr = client.textRenderer;
		
		blockField = new TextFieldWidget(tr, x1, y1, 200, 20, Text.literal(""));
		blockField.setText("");
		blockField.setSelectionStart(0);
		blockField.setMaxLength(65536);
		
		addSelectableChild(blockField);
		setFocused(blockField);
		blockField.setFocused(true);
		
		doneButton = ButtonWidget.builder(Text.literal("Done"), b -> done())
			.dimensions(x1, y2, 200, 20).build();
		addDrawableChild(doneButton);
	}
	
	private void done()
	{
		String cmd = blockField.getText();
		client.setScreen(prevScreen);
		WurstClient.INSTANCE.getCmdProcessor().process(cmd.startsWith(".") ? cmd.substring(1) :cmd);
	}
	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int int_3)
	{
		switch(keyCode)
		{
			case GLFW.GLFW_KEY_ENTER:
			done();
			break;
			
			case GLFW.GLFW_KEY_ESCAPE:
			client.setScreen(prevScreen);
			break;
		}
		
		return super.keyPressed(keyCode, scanCode, int_3);
	}
	
	@Override
	public void tick()
	{
		blockField.tick();
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY,
		float partialTicks)
	{
		TextRenderer tr = client.textRenderer;
		
		renderBackground(matrixStack);
		
		blockField.render(matrixStack, mouseX, mouseY, partialTicks);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		
		matrixStack.push();
		matrixStack.translate(-64 + width / 2 - 100, 115, 0);
		
		boolean lblAbove =
			!blockField.getText().isEmpty() || blockField.isFocused();
		String lblText =
			lblAbove ? "Command:" : "command";
		int lblX = lblAbove ? 64 : 68;
		int lblY = lblAbove ? -66 : -50;
		int lblColor = lblAbove ? 0xF0F0F0 : 0x808080;
		drawTextWithShadow(matrixStack, tr, lblText, lblX, lblY, lblColor);
		matrixStack.pop();
	}
	
	@Override
	public boolean shouldPause()
	{
		return false;
	}
	
	@Override
	public boolean shouldCloseOnEsc()
	{
		return false;
	}
}
