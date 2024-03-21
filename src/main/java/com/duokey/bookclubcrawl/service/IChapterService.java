package com.duokey.bookclubcrawl.service;

import com.duokey.bookclubcrawl.entity.Book;
import com.duokey.bookclubcrawl.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author duokey
 * @since 2024-03-20
 */
public interface IChapterService extends IService<Chapter> {
    List<Chapter> crawlChapters(Book book) throws IOException;
    String crawlChapterContent(Chapter chapter) throws IOException;
}
