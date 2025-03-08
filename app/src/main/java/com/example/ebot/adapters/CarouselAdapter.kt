import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ebot.R
import com.example.ebot.models.HomeBannerList

class CarouselAdapter(private var images: List<Int>, private var bannerList: ArrayList<HomeBannerList>?, private val context: Context) : RecyclerView.Adapter<CarouselAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_carousel, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!bannerList.isNullOrEmpty() && position < bannerList!!.size) {
            val img = bannerList!![position].image
            if (!img.isNullOrEmpty()) {
                Glide.with(context).load(img).into(holder.imageView)
            }
        } else if (position < images.size) {
            holder.imageView.setImageResource(images[position])
        }
    }

    override fun getItemCount():Int {
        return if (!bannerList.isNullOrEmpty()) bannerList!!.size else images.size
    }

    fun updateBanners(list: ArrayList<HomeBannerList>, image: List<Int>?){
        bannerList = list
        images = image ?: emptyList()  // Prevent null pointer exception
        notifyDataSetChanged()

    }
}
