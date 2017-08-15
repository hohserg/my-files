package com.lg.realism.dryer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntityCumpfire extends TileEntity implements ITickable, ISidedInventory {

	InventoryBasic inventory = new InventoryBasic("inventoryGrill", false, 6);
	String customName;
	RecipesDryer.Recipe[] recipe = new RecipesDryer.Recipe[6];
	int[] timeCooking = new int[6];
	int timeBurn = 0;

	public TileEntityCumpfire() {

	}

	@Override
	public void update() {
	//	boolean drying = world.canBlockSeeSky(getPos().up()) && world.isDaytime() && world.getLightBrightness(getPos().up()) > 0.5F;
/*
 * 4-фазовый цикл
 */
		for (int j = 0; j < 6; ++j) {
			//здесь получается итем стак, который кладется в слот
			ItemStack slot = inventory.getStackInSlot(j);
			//если слот пустой(то есть лежит "пустой" предмет то продолжать цикл
			if (slot == ItemStack.EMPTY) continue;
			//если массив recipe не равен нулю, то ...
			if (recipe[j] != null) {
				//если время готовки j больше нуля то...
				if (timeCooking[j] > 0) {
					//если время готовки больше нуля, то вычитать -1 каждый тик
					if (timeBurn > 0) --timeCooking[j];
					else {
						if (world.getWorldTime() % 20 == 0)
							--timeCooking[j];
					}
				}
				else {
					inventory.setInventorySlotContents(j, recipe[j].output.copy());
					recipe[j] = null;
				}
			} else {
				recipe[j] = RecipesDryer.instance.findRecipe(slot);
				if (recipe[j] != null)
					timeCooking[j] = recipe[j].time;
			}
		}

		if (timeBurn <= 0) {
			//Слот топлива получается
			ItemStack fuelSlot = inventory.getStackInSlot(6);
			System.out.println("" + timeBurn);
			if (fuelSlot != ItemStack.EMPTY) {
				boolean isEmpty = true;
				for (int j = 0; j < 9; ++j) {
					ItemStack slot = inventory.getStackInSlot(j);
					if (slot != ItemStack.EMPTY) {
						isEmpty = true;
						System.out.println("" + timeBurn);
						break;
					}
				}
				if (!isEmpty) {
					timeBurn = TileEntityFurnace.getItemBurnTime(fuelSlot);
					if (timeBurn > 0)
						fuelSlot.grow(-1);
					if (fuelSlot.getCount() <= 0)
						inventory.setInventorySlotContents(9, new ItemStack(Items.APPLE));
				}
			}
		} else --timeBurn;
	}
	@Override
	public int getSizeInventory() {
		return inventory.getSizeInventory();
	}

	@Override
	public boolean isEmpty() {
		return inventory.isEmpty();
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return inventory.getStackInSlot(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return inventory.decrStackSize(index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return inventory.removeStackFromSlot(index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		inventory.setInventorySlotContents(index, stack);
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {

	}

	@Override
	public void closeInventory(EntityPlayer player) {

	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return inventory.isItemValidForSlot(index, stack);
	}

	@Override
	public int getField(int id) {
		return inventory.getField(id);
	}

	@Override
	public void setField(int id, int value) {
		inventory.setField(id, value);
	}

	@Override
	public int getFieldCount() {
		return inventory.getFieldCount();
	}

	@Override
	public void clear() {
		inventory.clear();
	}

	@Override
	public String getName() {
		return hasCustomName() ? customName : "Dryer";
	}

	@Override
	public boolean hasCustomName() {
		return customName != null || customName.length() > 0;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return null;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return false;
	}
	  @Override
	   public NBTTagCompound writeToNBT(NBTTagCompound compound)
	   {
	       super.writeToNBT(compound);
	       NBTTagList list = new NBTTagList();

	       for(int i = 0; i < this.inventory.getSizeInventory(); ++i)
	       {
	           if(this.inventory.getStackInSlot(i) != null)
	           {
	               NBTTagCompound tag = new NBTTagCompound();
	               tag.setByte("Slot", (byte) i);
	               this.inventory.getStackInSlot(i).writeToNBT(tag);
	               list.appendTag(tag);
	           }
	       }

	       compound.setTag("Items", list);
	       return compound;
	   }

	   /**
	    *   Данный метод будет читать NBT и выводить значения из него.
	    */
	   @Override
	   public void readFromNBT(NBTTagCompound compound)
	   {
	       super.readFromNBT(compound);
	       NBTTagList list = compound.getTagList("Items", 10);

	       for(int i = 0; i < list.tagCount(); ++i)
	       {
	           NBTTagCompound tag = list.getCompoundTagAt(i);
	           int j = tag.getByte("Slot") & 255;

	           if(j >= 0 && j < this.inventory.getSizeInventory())
	           {
	        	   this.inventory.setInventorySlotContents(j, new ItemStack(tag));
	           }
	       }
	   
	}

}
