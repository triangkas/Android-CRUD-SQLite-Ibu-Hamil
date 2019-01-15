package id.triangkas.android.triangkasuas;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListDataActivity extends AppCompatActivity {

    private static final String TAG = "ListDataActivity";

    DatabaseHelper mDatabaseHelper;
    private ListView mListView;

    private Button btnAddData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        mListView = (ListView) findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);
        btnAddData = (Button) findViewById(R.id.btnAddData);

        populateListView();

        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListDataActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView");

        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()){

            String showList = data.getString(1);
            listData.add(showList);
        }

        final ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String nama = adapterView.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemClick: You Clicked on "+ nama);

                Cursor data = mDatabaseHelper.getItemId(nama);
                int itemID = -1;
                String itemAlamat = "";
                String itemTensi = "";
                String itemBeratBadan = "";
                String itemKondisiBayi = "";
                String itemKondisiIbu = "";
                while (data.moveToNext()){
                    itemID = data.getInt(0);
                    itemAlamat = data.getString(2);
                    itemTensi = data.getString(3);
                    itemBeratBadan = data.getString(4);
                    itemKondisiBayi = data.getString(5);
                    itemKondisiIbu = data.getString(6);
                }

                if(itemID > -1){
                    Log.d(TAG, "onItemClick: The ID is "+ itemID);
                    Intent editScreenIntent = new Intent(ListDataActivity.this, EditDataActivity.class);
                    editScreenIntent.putExtra("id", itemID);
                    editScreenIntent.putExtra("nama", nama);
                    editScreenIntent.putExtra("alamat", itemAlamat);
                    editScreenIntent.putExtra("tensi", itemTensi);
                    editScreenIntent.putExtra("berat_badan", itemBeratBadan);
                    editScreenIntent.putExtra("kondisi_bayi", itemKondisiBayi);
                    editScreenIntent.putExtra("kondisi_ibu", itemKondisiIbu);
                    startActivity(editScreenIntent);
                } else {
                    toastMessage("No ID yang ditemukan dari Nama tersebut.");
                }
            }
        });
    }

    public void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
