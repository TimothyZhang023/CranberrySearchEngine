/*
 * Copyright (c) 2017 By Timothy Zhang
 */

package com.zts1993.spider;

import com.zts1993.spider.http.GseHttpClient;
import com.zts1993.spider.http.GseHttpRequest;
import com.zts1993.spider.http.GseHttpResponse;
import com.zts1993.spider.http.GseHttpResponsePromise;
import com.zts1993.spider.http.channel.GseChannelCallback;
import com.zts1993.spider.queue.MemQueue;
import com.zts1993.spider.queue.QueueImpl;
import com.zts1993.spider.task.HttpRequestTask;
import com.zts1993.spider.task.TaskImpl;
import io.netty.util.internal.ConcurrentSet;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.nlpcn.commons.lang.util.MD5;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * GSE Component
 * Created by TimothyZhang on 2017/1/9.
 */
@Slf4j
public class Run {


    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException, ExecutionException {

        Set<String> set = new ConcurrentSet<>();
        GseHttpClient gseHttpClient = new GseHttpClient();
        gseHttpClient.init();

        final QueueImpl<TaskImpl<GseHttpResponsePromise>> taskMemQueue = new MemQueue<>();
        final QueueImpl<GseHttpResponse> promiseMemQueue = new MemQueue<>();

        GseHttpRequest gseHttpRequest = new GseHttpRequest(gseHttpClient, new URI("http://docs.oracle.com/javase/specs/jls/se8/html/index.html"));
        gseHttpRequest.setChannelCallback(new GseChannelCallback() {
            @Override
            public void onSuccess(GseHttpResponse response) {
                promiseMemQueue.addLast(response);
            }

            @Override
            public void onFailed(GseHttpResponse response) {

            }
        });
        HttpRequestTask httpRequestTask = new HttpRequestTask(gseHttpRequest);

        taskMemQueue.addLast(httpRequestTask);

        Thread task = new Thread(() -> {
            TaskImpl<GseHttpResponsePromise> poll;
            while (true) {

                try {
                    poll = taskMemQueue.poll(10, TimeUnit.SECONDS);
                    if (poll == null) {
                        continue;
                    }

                    log.info(poll.toString());

                    poll.Do();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        });

        //using callback


        Thread task2 = new Thread(() -> {

            GseHttpResponse poll;
            while (true) {
                try {
                    poll = promiseMemQueue.poll(10, TimeUnit.SECONDS);
                    if (poll == null) {
                        continue;
                    }
                    log.info("no.{} pages done", set.size());

                    GseHttpResponse gseHttpResponse = poll;
                    String content = gseHttpResponse.getContent();

                    if (content.isEmpty()) {
                        continue;
                    }

                    Document doc = Jsoup.parse(content);
                    Elements links = doc.select("a[href]");

//                    log.info(new HtmlParser().html2SimpleText(content));

                    try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                            new FileOutputStream("../data/" + MD5.code(gseHttpResponse.getRequest().getUri().toString()) + ".html"), "utf-8"))) {
                        writer.write(content);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    for (Element link : links) {
//                        log.info(" * a: <{}>  ({})", link.attr("abs:href"), trim(link.text(), 35));

                        String url = link.attr("href");
                        if (url != null && !url.isEmpty()) {

                            if (!url.startsWith("http") && !url.startsWith("https")) {
                                if ((url.startsWith("/"))) {
                                    url = "http://docs.oracle.com" + url;
                                } else {

                                    GseHttpRequest request = gseHttpResponse.getRequest();

                                    URL url1 = new URL(new URL(request.getUri().toASCIIString()), url);
                                    url = url1.toString();
                                }
                            }

                            if (!url.startsWith("http://docs.oracle.com")) {
                                continue;
                            }

                            if (set.contains(url)) {
                                continue;
                            } else {
                                set.add(url);
                            }

                            GseHttpRequest r = new GseHttpRequest(gseHttpClient, new URI(url));
                            r.setChannelCallback(new GseChannelCallback() {
                                @Override
                                public void onSuccess(GseHttpResponse response) {
                                    promiseMemQueue.addFirst(response);
                                }

                                @Override
                                public void onFailed(GseHttpResponse response) {

                                }
                            });

                            taskMemQueue.addLast(new HttpRequestTask(r));
                        }
                    }


                } catch (RuntimeException e) {
                    log.error(e.getMessage());
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                } catch (URISyntaxException e) {
                    log.error(e.getMessage());
                } catch (MalformedURLException e) {
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


    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width - 1) + ".";
        else
            return s;
    }
}
