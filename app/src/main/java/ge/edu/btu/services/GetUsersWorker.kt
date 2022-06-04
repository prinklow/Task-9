package ge.edu.btu.services

import android.content.Context
import androidx.room.Room
import androidx.work.Worker
import androidx.work.WorkerParameters
import ge.edu.btu.services.api.ApiClient

class GetUsersWorker(
    appContext: Context, workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val users = ApiClient.usersService().getUsers().execute()
        if (users.isSuccessful) {
            users.body().let {
                val db = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java, "users.db"
                ).build()

                it?.data?.map { user ->
                    db.userDao().insert(
                        User(
                            firstName = user.firstName,
                            lastName = user.lastName,
                            avatar = user.avatar,
                            email = user.email
                        )
                    )
                }
            }
            return Result.success()
        }
        return Result.failure()
    }
}
