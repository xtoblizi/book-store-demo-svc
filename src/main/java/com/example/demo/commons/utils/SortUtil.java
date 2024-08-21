package com.example.demo.commons.utils;


import lombok.experimental.UtilityClass;
import org.hibernate.query.SortDirection;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@UtilityClass
public class SortUtil {

    public Pageable getSortedPageable(int page, int size, SortDirection sortDirection, String sortColumn) {
        sortColumn = StringUtils.isBlank(sortColumn) ? "createdAt" : sortColumn;

        page = page == 1 ? 0: page;
        Sort sort = sortDirection == SortDirection.ASCENDING
                ? Sort.by(Sort.Direction.ASC, sortColumn)
                : Sort.by(Sort.Direction.DESC, sortColumn);

        return PageRequest.of(page, size, sort);
    }
}