package mate.amazon.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import mate.amazon.service.FindMostFrequentWordFromComments;
import org.springframework.stereotype.Service;

@Service
public class FindMostFrequentWordFromCommentsImpl implements FindMostFrequentWordFromComments {
    @Override
    public List<String> countWordsInString(List<String> comments) {
        Map<String, Integer> countWords = new HashMap<String, Integer>();
        for (String c : comments) {
            String[] words = c.toLowerCase()
                    .replaceAll("[^a-z]", ",")
                    .split(",");
            for (String word : words) {
                if (countWords.keySet().contains(word)) {
                    countWords.put(word, countWords.get(word) + 1);
                } else {
                    countWords.put(word, 1);
                }
            }
        }
        return countWords.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .limit(1000)
                .collect(Collectors.toList());
    }
}
