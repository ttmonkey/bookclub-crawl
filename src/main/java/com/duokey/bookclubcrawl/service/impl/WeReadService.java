package com.duokey.bookclubcrawl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duokey.bookclubcrawl.entity.Book;
import com.duokey.bookclubcrawl.mapper.BookMapper;
import com.duokey.bookclubcrawl.service.IWeReadService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@Service
public class WeReadService extends ServiceImpl<BookMapper, Book> implements IWeReadService {
    @Autowired
    private BookMapper bookMapper;
    private final ExecutorService executorService = Executors.newFixedThreadPool(5); // 创建一个固定大小为5的线程池

    private String[] types = {"玄幻精品","史学著作","古典文学","情感小说","青春文学","科幻经典","悬疑推理","武侠经典","影视原著","男生小说","女生小说"};
    public Set<Book> crawlBook () throws IOException, ExecutionException, InterruptedException {
        Set<Book> books = new HashSet<>();
        List<Future<List<Book>>> futures = new ArrayList<>(); // 用于存储线程任务的Future对象

        String preUrl = "https://weread.qq.com";

        // 设置Chrome webdriver路径
        System.setProperty("webdriver.chrome.driver","D:/develop/chromedriver-win64/chromedriver.exe");
        // 创建对象
        WebDriver driver = new ChromeDriver();
        // 设置页面加载超时时间
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

        Document doc = Jsoup.connect(preUrl + "/web/category").get();

        Elements links = doc.select(".ranking_list a");
        for (Element link : links) {
            String href = link.attr("href");
            Callable<List<Book>> task = () -> {
                List<Book> someBooks = new ArrayList<>();

                driver.get(preUrl + href);
                // 模拟滚动操作，直到页面底部
                JavascriptExecutor js = (JavascriptExecutor) driver;
                long lastHeight = (long) js.executeScript("return document.body.scrollHeight");
                while (true) {
                    js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
                    try {
                        Thread.sleep(2000); // 等待加载
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    long newHeight = (long) js.executeScript("return document.body.scrollHeight");
                    if (newHeight == lastHeight) {
                        break;
                    }
                    lastHeight = newHeight;
                }
                // 获取页面上的数据
                String pageSource = driver.getPageSource();

                Document booksDoc = Jsoup.parse(pageSource);
//                Document booksDoc = Jsoup.connect(preUrl + href).get();
                Elements booksEle = booksDoc.select("li.wr_bookList_item");
                for (Element element : booksEle) {
                    String coverImageUrl = element.select(".wr_bookCover_img").attr("src");
                    String title = element.select(".wr_bookList_item_title").text();
                    String author = element.select(".wr_bookList_item_author").text();
                    String info = element.select(".wr_bookList_item_desc").text();

                    Book book = new Book(title, author, info, coverImageUrl);

                    // 随机生成一些其他的信息
                    book.setType(randomType());
                    book.setStatus(randomStatus());
                    book.setUpdateTime(randomTime(book));

                    someBooks.add(book);
                }
                return someBooks;
            };
            Future<List<Book>> future = executorService.submit(task);
            futures.add(future);
        }
        for (Future<List<Book>> future : futures) {
            List<Book> bookList = future.get();
            if (!bookList.isEmpty()) {
                books.addAll(bookList);
            }
        }

        for (Book book : books) {
            bookMapper.insert(book);
        }
        return books;
    }

    /**
     * 生成随机数，返回随机的一个书籍类型
     * @return 书本的类型
     */
    private String randomType() {
        int count = types.length;
        Random random = new Random();
        int num = random.nextInt(count);
        return types[num];
    }

    /**
     * 随机生成书籍状态：连载或完本
     * @return 书本状态
     */
    private String randomStatus() {
        Random random = new Random();
        int num = random.nextInt(100);
        if (num % 2 == 0) {
            return "连载";
        }
        return "完本";
    }

    /**
     * 根据书本的状态随机生成书本的更新时间
     * @param book 书本，用于获得书本的状态
     * @return 书本的更新时间
     */
    private LocalDateTime randomTime(Book book) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime updateTime;
        String status = book.getStatus();

        Random random = new Random();
        if ("连载".equals(status)) {
            int randomHours = random.nextInt(24 * 7) + 1;
            updateTime = currentTime.minusHours(randomHours);
        } else {
            int randomHours = random.nextInt(24);
            int randomDays = random.nextInt(31 * 6 * 5);// 5年
            updateTime = currentTime.minusDays(randomDays).minusHours(randomHours);
        }

        return updateTime;
    }

}
