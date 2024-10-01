package com.webmetric.alireza;

import com.google.gson.Gson;
import com.webmetric.alireza.dto.CalculateMetricDTO;
import com.webmetric.alireza.dto.ClicksDTO;
import com.webmetric.alireza.dto.ImpressionsDTO;
import com.webmetric.alireza.dto.RecommendedResultDTO;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.List;

public class WriteJson {

    public void writeCalculateMetric(CalculateMetricDTO[] calculateMetricDTOS) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("metric.json"));
        writer.write(new Gson().toJson(calculateMetricDTOS));
        writer.close();
    }

    public void writeRecommendedResult(List<RecommendedResultDTO> recommendedResultDTOS) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("recommended.json"));
        writer.write(new Gson().toJson(recommendedResultDTOS));
        writer.close();
    }
}
