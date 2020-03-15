import java.util.ArrayList;

public class Category {

    private int idCategory;
    private String name;

    public Category() {}

    public Category(String name) {
        this.name = name;
    }

    public Category(int idCategory, String name) {
        this.idCategory = idCategory;
        this.name = name;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int countBoundProducts(){
        ArrayList<Product> products = ProductDataBase.selectByCategory(idCategory);
        return products.size();
    }
}
