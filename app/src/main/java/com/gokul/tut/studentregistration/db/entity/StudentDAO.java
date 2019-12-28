package com.gokul.tut.studentregistration.db.entity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface StudentDAO {
    @Insert
    void Insert(Student student);

    @Query("SELECT * FROM student_table")
    List<Student> getAllStudent();

    @Delete
    void Delete(Student student);
}
