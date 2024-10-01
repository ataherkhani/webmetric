package com.webmetric.alireza;

import com.google.gson.Gson;
import com.webmetric.alireza.dto.*;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;

public class ReadJson {

    public ClicksDTO[] readJsonClickFile(String path) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        String json = IOUtils.toString(fis, "UTF-8");
        fis.close();
        return new Gson().fromJson(json, ClicksDTO[].class);
    }

    public ImpressionsDTO[] readJsonImpressionFile(String path) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        String json = IOUtils.toString(fis, "UTF-8");
        fis.close();
        return new Gson().fromJson(json, ImpressionsDTO[].class);
    }
}
