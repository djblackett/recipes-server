package recipes.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import recipes.Recipe;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    @Query(value = "SELECT * FROM recipe WHERE id = ?1", nativeQuery = true)
    Recipe getRecipeById(Long id);

    @Query
    List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(String category);

    @Query//(value = "SELECT * FROM Recipe WHERE name LIKE '%:name%'")
    List<Recipe> findByNameContainingIgnoreCaseOrderByDateDesc(@Param("name") String name);

    @Query
    List<Recipe> findByNameLikeIgnoreCase(String name);



//    void update(Long id);
}
