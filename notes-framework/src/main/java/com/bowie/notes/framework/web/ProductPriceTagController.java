package com.bowie.notes.framework.web;

import com.bowie.notes.framework.dao.EsDao.ProductPriceTagRepository;
import com.bowie.notes.framework.entity.document.ProductPriceTag;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/search")
public class ProductPriceTagController {

    @Autowired
    private ProductPriceTagRepository repository;

    @GetMapping("/id/{id}")
    public ProductPriceTag getProductPriceTagById(@PathVariable Long id){
        Optional<ProductPriceTag> byId = repository.findById(id);
        return byId.orElse(null);
    }

    @GetMapping("/name/{tagName}")
    public Page<ProductPriceTag> getProductPriceTagByName(@PathVariable String tagName,@PageableDefault(value = 15, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable){
        return repository.search(QueryBuilders.matchQuery("tagName", tagName), pageable);
    }
}
