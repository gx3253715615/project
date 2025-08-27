package com.example.project.handler.result;

import com.mybatisflex.core.paginate.Page;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页返回结果
 *
 * @author gaoxinyu
 * @date 2025/8/27 16:59
 */
@Data
@Accessors(chain = true)
public class PageResult<T> implements Serializable {
    /**
     * 总条数
     */
    private int total;

    /**
     * 当前页
     */
    private int pageNumber;

    /**
     * 每页大小
     */
    private int pageSize;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 数据
     */
    private List<T> data = new ArrayList<>();

    /**
     * 是否有下一页
     */
    private boolean hasNextPage;

    /**
     * 是否有上一页
     */
    private boolean hasPreviousPage;


    /**
     * orm框架分页结果转自定义分页结果
     */
    public static <T> PageResult<T> pageToPageResult(Page<T> data) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setTotal((int) data.getTotalRow());
        pageResult.setPageNumber((int) data.getPageNumber());
        pageResult.setPageSize((int) data.getPageSize());
        pageResult.setTotalPage((int) data.getTotalPage());
        pageResult.setData(data.getRecords());
        pageResult.setHasNextPage(data.hasNext());
        pageResult.setHasPreviousPage(data.hasPrevious());
        return pageResult;
    }
}
