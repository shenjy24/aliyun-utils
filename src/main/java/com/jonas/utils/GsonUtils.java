package com.jonas.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author shenjy
 * @date 2020/9/3
 * @description
 */
public class GsonUtils {

    private static final Gson gson = new Gson();

    public static <T> String toJson(T t) {
        return gson.toJson(t);
    }

    public static <T> String toJson(T t, Type type) {
        return gson.toJson(t, type);
    }

    public static <T> T toBean(String json, Class<T> clz) {
        return gson.fromJson(json, clz);
    }

    public static <T> T toBean(String json, Type type) {
        return gson.fromJson(json, type);
    }

    public static <T> JsonArray toJsonArray(Collection<T> collection, Type type) {
        return gson.toJsonTree(collection, type).getAsJsonArray();
    }

    public static JsonArray toJsonArray(String json) {
        return JsonParser.parseString(json).getAsJsonArray();
    }

    public static <T> JsonObject toJsonObject(T obj) {
        return gson.toJsonTree(obj).getAsJsonObject();
    }

    public static JsonObject toJsonObject(String json) {
        return JsonParser.parseString(json).getAsJsonObject();
    }

    public static void main(String[] args) {
        List<User> users = new ArrayList<User>() {{
            add(new User(1, "Tom1"));
            add(new User(2, "Tom2"));
            add(new User(3));
        }};
        Type type = new TypeToken<List<User>>(){}.getType();
        String str = toJson(users, type);
        System.out.println(str);

        List<User> userList = toBean(str, type);
        System.out.println(userList);

        JsonArray jsonArray1 = toJsonArray(userList, type);
        System.out.println(jsonArray1);

        JsonArray jsonArray2 = toJsonArray(str);
        System.out.println(jsonArray2);

        JsonObject jsonObject1 = toJsonObject(users.get(0));
        System.out.println(jsonObject1.get("id").getAsString());

        JsonObject jsonObject2 = toJsonObject(toJson(users.get(2)));
        String name = jsonObject2.has("name") ? jsonObject2.get("name").getAsString() : "";
        System.out.println(name);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        private Integer id;
        private String name;

        public User(Integer id) {
            this.id = id;
        }
    }
}
