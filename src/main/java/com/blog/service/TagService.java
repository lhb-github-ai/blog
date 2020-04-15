package com.blog.service;

import com.blog.po.Tag;
import com.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by limi on 2017/10/16.
 */
public interface TagService {

    Tag saveTag(Tag type);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag();

    List<Tag> listTag(String ids);
    List<Tag> listTagTop(Integer size);

    Tag updateTag(Long id, Tag type);

    void deleteTag(Long id);
}
