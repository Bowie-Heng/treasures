package com.bowie.notes.framework.dao.EsDao;

import com.bowie.notes.framework.entity.document.ProductPriceTag;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductPriceTagRepository extends ElasticsearchRepository<ProductPriceTag, Long>, PagingAndSortingRepository<ProductPriceTag, Long> {

}
