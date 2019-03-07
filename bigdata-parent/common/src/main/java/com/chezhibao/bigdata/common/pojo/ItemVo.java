package com.chezhibao.bigdata.common.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/6/27.
 */
@Setter
@ToString
public class ItemVo {
    private String label;
    private String value;
    private List<ItemVo> children;

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public List<ItemVo> getChildren() {
        if(children==null){
            children=new ArrayList<>();
        }
        return children;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ItemVo){
            ItemVo that = (ItemVo) obj;
            return this.getValue().equals(that.getValue())
                    && this.getLabel().equals(that.getLabel());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getValue().hashCode()*19+this.getLabel().hashCode()+7;
    }
}
