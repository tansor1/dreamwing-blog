package com.dreamwing.mapper;

import com.dreamwing.pojo.ArticleDTO;
import com.dreamwing.pojo.ArticleGetListDataDTO;
import com.dreamwing.pojo.ArticleVO;
import com.dreamwing.pojo.TagVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {

    Integer add(@Param("articleDTO") ArticleDTO articleDTO);

    @Select("select article.id as id,user_id,username as author,category_id,category_name,article_cover,article_title,article_abstract," +
            "article_content,type,look_num,article.password as password,origin_url,article.create_time as create_time," +
            "article.update_time as update_time, status " +
            "from article join user join category on user_id=user.id and category_id=category.id " +
            "where is_delete=0 and article.id=#{id}")
    ArticleVO getById(Integer id);

    @Select("select article.id as id,user_id,username as author,category_id,category_name,article_cover,article_title,article_abstract," +
            "article_content,status,type,look_num,article.password as password,origin_url,article.create_time as create_time," +
            "article.update_time as update_time " +
            "from article join user join category on user_id=user.id and category_id=category.id " +
            "where is_delete=0 and status=1 and (article.password is null or article.password='')")
    Page<ArticleVO> getList();

    @Select("select tag.id as id,name,tag.create_time as create_time," +
            "tag.update_time as update_time,tag.create_user as create_user," +
            "user.username as create_user_name " +
            "from tag join user on create_user=user.id join article_tag on tag.id = article_tag.tag_id " +
            "where article_tag.article_id=#{articleId}")
    List<TagVO> getTagListByArticleId(Integer articleId);

    void deleteByArticleIdAndTagList(Integer articleId, List<Integer> tagList);

    void addTagListToArticle(Integer articleId, List<TagVO> tagList);

    @Update("update article set category_id=#{articleDTO.categoryId}," +
            "article_cover=#{articleDTO.articleCover}," +
            "article_title=#{articleDTO.articleTitle}," +
            "article_abstract=#{articleDTO.articleAbstract}," +
            "article_content=#{articleDTO.articleContent}," +
            "is_delete=#{articleDTO.isDelete}," +
            "status=#{articleDTO.status}," +
            "type=#{articleDTO.type}," +
            "password=#{articleDTO.password}," +
            "origin_url=#{articleDTO.originUrl}," +
            "update_time=#{articleDTO.updateTime} " +
            "where id=#{articleDTO.id} and user_id=#{thisUserId}")
    void updateArticle(ArticleDTO articleDTO, Integer thisUserId);

    @Update("update article set category_id=#{categoryId}," +
            "article_cover=#{articleCover}," +
            "article_title=#{articleTitle}," +
            "article_abstract=#{articleAbstract}," +
            "article_content=#{articleContent}," +
            "is_delete=#{isDelete}," +
            "status=#{status}," +
            "type=#{type}," +
            "password=#{password}," +
            "origin_url=#{originUrl}," +
            "update_time=#{updateTime} " +
            "where id=#{id}")
    void updateArticleForAdmin(ArticleDTO articleDTO);


    @Delete("update article set is_delete=1 where id=#{id} and user_id=#{thisUserId}")
    void deleteArticleById(Integer id, Integer thisUserId);

    Page<ArticleVO> getListByCondition(ArticleGetListDataDTO articleGetListDataDTO, Integer userId);

    void deleteArticleByIdList(List<Integer> idList, Integer thisUserId);

    Page<ArticleVO> getListByConditionForAdmin(ArticleGetListDataDTO articleGetListDataDTO);

    @Delete("update article set is_delete=1 where id=#{id}")
    void deleteArticleByIdForAdmin(Integer id);

    void deleteArticleByIdListForAdmin(List<Integer> idList);
}
