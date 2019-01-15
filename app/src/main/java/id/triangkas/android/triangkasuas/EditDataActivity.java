package id.triangkas.android.triangkasuas;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditDataActivity extends AppCompatActivity {

    private static final String TAG = "EditDataActivity";

    private Button btnUpdate, btnDelete;
    private EditText edtEditNama, edtEditAlamat, edtEditTensi, edtEditBeratBadan, edtEditKondisiBayi, edtEditKondisiIbu;

    DatabaseHelper mDatabaseHelper;

    private String selectedNama;
    private int selectedId;
    private String selectedAlamat;
    private String selectedTensi;
    private String selectedBeratBadan;
    private String selectedKondisiBayi;
    private String selectedKondisiIbu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_data_layout);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        edtEditNama = (EditText) findViewById(R.id.edtEditNama);
        edtEditAlamat = (EditText) findViewById(R.id.edtEditAlamat);
        edtEditTensi = (EditText) findViewById(R.id.edtEditTensi);
        edtEditBeratBadan = (EditText) findViewById(R.id.edtEditBeratBadan);
        edtEditKondisiBayi = (EditText) findViewById(R.id.edtEditKondisiBayi);
        edtEditKondisiIbu = (EditText) findViewById(R.id.edtEditKondisiIbu);
        mDatabaseHelper = new DatabaseHelper(this);

        Intent receivedIntent = getIntent();
        selectedId = receivedIntent.getIntExtra("id", -1);
        selectedNama = receivedIntent.getStringExtra("nama");
        selectedAlamat = receivedIntent.getStringExtra("alamat");
        selectedTensi = receivedIntent.getStringExtra("tensi");
        selectedBeratBadan = receivedIntent.getStringExtra("berat_badan");
        selectedKondisiBayi = receivedIntent.getStringExtra("kondisi_bayi");
        selectedKondisiIbu = receivedIntent.getStringExtra("kondisi_ibu");

        edtEditNama.setText(selectedNama);
        edtEditAlamat.setText(selectedAlamat);
        edtEditTensi.setText(selectedTensi);
        edtEditBeratBadan.setText(selectedBeratBadan);
        edtEditKondisiBayi.setText(selectedKondisiBayi);
        edtEditKondisiIbu.setText(selectedKondisiIbu);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edit_nama = edtEditNama.getText().toString();
                String edit_alamat = edtEditAlamat.getText().toString();
                String edit_tensi = edtEditTensi.getText().toString();
                String edit_berat_badan = edtEditBeratBadan.getText().toString();
                String edit_kondisi_bayi = edtEditKondisiBayi.getText().toString();
                String edit_kondisi_ibu = edtEditKondisiIbu.getText().toString();
                if(!edit_nama.equals("")){
                    mDatabaseHelper.updateData(edit_nama, edit_alamat, edit_tensi, edit_berat_badan, edit_kondisi_bayi, edit_kondisi_ibu, selectedId);
                    edtEditNama.setText("");
                    edtEditAlamat.setText("");
                    edtEditTensi.setText("");
                    edtEditBeratBadan.setText("");
                    edtEditKondisiBayi.setText("");
                    edtEditKondisiIbu.setText("");
                    toastMessage("Data berhasil disimpan");
                    Intent intent = new Intent(EditDataActivity.this, ListDataActivity.class);
                    startActivity(intent);
                } else {
                    toastMessage("Nama tidak boleh kosong");
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseHelper.deleteData(selectedId);
                edtEditNama.setText("");
                edtEditAlamat.setText("");
                edtEditTensi.setText("");
                edtEditBeratBadan.setText("");
                edtEditKondisiBayi.setText("");
                edtEditKondisiIbu.setText("");
                toastMessage("Delete data berhasil");
                Intent intent = new Intent(EditDataActivity.this, ListDataActivity.class);
                startActivity(intent);
            }
        });
    }

    public void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
