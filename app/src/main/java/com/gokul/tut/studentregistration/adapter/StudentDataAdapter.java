package com.gokul.tut.studentregistration.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gokul.tut.studentregistration.R;
import com.gokul.tut.studentregistration.db.entity.Student;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class StudentDataAdapter extends RecyclerView.Adapter<StudentDataAdapter.StudentViewHolder> {

    private ArrayList<Student> students;

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_list_item,viewGroup,false);
        return new StudentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder studentViewHolder, int i) {

        Student currentStudents = students.get(i);

        studentViewHolder.name.setText(currentStudents.getName());
        studentViewHolder.email.setText(currentStudents.getEmail());
        studentViewHolder.country.setText(currentStudents.getCountry());
        studentViewHolder.date.setText(currentStudents.getRegisteredTime());

    }

    @Override
    public int getItemCount() {

        if (students!=null) {
            return students.size();
        }else { }
        return 0;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
        notifyDataSetChanged();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder{
        private TextView name,email,country,date;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            email = itemView.findViewById(R.id.tv_email);
            country = itemView.findViewById(R.id.tv_country);
            date = itemView.findViewById(R.id.tv_time);

        }
    }


}
