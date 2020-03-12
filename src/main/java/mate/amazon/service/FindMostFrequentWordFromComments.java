package mate.amazon.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface FindMostFrequentWordFromComments {
    List<String> countWordsInString(List<String> comments);
}
