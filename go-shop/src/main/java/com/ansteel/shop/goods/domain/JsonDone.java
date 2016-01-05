package com.ansteel.shop.goods.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Administrator on 2015/12/1.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JsonDone {

    private boolean done;

    private String value_id;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getValue_id() {
        return value_id;
    }

    public void setValue_id(String value_id) {
        this.value_id = value_id;
    }
}
