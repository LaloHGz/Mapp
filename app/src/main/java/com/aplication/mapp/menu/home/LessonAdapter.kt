package com.aplication.mapp.menu.home

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aplication.mapp.R
import com.aplication.mapp.login.LoginEmailActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LessonAdapter: RecyclerView.Adapter<LessonAdapter.ViewHolder>() {

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]

    private lateinit var database: DatabaseReference

    private val titles = arrayOf(
        R.string.TEMA1,
        R.string.TEMA2,
        R.string.TEMA3,
        R.string.TEMA4,
        R.string.TEMA5,
        R.string.TEMA6,
        R.string.TEMA7,
        R.string.TEMA8,
        R.string.TEMA9,
        R.string.TEMA10)

    private val images = intArrayOf(
        R.drawable.ic_algoritmos_portada,
        R.drawable.ic_algoritmos_portada,
        R.drawable.ic_algoritmos_portada,
        R.drawable.ic_algoritmos_portada,
        R.drawable.ic_algoritmos_portada,
        R.drawable.ic_algoritmos_portada,
        R.drawable.ic_algoritmos_portada,
        R.drawable.ic_algoritmos_portada,
        R.drawable.ic_algoritmos_portada,
        R.drawable.ic_algoritmos_portada
    )


    private var siz: Int = 1


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_lessons, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, @SuppressLint("RecyclerView") i: Int) {

        database = Firebase.database.reference
        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = Firebase.auth
        // [END initialize_auth]

        val id: String? = auth.uid
        database.child("users").child(id!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){

                    //t1 = tema 1,   t2_3 = tema 2 nivel 3
                    val t1 = snapshot.child("tiempo_n1t1").value.toString()
                    val t2 = snapshot.child("tiempo_n1t2").value.toString()
                    val t3 = snapshot.child("tiempo_n1t3").value.toString()
                    val t4 = snapshot.child("tiempo_n1t4").value.toString()
                    val t5 = snapshot.child("tiempo_n1t5").value.toString()
                    val t6 = snapshot.child("tiempo_n1t6").value.toString()
                    val t7 = snapshot.child("tiempo_n1t7").value.toString()
                    val t8 = snapshot.child("tiempo_n1t8").value.toString()
                    val t9 = snapshot.child("tiempo_n1t9").value.toString()
                    val t10 = snapshot.child("tiempo_n1t10").value.toString()

                    val t1_2= snapshot.child("tiempo_n2t1").value.toString()
                    val t2_2= snapshot.child("tiempo_n2t2").value.toString()
                    val t3_2= snapshot.child("tiempo_n2t3").value.toString()
                    val t4_2= snapshot.child("tiempo_n2t4").value.toString()
                    val t5_2= snapshot.child("tiempo_n2t5").value.toString()
                    val t6_2= snapshot.child("tiempo_n2t6").value.toString()
                    val t7_2= snapshot.child("tiempo_n2t7").value.toString()
                    val t8_2= snapshot.child("tiempo_n2t8").value.toString()
                    val t9_2= snapshot.child("tiempo_n2t9").value.toString()
                    val t10_2= snapshot.child("tiempo_n2t10").value.toString()

                    val t1_3 = snapshot.child("tiempo_n3t1").value.toString()
                    val t2_3 = snapshot.child("tiempo_n3t2").value.toString()
                    val t3_3 = snapshot.child("tiempo_n3t3").value.toString()
                    val t4_3 = snapshot.child("tiempo_n3t4").value.toString()
                    val t5_3 = snapshot.child("tiempo_n3t5").value.toString()
                    val t6_3 = snapshot.child("tiempo_n3t6").value.toString()
                    val t7_3 = snapshot.child("tiempo_n3t7").value.toString()
                    val t8_3 = snapshot.child("tiempo_n3t8").value.toString()
                    val t9_3 = snapshot.child("tiempo_n3t9").value.toString()
                    val t10_3 =  snapshot.child("tiempo_n3t10").value.toString()

                    val t1_4 = snapshot.child("tiempo_n4t1").value.toString()
                    val t2_4 = snapshot.child("tiempo_n4t2").value.toString()
                    val t3_4 = snapshot.child("tiempo_n4t3").value.toString()
                    val t4_4 = snapshot.child("tiempo_n4t4").value.toString()
                    val t5_4 = snapshot.child("tiempo_n4t5").value.toString()
                    val t6_4 = snapshot.child("tiempo_n4t6").value.toString()
                    val t7_4 = snapshot.child("tiempo_n4t7").value.toString()
                    val t8_4 = snapshot.child("tiempo_n4t8").value.toString()
                    val t9_4 = snapshot.child("tiempo_n4t9").value.toString()
                    val t10_4 = snapshot.child("tiempo_n4t10").value.toString()

                    val t1_5 = snapshot.child("tiempo_n5t1").value.toString()
                    val t2_5 = snapshot.child("tiempo_n5t2").value.toString()
                    val t3_5 = snapshot.child("tiempo_n5t3").value.toString()
                    val t4_5 = snapshot.child("tiempo_n5t4").value.toString()
                    val t5_5 = snapshot.child("tiempo_n5t5").value.toString()
                    val t6_5 = snapshot.child("tiempo_n5t6").value.toString()
                    val t7_5 = snapshot.child("tiempo_n5t7").value.toString()
                    val t8_5 = snapshot.child("tiempo_n5t8").value.toString()
                    val t9_5 = snapshot.child("tiempo_n5t9").value.toString()
                    val t10_5 = snapshot.child("tiempo_n5t10").value.toString()

                    if (i == 0){
                        if (t1 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "1 de 5 ejercicios han sido completados"
                        }
                        if (t1_2 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "2 de 5 ejercicios han sido completados"
                        }
                        if (t1_3 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "3 de 5 ejercicios han sido completados"
                        }
                        if (t1_4 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "4 de 5 ejercicios han sido completados"
                        }
                        if (t1_5 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "5 de 5 ejercicios han sido completados"
                        }
                    }else if (i == 1){
                        if (t2 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "1 de 5 ejercicios han sido completados"
                        }
                        if (t2_2 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "2 de 5 ejercicios han sido completados"
                        }
                        if (t2_3 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "3 de 5 ejercicios han sido completados"
                        }
                        if (t2_4 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "4 de 5 ejercicios han sido completados"
                        }
                        if (t2_5 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "5 de 5 ejercicios han sido completados"

                        }
                    }else if (i == 2){
                        if (t3 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "1 de 5 ejercicios han sido completados"

                        }
                        if (t3_2 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "2 de 5 ejercicios han sido completados"
                        }
                        if (t3_3 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "3 de 5 ejercicios han sido completados"
                        }
                        if (t3_4 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "4 de 5 ejercicios han sido completados"
                        }
                        if (t3_5 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "5 de 5 ejercicios han sido completados"
                        }
                    }else if (i == 3){
                        if (t4 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "1 de 5 ejercicios han sido completados"

                        }
                        if (t4_2 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "2 de 5 ejercicios han sido completados"
                        }
                        if (t4_3 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "3 de 5 ejercicios han sido completados"
                        }
                        if (t4_4 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "4 de 5 ejercicios han sido completados"
                        }
                        if (t4_5 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "5 de 5 ejercicios han sido completados"
                        }
                    }else if (i == 4){
                        if (t5 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "1 de 5 ejercicios han sido completados"

                        }
                        if (t5_2 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "2 de 5 ejercicios han sido completados"
                        }
                        if (t5_3 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "3 de 5 ejercicios han sido completados"
                        }
                        if (t5_4 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "4 de 5 ejercicios han sido completados"
                        }
                        if (t5_5 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "5 de 5 ejercicios han sido completados"
                        }
                    }else if (i == 5){
                        if (t6 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "1 de 5 ejercicios han sido completados"

                        }
                        if (t6_2 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "2 de 5 ejercicios han sido completados"
                        }
                        if (t6_3 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "3 de 5 ejercicios han sido completados"
                        }
                        if (t6_4 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "4 de 5 ejercicios han sido completados"
                        }
                        if (t6_5 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "5 de 5 ejercicios han sido completados"
                        }
                    }else if (i == 6){
                        if (t7 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "1 de 5 ejercicios han sido completados"

                        }
                        if (t7_2 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "2 de 5 ejercicios han sido completados"
                        }
                        if (t7_3 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "3 de 5 ejercicios han sido completados"
                        }
                        if (t7_4 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "4 de 5 ejercicios han sido completados"
                        }
                        if (t7_5 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "5 de 5 ejercicios han sido completados"
                        }
                    }else if (i == 7){
                        if (t8 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "1 de 5 ejercicios han sido completados"

                        }
                        if (t8_2 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "2 de 5 ejercicios han sido completados"
                        }
                        if (t8_3 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "3 de 5 ejercicios han sido completados"
                        }
                        if (t8_4 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "4 de 5 ejercicios han sido completados"
                        }
                        if (t8_5 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "5 de 5 ejercicios han sido completados"
                        }
                    }else if (i == 8){
                        if (t9 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "1 de 5 ejercicios han sido completados"

                        }
                        if (t9_2 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "2 de 5 ejercicios han sido completados"
                        }
                        if (t9_3 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "3 de 5 ejercicios han sido completados"
                        }
                        if (t9_4 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "4 de 5 ejercicios han sido completados"
                        }
                        if (t9_5 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "5 de 5 ejercicios han sido completados"
                        }
                    }else if (i == 9){
                        if (t10 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "1 de 5 ejercicios han sido completados"

                        }
                        if (t10_2 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "2 de 5 ejercicios han sido completados"
                        }
                        if (t10_3 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "3 de 5 ejercicios han sido completados"
                        }
                        if (t10_4 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "4 de 5 ejercicios han sido completados"
                        }
                        if (t10_5 != "a")
                        {
                            viewHolder.progressLesson.progress += 20
                            viewHolder.itemProgress.text = "5 de 5 ejercicios han sido completados"
                        }
                    }



                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


        viewHolder.itemImage.setImageResource(images[i])
        viewHolder.itemLesson.text = viewHolder.itemLesson.context.getString(titles[i])

    }

    override fun getItemCount(): Int {

        database = Firebase.database.reference

        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = Firebase.auth
        // [END initialize_auth]

        val id: String? = auth.uid
        database.child("users").child(id!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){

                    //t1 = tema 1,   t2_3 = tema 2 nivel 3
                    val t1_5 = snapshot.child("tiempo_n5t1").value.toString()
                    val t2_5 = snapshot.child("tiempo_n5t2").value.toString()
                    val t3_5 = snapshot.child("tiempo_n5t3").value.toString()
                    val t4_5 = snapshot.child("tiempo_n5t4").value.toString()
                    val t5_5 = snapshot.child("tiempo_n5t5").value.toString()
                    val t6_5 = snapshot.child("tiempo_n5t6").value.toString()
                    val t7_5 = snapshot.child("tiempo_n5t7").value.toString()
                    val t8_5 = snapshot.child("tiempo_n5t8").value.toString()
                    val t9_5 = snapshot.child("tiempo_n5t9").value.toString()
                    val t10_5 = snapshot.child("tiempo_n5t10").value.toString()

                    if (t1_5 != "a"){
                        siz = 2
                    }
                    if (t2_5 != "a"){
                        siz = 3
                    }
                    if (t3_5 != "a"){
                        siz = 4
                    }
                    if (t4_5 != "a"){
                        siz = 5
                    }
                    if (t5_5 != "a"){
                        siz = 6
                    }
                    if (t6_5 != "a"){
                        siz = 7
                    }
                    if (t7_5 != "a"){
                        siz = 8
                    }
                    if (t8_5 != "a"){
                        siz = 9
                    }
                    if (t9_5 != "a"){
                        siz = 10
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        return siz
    }



    inner class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView
        var itemTitle: TextView
        var itemLesson: TextView
        var progressLesson: ProgressBar
        var itemProgress: TextView

        init {
            itemImage = itemView.findViewById(R.id.image_lesson)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemLesson = itemView.findViewById(R.id.item_lesson)
            progressLesson = itemView.findViewById(R.id.progressBar)
            itemProgress = itemView.findViewById(R.id.item_progress)

            itemView.setOnClickListener{
                val position: Int = adapterPosition
                val context = itemView.context

                database = Firebase.database.reference
                // [START initialize_auth]
                // Initialize Firebase Auth
                auth = Firebase.auth
                // [END initialize_auth]

                val id: String? = auth.uid
                database.child("users").child(id!!).addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.exists()){

                            //t1 = tema 1,   t2_3 = tema 2 nivel 3
                            val d1: String = snapshot.child("tiempo_diag_t1").value.toString()
                            val d2: String = snapshot.child("tiempo_diag_t2").value.toString()
                            val d3: String = snapshot.child("tiempo_diag_t3").value.toString()
                            val d4: String = snapshot.child("tiempo_diag_t4").value.toString()
                            val d5: String = snapshot.child("tiempo_diag_t5").value.toString()
                            val d6: String = snapshot.child("tiempo_diag_t6").value.toString()
                            val d7: String = snapshot.child("tiempo_diag_t7").value.toString()
                            val d8: String = snapshot.child("tiempo_diag_t8").value.toString()
                            val d9: String = snapshot.child("tiempo_diag_t9").value.toString()
                            val d10: String = snapshot.child("tiempo_diag_t10").value.toString()

                            if (position == 0){
                                if (d1 == "a"){
                                    // Intent to First Question of the subject
                                    val intent = Intent(context, LoginEmailActivity::class.java)
                                    context.startActivity(intent)
                                }else {
                                    // Intent to Subject Activity
                                    val intent = Intent(context, LoginEmailActivity::class.java)
                                    context.startActivity(intent)
                                }
                            }else if(position == 1){
                                if (d2 == "a"){
                                    // Intent to First Question of the subject
                                    val intent = Intent(context, LoginEmailActivity::class.java)
                                    context.startActivity(intent)
                                }else {
                                    // Intent to Subject Activity
                                    val intent = Intent(context, LoginEmailActivity::class.java)
                                    context.startActivity(intent)
                                }
                            }else if(position == 2){
                                if (d3 == "a"){
                                    // Intent to First Question of the subject
                                    val intent = Intent(context, LoginEmailActivity::class.java)
                                    context.startActivity(intent)
                                }else {
                                    // Intent to Subject Activity
                                    val intent = Intent(context, LoginEmailActivity::class.java)
                                    context.startActivity(intent)
                                }
                            }else if(position == 3){
                                if (d4 == "a"){
                                    // Intent to First Question of the subject
                                    val intent = Intent(context, LoginEmailActivity::class.java)
                                    context.startActivity(intent)
                                }else {
                                    // Intent to Subject Activity
                                    val intent = Intent(context, LoginEmailActivity::class.java)
                                    context.startActivity(intent)
                                }
                            }else if(position == 4){
                                if (d5 == "a"){
                                    // Intent to First Question of the subject
                                    val intent = Intent(context, LoginEmailActivity::class.java)
                                    context.startActivity(intent)
                                }else {
                                    // Intent to Subject Activity
                                    val intent = Intent(context, LoginEmailActivity::class.java)
                                    context.startActivity(intent)
                                }
                            }else if(position == 5){
                                if (d6 == "a"){
                                    // Intent to First Question of the subject
                                    val intent = Intent(context, LoginEmailActivity::class.java)
                                    context.startActivity(intent)
                                }else {
                                    // Intent to Subject Activity
                                    val intent = Intent(context, LoginEmailActivity::class.java)
                                    context.startActivity(intent)
                                }
                            }else if(position == 6){
                                if (d7 == "a"){
                                    // Intent to First Question of the subject
                                    val intent = Intent(context, LoginEmailActivity::class.java)
                                    context.startActivity(intent)
                                }else {
                                    // Intent to Subject Activity
                                    val intent = Intent(context, LoginEmailActivity::class.java)
                                    context.startActivity(intent)
                                }
                            }else if(position == 7){
                                if (d8 == "a"){
                                    // Intent to First Question of the subject
                                    val intent = Intent(context, LoginEmailActivity::class.java)
                                    context.startActivity(intent)
                                }else {
                                    // Intent to Subject Activity
                                    val intent = Intent(context, LoginEmailActivity::class.java)
                                    context.startActivity(intent)
                                }
                            }else if(position == 8){
                                if (d9 == "a"){
                                    // Intent to First Question of the subject
                                    val intent = Intent(context, LoginEmailActivity::class.java)
                                    context.startActivity(intent)
                                }else {
                                    // Intent to Subject Activity
                                    val intent = Intent(context, LoginEmailActivity::class.java)
                                    context.startActivity(intent)
                                }
                            }else if(position == 9){
                                if (d10 == "a"){
                                    // Intent to First Question of the subject
                                    val intent = Intent(context, LoginEmailActivity::class.java)
                                    context.startActivity(intent)
                                }else {
                                    // Intent to Subject Activity
                                    val intent = Intent(context, LoginEmailActivity::class.java)
                                    context.startActivity(intent)
                                }
                            }


                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })

            }

        }
    }
}