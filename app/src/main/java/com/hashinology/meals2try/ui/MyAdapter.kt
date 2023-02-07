package com.hashinology.meals2try.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hiltretrofitproject.model.Meal
import com.hashinology.meals2try.databinding.MealListItemBinding
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyAdapter @Inject constructor(): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private val callBack = object: DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callBack)

    private var onItemClickListner: ((Meal) -> Unit)? = null

    inner class MyViewHolder(val binding: MealListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: Meal){
            binding.apply {
                meal.also {
                    tvMealId.text = "Id: ${it.idMeal}"
                    tvMealName.text = it.strMeal
                    Glide.with(ivMeal.rootView).load(it.strMealThumb). into(ivMeal)
                }

                binding.root.setOnClickListener{
                    onItemClickListner?.let {
                        it(meal)
                    }
                }
            }
        }
    }

    fun setOnItemclickListner(Listner: (Meal) -> Unit){
        onItemClickListner = Listner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = MealListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val meal = differ.currentList.get(position)
        holder.bind(meal)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}