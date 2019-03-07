package com.chezhibao.bigdata.datax.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/6/8.
 */
@Getter
@Setter
@ToString
public class FormItemVo {
    private String value;
    private String label;
    private String index="0";
    private List<FormItemVo> children = new ArrayList<>();

    @Override
    public int hashCode() {
        return this.value.hashCode()+this.label.hashCode()*2+this.index.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FormItemVo) {
            FormItemVo that = (FormItemVo) obj;
            if (this.value.equals(that.getValue())) {
                if (this.label.equals(that.getLabel())) {
                    return this.index.equals(that.getIndex());
                }
            }
            return false;
        }
        return false;
    }
}
