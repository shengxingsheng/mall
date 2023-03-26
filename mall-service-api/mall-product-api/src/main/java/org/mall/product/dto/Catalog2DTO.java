package org.mall.product.dto;

import lombok.*;

import java.util.List;

/**
 * @author sxs
 * @since 2023/1/31
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Catalog2DTO {
    private Long id;
    private Long catalog1Id;
    private String name;
    private List<Catalog3DTO> catalog3List;


    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Catalog3DTO{
        private Long id;
        private Long catalog2Id;
        private String name;


    }
}
