package com.duokey.bookclubcrawl.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author duokey
 * @since 2024-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("chapter")
public class Chapter implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("chapterNumber")
    private Long chapterNumber;

    @TableField("chapterTitle")
    private String chapterTitle;

    @TableField("content")
    private String content;

    @TableField("bookId")
    private Long bookId;

    @TableField("chapterUrl")
    private String chapterUrl;

    public Chapter(Long bookId, Long chapterNumber, String chapterTitle, String chapterUrl) {
        this.bookId = bookId;
        this.chapterNumber = chapterNumber;
        this.chapterTitle = chapterTitle;
        this.chapterUrl = chapterUrl;
    }

    @Override
    public String toString() {
        return "Chapter{" +
            "id=" + id +
            ", chapterNumber=" + chapterNumber +
            ", chapterTitle='" + chapterTitle + '\'' +
            ", content='" + content + '\'' +
            ", bookId=" + bookId +
            '}';
    }
}
