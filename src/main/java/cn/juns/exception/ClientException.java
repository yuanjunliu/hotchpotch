package cn.juns.exception;

/**
 * Created by 水信玄饼 on 2017/12/22.
 */
public class ClientException extends RuntimeException{
    private String msg;

    public ClientException(String msg) {
        this.msg = msg;
    }

}
