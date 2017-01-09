/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider;

import com.zts1993.spider.http.GseHttpClient;
import com.zts1993.spider.http.GseHttpRequest;
import com.zts1993.spider.http.GseHttpResponse;
import com.zts1993.spider.http.GseHttpResponsePromise;
import com.zts1993.spider.queue.MemQueue;
import com.zts1993.spider.queue.QueueImpl;
import com.zts1993.spider.task.HttpRequestTask;
import com.zts1993.spider.task.TaskImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/9.
 */
@Slf4j
public class Run {


    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException, ExecutionException {
        GseHttpClient gseHttpClient = new GseHttpClient();
        gseHttpClient.init();

        final QueueImpl<TaskImpl<GseHttpResponsePromise>> taskMemQueue = new MemQueue<>();
        final QueueImpl<GseHttpResponsePromise> promiseMemQueue = new MemQueue<>();

        HttpRequestTask httpRequestTask = new HttpRequestTask(gseHttpClient, new GseHttpRequest(gseHttpClient, new URI("http://cqt.njtech.edu.cn/")));
        taskMemQueue.addLast(httpRequestTask);

        Thread task = new Thread(() -> {
            TaskImpl<GseHttpResponsePromise> poll;
            while ((poll = taskMemQueue.poll()) != null) {
                log.info(poll.toString());
                GseHttpResponsePromise aDo = poll.Do();
                promiseMemQueue.addLast(aDo);
            }

        });

        //using callback


        Thread task2 = new Thread(() -> {

            GseHttpResponsePromise poll;
            while (true) {
                try {
                    poll = promiseMemQueue.poll(10, TimeUnit.SECONDS);
                    if (poll == null) {
                        continue;
                    }

                    GseHttpResponse gseHttpResponse = poll.get();
                    log.info(gseHttpResponse.getContent());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }

        });

        task.start();
        task2.start();

        task.join();
        task2.join();


        gseHttpClient.close();

    }
}
