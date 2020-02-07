package tfire.springdemo.cache;

import lombok.Data;
import org.springframework.stereotype.Component;
import tfire.springdemo.dto.HotTagDTO;

import java.util.*;

@Component
@Data
public class HotTagCache {
    private Map<String,Integer> tags = new HashMap<>();
    private List<HotTagDTO> hots = new ArrayList<>();

    public void updateTags(Map<String, Integer> tags) {
        int max = 10;
        PriorityQueue<HotTagDTO> pq = new PriorityQueue<>(max);

        tags.forEach((name,priority) -> {
            HotTagDTO hotTagDTO = new HotTagDTO();
            hotTagDTO.setName(name);
            hotTagDTO.setPriority(priority);
            if (pq.size() < 10) {
                pq.add(hotTagDTO);
            } else {
                HotTagDTO minHot = pq.peek();
                if (hotTagDTO.compareTo(minHot) > 0) {
                    pq.poll();
                    pq.add(hotTagDTO);
                }
            }
        });

        List<HotTagDTO> sortedTags = new ArrayList<>();
        while (pq.size() > 0) {
            sortedTags.add(0,pq.poll());
        }
        hots = sortedTags;
    }
}
