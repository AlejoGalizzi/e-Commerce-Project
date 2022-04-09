package user.com.ecommerce.seeder;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import user.com.ecommerce.model.entity.Category;
import user.com.ecommerce.model.entity.Product;
import user.com.ecommerce.repository.ICategoryRepository;
import user.com.ecommerce.repository.IProductRepository;

@Component
public class ProductSeeder implements CommandLineRunner {

  private static final List<String> SKU_PRODUCT = List.of("BOOK-TECH-1000", "BOOK-TECH-1001",
      "BOOK-TECH-1002",
      "BOOK-TECH-1003", "BOOK-TECH-1004");
  private static final List<String> NAME_PRODUCT = List.of("JavaScript - The Fun Parts",
      "Spring Framework Tutorial", "Kubernetes - Deploying Containers",
      "Internet of Things (IoT) - Getting Started", "The Go Programming Language: A to Z");
  private static final List<String> DESCRIPTION_PRODUCT = List.of("Learn JavaScript",
      "Learn Spring", "Learn Kubernetes", "Learn IoT", "Learn Go");
  private static final String IMAGEURL_PRODUCT = "placeholder.png";
  private static final Boolean ACTIVE_PRODUCT = true;
  private static final Integer UNITS_IN_STOCK = 100;
  private static final List<Float> PRICE_UNIT = List.of(19.99F, 29.99F, 24.99F, 29.99F, 24.99F);

  private static final String CATEGORY_NAME = "BOOKS";

  @Autowired
  private IProductRepository productRepository;

  @Autowired
  private ICategoryRepository categoryRepository;


  @Override
  public void run(String... args) throws Exception {
    seedProductTable();
  }

  private void seedProductTable() {
    if (categoryRepository.count() == 0) {
      createCategory();
    }
    if (productRepository.count() == 0) {
      seedProducts();
    }
  }

  private void seedProducts() {
    for (int i = 0; i < SKU_PRODUCT.size(); i++) {
      createProduct(SKU_PRODUCT.get(i), NAME_PRODUCT.get(i), DESCRIPTION_PRODUCT.get(i),
          IMAGEURL_PRODUCT, UNITS_IN_STOCK, PRICE_UNIT.get(i));
    }
  }

  private void createProduct(String sku, String name, String description, String imageUrl,
      Integer unitsInStock, Float priceUnit) {
    Category category = categoryRepository.findByName("BOOKS");
    Product product = new Product();
    product.setSku(sku);
    product.setName(name);
    product.setDescription(description);
    product.setImageUrl(imageUrl);
    product.setUnitsInStock(unitsInStock);
    product.setUnitPrice(priceUnit);
    product.setCategory(category);
    productRepository.save(product);
  }

  private void createCategory() {
    Category category = new Category();
    category.setName(CATEGORY_NAME);
    categoryRepository.save(category);
  }
}
