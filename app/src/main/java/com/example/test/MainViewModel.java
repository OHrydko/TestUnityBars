package com.example.test;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.test.model.JsonModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
    private MutableLiveData<List<JsonModel>> model = new MutableLiveData<>();
    private String tmpJson;
    private MutableLiveData<Integer> state = new MutableLiveData<>(0);
    private JSONArray tmpArray;
    private List<String> prevJson;

    void init(Context context) {
        prevJson = new ArrayList<>();
        String jsonAll = loadJSONFromAsset(context);
        makeJson(jsonAll);
        tmpJson = jsonAll;
    }

    void onClick(int position) {
        try {
            plusState();
            prevJson.add(tmpJson);
            tmpJson = tmpArray.getJSONObject(position).toString();
            makeJson(tmpJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void back() {
        minusState();
        tmpJson = prevJson.get(prevJson.size() - 1);
        prevJson.remove(prevJson.size() - 1);
        makeJson(tmpJson);

    }

    private void makeJson(String json) {
        try {
            assert json != null;
            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = jsonObject.getJSONArray("items");
            List<JsonModel> list = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String content;
                try {
                    content = object.getString("content");
                } catch (JSONException e) {
                    content = null;
                }
                JsonModel jsonModel = new JsonModel(object.getString("name"),
                        object.getString("type"),
                        (content == null || content.isEmpty()) ? "" : content);
                list.add(jsonModel);
                System.out.println(object.toString());
            }
            model.postValue(list);
            tmpArray = array;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String loadJSONFromAsset(Context context) {
        String json;
        try {
            InputStream is = context.getAssets().open("filesystem-sample.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    MutableLiveData<Integer> getState() {
        return state;
    }

    private void plusState() {
        if (state.getValue() != null)
            state.postValue(state.getValue() + 1);
    }

    private void minusState() {
        if (state.getValue() != null)
            state.postValue(state.getValue() - 1);
    }


    MutableLiveData<List<JsonModel>> getModel() {
        return model;
    }


}
