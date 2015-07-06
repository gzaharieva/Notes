package com.parse.notes.data;

import com.parse.ParseObject;

import java.io.Serializable;

/**
 * Created by gzaharieva on 1.4.2015 г..
 */
public class Note implements Serializable {
    private String objectId;
    private String content;
    private boolean isImportant;

    public Note(String objectId) {
        this.objectId = objectId;
    }

    public Note(ParseObject parseObject) {
        this.objectId = parseObject.getObjectId();
        this.isImportant = parseObject.getBoolean(LocalObjects.FIELD_IS_IMPORTANT);
        this.content = (String) parseObject.get(LocalObjects.FIELD_CONTENT);
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean isImportant) {
        this.isImportant = isImportant;
    }

    @Override
    public String toString() {
        return content;
    }
}
