package com.txtled.gpa220.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Quan on 2020/3/30.
 */
public class FloatConverter implements PropertyConverter<List<Float>, String> {
    private final Gson gson;

    public FloatConverter() {
        this.gson = new Gson();
    }

    @Override
    public List<Float> convertToEntityProperty(String databaseValue) {
        Type type = new TypeToken<ArrayList<Float>>() {
        }.getType();
        ArrayList<Float> list = gson.fromJson(databaseValue , type);
        return list;
    }

    @Override
    public String convertToDatabaseValue(List<Float> entityProperty) {
        String dbString = gson.toJson(entityProperty);
        return dbString;
    }
}
