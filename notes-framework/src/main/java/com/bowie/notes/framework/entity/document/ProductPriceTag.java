package com.bowie.notes.framework.entity.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "newproduct",type = "skuPriceTag")
public class ProductPriceTag {
    @Id
    private Long id;
    private Long priceId;
    private String tagName;
}
