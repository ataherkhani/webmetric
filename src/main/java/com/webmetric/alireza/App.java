package com.webmetric.alireza;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.webmetric.alireza.dto.*;
import com.webmetric.alireza.entity.Click;
import com.webmetric.alireza.entity.Impression;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.webmetric.alireza.util.HibernateUtil;

public class App {
    public static void main(String[] args) throws IOException {
        Transaction transaction = null;

        ReadJson readJson = new ReadJson();
        ClicksDTO[] clicks = readJson.readJsonClickFile("src/main/resources/clicks.json");
        ImpressionsDTO[] impressions = readJson.readJsonImpressionFile("src/main/resources/impressions.json");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();

            // save the  objects
            for (ClicksDTO clicksDTO : clicks) {
                Click click = new Click();
                click.setRevenue(clicksDTO.getRevenue());
                click.setImpressionId(clicksDTO.getImpressionId());
                session.save(click);
            }
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();

            // save the  objects
            for (ImpressionsDTO impressionsDTO : impressions) {
                Impression impression = new Impression();
                impression.setImpressionId(impressionsDTO.getId());
                impression.setAppId(impressionsDTO.getAppId());
                impression.setAdvertiserId(impressionsDTO.getAdvertiserId());
                impression.setCountryCode(impressionsDTO.getCountryCode());
                session.save(impression);
            }
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            WriteJson writeJson = new WriteJson();

            List<Object[]> list1 = session.createSQLQuery("SELECT distinct (i.IMPRESSIONID) , i. ADVERTISERID ,i.APPID , i.COUNTRYCODE, count(i.IMPRESSIONID),sum(c.REVENUE) , cc.clicks  FROM IMPRESSIONS i  inner join CLICKS c on i.IMPRESSIONID   =  c.IMPRESSIONID join (select IMPRESSIONID , count(*) clicks from CLICKS group by IMPRESSIONID)  cc on c.IMPRESSIONID   =  cc.IMPRESSIONID  group by i.IMPRESSIONID, i. ADVERTISERID ,i.APPID , i.COUNTRYCODE;").list();

            CalculateMetricDTO[] calculateMetricDTOS = new CalculateMetricDTO[list1.size()];
            int i = 0;
            for (Object[] objects : list1) {
                CalculateMetricDTO calculateMetricDTO = new CalculateMetricDTO();
                calculateMetricDTO.setAppId(Integer.parseInt(objects[2] != null ? objects[2].toString() : "-1"));
                calculateMetricDTO.setCountryCode(objects[3] != null ? objects[3].toString() : "-1");
                calculateMetricDTO.setImpressionCount(Integer.parseInt(objects[4] != null ? objects[4].toString() : "-1"));
                calculateMetricDTO.setRevenueSum(Double.parseDouble(objects[5] != null ? objects[5].toString() : "-1"));
                calculateMetricDTO.setClickCount(Integer.parseInt(objects[6] != null ? objects[6].toString() : "-1"));
                calculateMetricDTOS[i++] = calculateMetricDTO;
            }
            writeJson.writeCalculateMetric(calculateMetricDTOS);

            List<Object[]> list2 = session.createSQLQuery("SELECT distinct(i.ADVERTISERID), i.IMPRESSIONID  ,i. APPID , i.COUNTRYCODE, c.REVENUE    FROM IMPRESSIONS i  left  join CLICKS c on i.IMPRESSIONID = c.IMPRESSIONID  order by  i.APPID , i.COUNTRYCODE").list();

            RecommendationResultDTO[] recommendationResultDTOS = new RecommendationResultDTO[list2.size()];
            i = 0;
            for (Object[] objects : list2) {
                RecommendationResultDTO recommendationResultDTO = new RecommendationResultDTO();
                recommendationResultDTO.setAdvertiserId(Integer.parseInt(objects[0] != null ? objects[0].toString() : "-1"));
                recommendationResultDTO.setImpressionId(objects[1] != null ? objects[1].toString() : "-1");
                recommendationResultDTO.setAppId(Integer.parseInt(objects[2] != null ? objects[2].toString() : "-1"));
                recommendationResultDTO.setCountryCode(objects[3] != null ? objects[3].toString() : "-1");
                recommendationResultDTO.setRevenue(Double.parseDouble(objects[4] != null ? objects[4].toString() : "-1"));
                recommendationResultDTOS[i++] = recommendationResultDTO;
            }

            List<RecommendedResultDTO> recommendedResultDTOS = new ArrayList<>();
            Arrays.stream(recommendationResultDTOS).forEach(r -> {
                List<RecommendationResultDTO> dtoList = Arrays.stream(recommendationResultDTOS)
                        .filter(re -> re.getAppId() == r.getAppId())
                        .filter(re -> re.getCountryCode().equals(r.getCountryCode())).collect(Collectors.toList());
                RecommendedResultDTO recommendedResultDTO = new RecommendedResultDTO();
                recommendedResultDTO.setAppId(r.getAppId());
                recommendedResultDTO.setCountryCode(r.getCountryCode());
                String s = "";
                for(RecommendationResultDTO dto : dtoList){
                     s = s.concat(String.valueOf(dto.getAdvertiserId())).concat(",");
                }
                recommendedResultDTO.setRecommendedAdvertiserIds(s.length() > 1 ? s.substring(0, s.length() -1) : s);
                recommendedResultDTOS.add(recommendedResultDTO);
            });
            writeJson.writeRecommendedResult(recommendedResultDTOS);

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        System.exit(0);
    }
}