package example.com.cleanwheels;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class CarWash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_wash);

        ImageButton combo_wash = (ImageButton) findViewById(R.id.combo_wash);
        combo_wash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCombo_wash();

            }
        });

        ImageButton exterior_refine = (ImageButton) findViewById(R.id.exterior_refine);
        exterior_refine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openexterior_refine();
            }
        });

        ImageButton interior = (ImageButton) findViewById(R.id.interior);
        interior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInterior();
            }
        });

        ImageButton complete360 = (ImageButton) findViewById(R.id.complete_360);
        complete360.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openComplete360();
            }
        });

    }




    private void openCombo_wash()
    {
        Intent intentComboWash= new Intent(this, ComboWash.class);
        startActivity(intentComboWash);
    }
    private void openexterior_refine()
    {
        Intent intentExteriorRefine= new Intent(this, ExteriorRefine.class);
        startActivity(intentExteriorRefine);
    }
    private void openInterior()
    {
        Intent intentInterior= new Intent(this, Interior.class);
        startActivity(intentInterior);
    }
    private void openComplete360()
    {
        Intent intentComplete360= new Intent(this, Complete360.class);
        startActivity(intentComplete360);
    }

}


