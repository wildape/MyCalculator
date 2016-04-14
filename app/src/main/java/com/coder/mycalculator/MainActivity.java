package com.coder.mycalculator;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import org.javia.arity.Symbols;
import org.javia.arity.SyntaxException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity{

    private String expr;
    StringBuffer stringBuffer = new StringBuffer();
    TextView textViewFormula;
    TextView textViewResult;
    Button btnDel;

    Symbols symbols;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);

        symbols = new Symbols();
        textViewFormula = (TextView) findViewById(R.id.formula);
        textViewResult = (TextView) findViewById(R.id.result);
        btnDel = (Button) findViewById(R.id.del);
        btnDel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                textViewFormula.setText("");
                textViewResult.setText("");
                stringBuffer.delete(0,stringBuffer.length());
                //or//stringBuffer.replace(0,stringBuffer.length(),"");
                return true;
            }
        });
    }

    public void onButtonClick(View view) {
        switch (view.getId()) {
            /*
            case R.id.digit_0:
                textViewFormula.append("0");
                break;
            case R.id.digit_1:
                textViewFormula.append("1");
                break;
            case R.id.digit_2:
                textViewFormula.append("2");
                break;
            case R.id.digit_3:
                textViewFormula.append("3");
                break;
            case R.id.digit_4:
                textViewFormula.append("4");
                break;
            case R.id.digit_5:
                textViewFormula.append("5");
                break;
            case R.id.digit_6:
                textViewFormula.append("6");
                break;
            case R.id.digit_7:
                textViewFormula.append("7");
                break;
            case R.id.digit_8:
                textViewFormula.append("8");
                break;
            case R.id.digit_9:
                textViewFormula.append("9");
                break;
            case R.id.lparen:
                textViewFormula.append("(");
                break;
            case R.id.rparen:
                textViewFormula.append(")");
                break;
            case R.id.op_pow:
                textViewFormula.append("^");
                break;
            case R.id.dot:
                textViewFormula.append(".");
                break;
            case R.id.op_add:
                textViewFormula.append("+");
                break;
            case R.id.op_sub:
                textViewFormula.append("-");
                break;
            case R.id.op_mul:
                textViewFormula.append("×");
                break;
            case R.id.op_div:
                textViewFormula.append("÷");
                break;
             */
            case R.id.fun_cos:
            case R.id.fun_ln:
            case R.id.fun_log:
            case R.id.fun_sin:
            case R.id.fun_tan:
                stringBuffer.append(((Button)view).getText()+"(");
                if(stringBuffer.length()<16){
                    textViewFormula.setText(stringBuffer.toString());
                } else {
                    textViewFormula.setText(stringBuffer.toString().substring(stringBuffer.length()-16,stringBuffer.length()));
                }
                break;
            case R.id.equal:
                onEquals();
                break;
            case R.id.del:
                onDelete();
                break;
            default:
                stringBuffer.append(((Button)view).getText());
                if(stringBuffer.length()<16){
                    textViewFormula.setText(stringBuffer.toString());
                } else {
                    textViewFormula.setText(stringBuffer.toString().substring(stringBuffer.length()-16,stringBuffer.length()));
                }

                //textViewFormula.append(((Button) view).getText());
                break;
        }


    }

    private void onDelete() {
        textViewResult.setText("");
        if(stringBuffer.toString().length()>0){
            stringBuffer.deleteCharAt(stringBuffer.length()-1);
        }

        if(stringBuffer.length()<16){
            textViewFormula.setText(stringBuffer.toString());
        } else {
            textViewFormula.setText(stringBuffer.toString().substring(stringBuffer.length()-16,stringBuffer.length()));
        }
//        expr = stringBuffer.toString();
//        expr = textViewFormula.getText().toString();
//        if(expr.length()>0){
//            stringBuffer.deleteCharAt(stringBuffer.length()-1);
//            expr = expr.substring(0,expr.length()-1);
//            textViewFormula.setText(expr);
//        }

    }

    private void onEquals() {
       // expr = textViewFormula.getText().toString();
        expr = stringBuffer.toString();
        expr = expr.replace('×','*');
        expr = expr.replace('÷','/');
        //去掉末尾的符号
        while (expr.length() > 0 && "+-/*".indexOf(expr.charAt(expr.length() - 1)) != -1) {
            expr = expr.substring(0, expr.length() - 1);
        }
        boolean Result = true;
        double values = 0;
        try {
             values = symbols.eval(expr);
        } catch (SyntaxException e) {

            Result = false;
            e.printStackTrace();
        }
        if(Result){
            if(values < 2147483640 && values%1==0){
                textViewResult.setText(Integer.toString((int) values));
            }else{
                textViewResult.setText(Double.toString(values));
            }
        } else {
            if(!expr.equals("")){
                textViewResult.setText("error");
                //Toast.makeText(MainActivity.this,"输入有误",Toast.LENGTH_SHORT).show();
            }

        }

    }
}
