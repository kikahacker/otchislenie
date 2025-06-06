package com.example.otchislenie.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.otchislenie.domain.model.Category
import com.example.otchislenie.domain.model.Transaction
import com.example.otchislenie.domain.repository.FinanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun AddTransactionDialog(
    repository: FinanceRepository,
    type: String,
    onDismiss: () -> Unit,
    onAdd: (Transaction) -> Unit
) {
    val viewModel: AddTransactionViewModel = AddTransactionViewModel(repository)
    var amount by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    val categories by viewModel.getCategoriesByType(type).collectAsState(emptyList())

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (type == "expense") "Добавить расход" else "Добавить доход") },
        text = {
            Column {
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Сумма") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow {
                    items(categories) { category ->
                        FilterChip(
                            selected = selectedCategory?.id == category.id,
                            onClick = { selectedCategory = category },
                            leadingIcon = {
                                Icon(
                                    painter = rememberVectorPainter(Icons.Default.Category),
                                    contentDescription = null,
                                    tint = Color(category.color)
                                )
                            },
                            label = { Text(category.name) }
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val transaction = Transaction(
                        amount = amount.toDoubleOrNull() ?: 0.0,
                        category = selectedCategory ?: Category.default(),
                        date = System.currentTimeMillis(),
                        note = "",
                        type = type
                    )
                    onAdd(transaction)
                },
                enabled = amount.isNotBlank() && selectedCategory != null
            ) {
                Text("Добавить")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val repository: FinanceRepository
) : ViewModel() {
    fun getCategoriesByType(type: String): Flow<List<Category>> {
        return repository.getCategoriesByType(type)
    }
}