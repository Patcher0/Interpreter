package cn.klee.interpreter.impl;

import cn.klee.interpreter.Interpreter;
import cn.klee.interpreter.Op;

import java.util.HashMap;
import java.util.Map;

public class StringInterpreter extends Interpreter<String,String, Map<String,String>> {
    int index = 0;
    String key = "EMPTY";
    String value = "EMPTY";
    Map<String,String> mappedString = new HashMap<>();
    public StringInterpreter(String value) {
        super(value);
        this.processing = "";
        this.putOp("entry",(strInt) -> {
            char processing1 = input.charAt(index);
            index++;
            switch (processing1) {
                case ';' -> {
                    this.key = processing;
                    this.processing = "";
                }
                case '\n' -> {
                    this.value = processing;
                    this.processing = "";
                    this.mappedString.put(key,value);
                    this.key = "";
                    this.value = "";
                }
                default -> processing += processing1;
            }
            if(index >= input.length()){
                return new Op.Flow<>(Op.OpOp.CONTINUE_PROCESS,null);
            }
            return new Op.Flow<>(Op.OpOp.DONE,mappedString);
        });
    }
}
