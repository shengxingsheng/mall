package org.mall.product.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.mall.common.constant.ErrorCode;
import org.mall.common.exception.BusinessException;
import org.mall.common.util.Assert;
import org.mall.product.constant.ProductConstant;
import org.mall.product.dto.Catalog2DTO;
import org.mall.product.dto.CategoryDTO;
import org.mall.product.entity.Category;
import org.mall.product.mapper.CategoryMapper;
import org.mall.product.service.CategoryBrandRelationService;
import org.mall.product.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品三级分类 服务实现类
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    private final CategoryBrandRelationService categoryBrandRelationService;
    private final ObjectMapper objectMapper;
    private final StringRedisTemplate redisTemplate;

    public CategoryServiceImpl(CategoryBrandRelationService categoryBrandRelationService, ObjectMapper objectMapper, StringRedisTemplate redisTemplate) {
        this.categoryBrandRelationService = categoryBrandRelationService;
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public List<CategoryDTO> listCategoryTree() {
        List<Category> categories = this.list();
        List<CategoryDTO> categoryDtos = categories.stream().map(category -> {
            CategoryDTO categoryDto = new CategoryDTO();
            BeanUtils.copyProperties(category, categoryDto);
            return categoryDto;
        }).collect(Collectors.toList());
        //构造三级分类
        //TODO 替换递归
        return categoryDtos.stream()
                .filter(categoryDTO -> categoryDTO.getCatLevel().equals(ProductConstant.CATEGORY_LEVEL_ONE))
                .sorted(Comparator.comparingInt(o -> (o.getSort() == null ? 0 : o.getSort())))
                .map(menu -> {
                    menu.setChild(setChildCategory(menu, categoryDtos));
                    return menu;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategoryByIds(Long[] catIds) {
        this.removeBatchByIds(Arrays.asList(catIds));
    }

    @Override
    public void addCategory(Category category) {
        this.save(category);
    }


    @Override
    public void updateCategorySort(Category[] category) {
        this.updateBatchById(Arrays.asList(category));
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        ArrayList<Long> pathList = new ArrayList<>();
        findParentPath(catelogId, pathList);
        return pathList.toArray(new Long[pathList.size()]);
    }

    @CacheEvict(cacheNames = {"category"},allEntries = true)
    @Override
    public void updateCategoryAndCascade(Category category) {
        this.updateById(category);
        if (StringUtils.isNotBlank(category.getName())) {
            categoryBrandRelationService.updateCatelogName(category.getCatId(), category.getName());
        }
    }

    @Cacheable(cacheNames = {"category"},key = "#root.methodName")
    @Override
    public List<Category> getLevel1() {
        return this.list(Wrappers.<Category>lambdaQuery().eq(Category::getCatLevel, ProductConstant.CATEGORY_LEVEL_ONE));
    }

    @Cacheable(cacheNames = {"category"},key = "#root.methodName",sync = true)
    @Override
    public Map<String, List<Catalog2DTO>> getCatalog(){
        return getCatalogFromDB();
    }
/*    public Map<Long, List<Catalog2DTO>> getCatalog() {
        *//**
         * 1.缓存穿透：给不存在的key添加到缓存中
         * 2.缓存雪崩：设置随机过期时间
         * 3.缓存击穿：加锁
         *//*
        String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1]\n" +
                "then\n" +
                "    return redis.call(\"del\",KEYS[1])\n" +
                "else\n" +
                "    return 0\n" +
                "end";
        String uuid = "";
        String catalogJSON = redisTemplate.opsForValue().get("catalogJSON");
        Map<Long, List<Catalog2DTO>> map = new HashMap<>(0);
        try {
            //缓存未命中
            if (StringUtils.isBlank(catalogJSON)) {
                while (true) {
                    uuid = UUID.randomUUID().toString();
                    //尝试拿锁
                    Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uuid, 300, TimeUnit.SECONDS);
                    //判断是否拿到锁和数据库是否有值
                    if (lock) {
                        catalogJSON = redisTemplate.opsForValue().get("catalogJSON");
                        if (StringUtils.isBlank(catalogJSON)) {
                            Map<Long, List<Catalog2DTO>> catalogFromDB = getCatalogFromDB();
                            System.out.println("查询了数据库" + Thread.currentThread().toString());
                            catalogJSON = objectMapper.writeValueAsString(catalogFromDB);
                            redisTemplate.opsForValue().set("catalogJSON", catalogJSON, 1, TimeUnit.DAYS);
                        }
                        //释放锁并退出循环
                        redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class), Arrays.asList("lock"), uuid);
                        break;
                    } else {
                        catalogJSON = redisTemplate.opsForValue().get("catalogJSON");
                        if (StringUtils.isNotBlank(catalogJSON)) {
                            break;
                        }
                    }
                }
            }
            map = objectMapper.readValue(catalogJSON, new TypeReference<Map<Long, List<Catalog2DTO>>>() {
            });
        } catch (JsonProcessingException e) {
            log.error(e.toString());
        } finally {
            redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class), Arrays.asList("lock"), uuid);
            return map;
        }
    }*/

    public Map<String, List<Catalog2DTO>> getCatalogFromDB() {

        List<Category> categories = this.list();
        List<Category> level1List = getCatalogByLevel(categories, 0L);
        if (CollectionUtils.isEmpty(level1List)) {
            return new HashMap<>(0);
        }
        Map<String, List<Catalog2DTO>> map = new HashMap<>(level1List.size());
        //遍历一级目录
        for (Category level1 : level1List) {
            List<Category> level2List = getCatalogByLevel(categories, level1.getCatId());
            List<Catalog2DTO> catalog2DTOs = Collections.emptyList();
            if (!CollectionUtils.isEmpty(level2List)) {
                catalog2DTOs = level2List.stream().map(level2 -> {
                    List<Category> level3List = getCatalogByLevel(categories, level2.getCatId());
                    List<Catalog2DTO.Catalog3DTO> catalog3DTOs = Collections.emptyList();
                    if (!CollectionUtils.isEmpty(level3List)) {
                        catalog3DTOs = level3List.stream()
                                .map(catalog3 -> new Catalog2DTO.Catalog3DTO(catalog3.getCatId(), level2.getCatId(), catalog3.getName()))
                                .collect(Collectors.toList());
                    }
                    return new Catalog2DTO(level2.getCatId(), level1.getCatId(), level2.getName(), catalog3DTOs);
                }).collect(Collectors.toList());
            }
            map.put(level1.getCatId().toString(), catalog2DTOs);
        }
        return map;
    }

    private List<Category> getCatalogByLevel(List<Category> categories, Long parentId) {
        return categories.stream().filter(category -> category.getParentCid().equals(parentId)).collect(Collectors.toList());
    }

    /**
     * 递归查找父id
     *
     * @param catelogId
     * @param pathList
     */
    private void findParentPath(Long catelogId, ArrayList<Long> pathList) {
        if (catelogId != null && catelogId != 0) {
            Category category = this.getById(catelogId);
            findParentPath(category.getParentCid(), pathList);
            pathList.add(catelogId);
        }
    }

    @Override
    public Category getCategoryById(Long catId) {
        Assert.notNull(catId);
        Optional<Category> category = Optional.ofNullable(this.getById(catId));
        if (!category.isPresent()) {
            throw new BusinessException(ErrorCode.USER_RESOURCE_EXCEPTION);
        }
        return category.get();
    }

    /**
     * 设置分类的子分类
     *
     * @param root         -
     * @param categoryDtos
     * @return List<CategoryDTO>
     */
    private List<CategoryDTO> setChildCategory(CategoryDTO root, List<CategoryDTO> categoryDtos) {
        return categoryDtos.stream()
                .filter(categoryDTO -> categoryDTO.getParentCid().equals(root.getCatId()))
                .sorted((o1, o2) -> (o1.getSort() == null ? 0 : o1.getSort()) - (o2.getSort() == null ? 0 : o2.getSort()))
                .map(categoryDTO -> {
                    categoryDTO.setChild(setChildCategory(categoryDTO, categoryDtos));
                    return categoryDTO;
                }).collect(Collectors.toList());

    }
}
