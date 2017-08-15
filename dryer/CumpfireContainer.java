package com.lg.realism.dryer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class CumpfireContainer extends Container {

	private TileEntityCumpfire  tile;
	private int numRows;

	public CumpfireContainer(InventoryPlayer playerInventory, TileEntityCumpfire tile) {
		this.tile = tile;

		tile.openInventory(playerInventory.player);//Типа инициализируем открытия инвентаря

		numRows = tile.getSizeInventory() / 3;
		int i = (numRows - 4) * 18;
		int j;
		int k;

		this.addSlotToContainer(new Slot(tile, 0, 54, 42));
		this.addSlotToContainer(new Slot(tile, 1, 72, 42));
		this.addSlotToContainer(new Slot(tile, 2, 90, 42));
		this.addSlotToContainer(new Slot(tile, 3, 108, 42));
		this.addSlotToContainer(new Slot(tile, 4, 8, 26));
		
	
		//Слоты инвентаря игрока
		for (j = 0; j < 3; ++j) {
			for (k = 0; k < 9; ++k) {
				addSlotToContainer(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, 139 + j * 18 + i));
			}
		}

		//Слоты хот-бара игрока
		for (j = 0; j < 9; ++j) {
			addSlotToContainer(new Slot(playerInventory, j, 8 + j * 18, 197 + i));
		}

	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tile.isUsableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot_i) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = (Slot)inventorySlots.get(slot_i);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemStack1 = slot.getStack();
			itemStack = itemStack1.copy();

			if (slot_i < tile.getSizeInventory()) {
				if (!mergeItemStack(itemStack1, tile.getSizeInventory(), inventorySlots.size(), true)) return ItemStack.EMPTY;
			} else if (!mergeItemStack(itemStack1, 0, tile.getSizeInventory(), false))
				return ItemStack.EMPTY;

			if (itemStack1.isEmpty()) slot.putStack(ItemStack.EMPTY);
			else slot.onSlotChanged();
		}

		return itemStack;
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		tile.closeInventory(player);//Типа инициализируем закрытия инвентаря
	}

}
