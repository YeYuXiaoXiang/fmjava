package com.fmjava.core.pojo.entity;

import com.fmjava.core.pojo.specification.Specification;
import com.fmjava.core.pojo.specification.SpecificationOption;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SpecEntity implements Serializable {
    private Specification spec;
    private List<SpecificationOption> specOption;


}
