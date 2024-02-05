package com.example.demo.util;

public class Result<T> {
    private String code;
    private String msg;
    private T data;

    private static <T> String $default$code() {
        return "0";
    }

    public static <T> Result.ResultBuilder<T> builder() {
        return new Result.ResultBuilder();
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public T getData() {
        return this.data;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Result)) {
            return false;
        } else {
            Result<?> other = (Result)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47: {
                    Object this$code = this.getCode();
                    Object other$code = other.getCode();
                    if (this$code == null) {
                        if (other$code == null) {
                            break label47;
                        }
                    } else if (this$code.equals(other$code)) {
                        break label47;
                    }

                    return false;
                }

                Object this$msg = this.getMsg();
                Object other$msg = other.getMsg();
                if (this$msg == null) {
                    if (other$msg != null) {
                        return false;
                    }
                } else if (!this$msg.equals(other$msg)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof Result;
    }

    @Override
    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $msg = this.getMsg();
        result = result * 59 + ($msg == null ? 43 : $msg.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Result(code=" + this.getCode() + ", msg=" + this.getMsg() + ", data=" + this.getData() + ")";
    }

    public Result() {
        this.code = $default$code();
    }

    public Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static class ResultBuilder<T> {
        private boolean code$set;
        private String code$value;
        private String msg;
        private T data;

        ResultBuilder() {
        }

        public Result.ResultBuilder<T> code(String code) {
            this.code$value = code;
            this.code$set = true;
            return this;
        }

        public Result.ResultBuilder<T> msg(String msg) {
            this.msg = msg;
            return this;
        }

        public Result.ResultBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public Result<T> build() {
            String code$value = this.code$value;
            if (!this.code$set) {
                code$value = Result.$default$code();
            }

            return new Result(code$value, this.msg, this.data);
        }

        public String toString() {
            return "Result.ResultBuilder(code$value=" + this.code$value + ", msg=" + this.msg + ", data=" + this.data + ")";
        }
    }
}
