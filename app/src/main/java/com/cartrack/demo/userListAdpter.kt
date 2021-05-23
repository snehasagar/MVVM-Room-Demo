package com.cartrack.demo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cartrack.demo.databinding.RowItemUserlistBinding
import com.cartrack.demo.viewmodel.UserViewModel


class UsersListAdapter(private val arrayList: ArrayList<UserViewModel>, private val listener: (UserViewModel, Int) -> Unit)
    : RecyclerView.Adapter<UsersListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val peopleBinding: RowItemUserlistBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_item_userlist, parent, false)
        return ViewHolder(peopleBinding)
    }

    override fun getItemCount(): Int = arrayList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(arrayList[position], position, listener)
    }

    class ViewHolder(private val userBinding: RowItemUserlistBinding) : RecyclerView.ViewHolder(userBinding.root) {
        @SuppressLint("SetTextI18n")
        fun bindItem(ViewModel: UserViewModel, position: Int, listener: (UserViewModel, Int) -> Unit) {
            this.userBinding.userData = ViewModel
            userBinding.executePendingBindings()
            userBinding.textViewName.text = ViewModel.user.name
            userBinding.textViewEmail.text = ViewModel.user.email
            userBinding.textViewAddress.text =ViewModel.user.address?.street +  "\n" +  ViewModel.user.address?.city

          /*  if (ViewModel.user.isTemp == true) {
                userBinding.textViewUsertype.text = ""
            } else if (ViewModel.user.isUser == false && ViewModel.user.isOwner == true) {
                userBinding.textViewUsertype.setText(R.string.user_primary_type)
            } else if (ViewModel.user.isUser == false && ViewModel.user.isOwner == false) {
                userBinding.textViewUsertype.setText(R.string.user_secondary_type)
            } else if (ViewModel.user.isUser == true && ViewModel.user.isOwner == false) {
                userBinding.textViewUsertype.setText(R.string.user_you_secondary_type)
                PreferencesManager(userBinding.root.context).prefAccountOwner = true
            } else if (ViewModel.user.isUser == true && ViewModel.user.isOwner == true) {
                userBinding.textViewUsertype.setText(R.string.user_you_primary_type)
                PreferencesManager(userBinding.root.context).prefAccountOwner = true
            }*/
          itemView.setOnClickListener {
                listener(ViewModel, position)


            }
        }
    }
}