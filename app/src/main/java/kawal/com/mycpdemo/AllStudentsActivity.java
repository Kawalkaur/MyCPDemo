package kawal.com.mycpdemo;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AllStudentsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @InjectView(R.id.listView)
    ListView listView;

    ArrayList<Student> studentList;

    @InjectView(R.id.editTextSearch)
    EditText editTextSearch;
    ContentResolver resolver;

    StudentAdapter adapter;

    Student student;

    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_students);
        ButterKnife.inject(this);

        resolver = getContentResolver();

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                if(adapter!=null){
                    adapter.filter(str);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        retrieveFromDB();
    }

    void retrieveFromDB(){

        studentList = new ArrayList<>();

        String[] projection = {Util.COL_ID, Util.COL_NAME,Util.COL_PHONE,Util.COL_EMAIL,Util.COL_GENDER,Util.COL_CITY};

        Cursor cursor = resolver.query(Util.STUDENT_URI,projection, null, null ,null);

        if (cursor != null){
             int i = 0;
            String n="", p="", e="", g="", c="";

            while (cursor.moveToNext()){
                i = cursor.getInt(cursor.getColumnIndex(Util.COL_ID));
                n = cursor.getString(cursor.getColumnIndex(Util.COL_NAME));
                p = cursor.getString(cursor.getColumnIndex(Util.COL_PHONE));
                e = cursor.getString(cursor.getColumnIndex(Util.COL_EMAIL));
                g = cursor.getString(cursor.getColumnIndex(Util.COL_GENDER));
                c = cursor.getString(cursor.getColumnIndex(Util.COL_CITY));

                studentList.add(new Student(i, n, p, e, g, c));
            }

            adapter = new StudentAdapter(this,R.layout.list_item,studentList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
        student = studentList.get(i);
        showOption();

    }

    void showOption(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] items = {"view", "update", "delete"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                switch (i){
                    case 0:
                        showStudent();

                        break;

                    case 1:
                        Intent intent = new Intent(AllStudentsActivity.this, MainActivity.class);
                        intent.putExtra("keyStudent", student);
                        startActivity(intent);
                        break;

                    case 2:
                        deleteStudent();
                        break;
                }
            }
        });

        builder.create().show();
    }

    void showStudent(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Details of "+student.getName());
        builder.setMessage(student.toString());
        builder.setNegativeButton("Done",null);
        builder.create().show();
    }

    void deleteStudent(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+student.getName());
        builder.setMessage("Do you wish to Delete");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                String where = Util.COL_ID+ " = "+student.getId();

                int j = resolver.delete(Util.STUDENT_URI, where, null);
                if (j>0){
                    studentList.remove(pos);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(AllStudentsActivity.this,student.getName()+ "Deleted sucessfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel",null);
        builder.create().show();

    }
}
