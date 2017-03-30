package com.example.laptoppoint.highwayrestaurantmanagement.mainly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.laptoppoint.highwayrestaurantmanagement.R;
import com.example.laptoppoint.highwayrestaurantmanagement.adapter.CategoriesAdapter;
import com.example.laptoppoint.highwayrestaurantmanagement.data.DataProvider;
import com.example.laptoppoint.highwayrestaurantmanagement.data.Order;
import com.example.laptoppoint.highwayrestaurantmanagement.data.ProductCategory;

import java.util.ArrayList;

public class ProductCategoriesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private final static int PICK_PRODUCT=0;

    /** The table. */
    Table table;

    /** The order. */
    Order order;

    /** The categories. */
    ArrayList<ProductCategory> categories;

    /** The listview. */
    GridView gridview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_categories);
        categories= DataProvider.getProductCategories();

        //Get the table
        Bundle b = getIntent().getExtras();
        table = DataProvider.getTable(b.getInt("tableID"));

        //Set order
        order=DataProvider.getOrder(table.id, b.getInt("orderID"));
        Log.d("CG","Product Category activity for "+order);

        //Prepare the grid
        gridview = (GridView) findViewById(R.id.categoriesGridView);
        gridview.setAdapter(new CategoriesAdapter(getBaseContext(), categories));

        gridview.setOnItemClickListener(this);

        }

	/* Event triggered on click on one of the orders
	 *
	 * (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
@Override
public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        Intent myIntent = new Intent(this, ProductsActivity.class);
        myIntent.putExtra("tableID", table.id);
        myIntent.putExtra("orderID", order.id);
        myIntent.putExtra("category", categories.get(position).type);
        this.startActivityForResult(myIntent,PICK_PRODUCT);
        }

	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("CG","Returned from Product selection with "+resultCode);
        if (requestCode == PICK_PRODUCT)
        {
        Log.d("CG","Request code is for PICK_PRODUCT");
        if (resultCode == RESULT_OK)
        {
        //Return success, so the parent knows it was a successful selection
        setResult(RESULT_OK);
        finish();
        }
        }
        }

        }