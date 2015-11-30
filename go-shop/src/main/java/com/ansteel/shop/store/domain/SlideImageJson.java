package com.ansteel.shop.store.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Administrator on 2015/11/16.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SlideImageJson {

    private String file_id;

    private String id;

    private String file_name;

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }
}
