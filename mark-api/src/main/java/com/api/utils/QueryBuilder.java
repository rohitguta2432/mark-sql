package com.api.utils;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;

import com.api.dto.Filter;

/**
 * @author RITESH SINGH
 * @since JDK 1.8
 * @version 1.0
 *
 */

public class QueryBuilder {


  public static Query createQuery(List<Filter> filters, Pageable pageable) {
    Query query = new Query();
    if (!CollectionUtils.isEmpty(filters)) {
      filters
          .forEach(filter -> {
            if (filter.getValues().size() == 1) {
              if (filter.isRegex()) {
                query.addCriteria(Criteria.where(filter.getName()).regex(
                    Pattern.compile(filter.getValues().get(0).toString(), Pattern.CASE_INSENSITIVE
                        | Pattern.UNICODE_CASE)));
              } else {
                query.addCriteria(Criteria.where(filter.getName()).is(filter.getValues().get(0)));
              }
            } else {
              if (filter.isRange()) {

                if (filter.getValues().size() == 4) {

                  if (filter.getValues().get(1).equals("gt")
                      && filter.getValues().get(3).equals("lt")) {
                    query.addCriteria(Criteria.where(filter.getName())
                        .gt(Long.parseUnsignedLong((String) filter.getValues().get(0)))
                        .lt(Long.parseLong((String) filter.getValues().get(2))));
                  }
                  if (filter.getValues().get(1).equals("lt")
                      && filter.getValues().get(3).equals("gt")) {
                    query.addCriteria(Criteria.where(filter.getName())
                        .gt(Long.parseUnsignedLong((String) filter.getValues().get(2)))
                        .lt(Long.parseLong((String) filter.getValues().get(0))));
                  }

                  if (filter.getValues().get(1).equals("lte")
                      && filter.getValues().get(3).equals("gte")) {
                    query.addCriteria(Criteria.where(filter.getName())
                        .gte(Long.parseUnsignedLong((String) filter.getValues().get(2)))
                        .lte(Long.parseLong((String) filter.getValues().get(0))));
                  }
                  if (filter.getValues().get(1).equals("gte")
                      && filter.getValues().get(3).equals("lte")) {
                    query.addCriteria(Criteria.where(filter.getName())
                        .gte(Long.parseUnsignedLong((String) filter.getValues().get(0)))
                        .lte(Long.parseLong((String) filter.getValues().get(2))));
                  }

                  if (filter.getValues().get(1).equals("gte")
                      && filter.getValues().get(3).equals("lt")) {
                    query.addCriteria(Criteria.where(filter.getName())
                        .gte(Long.parseUnsignedLong((String) filter.getValues().get(0)))
                        .lt(Long.parseLong((String) filter.getValues().get(2))));
                  }
                  if (filter.getValues().get(1).equals("lt")
                      && filter.getValues().get(3).equals("gte")) {
                    query.addCriteria(Criteria.where(filter.getName())
                        .gte(Long.parseUnsignedLong((String) filter.getValues().get(2)))
                        .lt(Long.parseLong((String) filter.getValues().get(0))));
                  }

                  if (filter.getValues().get(1).equals("lte")
                      && filter.getValues().get(3).equals("gt")) {
                    query.addCriteria(Criteria.where(filter.getName())
                        .gt(Long.parseUnsignedLong((String) filter.getValues().get(2)))
                        .lte(Long.parseLong((String) filter.getValues().get(0))));
                  }
                  if (filter.getValues().get(1).equals("gt")
                      && filter.getValues().get(3).equals("lte")) {
                    query.addCriteria(Criteria.where(filter.getName())
                        .gt(Long.parseUnsignedLong((String) filter.getValues().get(0)))
                        .lte(Long.parseLong((String) filter.getValues().get(2))));
                  }

                } else {
                  if (filter.getValues().get(1).equals("gt")) {
                    query.addCriteria(Criteria.where(filter.getName()).gt(
                        Long.parseUnsignedLong((String) filter.getValues().get(0))));
                  }
                  if (filter.getValues().get(1).equals("lt")) {
                    query.addCriteria(Criteria.where(filter.getName()).lt(
                        Long.parseLong((String) filter.getValues().get(0))));
                  }
                  if (filter.getValues().get(1).equals("gte")) {
                    query.addCriteria(Criteria.where(filter.getName()).gte(
                        Long.parseUnsignedLong((String) filter.getValues().get(0))));
                  }
                  if (filter.getValues().get(1).equals("lte")) {
                    query.addCriteria(Criteria.where(filter.getName()).lte(
                        Long.parseLong((String) filter.getValues().get(0))));
                  }
                }
              } else
                query.addCriteria(Criteria.where(filter.getName()).in(filter.getValues()));
            }
          });
    }

    if (pageable != null) {
      query.with(pageable);
    }
    return query;
  }

  public static Query createQuery(List<Filter> filters, Map<String, Object> queryMap,
      Pageable pageable) {

    Query query = createQuery(filters, pageable);
    if (!CollectionUtils.isEmpty(queryMap)) {
      queryMap.keySet().forEach(key -> {
        query.addCriteria(Criteria.where(key).is(queryMap.get(key)));
      });
    }
    return query;
  }

}
