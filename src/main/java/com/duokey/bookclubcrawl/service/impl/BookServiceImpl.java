//package com.duokey.bookclubcrawl.service.impl;
//
//import com.duokey.bookclubcrawl.constant.websiteConstant;
//import com.duokey.bookclubcrawl.entity.Book;
//import com.duokey.bookclubcrawl.entity.Result;
//import com.duokey.bookclubcrawl.mapper.BookMapper;
//import com.duokey.bookclubcrawl.service.IBookService;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import jakarta.annotation.Resource;
//import lombok.extern.slf4j.Slf4j;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * <p>
// * 书本 服务实现类
// * </p>
// *
// * @author duokey
// * @since 2024-03-20
// */
//@Slf4j
//@Service
//public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {
//    @Autowired
//    private BookMapper bookMapper;
//
//    public List<Book> crawlBooks() throws IOException {
//        List<Book> books = new ArrayList<>();
//
//        // 使用Jsoup连接到网站首页并获取网页内容
//        String pageUrl = websiteConstant.pre_url + "/top/";
//        log.info("pageUrl: " + pageUrl);
//        Document doc = Jsoup.connect(pageUrl).get();
//
//        // 解析网页内容，获取书籍基本信息
//        Elements bookBlocks = doc.select(".blocks");
//        for (Element bookBlock : bookBlocks) {
//            // 在每个.blocks元素中选择所有的a标签
//            Elements bookLinks = bookBlock.select("a");
//
//            for(Element bookLink : bookLinks) {
//                // 获得书本名字和URL
//                String bookName = bookLink.text();
//                String bookUrl = bookLink.attr("href");
//
//                // 创建Book对象
//                Book book = new Book();
//
//                // 使用Jsoup连接到书本详情页并获取网页内容
//                Document bookDoc = Jsoup.connect(websiteConstant.pre_url + bookUrl).get();
//
//                // 获取书籍类别
//                // 选择类名为"path wap_none"的<div>元素
//                Element divElement = bookDoc.selectFirst("div.path.wap_none");
//
//                // 获取<div>元素的文本内容
//                String text = divElement.text();
//
//                // 使用split方法提取两个">"之间的内容
//                String[] parts = text.split(">");
//                if (parts.length >= 3) {
//                    String type = parts[1].trim();
//                    book.setType(type);
//                }
//
//
//                // 获取书籍信息的父级元素
//                Element infoElement = bookDoc.selectFirst(".info");
//
//                // 获取书籍封面URL
//                String coverUrl = infoElement.selectFirst(".cover img").attr("src");
//
//                // 获取书籍作者
//                String author = infoElement.selectFirst(".small span:eq(0)").text()
//                    .split("：", 2)[1].trim();
//
//                // 获取书籍状态
//                String status = infoElement.selectFirst(".small span:eq(1)").text()
//                    .split("：", 2)[1].trim();
//
//                // 获取书籍更新时间
//                String update = infoElement.selectFirst(".small span.last").text();
//                String updateTime = update.split("：", 2)[1].trim();
//                // 定义日期时间的格式化对象
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//                // 解析字符串并转换为 LocalDateTime 对象
//                LocalDateTime dateTime = LocalDateTime.parse(updateTime, formatter);
//
//                // 获取书籍内容简介
//                String intro = infoElement.selectFirst(".intro dd").text();
//
//                book.setImgUrl(coverUrl);
//                book.setAuthor(author);
//                book.setStatus(status);
//                book.setUpdateTime(dateTime);
//                book.setBookInfo(intro);
//
//                log.info("book: " + book.toString());
//                // 添加到数据库中并添加到列表中
//                int insert = bookMapper.insert(book);
//                if (insert != 0) {
//                    books.add(book);
//                }
//            }
//        }
//        return books;
//    }
//}
