package in.ed.poonacollege.androidassignments.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Stack;

import in.ed.poonacollege.androidassignments.R;

public class Question3Fragment extends Fragment implements View.OnClickListener {

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button buttonDecimal;
    private Button button0;
    private Button buttonClear;
    private Button buttonAdd;
    private Button buttonSubtract;
    private Button buttonMultiply;
    private Button buttonDivide;
    private Button buttonEquals;

    private Stack<String> calculatorStack = new Stack<>();
    private Stack<Float> valueStack = new Stack<>();
    private EditText field;

    public Question3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question3, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        field = view.findViewById(R.id.field);

        //Disable the keyboard on the EditText
        field.setOnTouchListener((v, event) -> {
            int inType = field.getInputType(); // backup the input type
            field.setInputType(InputType.TYPE_NULL); // disable soft input
            field.onTouchEvent(event); // call native handler
            field.setInputType(inType); // restore input type
            return true; // consume touch event
        });

        initButtons(view);

        setListeners();
    }

    private void setListeners() {
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        buttonDecimal.setOnClickListener(this);
        button0.setOnClickListener(this);
        buttonClear.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);
        buttonSubtract.setOnClickListener(this);
        buttonMultiply.setOnClickListener(this);
        buttonDivide.setOnClickListener(this);
        buttonEquals.setOnClickListener(this);
    }


    private void initButtons(View view) {
        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        button4 = view.findViewById(R.id.button4);
        button5 = view.findViewById(R.id.button5);
        button6 = view.findViewById(R.id.button6);
        button7 = view.findViewById(R.id.button7);
        button8 = view.findViewById(R.id.button8);
        button9 = view.findViewById(R.id.button9);
        buttonDecimal = view.findViewById(R.id.buttonDecimal);
        button0 = view.findViewById(R.id.button0);
        buttonClear = view.findViewById(R.id.buttonClear);
        buttonAdd = view.findViewById(R.id.buttonAdd);
        buttonSubtract = view.findViewById(R.id.buttonSubtract);
        buttonMultiply = view.findViewById(R.id.buttonMultiply);
        buttonDivide = view.findViewById(R.id.buttonDivide);
        buttonEquals = view.findViewById(R.id.buttonEquals);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                addStringToStack("1");
                break;
            case R.id.button2:
                addStringToStack("2");
                break;
            case R.id.button3:
                addStringToStack("3");
                break;
            case R.id.button4:
                addStringToStack("4");
                break;
            case R.id.button5:
                addStringToStack("5");
                break;
            case R.id.button6:
                addStringToStack("6");
                break;
            case R.id.button7:
                addStringToStack("7");
                break;
            case R.id.button8:
                addStringToStack("8");
                break;
            case R.id.button9:
                addStringToStack("9");
                break;
            case R.id.buttonDecimal:
                addStringToStack(".");
                break;
            case R.id.button0:
                addStringToStack("0");
                break;
            case R.id.buttonClear:
                clearClicked();
                break;
            case R.id.buttonAdd:
                addOperatorToStack("+");
                break;
            case R.id.buttonSubtract:
                addOperatorToStack("-");
                break;
            case R.id.buttonMultiply:
                addOperatorToStack("*");
                break;
            case R.id.buttonDivide:
                addOperatorToStack("/");
                break;
            case R.id.buttonEquals:
                calculate();
                break;
        }
    }

    private void calculate() {
        for(int i=0;i<calculatorStack.size();i++){
            Log.d("Question3","value : " + calculatorStack.get(i));
            String value = calculatorStack.get(i);
            if(!value.matches("([+\\-*/])")){
                valueStack.push(Float.valueOf(value));
            } else{
                float finalValue = 0;
                float firstValue = valueStack.pop();
                float secondValue = Integer.valueOf(calculatorStack.get(i+1));
                i++;
                switch (value){
                    case "+":
                        finalValue = firstValue + secondValue;
                        break;

                    case "-":
                        finalValue = firstValue - secondValue;
                        break;

                    case "*":
                        finalValue = firstValue * secondValue;
                        break;

                    case "/":
                        finalValue = firstValue / secondValue;
                        break;
                }
                valueStack.push(finalValue);
            }
        }

        calculatorStack.clear();
        calculatorStack.push(valueStack.peek().toString());
        field.setText(valueStack.pop().toString());
    }

    @SuppressLint("SetTextI18n")
    private void addOperatorToStack(String operator) {
        String topValue = calculatorStack.peek();
        if(topValue.matches("([+\\-*/])")){
            calculatorStack.pop();
            clearClicked();
        }
        calculatorStack.push(operator);
        field.setText(field.getEditableText().toString()+operator);
    }

    private void clearClicked(){
        String fieldValue = field.getEditableText().toString();
        fieldValue = fieldValue.substring(0,fieldValue.length()-1);
        field.setText(fieldValue);
    }

    @SuppressLint("SetTextI18n")
    private void addStringToStack(String number){
        String value = "";
        if(!calculatorStack.empty() && !calculatorStack.peek().matches("([+\\-*/])")){
            value = calculatorStack.pop();
        }
        if(value.matches("([+\\-*/])")){
            calculatorStack.push(value);
        } else {
            value += number;
            calculatorStack.push(value);
        }
        field.setText(field.getEditableText().toString()+number);
    }
}
