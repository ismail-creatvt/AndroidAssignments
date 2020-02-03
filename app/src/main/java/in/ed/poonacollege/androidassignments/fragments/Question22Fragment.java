package in.ed.poonacollege.androidassignments.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import in.ed.poonacollege.androidassignments.R;

public class Question22Fragment extends Fragment {

    private ArrayList<TodoItem> todoItems = new ArrayList<>();
    private ArrayList<TodoItem> filteredTodoItems = new ArrayList<>();

    public Question22Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question22, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText todoField = view.findViewById(R.id.todo_field);
        RecyclerView todoList = view.findViewById(R.id.todo_list);
        todoList.setAdapter(new TodoAdapter());
        todoList.setLayoutManager(new LinearLayoutManager(getContext()));

        view.findViewById(R.id.add_todo).setOnClickListener((v)->{
            String todo = todoField.getText().toString();
            if(todo.isEmpty()) return;

            TodoItem todoItem = new TodoItem((int) System.currentTimeMillis()/1000, todo);
            todoItems.add(todoItem);

            filteredTodoItems.add(todoItem);
            if(todoList.getAdapter()!= null)
                todoList.getAdapter().notifyItemInserted(filteredTodoItems.size()-1);
        });

        view.findViewById(R.id.search_todo).setOnClickListener((v)->{
            String todo = todoField.getText().toString();
            if(todo.isEmpty()) return;

            filteredTodoItems.clear();
            for(TodoItem item:todoItems){
                if(item.title.contains(todo)){
                    filteredTodoItems.add(item);
                }
            }
            if(todoList.getAdapter()!= null)
                todoList.getAdapter().notifyDataSetChanged();
        });
    }

    private class TodoItem{
        public int id;
        String title;

        TodoItem(int id, String title) {
            this.id = id;
            this.title = title;
        }
    }

    private class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder>{

        @NonNull
        @Override
        public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new TodoViewHolder(inflater.inflate(R.layout.todo_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
            holder.todoText.setText(filteredTodoItems.get(position).title);
            holder.deleteTodo.setOnClickListener((v)->{
                TodoItem todo = filteredTodoItems.get(holder.getAdapterPosition());
                filteredTodoItems.remove(todo);
                todoItems.remove(todo);
                notifyDataSetChanged();
            });
        }

        @Override
        public int getItemCount() {
            return filteredTodoItems.size();
        }

        private class TodoViewHolder extends RecyclerView.ViewHolder{
            TextView todoText;
            ImageView deleteTodo;

            TodoViewHolder(@NonNull View itemView) {
                super(itemView);

                todoText = itemView.findViewById(R.id.todo_text);
                deleteTodo = itemView.findViewById(R.id.delete_todo);
            }
        }

    }
}
