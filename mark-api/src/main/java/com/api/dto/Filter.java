package com.api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author OrangeMantra
 * @since JDK 1.8
 * @version 1.0
 *
 */
@Data
@EqualsAndHashCode(of = {"name", "values"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Filter {

  private String name;
  private List<Object> values;
  private boolean isRegex;
  private boolean isRange;

}
