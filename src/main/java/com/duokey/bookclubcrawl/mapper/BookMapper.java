package com.duokey.bookclubcrawl.mapper;

import com.duokey.bookclubcrawl.entity.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 书本 Mapper 接口
 * </p>
 *
 * @author duokey
 * @since 2024-03-20
 */
@Mapper
public interface BookMapper extends BaseMapper<Book> {

}
