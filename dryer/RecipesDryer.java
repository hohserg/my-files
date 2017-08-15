package com.lg.realism.dryer;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RecipesDryer {

	public static RecipesDryer instance = new RecipesDryer();
	List<Recipe> list = new ArrayList<Recipe>();

	public RecipesDryer() {
		this.addRecipe(new IngredientRecipe(Items.PORKCHOP), new ItemStack(Items.COOKED_PORKCHOP), 15);
		this.addRecipe(new IngredientRecipe(Items.BEEF), new ItemStack(Items.COOKED_BEEF), 17);
		this.addRecipe(new IngredientRecipe(Items.POTATO), new ItemStack(Items.BAKED_POTATO), 8);
	}

	public boolean addRecipe(IngredientRecipe input, ItemStack output) {
		return addRecipe(input, output, 20);
	}

	public boolean addRecipe(IngredientRecipe input, ItemStack output, int time) {
		if (input == null) return false;
		try {
			list.add(new Recipe(input, output, time));
			return true;
		} catch (Exception e) {}
		return false;
	}

	public Recipe findRecipe(ItemStack itemStack) {
		if (list.size() > 0)
			for (Recipe recipe : list)
				if (recipe.input.isMatchIngredient(itemStack))
					return recipe;
		return null;
	}

	public class Recipe {

		public final IngredientRecipe input;
		public final ItemStack output;
		public final int time;

		public Recipe(IngredientRecipe input, ItemStack output, int time) {
			this.input = input;
			this.output = output;
			this.time = time;
		}

	}

}
