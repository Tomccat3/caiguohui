package com.myapp.service.house.search;

import com.myapp.SearchProjectApplicationTests;
import com.myapp.service.search.ISearchService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author cgh
 */
public class SearchServiceTests extends SearchProjectApplicationTests {

    @Autowired
    private ISearchService searchService;

    @Test
    public void test(){
        Long tId = 15L;
        searchService.index(tId);
    }

    @Test
    public void testRemove(){
        Long tId = 15L;
        searchService.remove(tId);
    }
}
