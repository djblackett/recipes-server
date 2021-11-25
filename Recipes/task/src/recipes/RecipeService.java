package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.data.RecipeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    public RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> findByNameLike(String name) {
        return recipeRepository.findByNameLikeIgnoreCase(name);
    }
    public void newRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    public Recipe getRecipe(Long id) {
        return recipeRepository.getRecipeById(id);
    }

    public void deleteRecipeById(Long id) {
        recipeRepository.deleteById(id);
    }

    public List<Recipe> getRecipesByName(String name) {
        return recipeRepository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    public List<Recipe> getRecipesByCategory(String category) {
        return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }
}
