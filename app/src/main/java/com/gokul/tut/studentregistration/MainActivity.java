package com.gokul.tut.studentregistration;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.gokul.tut.studentregistration.adapter.StudentDataAdapter;
import com.gokul.tut.studentregistration.db.entity.Student;
import com.gokul.tut.studentregistration.db.entity.StudentAppDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private StudentAppDatabase studentAppDatabase;
    private ArrayList<Student> students;
    private StudentDataAdapter studentDataAdapter;
    public static final int NEW_STUDENT_ACTIVITY_REQUEST_CODE=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rv_students);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        studentDataAdapter = new StudentDataAdapter();
        recyclerView.setAdapter(studentDataAdapter);


        studentAppDatabase = Room.databaseBuilder(getApplicationContext(),StudentAppDatabase.class,"StudentDB").build();

        loadData();
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Student studentToDelete = students.get(viewHolder.getAdapterPosition());
                deleteStudent(studentToDelete);

            }
        }).attachToRecyclerView(recyclerView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddNewStudentActivity.class);
                startActivityForResult(intent,NEW_STUDENT_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==NEW_STUDENT_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK){

            String name = data.getStringExtra("NAME");
            Log.d(TAG,"ReceivedName:"+name);
            String email = data.getStringExtra("EMAIL");
            Log.d(TAG,"ReceivedEmail:"+email);
            String country = data.getStringExtra("COUNTRY");
            Log.d(TAG,"ReceivedCountry:"+country);

            SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy");
            String currentDate = sdf.format(new Date());

            Log.d(TAG,"CurrentDate:"+currentDate);

            Student stud = new Student();
            stud.setName(name);
            stud.setEmail(email);
            stud.setCountry(country);
            stud.setRegisteredTime(currentDate);
            addNewStudent(stud);
        }
    }

    void loadData(){
        new GetAllStudentAsyncTask().execute();
    }
    private class GetAllStudentAsyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            students = (ArrayList<Student>)studentAppDatabase.getStudentDao().getAllStudent();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            studentDataAdapter.setStudents(students);
        }
    }

    private void deleteStudent(Student student){
        new DeleteStudentAsyncTask().execute(student);
    }

    private class DeleteStudentAsyncTask extends AsyncTask<Student,Void,Void>{

        @Override
        protected Void doInBackground(Student... students) {
            studentAppDatabase.getStudentDao().Delete(students[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loadData();
        }
    }

    private void addNewStudent(Student student){
        new AddNewStudentAsyncTask().execute(student);
    }

    private class AddNewStudentAsyncTask extends AsyncTask<Student,Void,Void>{

        @Override
        protected Void doInBackground(Student... students) {
            studentAppDatabase.getStudentDao().Insert(students[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loadData();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
