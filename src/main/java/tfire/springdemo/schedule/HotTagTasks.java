package tfire.springdemo.schedule;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tfire.springdemo.cache.HotTagCache;
import tfire.springdemo.mapper.QuestionMapper;
import tfire.springdemo.model.Question;
import tfire.springdemo.model.QuestionExample;

import java.util.*;
import java.util.logging.Logger;

@Component
@Slf4j
public class HotTagTasks {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private HotTagCache hotTagCache;


    @Scheduled(fixedRate = 360000)
    public void hotTagSchedule() {
        int offset = 0;
        int limit = 20;
        List<Question> list = new ArrayList<>();
        Map<String, Integer> tagMap = new HashMap<>();
        while (offset == 0 || list.size() == limit) {
            list = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset,limit));
            for (Question question : list) {
                String[] tags = StringUtils.split(question.getTag(), ";");
                for (String tag : tags) {
                    Integer priority = tagMap.get(tag);
//                    System.out.println(tag + " : " + priority);
                    if (priority != null) {
                        tagMap.put(tag, priority + 5 + question.getCommentCount() + question.getViewCount() / 10);
                    } else {
                        tagMap.put(tag, 5 + question.getCommentCount() + question.getViewCount() / 10);
                    }
                }
            }
            offset += limit;
        }
        hotTagCache.updateTags(tagMap);
    }
}
