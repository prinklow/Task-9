package ge.edu.btu.services

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.work.*


class MainActivity : AppCompatActivity() {

    private lateinit var usersRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "users.db"
        ).allowMainThreadQueries().build()

        usersRecyclerView = findViewById(R.id.usersRecyclerView)

        usersRecyclerView.layoutManager = LinearLayoutManager(this)

        val constraint: Constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val getUsersWorker: WorkRequest = OneTimeWorkRequestBuilder<GetUsersWorker>()
            .setConstraints(constraint)
            .build()

        WorkManager
            .getInstance(this)
            .enqueue(getUsersWorker)

        WorkManager.getInstance(this)
            .getWorkInfoByIdLiveData(getUsersWorker.id)
            .observe(this) { workStatus ->
                if (workStatus?.state?.isFinished == true) {
                    usersRecyclerView.adapter =
                        UsersRecyclerAdapter(db.userDao().getAll().map { it } as ArrayList<User>)
                }
            }
    }
}
