package de.toomuchcoffee.model.services;

import de.toomuchcoffee.view.CollectibleDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.util.Arrays.stream;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class ImportService {

    private final CollectibleService collectibleService;

    @Transactional
    public void importCsv(byte[] data) throws IOException {
        CSVParser parser = new CSVParser(
                new InputStreamReader(new ByteArrayInputStream(data)),
                CSVFormat.DEFAULT
                        .withHeader()
                        .withDelimiter(';')
                        .withIgnoreSurroundingSpaces()
                        .withIgnoreEmptyLines()
                        .withAllowMissingColumnNames());

        parser.getRecords().stream()
                .filter(this::isNotEmpty)
                .map(this::getCollectibleDto)
                .forEach(collectibleService::add);
    }

    private CollectibleDto getCollectibleDto(CSVRecord record) {
        CollectibleDto collectible = new CollectibleDto();
        collectible.setVerbatim(record.get("verbatim"));
        collectible.setProductLine(record.get("productLine"));
        collectible.setPlacementNo(getNullableValue(record, "placementNo"));
        collectible.setTags(getNullableValue(record, "movie"));
        collectible.setPartOf(getNullableValue(record, "partOf"));
        return collectible;
    }

    private String getNullableValue(CSVRecord record, String key) {
        String value = record.get(key);
        return isEmpty(value) ? null : value;
    }

    private boolean isNotEmpty(CSVRecord record) {
        return stream(record.toMap().values().stream().mapToInt(s -> s.trim().length()).toArray()).sum() > 0;
    }

}