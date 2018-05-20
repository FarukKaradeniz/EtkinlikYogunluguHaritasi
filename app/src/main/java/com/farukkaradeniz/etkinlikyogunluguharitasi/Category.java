package com.farukkaradeniz.etkinlikyogunluguharitasi;

/**
 * Created by Faruk Karadeniz on 20.05.2018.
 * Twitter: twitter.com/Omeerfk
 * Github: github.com/FarukKaradeniz
 * LinkedIn: linkedin.com/in/FarukKaradeniz
 * Website: farukkaradeniz.com
 */
public class Category {
    private String category;
    private String subCategory;

    public Category() { /*Required no argument constructor for firestore*/ }

    public Category(String category, String subCategory) {
        this.category = category;
        this.subCategory = subCategory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    @Override
    public String toString() {
        return String.format("{\n(Category: %s)\n(Subcategory: %s)\n}", category, subCategory);
    }
}
