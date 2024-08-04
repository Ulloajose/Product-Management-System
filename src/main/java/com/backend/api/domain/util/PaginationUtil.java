package com.backend.api.domain.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class PaginationUtil {

    public List<Sort.Order> setSort(String[] sorts){
        List<Sort.Order> orders = new ArrayList<>();

        if (sorts[0].contains(",")) {
            for (String sortOrder : sorts) {
                String[] sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
            }
        } else {
            orders.add(new Sort.Order(getSortDirection(sorts[1]), sorts[0]));
        }
        return orders;
    }

    private Sort.Direction getSortDirection(String s) {
        return s.contains("desc")?Sort.Direction.DESC:Sort.Direction.ASC;
    }
}
