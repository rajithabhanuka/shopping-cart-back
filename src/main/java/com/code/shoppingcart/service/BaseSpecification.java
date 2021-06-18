package com.code.shoppingcart.service;

import org.springframework.data.jpa.domain.Specification;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public interface BaseSpecification {

    /** 1
     * if value == null then specification is ignored
     * This is for like query for strings
     */
    default <T> Specification<T> querySpecificationLike(String value, String column) {
        return (root, query, builder) ->
                value == null ?
                        builder.conjunction() :
                        builder.like(root.get(column), "%" +value+ "%");
    }

    /** 2
     * if value == null then specification is ignored
     * this is for equal query for an integers
     */
    default <T> Specification<T> querySpecificationEqual(int value, String column) {
        return (root, query, builder) ->
                value == 0 ?
                        builder.conjunction() :
                        builder.equal(root.get(column), value);
    }

    /** 3
     * if value == null then specification is ignored
     * This is for boolean checking
     */
    default  <T> Specification<T> querySpecificationStatusEquals(String value, String column) {
        return (root, query, builder) ->
                value == null || value.isEmpty() ?
                        builder.conjunction() :
                        builder.equal(root.get(column), Boolean.parseBoolean(value));
    }

    /** 4
     * if value == null then specification is ignored
     * This is for date checking
     * Entered date less than or equals to date in the db
     */
    default <T> Specification<T> querySpecificationLessThanOrEqual(String value, String column) {
        return (root, query, builder) ->
        {
            try {
                return value == null || value.isEmpty() ?
                        builder.conjunction() :
                        builder.lessThanOrEqualTo(root.get(column),  new SimpleDateFormat("yyyy-MM-dd").parse(value));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        };
    }

    /** 5
     * if value == null then specification is ignored
     * This is for date checking
     * Entered date greater than or equals to date in the db
     */
    default <T> Specification<T> querySpecificationGreaterThanOrEqual(String value, String column) {
        return (root, query, builder) ->
        {
            try {
                return value == null || value.isEmpty() ?
                        builder.conjunction() :
                        builder.greaterThanOrEqualTo(root.get(column), new SimpleDateFormat("yyyy-MM-dd").parse(value));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        };
    }

}
