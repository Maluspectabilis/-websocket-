package com.example.demo.dao;



import com.example.demo.pojo.ChatMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Classname ChatMessageMapper
 * @Description 消息存储
 * @Date 2023/3/11 12:07
 * @Created by FunCoder
 */
@Mapper
public interface ChatMessageMapper {

    @Insert("INSERT INTO chatMessage(text, sender, receiver, sendtime)\n" +
            "        VALUES (#{text}, #{sender}, #{receiver}, #{sendtime})")
    void insertMessage(ChatMessage chatMessage);

    @Select("SELECT *\n" +
            "        FROM chatMessage\n" +
            "        WHERE (sender = #{sender} AND receiver = #{receiver})\n" +
            "           OR (sender = #{receiver} AND receiver = #{sender})")
    List<ChatMessage> selectMessageHistory(String sender,String receiver);

}
