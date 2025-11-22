package cn.klee.interpreter;

@FunctionalInterface
public interface Op<E,T,Result> {
    Flow<Result> flowIn(Interpreter<E,T,Result> ctx);
    public enum OpOp {
        CONTINUE_PROCESS,
        DONE;
    }
    public static class Flow<Result> {
        OpOp op;
        Result result;
        public Flow(OpOp op, Result result) {
            this.op = op;
            this.result = result;
        }
    }
}
