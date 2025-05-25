package com.pfe.hypermax.service.impl;


import com.pfe.hypermax.model.SearchHistory;
import com.pfe.hypermax.repository.SearchHistoryRepository;
import com.pfe.hypermax.service.SearchHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchHistoryServiceImpl implements SearchHistoryService {

    private final SearchHistoryRepository searchHistoryRepository;

    public SearchHistoryServiceImpl(SearchHistoryRepository searchHistoryRepository) {
        this.searchHistoryRepository = searchHistoryRepository;
    }

    @Override
    public List<SearchHistory> getAllSearchHistories() {
        return searchHistoryRepository.findAllByOrderBySearchDateDesc();
    }
}