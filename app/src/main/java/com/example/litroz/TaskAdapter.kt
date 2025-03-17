package com.example.litroz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.litroz.data.model.Task
import com.example.litroz.databinding.TaskItemBinding
class TaskAdapter(private val onTaskClick: (Task) -> Unit) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task)
    }

    inner class TaskViewHolder(private val binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.cbCompletedTask.isChecked = task.isCompleted
            binding.tvTitleTask.text = task.title
            binding.tvDescriptionTask.text = task.description
            binding.root.setOnClickListener { onTaskClick(task) }
        }
    }

    // Implementando um DiffCallback para otimizar as atualizações da lista
    class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
}
/*class TaskAdapter(private val onItemClick: (Task) -> Unit) :
    ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task)
    }

    inner class TaskViewHolder(private val binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            // Aqui estamos vinculando a Task à UI usando o ViewBinding.
            //binding.task = task // Isso usa a vinculação de dados

            // Se você quiser, pode manualmente definir valores para os elementos individuais
            // Exemplo:
            binding.tvTaskTitle.text = task.title
            //binding.taskDescription.text = task.description
            binding.cbTaskCompleted.isChecked = task.isCompleted
            binding.root.setOnClickListener {
                onItemClick(task)
            }
        }
    }

    class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
}*/
/*class TaskAdapter : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    // Criação da ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    // Ligando os dados ao ViewHolder
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)  // A função getItem() é fornecida pelo ListAdapter
        holder.bind(task)
    }

    // ViewHolder para vincular a interface de item com os dados
    inner class TaskViewHolder(private val binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            // Acessando diretamente os componentes do layout
            binding.tvTaskTitle.text = task.title  // Configura o título da tarefa
            //binding.taskDescription.text = task.description  // Configura a descrição da tarefa
            binding.cbTaskCompleted.isChecked = task.isCompleted  // Configura o estado da tarefa

            // Se quiser atualizar outras propriedades, faça isso aqui
            // Por exemplo: binding.taskCompleted.setOnCheckedChangeListener { ... }

        }
    }

    // Implementando o DiffUtil para comparar itens e otimizar atualizações
    class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id  // Comparação pelo ID (ou outro identificador único)
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem  // Verifica se o conteúdo dos itens é igual
        }
    }
}*/
/*class TaskAdapter(private val tasks: List<Task>, private val onItemClick: (Task) -> Unit) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvTaskTitle)

        init {
            itemView.setOnClickListener {
                onItemClick(tasks[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.title.text = task.title
    }

    override fun getItemCount() = tasks.size
} */