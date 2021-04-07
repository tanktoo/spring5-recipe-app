package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.boot.CommandLineRunner;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

//@Component
public class DataLoader implements CommandLineRunner {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;


    public DataLoader(UnitOfMeasureRepository unitOfMeasureRepository, CategoryRepository categoryRepository, RecipeRepository recipeRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        Recipe guacamole = new Recipe();
        guacamole.setDescription("This is guacamole");
        guacamole.setPrepTime(10);
        guacamole.setCookTime(0);
        guacamole.setServings(2);
        guacamole.setSource("Simply Recipes");
        guacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamole.setDirections("Guacamole");
        guacamole.setDifficulty(Difficulty.EASY);

        Set<Ingredient> ingredients = new HashSet<>();
        Ingredient avocado = new Ingredient();
        avocado.setDescription("Ripe avocados");
        avocado.setAmount(new BigDecimal("2"));
        avocado.setRecipe(guacamole);
        avocado.setUom(unitOfMeasureRepository.findByDescription("Piece").get());
        ingredients.add(avocado);

        Ingredient salt = new Ingredient();
        salt.setDescription("Salt");
        salt.setAmount(new BigDecimal("0.25"));
        salt.setRecipe(guacamole);
        salt.setUom(unitOfMeasureRepository.findByDescription("Teaspoon").get());
        ingredients.add(salt);

        guacamole.setIngredients(ingredients);

        Notes notes = new Notes();
        notes.setRecipeNotes("Step 1: ..., Step 2: ...");
        guacamole.setNotes(notes);

        Set<Category> categories = new HashSet<>();
        categories.add(categoryRepository.findByDescription("Mexican").get());
        guacamole.setCategories(categories);

        recipeRepository.save(guacamole);
    }

}
