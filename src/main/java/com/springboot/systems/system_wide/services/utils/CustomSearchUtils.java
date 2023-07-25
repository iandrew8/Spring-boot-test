package com.springboot.systems.system_wide.services.utils;

import com.springboot.systems.system_wide.models.enums.RecordStatus;
import com.springboot.systems.system_wide.models.enums.TemplateType;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.Sort;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CustomSearchUtils {

    private static final int MINIMUM_CHARACTERS_FOR_SEARCH_TERM = 1;

    public static boolean searchTermSatisfiesQueryCriteria(String query) {
        if (StringUtils.isBlank(query)) {
            return false;
        }
        return query.length() >= MINIMUM_CHARACTERS_FOR_SEARCH_TERM;
    }

    private static Search generateSearchTerms(String query, List<String> searchFields) {
        Search search = new Search();
        search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);

        if (StringUtils.isNotBlank(query) && CustomSearchUtils.searchTermSatisfiesQueryCriteria(query)) {
            ArrayList<Filter> filters = new ArrayList<Filter>();
            CustomSearchUtils.generateSearchTerms(searchFields, query, filters);
            search.addFilterAnd(filters.toArray(new Filter[filters.size()]));
        }
        return search;
    }

    private static boolean generateSearchTerms(List<String> searchFields, String query, List<Filter> filters) {
        if (searchFields != null && !searchFields.isEmpty()) {
            for (String token : query.replaceAll("  ", " ").split(" ")) {
                String searchTerm = "%" + StringEscapeUtils.escapeSql(token) + "%";
                Filter[] orFilters = new Filter[searchFields.size()];
                int counter = 0;
                for (String searchField : searchFields) {
                    orFilters[counter] = Filter.like(searchField, searchTerm);
                    counter++;
                }
                filters.add(Filter.or(orFilters));
            }
            return true;
        }
        return false;
    }

    public static Search generateSearchObjectForRoles(String searchTerm, Date createdFrom,
                                                      Date createdTo, Sort sort) {
        Search search = generateSearchTerms(searchTerm, Arrays.asList("name", "description"));

        if (sort != null)
            search.addSort(sort);

        if (createdFrom != null)
            search.addFilterGreaterOrEqual("dateCreated", createdFrom);

        if (createdTo != null)
            search.addFilterLessOrEqual("dateCreated", createdTo);

        return search;
    }

    public static Search generateSearchObjectForUsers(String searchTerm, Sort sort) {
        Search search = generateSearchTerms(searchTerm, Arrays.asList("firstName", "lastName", "username", "email",
                "phoneNumber"));

        if (sort != null)
            search.addSort(sort);

        return search;
    }

    public static Search generateSearchObjectForCurrenciesAndUnitOfMeasure(String searchTerm, Sort sort) {
        Search search = generateSearchTerms(searchTerm, Arrays.asList("name", "unit"));

        if (sort != null)
            search.addSort(sort);

        return search;
    }

    public static Search generateSearchObjectForCurrencyConversions(String searchTerm, Sort sort, int min, int max) {
        Search search = generateSearchTerms(searchTerm, Arrays.asList("currency.name", "currency.unit"));

        if (min > 0)
            search.addFilterLessOrEqual("oneDollarIsWorth", min);

        if (max > 0)
            search.addFilterGreaterOrEqual("oneDollarIsWorth", max);

        if (sort != null)
            search.addSort(sort);

        return search;
    }

    public static Search generateSearchObjectForApplicationSettings(String searchTerm, Sort sort) {
        Search search = generateSearchTerms(searchTerm, Arrays.asList("clickUpPrivateKey", "smartOLTAPIKey"));

        if (sort != null)
            search.addSort(sort);

        return search;
    }

    public static Search generateSearchObjectForServiceZones(String searchTerm, Sort sort) {
        Search search = generateSearchTerms(searchTerm, List.of("name"));

        if (sort != null)
            search.addSort(sort);

        return search;
    }

    public static Search generateSearchObjectForPermissions(String searchTerm, Sort sort) {
        Search search = generateSearchTerms(searchTerm, Arrays.asList("name", "description"));

        if (sort != null)
            search.addSort(sort);

        return search;
    }

    public static Search generateSearchObjectForCapacities(String searchTerm, Sort sort, int min, int max) {
        Search search = generateSearchTerms(searchTerm, Arrays.asList("unitOfMeasure.name", "unitOfMeasure.unit",
                "unitOfMeasure.id"));

        if (min > 0)
            search.addFilterLessOrEqual("quantity", min);

        if (max > 0)
            search.addFilterGreaterOrEqual("quantity", max);

        if (sort != null)
            search.addSort(sort);

        return search;
    }

    public static Search generateSearchObjectForEmailTemplate(String searchTerm, String templateType, Sort sort,
                                                              String startDateParam, String endDateParam) throws ParseException {
        Search search = generateSearchTerms(searchTerm, List.of("template"));

        if (templateType != null)
            search.addFilterEqual("templateType", TemplateType.getEnumObject(templateType));

        if (startDateParam != null)
            search.addFilterGreaterOrEqual("dateCreated", DateConversionHelper.convertToDate(startDateParam));

        if (endDateParam != null)
            search.addFilterLessOrEqual("dateCreated", DateConversionHelper.convertToDate(endDateParam));

        if (sort != null)
            search.addSort(sort);

        return search;
    }

    public static Search generateSearchObjectForAccessKeys(String searchTerm, Sort sort, String startDateParam, String endDateParam) throws ParseException {
        Search search = generateSearchTerms(searchTerm, Arrays.asList("isp.name", "isp.contact", "isp.currency.name",
                "isp.currency.unit", "isp.currency.id", "accessKey"));

        if (startDateParam != null)
            search.addFilterGreaterOrEqual("dateCreated", DateConversionHelper.convertToDate(startDateParam));

        if (endDateParam != null)
            search.addFilterLessOrEqual("dateCreated", DateConversionHelper.convertToDate(endDateParam));

        if (sort != null)
            search.addSort(sort);

        return search;
    }

    public static Search generateSearchObjectForLogs(String searchTerm, Sort sort, String startDateParam, String endDateParam) throws ParseException {
        Search search = generateSearchTerms(searchTerm, List.of("log"));

        Date startDate = null;
        Date endDate = null;
        if (StringUtils.isNotBlank(startDateParam))
            startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateParam);

        if (StringUtils.isNotBlank(endDateParam))
            endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateParam);

        if (startDate != null)
            search.addFilterGreaterOrEqual("dateCreated", startDate);

        if (endDate != null)
            search.addFilterLessOrEqual("dateCreated", endDate);

        if (sort != null)
            search.addSort(sort);

        return search;
    }

    public static Search generateSearchObjectForUserProfiles(String searchTerm, Sort sort) {
        Search search = generateSearchTerms(searchTerm, Arrays.asList("user.firstName", "user.lastName", "user.username", "user.email", "user.phoneNumber"));

        if (sort != null)
            search.addSort(sort);

        return search;
    }

    public static Search generateSearchObjectForISPs(String searchTerm, Sort sort) {
        Search search = generateSearchTerms(searchTerm, Arrays.asList("name", "contact", "currency.name", "currency.unit",
                "currency.id"));

        if (sort != null)
            search.addSort(sort);

        return search;
    }

    public static Search generateSearchObjectForResponses(String searchTerm, Sort sort) {
        Search search = generateSearchTerms(searchTerm, Arrays.asList("status", "message"));

        if (sort != null)
            search.addSort(sort);

        return search;
    }

    public static Search generateSearchObjectForRequests(String searchTerm, Sort sort) {
        Search search = generateSearchTerms(searchTerm, Arrays.asList("response.status", "response.message", "coordinates",
                "isp.name", "serviceZone.name"));

        if (sort != null)
            search.addSort(sort);

        return search;
    }
}
