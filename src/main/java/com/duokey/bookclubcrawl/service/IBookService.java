package com.duokey.bookclubcrawl.service;

import com.duokey.bookclubcrawl.entity.Book;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 书本 服务类
 * </p>
 *
 * @author duokey
 * @since 2024-03-20
 */
public interface IBookService extends IService<Book> {
    public List<Book> crawlBooks()throws IOException;
}
