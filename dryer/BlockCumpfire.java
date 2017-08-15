package com.lg.realism.dryer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.lg.realism.Realism;
import com.lg.realism.RegBlocks;
import com.lg.realism.proxy.GuiHundler;

public class BlockCumpfire extends Block implements ITileEntityProvider {
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	private static boolean keepInventory;
	public BlockCumpfire(final String name) {
		super(Material.WOOD);
		setUnlocalizedName(name);
		
		setRegistryName(name);
		setCreativeTab(Realism.tabMain);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityCumpfire();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		System.out.println("0");
		if(!worldIn.isRemote) {
			TileEntity entity = worldIn.getTileEntity(pos);
			if(entity instanceof TileEntityCumpfire) {
				playerIn.openGui(Realism.INSTANCE, GuiHundler.cumpfire, worldIn, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		return true;
	}


	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityCumpfire();
	}



	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		if (!keepInventory)
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof TileEntityCumpfire)
			{
				InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityCumpfire)tileentity);
				worldIn.updateComparatorOutputLevel(pos, this);
			}
		}

		super.breakBlock(worldIn, pos, state);
	}

	public boolean hasComparatorInputOverride(IBlockState state)
	{
		return true;
	}

	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
	{
		return Container.calcRedstone(worldIn.getTileEntity(pos));
	}

	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return new ItemStack(RegBlocks.cumpfire);
	}

	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}

	public IBlockState getStateFromMeta(int meta)
	{

		return this.getDefaultState();
	}

	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}


	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
		return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
	}

	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {FACING});
	}

}
