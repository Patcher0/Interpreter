package cn.klee.interpreter.impl;

import cn.klee.interpreter.Interpreter;
import cn.klee.interpreter.Op;

import java.util.HashMap;
import java.util.Map;

public class StringInterpreter extends Interpreter<String,StringBuilder, Map<String,String>> {
    int index = 0;
    String key = "EMPTY";
    Map<String,String> mappedString = new HashMap<>();
    public StringInterpreter(String value) {
        super(value);
        this.processing = new StringBuilder();
        this.putOp("entry",(strInt) -> {
            char processing1 = input.charAt(index);
            index++;
            switch (processing1) {
                case ';' -> {
                    this.key = processing.toString();
                    this.processing.setLength(0);

                }
                case '\n' -> {
                    this.mappedString.put(key,processing.toString());
                    this.processing.setLength(0);
                    this.key = "";
                }
                default -> {
                    processing.append(processing1);
                }
            }

            if(index < input.length()){
                return new Op.Flow<>(Op.OpOp.CONTINUE_PROCESS,null);
            }
            return new Op.Flow<>(Op.OpOp.DONE,mappedString);
        });
    }

}
