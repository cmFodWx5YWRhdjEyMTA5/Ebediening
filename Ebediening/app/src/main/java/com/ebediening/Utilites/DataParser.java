package com.ebediening.Utilites;

import com.ebediening.Response.OdrHistoryResponse;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataParser implements JsonDeserializer<OdrHistoryResponse.HistoryData> {


    @Override
    public OdrHistoryResponse.HistoryData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        OdrHistoryResponse.HistoryData result = new OdrHistoryResponse.HistoryData();


        try {
            final LinkedHashMap<String, OdrHistoryResponse.HistoryData.KeyList> map = readServiceUrlMap(json.getAsJsonObject());

            if(map != null) {
                result.data = map;
            }

        }catch (JsonSyntaxException ex){
            return null;
        }

        return result;
    }


    private LinkedHashMap<String, OdrHistoryResponse.HistoryData.KeyList> readServiceUrlMap(final JsonObject jsonObject) throws JsonSyntaxException {

        if(jsonObject == null) {
            return null;
        }
        Gson gson = new Gson();

        LinkedHashMap<String, OdrHistoryResponse.HistoryData.KeyList> products = new LinkedHashMap<>();

        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {

            String key = entry.getKey();
            OdrHistoryResponse.HistoryData.KeyList value = gson.fromJson(entry.getValue(), OdrHistoryResponse.HistoryData.KeyList.class);
            products.put(key, value);
        }
        return products;
    }}
