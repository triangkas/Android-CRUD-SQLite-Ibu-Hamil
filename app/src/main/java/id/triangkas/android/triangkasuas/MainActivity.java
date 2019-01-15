package id.triangkas.android.triangkasuas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    DatabaseHelper mDatabaseHelper;
    private Button btnAdd, btnView;
    private EditText edtNama, edtAlamat, edtTensi, edtBeratBadan, edtKondisiBayi, edtKondisiIbu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtNama = (EditText) findViewById(R.id.edtNama);
        edtAlamat = (EditText) findViewById(R.id.edtAlamat);
        edtTensi = (EditText) findViewById(R.id.edtTensi);
        edtBeratBadan = (EditText) findViewById(R.id.edtBeratBadan);
        edtKondisiBayi = (EditText) findViewById(R.id.edtKondisiBayi);
        edtKondisiIbu = (EditText) findViewById(R.id.edtKondisiIbu);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnView = (Button) findViewById(R.id.btnView);
        mDatabaseHelper = new DatabaseHelper(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = edtNama.getText().toString();
                String alamat = edtAlamat.getText().toString();
                String tensi = edtTensi.getText().toString();
                String berat_badan = edtBeratBadan.getText().toString();
                String kondisi_bayi = edtKondisiBayi.getText().toString();
                String kondisi_ibu = edtKondisiIbu.getText().toString();
                if(edtNama.length() != 0){
                    addData(nama, alamat, tensi, berat_badan, kondisi_bayi, kondisi_ibu);
                    edtNama.setText("");
                    edtAlamat.setText("");
                    edtTensi.setText("");
                    edtBeratBadan.setText("");
                    edtKondisiBayi.setText("");
                    edtKondisiIbu.setText("");
                } else {
                    toastMessage("Nama tidak boleh kosong");
                }
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListDataActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addData(String nama, String alamat, String tensi, String berat_badan, String kondisi_bayi, String kondisi_ibu) {
        boolean insertData = mDatabaseHelper.addData(nama, alamat, tensi, berat_badan, kondisi_bayi, kondisi_ibu);

        if(insertData){
            toastMessage("Data berhasil ditambahkan");
        } else {
            toastMessage("Terjadi kesalahan");
        }
    }

    public void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
