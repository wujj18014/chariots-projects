/**
 * Copyright 2025, Wujingjie. All rights reserved.
 */
package cn.adminkhan.chariots.service;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import org.springframework.stereotype.Service;


/**
 * @Package : cn.adminkhan.chariots.service
 * @Description : 
 * @Date : 2025/10/16 
 * @Author Wujingjie
 * @Version v1.0.0
 **/
@Service
public class Dsl2SqlService {

    private final DashScopeChatModel chatModel;

    public Dsl2SqlService(DashScopeChatModel chatModel) {
        this.chatModel = chatModel;
    }
    public String jsonToSql(String dslJson) {
        String system = "你是一名 SQL 专家。数据库表名：sales\n" +
                "                字段如下：\n" +
                "                  id          BIGINT      主键\n" +
                "                  product     VARCHAR(50) 产品名\n" +
                "                  pl1         DECIMAL     利润1\n" +
                "                  pl2         DECIMAL     利润2\n" +
                "                请根据用户给出的 JSON 报表 DSL，生成标准 MySQL 8.x 查询语句，\n" +
                "                不要解释，只返回一条 SQL 字符串。";
        String prompt = system + "\nDSL JSON:\n" + dslJson;
        String sql = chatModel.call(prompt);
        return sql.replaceAll("(?i)```sql", "").replace("```", "").trim();
    }

}
