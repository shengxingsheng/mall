package org.mall.product.constant;

/**
 * @author sxs
 * @since 2023/1/13
 */
public class ProductConstant {

    private ProductConstant() {}

    //分类等级
    public static final Integer CATEGORY_LEVEL_ZERO = 0;
    public static final Integer CATEGORY_LEVEL_ONE = 1;
    public static final Integer CATEGORY_LEVEL_TOW = 2;
    public static final Integer CATEGORY_LEVEL_THREE = 3;

    //属性类型 0销售属性 1基本属性
    public static final Byte ATTR_TYPE_SALE = 0;
    public static final String ATTR_TYPE_SALE_STRING = "sale";
    public static final Byte ATTR_TYPE_BASE = 1;
    public static final String ATTR_TYPE_BASE_STRING = "base";
    //pms_sku_images
    //不是默认
    public static final Byte NOT_DEFAULT = 0;
    //是默认
    public static final Byte IS_DEFAULT = 1;


    /**
     * 下架 - 商品上架状态
     */
    public static final byte SPU_DOWN = 0;
    /**
     * 上架 -商品上架状态
     */
    public static final byte SPU_UP = 1;

    /**
     * 属性搜索状态 - 不可被搜索
     */
    public static final byte ATTR_BAN_SEARCH = 0;
    /**
     * 属性搜索状态 - 可被搜索
     */
    public static final byte ATTR_CAN_SEARCH = 1;
}
