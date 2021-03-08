package com.fmjava.core.pojo.entity;

import com.fmjava.core.pojo.good.Goods;
import com.fmjava.core.pojo.good.GoodsDesc;
import com.fmjava.core.pojo.item.Item;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GoodsEntity implements Serializable {

    private Goods goods;
    private GoodsDesc goodsDesc;
    private List<Item> itemList;
}
