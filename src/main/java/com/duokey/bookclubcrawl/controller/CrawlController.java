package com.duokey.bookclubcrawl.controller;

import com.duokey.bookclubcrawl.entity.Book;
import com.duokey.bookclubcrawl.entity.Chapter;
import com.duokey.bookclubcrawl.entity.Result;
import com.duokey.bookclubcrawl.service.IBookService;
import com.duokey.bookclubcrawl.service.IChapterService;
import com.duokey.bookclubcrawl.service.IWeReadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

@Slf4j
@RestController
@RequestMapping("/crawl")
public class CrawlController {
    @Autowired
    private IWeReadService weReadService;

    private ExecutorService executorService = Executors.newFixedThreadPool(5); // 创建一个固定大小为5的线程池

//    @GetMapping
//    public Result crawlNovel() throws IOException {
//        List<Book> list = bookService.crawlBooks();
//        List<Future<?>> futures = new ArrayList<>(); // 用于存储线程任务的Future对象
//        for (Book book : list) {
//            log.info("爬取书籍：" + book.toString());
//            Callable<Void> task = () -> {
//                List<Chapter> chapters = chapterService.crawlChapters(book);
//                for (Chapter chapter : chapters) {
//                    log.info("爬取章节：" + chapter.toString());
//                    String content = chapterService.crawlChapterContent(chapter);
//                    log.info("章节的内容" + content);
//                }
//                return null;
//            };
//            Future<?> future = executorService.submit(task);
//            futures.add(future);
//        }
//        for (Future<?> future : futures) {
//            try {
//                future.get();
//            } catch (Exception e) {
//                log.error("Error occurred while crawling novel", e);
//            }
//        }
//
//        return Result.success();
//    }

//    @GetMapping
//    public void crawlNovel() throws IOException {
//        List<Book> list = bookService.crawlBooks();
//        for (Book book : list) {
//            log.info("爬取书籍：" + book.toString());
//            List<Chapter>chapters = chapterService.crawlChapters(book);
//            for (Chapter chapter : chapters) {
//                log.info("爬取章节：" + chapter.toString());
//                String content = chapterService.crawlChapterContent(chapter);
//                log.info("章节的内容" + content);
//            }
//        }
//    }

    @GetMapping
    public Result crawlBook() throws IOException, ExecutionException, InterruptedException {
        Set<Book> books = weReadService.crawlBook();
        return Result.success(books);
    }
}
