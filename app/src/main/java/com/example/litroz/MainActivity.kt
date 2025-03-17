package com.example.litroz

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager


import com.example.litroz.databinding.ActivityMainBinding
import com.example.litroz.ui.adapter.TaskAdapter
import com.example.litroz.ui.AddTaskActivity
import com.example.litroz.ui.EditTaskActivity
import com.example.litroz.viewmodel.TaskViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.progressBar.visibility = View.VISIBLE


        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        taskViewModel.syncTasks()

        // Inicializando o Adapter
        taskAdapter = TaskAdapter { task ->
            // Ação ao clicar na tarefa (exemplo: marcar como concluída)
            val intent = Intent(binding.root.context, EditTaskActivity::class.java)
            intent.putExtra("TAREFA_OBJETO", task) // Passando a tarefa inteira
            binding.root.context.startActivity(intent)
        }

        // Configuração do RecyclerView
        binding.recyclerViewTasks.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = taskAdapter
        }


        // Observando as mudanças no LiveData
        taskViewModel.allTasks.observe(this, Observer { tasks ->
            // Filtrando as tarefas para excluir as que possuem isDeleted = true
            val filteredTasks = tasks.filter { !it.isDeleted }

            // Atualiza a lista do Adapter com as tarefas filtradas
            taskAdapter.submitList(filteredTasks)

            binding.progressBar.visibility = View.GONE

        })

        // Adicionar tarefa
        binding.addButton.setOnClickListener {

            val intent = Intent(binding.root.context, AddTaskActivity::class.java)
            binding.root.context.startActivity(intent)

        }
    }
}