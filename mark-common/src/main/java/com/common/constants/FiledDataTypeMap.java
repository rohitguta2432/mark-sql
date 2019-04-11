package com.common.constants;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

/**
 * @author OrangeMantra
 * @since JDK 1.8
 * @version 1.0
 *
 */
public class FiledDataTypeMap {


  private static final Map<String, String> DATA_TYPE_MAP;
  static {
    DATA_TYPE_MAP = new HashMap<String, String>();
    DATA_TYPE_MAP.put(ModelConstants.STATUS, "INTEGER");
    DATA_TYPE_MAP.put(ModelConstants.CREATED_DATE, "LONG");
  }

/**
 * 
 * @param attributeName
 * @param value
 * @return returns {@code Object}
 */
  public static Object parseValue(String attributeName, String value) {
    String type = getDataType(attributeName);
    if ("INTEGER".equals(type)) {
      return Integer.parseInt(value);
    }else if("LONG".equals(type)){
      return Long.parseLong(value);
    }
    
    return value;
  }

  /**
   * 
   * @param fieldName
   * @return returns {@link java.lang.String}
   */
  private static String getDataType(String fieldName) {
    String type = DATA_TYPE_MAP.get(fieldName.toLowerCase());
    if (StringUtils.isEmpty(type)) {
      return "STRING";
    }
    return type;
  }
}
