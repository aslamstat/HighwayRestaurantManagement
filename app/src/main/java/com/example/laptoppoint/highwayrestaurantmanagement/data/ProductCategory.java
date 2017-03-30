package com.example.laptoppoint.highwayrestaurantmanagement.data;

import android.icu.text.DisplayContext;

/**
 * Created by Laptop Point on 3/30/2017.
 */

public class ProductCategory {
    /** The name. */
    public String name;

    /** The type. */
    public Product.Type type;

    /** The resource. */
    public int resource;

    /**
     * Instantiates a new product category.
     *
     * @param type the type
     * @param resource the resource
     */
    public ProductCategory(String name, Product.Type type, int resource) {
        super();
        this.type = type;
        this.name=name;
        this.resource = resource;
    }
}


