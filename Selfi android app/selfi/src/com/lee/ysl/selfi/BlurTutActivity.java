package com.lee.ysl.selfi;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;



public class BlurTutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blurtut);

        GridView grid = (GridView) findViewById(R.id.grid);
        grid.setAdapter(new ImageAdapter(this));

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent i = new Intent(BlurTutActivity.this, DetailsActivity.class);
                i.putExtra(Constants.DROID_ID, Constants.THUMBNAILS[position]);
                new BlurTask(BlurTutActivity.this, i, R.id.container).execute();
            }
        });
    }
}
