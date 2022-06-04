package ge.edu.btu.services

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class UsersRecyclerAdapter(
    private val items: ArrayList<User>
) : RecyclerView.Adapter<UsersRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.user_item,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(item: User) {
            itemView.findViewById<TextView>(R.id.userName).text = item.firstName
            itemView.findViewById<TextView>(R.id.userEmail).text = item.email
            Picasso.get().load(item.avatar).into(itemView.findViewById<ImageView>(R.id.userProfile))
        }
    }
}
