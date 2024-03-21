package com.duokey.bookclubcrawl.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Objects;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Lombok;
import lombok.experimental.Accessors;

/**
 * <p>
 * 书本
 * </p>
 *
 * @author duokey
 * @since 2024-03-20
 */
@Data
//@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("book")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("bookName")
    private String bookName;

    @TableField("author")
    private String author;

    @TableField("type")
    private String type;

    @TableField("status")
    private String status;

    @TableField("updateTime")
    private LocalDateTime updateTime;

    @TableField("bookInfo")
    private String bookInfo;

    @TableField("imgUrl")
    private String imgUrl;

    public Book(String bookName, String author, String bookInfo, String imgUrl) {
        this.bookName = bookName;
        this.author = author;
        this.bookInfo = bookInfo;
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "Book{" +
            "id=" + id +
            ", bookName='" + bookName + '\'' +
            ", author='" + author + '\'' +
            ", type='" + type + '\'' +
            ", status='" + status + '\'' +
            ", updateTime=" + updateTime +
            ", bookInfo='" + bookInfo + '\'' +
            ", imgUrl='" + imgUrl + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        return Objects.equals(bookName, book.bookName) && Objects.equals(author, book.author)
            && Objects.equals(bookInfo, book.bookInfo) && Objects.equals(imgUrl, book.imgUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookName, author, bookInfo, imgUrl);
    }
}
