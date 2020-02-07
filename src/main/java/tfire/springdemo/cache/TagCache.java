package tfire.springdemo.cache;

import tfire.springdemo.dto.TagDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TagCache {
    public static List<TagDTO> get() {
        List<TagDTO> tagDTOS = new ArrayList<>();

        TagDTO algorithms = new TagDTO();
        algorithms.setCategoryName("Algorithms");
        algorithms.setTags(Arrays.asList("LinkedList", "Tree", "Graph", "DFS", "BFS", "Topological Sort", "Greedy", "DP", "Recursion", "Two Pointer", "Sliding Window", "Network Flow"));
        tagDTOS.add(algorithms);

        TagDTO language = new TagDTO();
        language.setCategoryName("Language");
        language.setTags(Arrays.asList("Java","PHP","JavaScript","C","C++","C#","Python", "Lua", "Go", "HTML", "CSS", "Shell", "Ruby", "Perl", "Scala", "Other Languages"));
        tagDTOS.add(language);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("Framework");
        framework.setTags(Arrays.asList("Spring", "Spring Boot", "Spring MVC", "NodeJS","Flask", "Django", "Koa", "Vue", "React", "Angular", "Other frameworks"));
        tagDTOS.add(framework);

        TagDTO server =  new TagDTO();
        server.setCategoryName("Server");
        server.setTags(Arrays.asList("Linux", "Nginx", "Docker", "Apache", "Other servers"));
        tagDTOS.add(server);

        TagDTO database = new TagDTO();
        database.setCategoryName("Database");
        database.setTags(Arrays.asList("MySQL", "Redis", "MongoDB", "SQL", "Oracle", "PostgreSQL", "SQLite", "Other databases"));
        tagDTOS.add(database);

        return tagDTOS;
    }
}
