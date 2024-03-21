//package com.duokey.bookclubcrawl.service.impl;
//
//import com.duokey.bookclubcrawl.constant.websiteConstant;
//import com.duokey.bookclubcrawl.entity.Book;
//import com.duokey.bookclubcrawl.entity.Chapter;
//import com.duokey.bookclubcrawl.mapper.ChapterMapper;
//import com.duokey.bookclubcrawl.service.IChapterService;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import lombok.extern.slf4j.Slf4j;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//
///**
// * <p>
// *  服务实现类
// * </p>
// *
// * @author duokey
// * @since 2024-03-20
// */
//@Slf4j
//@Service
//public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements IChapterService {
//    @Autowired
//    private ChapterMapper chapterMapper;
//
//    // 获得书本的章节信息（章节标题等）
//    public List<Chapter> crawlChapters(Book book) throws IOException {
//        List<Chapter> chapters = new ArrayList<>();
//
//        String bookUrl = book.getUrl();
//        Document doc = Jsoup.connect(websiteConstant.pre_url + bookUrl).get();
//
//        // 获取章节列表
//        Element listMain = doc.selectFirst("div.listmain");
//        if (listMain != null) {
//            Elements chaptersEle = listMain.select("dl dd");
//            Long chapterNumber = 1L;
//            for (Element chapterEle : chaptersEle) {
//                String chapterTitle = chapterEle.text();
//                String chapterUrl = chapterEle.attr("href");
//                Chapter chapter = new Chapter(book.getId(), chapterNumber, chapterTitle, chapterUrl);
//
//                int insert = chapterMapper.insert(chapter);
//                if ( insert != 0 ) {
//                    chapters.add(chapter);
//                } else {
//                    log.info("第" + insert + "章插入数据库失败！");
//                }
//                chapterNumber++;
//            }
//        }
//
//        return chapters;
//    }
//
//    public String crawlChapterContent(Chapter chapter) throws IOException {
//        String chapterUrl = chapter.getChapterUrl();
//        Document doc = Jsoup.connect(websiteConstant.pre_url + chapterUrl).get();
//
//        // 解析网页内容，获取章节内容
//        Element contentElement = doc.selectFirst("#chaptercontent");
//        String chapterContent = contentElement.html();
//
//        // 更新数据库中的章节内容
//        chapter.setContent(chapterContent);
//        chapterMapper.updateById(chapter);
//
//        return chapterContent;
//    }
//}
