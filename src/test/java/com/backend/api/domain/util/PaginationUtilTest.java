package com.backend.api.domain.util;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaginationUtilTest {
    @Test
    void testSetSort() {
        String[] sorts = new String[]{"id,asc"};
        List<Sort.Order> actualSetSortResult = PaginationUtil.setSort(sorts);
        assertEquals(1, actualSetSortResult.size());
    }

    @Test
    void testSetSort_withAnotherFilters() {
        String[] sorts = new String[]{"Sorts", "desc"};
        List<Sort.Order> actualSetSortResult = PaginationUtil.setSort(sorts);
        assertEquals(1, actualSetSortResult.size());
    }
}
