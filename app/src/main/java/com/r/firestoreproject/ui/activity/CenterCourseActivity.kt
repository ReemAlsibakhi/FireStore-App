package com.r.firestoreproject.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.r.dbfirestrore.adapter.CourseAdapter
import com.r.dbfirestrore.model.AppConstants
import com.r.dbfirestrore.model.Course
import com.r.firestoreproject.R
import kotlinx.android.synthetic.main.activity_center_course.*

class CenterCourseActivity : AppCompatActivity() {
    var db: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_center_course)
        db = Firebase.firestore


        val data = intent
        when {
            data.getStringExtra(AppConstants.GSG) == AppConstants.GSG -> {
                tv_centerTitle.text = AppConstants.GSG
                viewAllCourses(AppConstants.GSG)
            }
            data.getStringExtra(AppConstants.VISIONPLUS) == AppConstants.VISIONPLUS -> {
                tv_centerTitle.text = AppConstants.VISIONPLUS
                viewAllCourses(AppConstants.VISIONPLUS)
            }
            data.getStringExtra(AppConstants.GGATAWAY) == AppConstants.GGATAWAY -> {
                tv_centerTitle.text = AppConstants.GGATAWAY
                viewAllCourses(AppConstants.GGATAWAY)
            }
            data.getStringExtra(AppConstants.COMPUTERLAND) == AppConstants.COMPUTERLAND -> {
                tv_centerTitle.text = AppConstants.COMPUTERLAND
                viewAllCourses(AppConstants.COMPUTERLAND)
            }

        }
    }

    private fun viewAllCourses(title: String): ArrayList<Course> {
        startAnim()
        val data = ArrayList<Course>()
        db!!.collection(AppConstants.COLLECTION_COURSE)
            .whereEqualTo("centerName", title)
            .get()
            .addOnSuccessListener { documents ->
                stopAnim()
                for (document in documents) {
                    Toast.makeText(this, "sucess get Center", Toast.LENGTH_LONG).show()
                    val course = document.toObject(Course::class.java)
                    course.id = document.id
                    data.add(course)
                    rv_centers.layoutManager = LinearLayoutManager(this)
                    val adapter = CourseAdapter(this, data, db!!)
                    rv_centers.adapter = adapter
                    Log.d("TAG", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                stopAnim()
                Toast.makeText(this, "failed get Center", Toast.LENGTH_LONG).show()

              //  Log.w("TAG", "Error getting documents: ", exception)
            }
        return data
    }

    fun startAnim() {
        avi.show()
        // or avi.smoothToShow();
    }
    fun stopAnim() {
        avi.hide()
        // or avi.smoothToHide();
    }


}
