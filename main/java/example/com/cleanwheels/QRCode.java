package example.com.cleanwheels;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRCode extends AppCompatActivity {

    private Button btngenerateQR;
    private ImageView qrCode;
    private Intent intent;
    private String phoneNo;
    private Button buttonviewDB;
    DatabaseHelper my;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        btngenerateQR = findViewById(R.id.qrCode);
        qrCode = findViewById(R.id.qrCodeImage);
        //buttonviewDB = findViewById(R.id.viewDB);


        btngenerateQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=getIntent();
                phoneNo = intent.getStringExtra("Phone");
                if(phoneNo!=null)
                {
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    BitMatrix bitMatrix = null;
                    try {
                        bitMatrix = multiFormatWriter.encode(phoneNo,BarcodeFormat.QR_CODE, 500, 500);

                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        qrCode.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

      /*  buttonviewDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                intent = new Intent(QRCode.this, ListDataActivity.class);
                startActivity(intent);
            }
        });*/


    }



}
