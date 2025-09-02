package com.example.dropdownmenuexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.dropdownmenuexample.ui.theme.DropdownMenuExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DropdownMenuExampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainContent(innerPadding)
                }
            }
        }
    }
}

@Composable
private fun MainContent(innerPadding: PaddingValues) {
    val coffeeDrinks by remember {
        mutableStateOf(
            listOf(
                "Americano",
                "Cappuccino",
                "Espresso",
                "Latte",
                "Mocha"
            )
        )
    }
    // Initialize selectedText with the first item from the list
    var selectedText by remember { mutableStateOf(coffeeDrinks[0]) }
    Column(modifier = Modifier.padding(innerPadding)) {
        Text(text = "Which coffee")
        MyDropdownMenu(
            items = coffeeDrinks,
            selectedItem = selectedText,
            onItemSelected = { selectedText = it }
        )
        Text(text = "You selected: $selectedText")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// https://alexzh.com/jetpack-compose-dropdownmenu/
// https://gist.github.com/tpoisson/15835d1df5a9f10f0baea838cea1dc1e
fun MyDropdownMenu(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        // ExposedDropdownMenuBox https://composables.com/material3/exposeddropdownmenubox
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            TextField(
                value = selectedItem,
                onValueChange = {
                    /* No need to handle changes here as it's read-only */
                },
                readOnly = true, // MenuAnchor, editable, change to false
                singleLine = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                // gradle: implementation 'androidx.compose.material3:material3:1.0.0-alpha06'
                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            onItemSelected(item)
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DropdownMenuPreview() {
    DropdownMenuExampleTheme {
        val items = listOf(
            "Americano",
            "Cappuccino",
            "Espresso",
            "Latte",
            "Mocha"
        )
        MyDropdownMenu(
            items = items,
            selectedItem = items[0],
            onItemSelected = {}
        )
    }
}