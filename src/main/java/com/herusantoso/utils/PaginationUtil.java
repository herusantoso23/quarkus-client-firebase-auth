package com.herusantoso.utils;

import com.herusantoso.dtos.ResultPageDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collection;

public class PaginationUtil {

    private PaginationUtil() {
    }

    public static ResultPageDTO map(Collection datas, Page page) {
        ResultPageDTO dto = new ResultPageDTO();
        dto.setData(datas);
        dto.setPage(page.getNumber());
        dto.setPages(page.getTotalPages());
        dto.setSize(page.getSize());

        return dto;
    }

    public static Pageable getPageRequest(String page, String size, String sortBy, String direction){
        int pageNum = StringUtils.isEmpty(page) || !StringUtils.isNumeric(page) ? 0 : Integer.parseInt(page);
        int sizeNum = StringUtils.isEmpty(size) || !StringUtils.isNumeric(size) ? 10 : Integer.parseInt(size);
        sortBy = StringUtils.isEmpty(sortBy) ? "id" : sortBy;
        direction = StringUtils.isEmpty(direction) ? "desc" : direction;

        return PageRequest.of(pageNum,sizeNum, getSortBy(direction),sortBy);
    }

    private static Sort.Direction getSortBy(String sortBy) {
        if (sortBy.equalsIgnoreCase("asc")) {
            return Sort.Direction.ASC;
        } else {
            return Sort.Direction.DESC;
        }
    }



}
