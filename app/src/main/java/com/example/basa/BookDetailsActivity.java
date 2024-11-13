package com.example.basa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookDetailsActivity extends AppCompatActivity {

    private TextView textViewName, textViewAuthor;
    private Button deleteButton;
    private DataBaseHelper dbHelper;
    private int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        textViewName = findViewById(R.id.textViewName);
        textViewAuthor = findViewById(R.id.textViewAuthor);
        deleteButton = findViewById(R.id.deleteButton);

        dbHelper = new DataBaseHelper(this);

        Intent intent = getIntent();
        bookId = intent.getIntExtra("BOOK_ID", -1);
        String bookName = intent.getStringExtra("BOOK_NAME");
        String bookAuthor = intent.getStringExtra("BOOK_AUTHOR");

        textViewName.setText(bookName);
        textViewAuthor.setText(bookAuthor);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBook();
            }
        });
        Button editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookDetailsActivity.this, EditBookActivity.class);
                intent.putExtra("BOOK_ID", bookId);
                intent.putExtra("BOOK_NAME", textViewName.getText().toString());
                intent.putExtra("BOOK_AUTHOR", textViewAuthor.getText().toString());
                startActivity(intent);
            }
        });
    }

    private void deleteBook() {
        int result = dbHelper.deleteBook(bookId);

        if (result > 0) {
            Toast.makeText(this, "Книга удалена", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Ошибка удаления книги", Toast.LENGTH_SHORT).show();
        }
    }
}