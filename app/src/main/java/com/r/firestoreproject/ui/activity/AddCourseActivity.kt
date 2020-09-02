package com.r.firestoreproject.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.r.dbfirestrore.model.AppConstants
import com.r.dbfirestrore.model.Course
import com.r.firestoreproject.R
import kotlinx.android.synthetic.main.activity_add_course.*

class AddCourseActivity : AppCompatActivity() {
    var db: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)


        // Access a Cloud Firestore instance from your Activity
        db = Firebase.firestore

        val data_course=intent
        if(data_course.getStringExtra(AppConstants.UPDATE)==AppConstants.UPDATE){
            btn_add.text=getString(R.string.update)
            tv_title.text=getString(R.string.update)
            val id=data_course.getStringExtra(AppConstants.ID_COURSE)
            val name=data_course.getStringExtra(AppConstants.COURSE_NAME)
            val description=data_course.getStringExtra(AppConstants.DESCRIPTION)
            val trainer_name=data_course.getStringExtra(AppConstants.TRAINER_NAME)
            val hours_num=data_course.getStringExtra(AppConstants.HOURS)
            val price=data_course.getStringExtra(AppConstants.COURSE_PRICE)
            val date=data_course.getStringExtra(AppConstants.EXPIRY_DATE)

            et_courseName.setText(name)
            et_description.setText(description)
            et_trainer_name.setText(trainer_name)
            et_hours_num.setText(hours_num)
            et_price.setText(price)
            expiryDate.setText(date)
            val center=data_course.getStringExtra(AppConstants.CENTER_NAME)
            when {
                center == AppConstants.GSG ->
                { sp_center.setSelection(0) }
               center==AppConstants.GGATAWAY ->
               {sp_center.setSelection(1) }
              center==AppConstants.VISIONPLUS ->
               {sp_center.setSelection(2) }
                center==AppConstants.COMPUTERLAND ->
               {sp_center.setSelection(3) }

            }
            val center_name=sp_center.selectedItem.toString()
            btn_add.setOnClickListener {
                updateCourseById( id,name,description,trainer_name,hours_num,price,center_name,date)
            }
        }else {
            btn_add.setOnClickListener {
                var course_name = et_courseName.text.toString()
                var description = et_description.text.toString()
                var trainer_name = et_trainer_name.text.toString()
                var hours_num = et_hours_num.text.toString()
                var course_price = et_price.text.toString()
                var center_name = sp_center.selectedItem.toString()
                var expiryDate = expiryDate.text.toString()

                when {
                    course_name.isEmpty() -> {
                        Toast.makeText(this, "Please all fields required", Toast.LENGTH_SHORT)
                            .show()
                    }
                    description.isEmpty() -> {
                        Toast.makeText(this, "Please all fields required", Toast.LENGTH_SHORT)
                            .show()
                    }
                    trainer_name.isEmpty() -> {
                        Toast.makeText(this, "Please all fields required", Toast.LENGTH_SHORT)
                            .show()
                    }
                    hours_num.isEmpty() -> {
                        Toast.makeText(this, "Please all fields required", Toast.LENGTH_SHORT)
                            .show()
                    }
                    course_price.isEmpty() -> {
                        Toast.makeText(this, "Please all fields required", Toast.LENGTH_SHORT)
                            .show()
                    }
                    expiryDate.isEmpty() -> {
                        Toast.makeText(this, "Please all fields required", Toast.LENGTH_SHORT)
                            .show()
                    }
                    else -> {
                        addCourse(
                            course_name,
                            description,
                            trainer_name,
                            hours_num.toInt(),
                            course_price.toDouble(),
                            center_name,
                            expiryDate
                        )
                    }

                }

            }
        }
    }

    private fun updateCourseById(id:String,name: String, description: String, trainerName: String, hoursNum: String, price: String, spCenter: String, date: String) {
        val course = Course(name,description,trainerName, hoursNum!!.toInt(),
            price!!.toDouble(),spCenter,date)


        db!!.collection(AppConstants.COLLECTION_USER)
            .document(id)
            .set(course)
            .addOnSuccessListener {
                Toast.makeText(this, "Update Success", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                Toast.makeText(this, "Error update document", Toast.LENGTH_SHORT).show()

            }
    }


    private fun addCourse(courseName: String, description:String,trainerName: String, hoursNum: Int, coursePrice: Double, centerName: String,expiryDate:String) {

        var course = Course(courseName, description,trainerName,hoursNum,coursePrice,centerName,expiryDate)

        db!!.collection(AppConstants.COLLECTION_COURSE)
            .add(course)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this, "add Success", Toast.LENGTH_SHORT).show()
                Log.d("Reem", "DocumentSnapshot added with ID:  ${documentReference.id}")
                finish()
            }
            .addOnFailureListener { e ->

                Toast.makeText(this, "Error adding document", Toast.LENGTH_SHORT).show()
                Log.w("reem", e.message.toString())
            }
    }
}
