package com.auk.surveyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.auk.surveyapp.ui.theme.SurveyAppTheme
import androidx.compose.ui.Alignment


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SurveyAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Survey()
                }
            }
        }
    }
}

@Composable
fun Survey() {
    val questions = getDummyQuestions()
    var isSubmitted by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        LazyColumn {
            items(questions.size) { index ->
                SurveyQuestion(
                    questionNumber = index + 1,
                    questionText = questions[index]
                )

                // Check if it's the last question, then display the submit button
                if (index == questions.size - 1) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { isSubmitted = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Submit")
                    }
                }
            }
        }
    }

    if (isSubmitted) {
        AlertDialog(
            onDismissRequest = { isSubmitted = false },
            title = { Text("Submitted") },
            confirmButton = {
                Button(
                    onClick = { isSubmitted = false },
                ) {
                    Text("OK")
                }
            }
        )
    }
}


@Composable
fun SurveyQuestion(questionNumber: Int, questionText: String) {
    var selectedAnswer by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(
            text = "$questionNumber. $questionText",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        RadioButtonGroup(
            options = listOf(
                "Strongly Disagree",
                "Disagree",
                "Neither Agree nor Disagree",
                "Agree",
                "Strongly Agree"
            ),
            selectedOption = selectedAnswer,
            onOptionSelected = { selectedAnswer = it }
        )
    }
}

@Composable
fun RadioButtonGroup(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Column {
        options.forEach { option ->
            Row(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = option == selectedOption,
                    onClick = { onOptionSelected(option) }
                )
                Text(
                    text = option,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

fun getDummyQuestions(): List<String> {
    return listOf(
        "I enjoy sharing my recent location on social networks",
        "I make sure I check the SSL certificate quality when doing online financial transactions",
        "I prefer watching movies over reading books",
        "I find it important to exercise regularly",
        "I enjoy trying out new cuisines",
        "I prefer working alone rather than in a team",
        "I am interested in learning new languages",
        "I enjoy outdoor activities like hiking or camping",
        "I prefer a flexible work schedule over a fixed one",
        "I find it easy to adapt to new environments"
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSurvey() {
    SurveyAppTheme {
        Survey()
    }
}