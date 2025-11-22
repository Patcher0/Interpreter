package cn.klee.interpreter;

import java.util.HashMap;
import java.util.Map;

public abstract class Interpreter<T,E,Result> {
    public T input;

    Op.Flow<Result> flow;
    Result result;
    public E processing;
    static String DEFAULT_PROCESSOR = "entry";
    String processorName = DEFAULT_PROCESSOR;

    final Map<String,Op<T,E,Result>> ops = new HashMap<>();
    final Map<Integer,?> ctx = new HashMap<>();

    public Interpreter(T input) {
        this.input = input;
    }
    public Map<Integer,?> getCtx() {
        return ctx;
    }
    public void putOp(String entry, Op<T,E,Result> op) {
        synchronized (ops) {
            ops.put(entry, op);
        }
    }
    public void nextOp(String name){
        synchronized (ops) {
            processorName = name;
        }
    }
    public void process() {
        Op<T,E,Result> teOp = ops.get(processorName);
        if(teOp == null) {
            throw new RuntimeException("No Op found for processor: " + processorName);
        }
        this.flow = teOp.flowIn(this);
    }
    public void step(){
        while(this.flow == null || this.flow.op != Op.OpOp.DONE){
            process();
        }
        //done logic
        this.processorName = DEFAULT_PROCESSOR;
        this.processing = null;
        this.input = null;
        this.result = flow.result;
        this.flow = null;
    }
    public Result getResult() {
        if(result == null){
            throw new RuntimeException("No Result found");
        }
        return result;
    }


}
