package com.github.project.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.IdUtil;
import com.github.project.enums.OrderEnum;
import com.github.project.model.entity.Order;
import com.github.project.model.entity.table.OrderTableDef;
import com.github.project.service.OrderService;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.github.project.model.entity.table.OrderTableDef.ORDER;

/**
 * 订单
 *
 * @author gaoxinyu
 * @date 2025/10/8 18:48
 **/
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/getById/{id}")
    public Order getById(@PathVariable Long id) {
        Order order = orderService.getById(id);
        return order;
    }

    @GetMapping("/task")
    public String task(String s) {
        // 56w条数据扫表的耗时
        TimeInterval timer = DateUtil.timer();
        // 比如一次查询500条数据 尾号为01
        QueryWrapper q = QueryWrapper.create()
                .select(ORDER.DEFAULT_COLUMNS)
                .where(ORDER.STATUS.eq(OrderEnum.CONFIRM))
                .and(ORDER.USER_ID.likeRight(s))
                .limit(2000);
        List<Order> list = orderService.list(q);
        log.info("查询耗时：{}ms", timer.intervalRestart());
        return "success";
    }

    @PostMapping("/save")
    public boolean save() {
        int totalRecords = 15000000; // 总记录数
        int batchSize = 10000;      // 每个批次的大小
        int threads = 25;           // 使用10个线程

        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        List<Future<Void>> futures = new ArrayList<>();

        // 任务分割：每个任务处理一部分数据
        long startCursor = 10000000;
        long endCursor = totalRecords;

        // 记录耗时
        TimeInterval timer = DateUtil.timer();
        timer.start();
        for (int i = 0; i < threads; i++) {
            long taskStart = startCursor + (i * (endCursor - startCursor) / threads);
            long taskEnd = startCursor + ((i + 1) * (endCursor - startCursor) / threads);
            // 如果是最后一个线程，确保它处理的范围包括最后的数据
            if (i == threads - 1) {
                taskEnd = endCursor;
            }

            // 提交任务
            Callable<Void> task = createBatchInsertTask(taskStart, taskEnd, batchSize);
            futures.add(executorService.submit(task));
        }

        // 等待所有任务完成
        for (Future<Void> future : futures) {
            try {
                future.get();  // 等待任务完成
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        long interval = timer.intervalRestart();
        log.info("批量插入总耗时：{}ms", interval);

        // 关闭线程池
        executorService.shutdown();
        return true;
    }

    // 创建一个批量插入的任务
    private Callable<Void> createBatchInsertTask(long startCursor, long endCursor, int batchSize) {
        return () -> {
            long cursor = startCursor;
            while (cursor <= endCursor) {
                List<Order> orders = new ArrayList<>();
                long line = Math.min(cursor + batchSize, endCursor); // 最后一批数据大小可能小于batchSize
                while (cursor <= line) {
                    cursor++;
                    Order order = new Order();
                    String str = String.valueOf(cursor);
                    order.setUserId(str);
                    order.setReverseUserId(reverse(str));
                    order.setOrderNo(IdUtil.randomUUID().replace("-", ""));
                    order.setStatus(OrderEnum.random());
                    orders.add(order);
                }
                orderService.saveBatch(orders);
                log.info("已处理：{}", cursor);
            }
            return null;
        };
    }

    public String reverse(String numStr) {
        StringBuilder sb = new StringBuilder(numStr);
        return sb.reverse().toString();
    }

}
