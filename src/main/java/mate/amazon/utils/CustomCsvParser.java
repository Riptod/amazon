package mate.amazon.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import mate.amazon.entity.AmazonReviewEntity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class CustomCsvParser {

    public List<AmazonReviewEntity> readCsvFile(String fileName) throws IOException {
        Resource resource = new ClassPathResource(fileName);
        File csvFile = resource.getFile();
        InputStreamReader input = new InputStreamReader(new FileInputStream(csvFile));
        return parseCsvFile(input);
    }

    public List<AmazonReviewEntity> parseCsvFile(InputStreamReader inputStreamReader)
            throws IOException {
        CSVParser csvParser = null;
        csvParser = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(inputStreamReader);
        List<AmazonReviewEntity> reviews = new ArrayList<>();
        for (CSVRecord record : csvParser) {
            AmazonReviewEntity review = new AmazonReviewEntity();
            review.setProductId(record.get("ProductId"));
            review.setUserId(record.get("UserId"));
            review.setProfileName(record.get("ProfileName"));
            review.setText(record.get("Text"));
            reviews.add(review);
        }
        return reviews;
    }
}
