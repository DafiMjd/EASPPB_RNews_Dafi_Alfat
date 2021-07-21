package id.prodigy.rnews.filters;

public class Category {
    private String id;
    private String categoryName;

    public Category(String id, String name) {
        this.id = id;
        this.categoryName = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return categoryName;
    }

    @Override
    public String toString() {
        return categoryName;
    }
}
