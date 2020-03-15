public class Product {

    private int idProduct;
    private int idCategory;
    private String name;
    private int price;
    private int amount;
    private String description;

    public Product(){ }

    public Product(int idCategory, String name, int price, int amount, String description){
        this.idCategory = idCategory;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.description = description;
    }

    public Product(int idProduct, int idCategory, String name, int price, int amount, String description){
        this.idProduct = idProduct;
        this.idCategory = idCategory;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.description = description;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory(int idCategory){
        Category category = CategoryDataBase.selectOne(idCategory);
        return category.getName();
    }
}

