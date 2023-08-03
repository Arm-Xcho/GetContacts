package com.example.getcontacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.getcontacts.databinding.ContactsRvBinding
import java.security.PrivateKey

class MyRecyclerViewAdapter(
    private val data: List<RvData>,
    private val onNameClick: (String) -> Unit,
    private val onPhoneNumberClick: (String) -> Unit,
) : RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ContactsRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(
            binding, onNameClick, onPhoneNumberClick
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
    }


    class MyViewHolder(
        private val viewBinding: ContactsRvBinding,
        private val onNameClick: (String) -> Unit,
        private val onPhoneNumberClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(itemData: RvData) = with(viewBinding) {
            tvName.text = itemData.name
            tvName.setOnClickListener {
                onNameClick(itemData.name)
            }
            tvPhoneNumber.text = itemData.phone
            tvPhoneNumber.setOnClickListener {
                onPhoneNumberClick(itemData.phone)
            }
        }
    }
}