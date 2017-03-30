package com.example.laptoppoint.highwayrestaurantmanagement.mainly;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.laptoppoint.highwayrestaurantmanagement.R;
import com.example.laptoppoint.highwayrestaurantmanagement.adapter.TableAdapter;
import com.example.laptoppoint.highwayrestaurantmanagement.data.DataProvider;
import com.example.laptoppoint.highwayrestaurantmanagement.data.TableOrder;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ArrayList<Table> tables;
    GridView gridview;

    static boolean initFinished;

    /**
     * One time init.
     */
    public static void oneTimeInit()
    {
        if(!initFinished)
        {
            initFinished=true;
            //Prepare the tables
            DataProvider.generateTables();
            DataProvider.generateProducts();
            DataProvider.generateProductCategories();
        }
    }

    int customersNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            oneTimeInit();

            //Check if it's a restricted tables activity
            Bundle b = getIntent().getExtras();
            if(b!=null)
            {
                customersNo = b.getInt("customersNO");
                Log.d("CG","Main activity for no customers: "+customersNo);
            }
            else customersNo=0;


            //Get Tables
            tables=new ArrayList<Table>(DataProvider.getTables());

            //If a certain number of customers are searching for a table, restrict the rest

            if(customersNo>0)
            {
                ArrayList<Table> toDelete=new ArrayList<Table>();
                for(Table t:tables)
                {
                    if(!t.empty)
                        toDelete.add(t);
                    if(t.empty && t.maxClients<customersNo)
                        toDelete.add(t);
                }
                tables.removeAll(toDelete);
            }
            //Prepare the grid
            gridview = (GridView) findViewById(R.id.gridView);
            gridview.setAdapter(new TableAdapter(this, tables));

            gridview.setOnItemClickListener(this);
        }

        protected void onStart()
        {
            super.onStart();
            gridview.invalidateViews();
        }
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

            Toast.makeText(MainActivity.this, "Table "+position+" selected.",Toast.LENGTH_SHORT).show();
            final Table table=tables.get(position);
            Log.d("CG","Clicked on table "+table);
            final Activity mactivity=this;


            if(table.empty)
            {
                final CharSequence[] customers= new CharSequence[table.maxClients];
                for(int i=0;i<customers.length;i++)
                    customers[i]=Integer.toString(i+1);


                if(customersNo>0)
                {
                    table.empty=false;
                    table.curClients=customersNo;
                    table.tableOrder=new TableOrder();

                    Intent myIntent = new Intent(mactivity, TableActivity.class);
                    myIntent.putExtra("tableID", table.id);
                    mactivity.startActivity(myIntent);

                    mactivity.finish();
                }
                else
                {
                    //Create the dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setItems(customers, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            Toast.makeText(getApplicationContext(), customers[item] + " customers selected.", Toast.LENGTH_SHORT).show();
                            table.empty=false;
                            table.curClients=item+1;
                            table.tableOrder=new TableOrder();

                            Intent myIntent = new Intent(mactivity, TableActivity.class);
                            myIntent.putExtra("tableID", table.id);
                            mactivity.startActivity(myIntent);

                        }
                    });

                    //Show the dialog
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
            //If the table is not empty, start the Table Activity
            else {
                Intent myIntent = new Intent(this, TableActivity.class);
                myIntent.putExtra("tableID", table.id);
                this.startActivity(myIntent);

                //Kill the intermediate activity if it's a search type
                if(customersNo>0)
                    finish();
            }
        }

        /* (non-Javadoc)
         * @see android.app.Activity#onSearchRequested()
         */
        @Override
        public boolean onSearchRequested() {

            //Generate numbers
            final CharSequence[] customers= new CharSequence[8];
            for(int i=0;i<customers.length;i++)
                customers[i]=Integer.toString(i+1);
            final Activity mactivity=this;

            //Create the dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Number of customers");
            builder.setItems(customers, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    Toast.makeText(getApplicationContext(), customers[item] + " customers selected.", Toast.LENGTH_SHORT).show();

                    Intent myIntent = new Intent(mactivity, MainActivity.class);
                    myIntent.putExtra("customersNO", (item+1));
                    mactivity.startActivity(myIntent);
                }
            });

            //Show the dialog
            AlertDialog alert = builder.create();
            alert.show();

            return false; // don't go ahead and show the search box
        }


    }