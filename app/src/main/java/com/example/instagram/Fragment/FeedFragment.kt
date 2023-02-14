package com.example.instagram.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.instagram.InstaPost
import com.example.instagram.R
import com.example.instagram.RetrofitService
import com.example.instagram.databinding.FragmentFeedBinding
import com.example.instagram.databinding.PostItemBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FeedFragment : Fragment() {

    lateinit var binding: FragmentFeedBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitService = retrofit.create(RetrofitService::class.java)

        retrofitService.getInstaPosts().enqueue(object : Callback<ArrayList<InstaPost>> {
            override fun onResponse(
                call: Call<ArrayList<InstaPost>>,
                response: Response<ArrayList<InstaPost>>
            ) {
                val postList = response.body()

                val postRecyclerView = binding.feedList
                postRecyclerView.adapter = PostRecyclerViewAdapter(
                    postList!!,
                    LayoutInflater.from(activity),
                    Glide.with(activity!!),
                )

            }

            override fun onFailure(call: Call<ArrayList<InstaPost>>, t: Throwable) {
            }
        })
    }
}

class PostRecyclerViewAdapter(
    val postList: ArrayList<InstaPost>,
    val inflater: LayoutInflater,
    val glide: RequestManager
) : RecyclerView.Adapter<PostRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val ownerImg: ImageView
        val ownerUsername: TextView
        val postImg: ImageView
        val postContent: TextView

        init {
            ownerImg = binding.ownerImg
            ownerUsername = binding.ownerUsername
            postImg = binding.postImg
            postContent = binding.postContent
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_item, parent, false)
        return ViewHolder(PostItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = postList.get(position)

        post.owner_profile.image?.let {
            glide.load(it).centerCrop().circleCrop().into(holder.ownerImg)
        }
        holder.ownerUsername.text = post.owner_profile.username

        post.image.let {
            glide.load(it).centerCrop().into(holder.postImg)
        }
        holder.postContent.text = post.content

    }

    override fun getItemCount(): Int {
        return postList.size
    }
}