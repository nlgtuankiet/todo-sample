package com.sample.todo.main.tasks

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.sample.todo.domain.model.TaskMini

@EpoxyModelClass(layout = R2.layout.task_item)
abstract class TaskItemView : EpoxyModelWithHolder<TaskItemView.TaskItemHolder>() {

    @EpoxyAttribute
    var taskMini: TaskMini? = null

    @EpoxyAttribute
    lateinit var taskViewModel: TasksViewModel

    override fun bind(holder: TaskItemHolder) {
        val task = taskMini
        if (task != null) {
            holder.container.setOnClickListener {
                taskViewModel.onTaskItemClick(
                    taskId = task.id,
                    checked = task.isCompleted
                )
            }
            holder.title.text = task.title

            holder.is_completed.isChecked = task.isCompleted
            holder.is_completed.visibility = View.VISIBLE
            holder.is_completed.setOnCheckedChangeListener { _, isChecked ->
                taskViewModel.onTaskCheckBoxClick(task, isChecked)
            }
        } else {
            holder.container.setOnClickListener { }
            holder.title.text = ""

            holder.is_completed.visibility = View.GONE
            holder.is_completed.setOnCheckedChangeListener { _, _ ->
            }
        }
    }

    class TaskItemHolder : EpoxyHolder() {
        lateinit var container: ConstraintLayout
        lateinit var title: TextView
        lateinit var is_completed: CheckBox

        override fun bindView(itemView: View) {
            container = itemView.findViewById(R.id.container)
            title = itemView.findViewById(R.id.title)
            is_completed = itemView.findViewById(R.id.is_completed)
        }
    }
}