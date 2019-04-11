package com.api.utils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.google.common.base.Splitter;
import com.common.constants.FiledDataTypeMap;
import com.api.dto.Filter;

/**
 * @author RITESH SINGH
 * @since JDK 1.8
 * @version 1.0
 *
 */


public class QueryParser {

  private static final String FILTER_SLIPTTER = ";";
  private static final String ATTRIBUTE_NAME_SLIPTTER = ":";
  private static final String ATTRIBUTE_VALUE_SLIPTTER = ",";
  private static final String REGEX_SPLITTER = "~";
  private static final String RANGE_SPLITTER = "!";


  public static List<Filter> parse(String query) {

    if (StringUtils.isEmpty(query)) {
      return Collections.emptyList();
    }
    List<String> filterAsStringList =
        Splitter.on(FILTER_SLIPTTER).omitEmptyStrings().splitToList(query);
    return filterAsStringList.stream().map(filterAsList -> {
      return createFilter(filterAsList);
    }).filter(f -> f != null).collect(Collectors.toList());
  }

  private static Filter createFilter(String attributeKeyValue) {

    List<String> attributesAndRegexFilter =
        Splitter.on(REGEX_SPLITTER).omitEmptyStrings().splitToList(attributeKeyValue);

    if (CollectionUtils.isEmpty(attributesAndRegexFilter)) {
      return null;
    }

    List<String> attributesAndBetweenFilter =
        Splitter.on(RANGE_SPLITTER).omitEmptyStrings().splitToList(attributeKeyValue);

    if (CollectionUtils.isEmpty(attributesAndBetweenFilter)) {
      return null;
    }

    String reange = attributesAndBetweenFilter.size() > 1 ? attributesAndBetweenFilter.get(1) : "";
    String regex = attributesAndRegexFilter.size() > 1 ? attributesAndRegexFilter.get(1) : "";

    String filter;
    if (!StringUtils.isEmpty(reange))
      filter = attributesAndBetweenFilter.get(0);
    else
      filter = attributesAndRegexFilter.get(0);

    List<String> keyValueList =
        Splitter.on(ATTRIBUTE_NAME_SLIPTTER).omitEmptyStrings().splitToList(filter);
    String attributeName = keyValueList.get(0).trim();
    List<String> values =
        Splitter.on(ATTRIBUTE_VALUE_SLIPTTER).omitEmptyStrings().splitToList(keyValueList.get(1));
    if (!StringUtils.isEmpty(reange)) {
      return Filter.builder().name(attributeName)
          .values(values.stream().map(String::trim).map(value -> {
            return FiledDataTypeMap.parseValue(attributeName, value);
          }).collect(Collectors.toList())).isRange(!StringUtils.isEmpty(reange)).build();
    } else {
      return Filter.builder().name(attributeName)
          .values(values.stream().map(String::trim).map(value -> {
            return FiledDataTypeMap.parseValue(attributeName, value);
          }).collect(Collectors.toList())).isRegex(!StringUtils.isEmpty(regex)).build();
    }
  }
}
