package com.arcplus.fm.controller.statistic;

import com.arcplus.fm.common.Result;
import com.arcplus.fm.common.ResultGenerator;
import com.arcplus.fm.constant.EWoStatus;
import com.arcplus.fm.entity.BoWorkorder;
import com.arcplus.fm.entity.TpFailure;
import com.arcplus.fm.mapper.BoRepairMapper;
import com.arcplus.fm.mapper.BoWorkorderMapper;
import com.arcplus.fm.mapper.TpFailureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("statistic")
public class WorkOrderController {

    @Autowired
    BoWorkorderMapper boWorkorderMapper;

    @Autowired
    TpFailureMapper tpFailureMapper;

    @Autowired
    BoRepairMapper boRepairMapper;

    @GetMapping(value = "workorderbytype/{year}")
    public Result workorderbytype(@PathVariable("year") int year) throws Exception {

        Map<String, Object> request = new HashMap<>();
        request.put("fromDate", LocalDateTime.of(year, 1, 1, 0, 0, 0));
        request.put("toDate", LocalDateTime.of(year, 12, 31, 23, 59, 59));
        List<BoWorkorder> data = boWorkorderMapper.search(request);

        List<TpFailure> tpFailuresList = tpFailureMapper.selectAll();
        HashMap<String, Integer> sumFail = (HashMap<String, Integer>) tpFailuresList.stream()
                .filter(x -> !StringUtils.isEmpty(x.getFailureName()))
                .collect(Collectors.toMap(x -> x.getFailureName(), x -> 0));
        HashMap<Integer, String> failDic = (HashMap<Integer, String>) tpFailuresList.stream()
                .filter(x -> !StringUtils.isEmpty(x.getFailureName()))
                .collect(Collectors.toMap(x -> x.getFailureId(), x -> x.getFailureName()));

        HashMap<Integer, HashMap<String, Integer>> monthly = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            monthly.put(i, (HashMap<String, Integer>) sumFail.clone());
        }

        HashMap<String, Integer> yearly = (HashMap<String, Integer>) sumFail.clone();

        for (BoWorkorder item : data) {
            String fialStr = failDic.get(item.getFailureTypeId());
            if (StringUtils.isEmpty(fialStr)) {
                continue;
            }
            HashMap<String, Integer> monthMap = monthly.get(item.getCreateAt().getMonth() + 1);
            monthMap.put(fialStr, monthMap.get(fialStr) + 1);
            yearly.put(fialStr, yearly.get(fialStr) + 1);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("monthly", monthly);
        result.put("yearly", yearly);

        HashMap<Integer, HashMap<String, Object>> monthlyPercentage = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            int monthTotal = (monthly.get(i)).values().stream().mapToInt(x -> x).sum();

            HashMap<String, Object> monthMap = (HashMap<String, Object>) monthly.get(i).clone();
            for (Map.Entry<String, Object> item : monthMap.entrySet()) {
                if ((Integer)item.getValue() > 0) {
                    monthMap.put(
                            item.getKey(),
                            (
                                    (float) Math.round(
                                            (
                                                    (Integer)item.getValue() * 10000f / monthTotal
                                            )
                                    ) / 100
                            )
                    );
                }
            }
            monthlyPercentage.put(i, monthMap);
        }

        HashMap<String, Object> yearlyPercentage = (HashMap<String, Object>) yearly.clone();
        int yearlyTotal = yearly.values().stream().mapToInt(x -> x).sum();
        for (Map.Entry<String, Object> item : yearlyPercentage.entrySet()) {
            if ((Integer) item.getValue() > 0) {
                yearlyPercentage.put(
                        item.getKey(),
                        (
                                (float) Math.round(((Integer) item.getValue() * 10000f / yearlyTotal)) / 100
                        )
                );
            }
        }


        result.put("monthly-percentage", monthlyPercentage);
        result.put("yearly-percentage", yearlyPercentage);
        result.put("total", data.size());

        return ResultGenerator.genSuccessResult(result);
    }

    @GetMapping(value = "workordertrend/{year}")
    public Result workordertrend(@PathVariable("year") int year) throws Exception {

        Map<String, Object> request = new HashMap<>();
        request.put("fromDate", LocalDateTime.of(year, 1, 1, 0, 0, 0));
        request.put("toDate", LocalDateTime.of(year, 12, 31, 23, 59, 59));
        List<BoWorkorder> data = boWorkorderMapper.search(request);

        HashMap<String, Integer> statusSum = (HashMap<String, Integer>) Arrays.stream(EWoStatus.values())
                .collect(Collectors.toMap(x -> x.getValue(), x -> 0));

        HashMap<Integer, Integer> levelSum = new HashMap<>();
        levelSum.put(1, 0);
        levelSum.put(2, 0);
        levelSum.put(3, 0);
        levelSum.put(4, 0);
        levelSum.put(5, 0);

        HashMap<Integer, HashMap<Integer, Integer>> monthly = new HashMap<>();

        HashMap<Integer, HashMap<String, Integer>> monthlyStatus = new HashMap<>();

        for (int i = 1; i <= 12; i++) {
            monthly.put(i, (HashMap<Integer, Integer>) levelSum.clone());
            monthlyStatus.put(i, (HashMap<String, Integer>) statusSum.clone());
        }

        for (BoWorkorder item : data) {

            String statusStr = item.getDealStatus().getValue();
            if (!StringUtils.isEmpty(statusStr)) {
                HashMap<String, Integer> monthMap = monthlyStatus.get(item.getCreateAt().getMonth() + 1);
                monthMap.put(statusStr, monthMap.get(statusStr) + 1);
            }

            Integer priorityLevel = item.getPriorityLevel();
            if (priorityLevel > 0 && priorityLevel < 6) {
                HashMap<Integer, Integer> monthMap = monthly.get(item.getCreateAt().getMonth() + 1);
                monthMap.put(priorityLevel, monthMap.get(priorityLevel) + 1);
            }
        }

        HashMap<Integer, HashMap<Integer, Object>> monthlyAverage
                = (HashMap<Integer, HashMap<Integer, Object>>) monthly.clone();
        for (int i = 1; i <= 12; i++) {
            int daysInt =
                    i == 12 ?
                            LocalDate.of(year + 1, 1, 1).minusDays(1).getDayOfMonth()
                            : LocalDate.of(year, i + 1, 1).minusDays(1).getDayOfMonth();
            HashMap<Integer, Object> monthMap = (HashMap<Integer, Object>) monthly.get(i).clone();
            for (Map.Entry<Integer, Object> item : monthMap.entrySet()) {
                if ((int) item.getValue() > 0) {
                    monthMap.put(
                            item.getKey(),
                            (
                                    (float) Math.round((Integer) item.getValue() * 100f / (float) daysInt) / 100
                            )
                    );
                }
            }
            monthlyAverage.put(i, monthMap);
        }

        HashMap<Integer, HashMap<Integer, Object>> monthlyLevelPercentage
                = (HashMap<Integer, HashMap<Integer, Object>>) monthly.clone();
        for (int i = 1; i <= 12; i++) {
            int monthTotal = ((HashMap<Integer, Integer>) monthly.get(i)).values().stream().mapToInt(x -> x).sum();

            HashMap<Integer, Object> monthMap = (HashMap<Integer, Object>) monthly.get(i).clone();
            for (Map.Entry<Integer, Object> item : monthMap.entrySet()) {
                if ((int) item.getValue() > 0) {
                    monthMap.put(
                            item.getKey(),
                            (
                                    (float) Math.round(((int) item.getValue() * 10000f / monthTotal)) / 100
                            )
                    );
                }
            }
            monthlyLevelPercentage.put(i, monthMap);
        }

        List<MonthlyValueObj> monthlyStatusList = new LinkedList<>();
        for (Map.Entry<Integer, HashMap<String, Integer>> x : monthlyStatus.entrySet()) {
            for (Map.Entry<String, Integer> y : x.getValue().entrySet()) {
                monthlyStatusList.add(MonthlyValueObj.valueOf(x.getKey() + "月", y.getKey(), y.getValue()));
            }
        }

        List<MonthlyValueObj> monthlyList = new LinkedList<>();
        for (Map.Entry<Integer, HashMap<Integer, Integer>> x : monthly.entrySet()) {
            for (Map.Entry<Integer, Integer> y : x.getValue().entrySet()) {
                monthlyList.add(MonthlyValueObj.valueOf(x.getKey() + "月", y.getKey(), y.getValue()));
            }
        }

        List<MonthlyValueObj> monthlyAverageList = new LinkedList<>();
        for (Map.Entry<Integer, HashMap<Integer, Object>> x : monthlyAverage.entrySet()) {
            for (Map.Entry<Integer, Object> y : x.getValue().entrySet()) {
                monthlyAverageList.add(MonthlyValueObj.valueOf(x.getKey() + "月", y.getKey(), y.getValue()));
            }
        }

        List<MonthlyValueObj> monthlyLevelPercentageList = new LinkedList<>();
        for (Map.Entry<Integer, HashMap<Integer, Object>> x : monthlyLevelPercentage.entrySet()) {
            for (Map.Entry<Integer, Object> y : x.getValue().entrySet()) {
                monthlyLevelPercentageList.add(MonthlyValueObj.valueOf(x.getKey() + "月", y.getKey(), y.getValue()));
            }
        }

        Map<String, Object> result = new HashMap<>();

        result.put("monthly-status", monthlyStatusList);
        result.put("monthly-level", monthlyList);
        result.put("monthly-level-average", monthlyAverageList);
        result.put("monthly-level-percentage", monthlyLevelPercentageList);

        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping(value = "workordercompare")
    public Result workordercompare(@RequestBody Map<String, Integer> request) {

        Integer sourceYear = request.get("source_year");
        Integer sourceMonth = request.get("source_month");
        Integer targetYear = request.get("target_year");
        Integer targetMonth = request.get("target_month");

        Map<String, Object> requestSource = new HashMap<>();
        requestSource.put("fromDate", LocalDateTime.of(sourceYear, sourceMonth, 1, 0, 0, 0));
        requestSource.put("toDate", LocalDateTime.of(sourceYear, sourceMonth, 1, 0, 0, 0).plusMonths(1));
        List<BoWorkorder> sourceData = boWorkorderMapper.search(requestSource);

        Map<String, Object> requesttarget = new HashMap<>();
        requesttarget.put("fromDate", LocalDateTime.of(targetYear, targetMonth, 1, 0, 0, 0));
        requesttarget.put("toDate", LocalDateTime.of(targetYear, targetMonth, 1, 0, 0, 0).plusMonths(1));
        List<BoWorkorder> targetData = boWorkorderMapper.search(requesttarget);

        HashMap<String, HashMap<String, Integer>> compare = new HashMap<String, HashMap<String, Integer>>();
        List<TpFailure> tpFailuresList = tpFailureMapper.selectAll();

        tpFailuresList.stream().forEach(x -> {
            if (StringUtils.isEmpty(x.getFailureName())) {
                return;
            }

            Integer sourceCount = (int) sourceData.stream()
                    .filter(y -> y.getFailureTypeId().equals(x.getFailureId()))
                    .count();
            Integer sourcePercentage = 0;
            if (sourceCount > 0 && sourceData.size() > 0) {
                sourcePercentage = (int) ((sourceCount * 100f) / sourceData.size());
            }
            Integer targetCount = (int) targetData.stream()
                    .filter(y -> y.getFailureTypeId().equals(x.getFailureId()))
                    .count();
            Integer targetPercentage = 0;
            if (targetCount > 0 && targetData.size() > 0) {
                targetPercentage = (int) ((targetCount * 100f) / targetData.size());
            }
            Integer diff = targetPercentage - sourcePercentage;

            HashMap<String, Integer> compareItem = new HashMap<String, Integer>();
            compareItem.put("source_count", sourceCount);
            compareItem.put("source_percentage", sourcePercentage);
            compareItem.put("target_count", targetCount);
            compareItem.put("target_percentage", targetPercentage);
            compareItem.put("diff", diff);
            compare.put(x.getFailureName(), compareItem);
        });

        HashMap<String, Object> result = new HashMap<>();
        result.put("source_total", sourceData.size());
        result.put("target_total", targetData.size());
        result.put("compare", compare);
        return ResultGenerator.genSuccessResult(result);
    }


    @GetMapping(value = "device")
    public Result device() throws Exception {

        int namorl = (int) (Math.random() * 100);
        int error = (int) (Math.random() * 10);

        Map<String, Object> result = new HashMap<>();
        result.put("total", namorl + error);
        result.put("normal", namorl);
        result.put("error", error);

        return ResultGenerator.genSuccessResult(result);
    }


    public static class MonthlyValueObj {

        public Object type;

        public Object month;

        public Object value;

        public MonthlyValueObj(Object... value) {
            this.month = value[0];
            this.type = value[1];
            this.value = value[2];
        }

        // Static factory method to create new instances.
        public static MonthlyValueObj valueOf(Object... value) {
            return new MonthlyValueObj(value);
        }

        @Override
        public int hashCode() {
            final int PRIME = 31;
            int result = 1;
            result = PRIME * result + ((type == null) ? 0 : type.hashCode());
            result = PRIME * result + ((month == null) ? 0 : month.hashCode());
            result = PRIME * result + ((value == null) ? 0 : value.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final MonthlyValueObj other = (MonthlyValueObj) obj;

            if (month != other.month)
                return false;
            if (type != other.type)
                return false;
            if (value != other.value)
                return false;

            return true;
        }

    }

}
