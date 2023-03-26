package org.mall.search.controller;

import org.mall.search.query.SearchQuery;
import org.mall.search.service.MallSearchService;
import org.mall.search.vo.SearchVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sxs
 * @since 2023/2/8
 */
@Controller
public class SearchController {

    private final MallSearchService mallSearchService;

    public SearchController(MallSearchService mallSearchService) {
        this.mallSearchService = mallSearchService;
    }

    @GetMapping("list.html")
    public String list(SearchQuery query, Model model, HttpServletRequest request) {
        query.setQueryString(request.getQueryString());
        SearchVo result = mallSearchService.search(query);
        model.addAttribute("result", result);

        return "list";
    }

}
