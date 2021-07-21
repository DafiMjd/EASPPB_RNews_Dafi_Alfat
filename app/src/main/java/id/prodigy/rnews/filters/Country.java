package id.prodigy.rnews.filters;

public class Country {
    private String id;
    private String countryName;

    public Country(String id, String name) {
        this.id = id;
        this.countryName = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return countryName;
    }

    @Override
    public String toString() {
        return countryName;
    }
}
