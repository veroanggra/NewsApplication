package com.veroanggra.newsapplication.feature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.veroanggra.newsapplication.R
import com.veroanggra.newsapplication.common.model.News
import com.veroanggra.newsapplication.databinding.ItemArticleBinding

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
    private lateinit var itemArticleBinding: ItemArticleBinding

    inner class ArticleViewHolder(itemArticleBinding: ItemArticleBinding) :
        RecyclerView.ViewHolder(itemArticleBinding.root)

    private val differCallback = object : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        itemArticleBinding =
            ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(itemArticleBinding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((News) -> Unit)? = null

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        Glide.with(holder.itemView).load(article.urlToImage).apply(
            RequestOptions().placeholder(R.drawable.baseline_image_24)
                .error(R.drawable.baseline_image_24)
        ).into(itemArticleBinding.ivArticleImage)
        with(article) {
            itemArticleBinding.apply {
                tvSource.text = source?.name
                tvTitle.text = title
                tvDescription.text = description
                tvPublishedAt.text = publishedAt
            }
            holder.itemView.apply {
                setOnClickListener {
                    onItemClickListener?.let { it(article) }
                }
            }
        }
    }

    fun setOnItemClickListener(listener: (News) -> Unit) {
        onItemClickListener = listener
    }
}
