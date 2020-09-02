package com.r.firestoreproject.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.r.dbfirestrore.model.AppConstants
import com.r.firestoreproject.R
import com.r.firestoreproject.ui.model.Users
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // Initialize Firebase Auth
       mAuth = FirebaseAuth.getInstance();

        btn_join.setOnClickListener {
            //startAnim()

            val email=email.text.toString()
            val password=password.text.toString()
            val phone=phone.text.toString()

            if(email.isEmpty() || password.isEmpty() || phone.isEmpty()){
                Toast.makeText(this,"All fields are required",Toast.LENGTH_LONG).show()
            }else if(password.length < 6 ){
                Toast.makeText(this,"Password must have 6 characters",Toast.LENGTH_LONG).show()

            }else{
                login(email,password,phone)
            }
        }
    }

    private fun login(email: String, password: String, phone: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                 //   stopAnim()
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("create account", "createUserWithEmail:success")
                    val user = mAuth.currentUser
                    val userId= user!!.uid
                    val users = Users(userId,email,password,phone)
                    db.collection(AppConstants.COLLECTION_USER)
                        .add(users)
                        .addOnSuccessListener { documentReference ->
                            Toast.makeText(this, "Join success, you will send you message to your mobile ", Toast.LENGTH_SHORT).show()
                            Log.d("Reem", "DocumentSnapshot added with ID:  ${documentReference.id}")
                            finish()
//                            val i=Intent(this,
//                                MainActivity::class.java)
//                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or  Intent.FLAG_ACTIVITY_NEW_TASK )
//                            startActivity(i)

                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Error adding document", Toast.LENGTH_SHORT).show()
                            Log.w("reem", "Error adding document", e)
                        }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "You can not register with email and password", Toast.LENGTH_SHORT).show()
                }

                // ...
            }
    }
}

