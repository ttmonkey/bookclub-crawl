package com.duokey.bookclubcrawl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.duokey.bookclubcrawl.entity.Book;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public interface IWeReadService extends IService<Book> {
    Set<Book> crawlBook () throws IOException, ExecutionException, InterruptedException;
}
