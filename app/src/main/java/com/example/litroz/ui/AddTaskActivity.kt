package com.example.litroz.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.litroz.data.model.Task
import com.example.litroz.databinding.ActivityAddTaskBinding
import com.example.litroz.viewmodel.TaskViewModel

class AddTaskActivity : AppCompatActivity() {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa o ViewModel
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)


        // Ao clicar no botão de salvar, salva as alterações
        binding.addTask.setOnClickListener {
            // Recupera os dados dos campos de entrada
            val title = binding.etTitleTask.text.toString()
            val description = binding.etDescriptionTask.text.toString()
            val isCompleted = binding.cbEditCompletedTask.isChecked

            // Valida se os campos de título e descrição não estão vazios
            if (title.isNotEmpty() && description.isNotEmpty()) {
                // Cria uma nova tarefa
                val newTask = Task(
                    title = title,
                    description = description,
                    isCompleted = isCompleted,
                    isDeleted = false,
                    syncStatus = false,
                    isNew = true
                )

                // Adiciona a nova tarefa no ViewModel
                taskViewModel.addTask(newTask)

                Toast.makeText(this, "Tarefa Adicionada!", Toast.LENGTH_SHORT).show()

                // Fecha a atividade após adicionar a tarefa
                finish()
            } else {
                // Exibe um alerta caso algum campo obrigatório não tenha sido preenchido
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }

    }

}