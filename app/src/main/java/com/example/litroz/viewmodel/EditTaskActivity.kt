package com.example.litroz.viewmodel

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.litroz.data.model.Task
import com.example.litroz.databinding.ActivityEditTaskBinding

class EditTaskActivity : AppCompatActivity() {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var binding: ActivityEditTaskBinding

    var taskMain: Task ? = null
    private lateinit var task: Task

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa o ViewModel
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        // Recuperar o ID da tarefa enviada pela Activity anterior
        taskMain = intent.getParcelableExtra("TAREFA_OBJETO")

        binding.etTitleTask.setText(taskMain?.title)
        binding.etDescriptionTask.setText(taskMain?.description)
        binding.cbEditCompletedTask.isChecked = taskMain!!.isCompleted

        // Ao clicar no botão de salvar, salva as alterações
        binding.saveTask.setOnClickListener {
            val updatedTask = taskMain?.copy(
                title = binding.etTitleTask.text.toString(),
                description = binding.etDescriptionTask.text.toString(),
                isCompleted = binding.cbEditCompletedTask.isChecked
            )

            updatedTask?.let {
                taskViewModel.updateTask(it)
                Toast.makeText(this, "Tarefa Atualizada!", Toast.LENGTH_SHORT).show()
                finish() // Fecha a atividade após salvar a tarefa
            }
        }

        // Observando o LiveData para exibir a caixa de diálogo
        taskViewModel.showDialog.observe(this, Observer { showDialog ->
            if (showDialog) {
                showAlertDialog()
                // Resetando a LiveData para evitar que o diálogo seja exibido várias vezes
                taskViewModel.showDialog.value = false
            }
        })

        // Ao clicar no botão de deletar, seta atualiza o status do campo para deletado
        binding.deleteTask.setOnClickListener {
            taskViewModel.onActionClicked()
        }

    }
    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Atenção")
            .setMessage("Você tem certeza de que deseja deletar essa tarefa?")
            .setPositiveButton("Sim") { dialog, which ->
                val updatedTask = taskMain?.copy(
                    isDeleted = true
                )

                updatedTask?.let {
                    taskViewModel.updateTask(it)
                    Toast.makeText(this, "Tarefa Atualizada!", Toast.LENGTH_SHORT).show()
                }
                finish() // Fecha a atividade após salvar a tarefa
            }
            .setNegativeButton("Cancelar") { dialog, which ->
                Toast.makeText(this, "Você clicou em Cancelar", Toast.LENGTH_SHORT).show()
            }

        builder.create().show()
    }
}