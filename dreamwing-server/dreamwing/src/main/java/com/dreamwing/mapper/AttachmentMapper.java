package com.dreamwing.mapper;

import com.dreamwing.pojo.Attachment;
import com.dreamwing.pojo.AttachmentGetListDataDTO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AttachmentMapper {
    @Insert("insert into attachment(type, url, create_time,create_user,origin_name) VALUES (#{type},#{url},NOW(),#{user_id},#{originalFilename})")
    void add(String type, String url, Integer user_id,String originalFilename);

    @Select("select attachment.id as id,type,url,attachment.create_time as create_time,create_user,username,origin_name " +
            "from attachment join user on create_user=user.id where user.id=#{id} and create_user=#{thisUserId}")
    Attachment getById(Integer id, Integer thisUserId);

    @Delete("delete from attachment where id=#{id} and create_user=#{thisUserId}")
    void delete(Integer id,Integer thisUserId);

    @Delete("delete from attachment where id=#{id}")
    void deleteForAdmin(Integer id);

    Page<Attachment> getListByCondition(@Param("attachmentGetListDataDTO") AttachmentGetListDataDTO attachmentGetListDataDTO, Integer user_id);

    @Select("select DISTINCT type FROM attachment;")
    List<String> getAttachmentTypeList();

    Page<Attachment> getListByConditionForAdmin(@Param("attachmentGetListDataDTO") AttachmentGetListDataDTO attachmentGetListDataDTO);

    @Select("select attachment.id as id,type,url,attachment.create_time as create_time,create_user,username,origin_name " +
            "from attachment join user on create_user=user.id where user.id=#{id}")
    Attachment getByIdForAdmin(Integer id);
}
